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

package org.apache.ibatis.builder.annotation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.builder.BuilderException;

/**
 * The interface that resolve an SQL provider method via an SQL provider class.
 * 通过SQL提供程序类解析SQL提供程序方法的接口。
 * <p> This interface need to implements at an SQL provider class and
 * it need to define the default constructor for creating a new instance.
 * 该接口需要在SQL提供程序类上实现，并且需要定义用于创建新实例的默认构造函数。
 * @since 3.5.1
 * @author Kazuki Shimizu
 */
public interface ProviderMethodResolver {

  /**
   * Resolve an SQL provider method.
   * 解决SQL提供程序方法。
   * <p> The default implementation return a method that matches following conditions.
   * <ul>
   *   <li>Method name matches with mapper method</li>
   *   <li>Return type matches the {@link CharSequence}({@link String}, {@link StringBuilder}, etc...)</li>
   * </ul>
   *
   * 默认实现返回一个符合以下条件的方法：
   * 1.方法名称必须和Mapper名称一致
   * 2.返回类型匹配{@link CharSequence}（{@ link String}，{@ link StringBuilder}等。
   *
   * If matched method is zero or multiple, it throws a {@link BuilderException}.
   *  如果匹配的方法为零或多个，则将引发{@link BuilderException}。
   * @param context a context for SQL provider
   * @return an SQL provider method
   * @throws BuilderException Throws when cannot resolve a target method
   */
  default Method resolveMethod(ProviderContext context) {
    List<Method> sameNameMethods = Arrays.stream(getClass().getMethods())
        .filter(m -> m.getName().equals(context.getMapperMethod().getName()))
        .collect(Collectors.toList());

    //不存在相同的方法名称则抛出异常
    if (sameNameMethods.isEmpty()) {
      throw new BuilderException("Cannot resolve the provider method because '"
          + context.getMapperMethod().getName() + "' not found in SqlProvider '" + getClass().getName() + "'.");
    }

    //检查返回类型是否一致
    List<Method> targetMethods = sameNameMethods.stream()
        .filter(m -> CharSequence.class.isAssignableFrom(m.getReturnType()))
        .collect(Collectors.toList());

    //目标方法个数为1个，则继续执行
    if (targetMethods.size() == 1) {
      return targetMethods.get(0);
    }
    //target方法个数为空
    if (targetMethods.isEmpty()) {
      throw new BuilderException("Cannot resolve the provider method because '"
          + context.getMapperMethod().getName() + "' does not return the CharSequence or its subclass in SqlProvider '"
          + getClass().getName() + "'.");
    } else {
      throw new BuilderException("Cannot resolve the provider method because '"
          + context.getMapperMethod().getName() + "' is found multiple in SqlProvider '" + getClass().getName() + "'.");
    }
  }

}
