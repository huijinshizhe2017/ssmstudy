/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.io;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

/**
 * <p>ResolverUtil is used to locate classes that are available in the/a class path and meet
 * arbitrary conditions. The two most common conditions are that a class implements/extends
 * another class, or that is it annotated with a specific annotation. However, through the use
 * of the {@link Test} class it is possible to search using arbitrary conditions.</p>
 *
 * ResolverUtil用于查找类路径中可用并满足任意条件的类。最常见的两个条件是，
 * 一个类实现/扩展了另一个类，或者用特定的注释对其进行了注释。
 * 但是，通过使用{@link Test}类，可以使用任意条件进行搜索。
 *
 * <p>A ClassLoader is used to locate all locations (directories and jar files) in the class
 * path that contain classes within certain packages, and then to load those classes and
 * check them. By default the ClassLoader returned by
 * {@code Thread.currentThread().getContextClassLoader()} is used, but this can be overridden
 * by calling {@link #setClassLoader(ClassLoader)} prior to invoking any of the {@code find()}
 * methods.</p>
 *
 * ClassLoader用于在类路径中定位包含某些包中的类的所有位置（目录和jar文件），
 * 然后加载这些类并进行检查。默认情况下，使用{@code Thread.currentThread().getContextClassLoader()}
 * 返回的ClassLoader，但是可以通过在调用任何{@code find()}之前调用{@link #setClassLoader(ClassLoader)}来覆盖此方法。 } 方法
 *
 * <p>General searches are initiated by calling the
 * {@link #find(org.apache.ibatis.io.ResolverUtil.Test, String)} ()} method and supplying
 * a package name and a Test instance. This will cause the named package <b>and all sub-packages</b>
 * to be scanned for classes that meet the test. There are also utility methods for the common
 * use cases of scanning multiple packages for extensions of particular classes, or classes
 * annotated with a specific annotation.</p>
 * 通过调用{@link #find(org.apache.ibatis.io.ResolverUtil.Test,String)}()}方法并提供程序包名称
 * 和Test实例来启动常规搜索。这将导致扫描命名的软件包<b>和所有子软件包中符合测试要求的类。
 * 对于扫描多个软件包以查找特定类的扩展或使用特定注释进行注释的类的常见用例，还有实用程序方法。
 *
 * <p>The standard usage pattern for the ResolverUtil class is as follows:</p>
 *
 * <pre>
 * ResolverUtil&lt;ActionBean&gt; resolver = new ResolverUtil&lt;ActionBean&gt;();
 * resolver.findImplementation(ActionBean.class, pkg1, pkg2);
 * resolver.find(new CustomTest(), pkg1);
 * resolver.find(new CustomTest(), pkg2);
 * Collection&lt;ActionBean&gt; beans = resolver.getClasses();
 * </pre>
 *
 * ResolverUtil类的标准用法模式如下:
 * ResolverUtil<ActionBean> resolver = new ResolverUtil<ActionBean>();
 * resolver.findImplementation(ActionBean.class, pkg1, pkg2);
 * resolver.find(new CustomTest(), pkg1);
 * resolver.find(new CustomTest(), pkg2);
 * Collection<ActionBean> beans = resolver.getClasses();
 *
 * @author Tim Fennell
 */
public class ResolverUtil<T> {
  /**
   * An instance of Log to use for logging in this class.
   * 用于登录此类的Log实例。
   */
  private static final Log log = LogFactory.getLog(ResolverUtil.class);

  /**
   * A simple interface that specifies how to test classes to determine if they
   * are to be included in the results produced by the ResolverUtil.
   * 一个简单的接口，指定如何测试类以确定是否将其包含在ResolverUtil生成的结果中。
   */
  public interface Test {
    /**
     * 将与候选课程一起反复调用。如果要在结果中包含类，则必须返回True，否则返回false。
     * Will be called repeatedly with candidate classes. Must return True if a class
     * is to be included in the results, false otherwise.
     */
    boolean matches(Class<?> type);
  }

  /**
   * A Test that checks to see if each class is assignable to the provided class. Note
   * that this test will match the parent type itself if it is presented for matching.
   * 一个测试，检查每个类是否可分配给提供的类。请注意，如果此测试用于匹配，则它将与父类型本身匹配。
   */
  public static class IsA implements Test {
    private Class<?> parent;

    /**
     * Constructs an IsA test using the supplied Class as the parent class/interface.
     * 使用提供的类作为父类/接口构造一个IsA测试。
     */
    public IsA(Class<?> parentType) {
      this.parent = parentType;
    }

    /**
     * Returns true if type is assignable to the parent type supplied in the constructor.
     * 如果type可分配给构造函数中提供的父类型，则返回true。
     */
    @Override
    public boolean matches(Class<?> type) {
      return type != null && parent.isAssignableFrom(type);
    }

    @Override
    public String toString() {
      return "is assignable to " + parent.getSimpleName();
    }
  }

  /**
   * A Test that checks to see if each class is annotated with a specific annotation. If it
   * is, then the test returns true, otherwise false.
   */
  public static class AnnotatedWith implements Test {
    private Class<? extends Annotation> annotation;

    /** Constructs an AnnotatedWith test for the specified annotation type. */
    public AnnotatedWith(Class<? extends Annotation> annotation) {
      this.annotation = annotation;
    }

    /** Returns true if the type is annotated with the class provided to the constructor. */
    @Override
    public boolean matches(Class<?> type) {
      return type != null && type.isAnnotationPresent(annotation);
    }

    @Override
    public String toString() {
      return "annotated with @" + annotation.getSimpleName();
    }
  }

  /** The set of matches being accumulated. */
  private Set<Class<? extends T>> matches = new HashSet<>();

  /**
   * The ClassLoader to use when looking for classes. If null then the ClassLoader returned
   * by Thread.currentThread().getContextClassLoader() will be used.
   */
  private ClassLoader classloader;

  /**
   * Provides access to the classes discovered so far. If no calls have been made to
   * any of the {@code find()} methods, this set will be empty.
   *
   * @return the set of classes that have been discovered.
   */
  public Set<Class<? extends T>> getClasses() {
    return matches;
  }

  /**
   * Returns the classloader that will be used for scanning for classes. If no explicit
   * ClassLoader has been set by the calling, the context class loader will be used.
   *
   * @return the ClassLoader that will be used to scan for classes
   */
  public ClassLoader getClassLoader() {
    return classloader == null ? Thread.currentThread().getContextClassLoader() : classloader;
  }

  /**
   * Sets an explicit ClassLoader that should be used when scanning for classes. If none
   * is set then the context classloader will be used.
   *
   * @param classloader a ClassLoader to use when scanning for classes
   */
  public void setClassLoader(ClassLoader classloader) {
    this.classloader = classloader;
  }

  /**
   * 尝试发现可分配给所提供类型的类。在提供接口的情况下，此方法将收集实现。
   * 对于非接口类，将收集子类。可以通过调用{@link #getClasses()}来访问累积的类。
   * Attempts to discover classes that are assignable to the type provided. In the case
   * that an interface is provided this method will collect implementations. In the case
   * of a non-interface class, subclasses will be collected.  Accumulated classes can be
   * accessed by calling {@link #getClasses()}.
   *
   * @param parent the class of interface to find subclasses or implementations of
   * @param packageNames one or more package names to scan (including subpackages) for classes
   */
  public ResolverUtil<T> findImplementations(Class<?> parent, String... packageNames) {
    if (packageNames == null) {
      return this;
    }

    Test test = new IsA(parent);
    for (String pkg : packageNames) {
      find(test, pkg);
    }

    return this;
  }

  /**
   * Attempts to discover classes that are annotated with the annotation. Accumulated
   * classes can be accessed by calling {@link #getClasses()}.
   * 尝试发现带有注释的类。可以通过调用{@link #getClasses()}来访问累积的类。
   *
   * @param annotation the annotation that should be present on matching classes
   * @param packageNames one or more package names to scan (including subpackages) for classes
   */
  public ResolverUtil<T> findAnnotated(Class<? extends Annotation> annotation, String... packageNames) {
    if (packageNames == null) {
      return this;
    }

    Test test = new AnnotatedWith(annotation);
    for (String pkg : packageNames) {
      find(test, pkg);
    }

    return this;
  }

  /**
   * Scans for classes starting at the package provided and descending into subpackages.
   * Each class is offered up to the Test as it is discovered, and if the Test returns
   * true the class is retained.  Accumulated classes can be fetched by calling
   * {@link #getClasses()}.
   * 扫描从提供的包开始到子包的类。发现时，将为每个类提供测试，如果测试返回true，则保留该类。
   * 可以通过调用{@link #getClasses()}来获取累积的类。
   *
   * @param test an instance of {@link Test} that will be used to filter classes
   * @param packageName the name of the package from which to start scanning for
   *        classes, e.g. {@code net.sourceforge.stripes}
   */
  public ResolverUtil<T> find(Test test, String packageName) {
    String path = getPackagePath(packageName);

    try {
      List<String> children = VFS.getInstance().list(path);
      for (String child : children) {
        if (child.endsWith(".class")) {
          addIfMatching(test, child);
        }
      }
    } catch (IOException ioe) {
      log.error("Could not read package: " + packageName, ioe);
    }

    return this;
  }

  /**
   * Converts a Java package name to a path that can be looked up with a call to
   * {@link ClassLoader#getResources(String)}.
   *
   * @param packageName The Java package name to convert to a path
   */
  protected String getPackagePath(String packageName) {
    return packageName == null ? null : packageName.replace('.', '/');
  }

  /**
   * Add the class designated by the fully qualified class name provided to the set of
   * resolved classes if and only if it is approved by the Test supplied.
   *
   * @param test the test used to determine if the class matches
   * @param fqn the fully qualified name of a class
   */
  @SuppressWarnings("unchecked")
  protected void addIfMatching(Test test, String fqn) {
    try {
      String externalName = fqn.substring(0, fqn.indexOf('.')).replace('/', '.');
      ClassLoader loader = getClassLoader();
      if (log.isDebugEnabled()) {
        log.debug("Checking to see if class " + externalName + " matches criteria [" + test + "]");
      }

      Class<?> type = loader.loadClass(externalName);
      if (test.matches(type)) {
        matches.add((Class<T>) type);
      }
    } catch (Throwable t) {
      log.warn("Could not examine class '" + fqn + "'" + " due to a " +
          t.getClass().getName() + " with message: " + t.getMessage());
    }
  }
}
