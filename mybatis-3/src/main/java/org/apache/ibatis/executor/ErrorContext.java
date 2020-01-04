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
package org.apache.ibatis.executor;

/**
 * @author Clinton Begin
 */
public class ErrorContext {

  private static final String LINE_SEPARATOR = System.getProperty("line.separator","\n");
  private static final ThreadLocal<ErrorContext> LOCAL = new ThreadLocal<>();

  /**
   * 类似于单例模式，这里采用线程中单例模式的思想
   * ErrorContext context = LOCAL.get();
   */
  private ErrorContext stored;
  /**
   * 存储异常存在于哪个资源文件中。
   * ### The error may exist in
   */
  private String resource;

  /**
   * 存储异常是做什么操作时发生的。
   * ### The error occurred while
   */
  private String activity;

  /**
   * 存储哪个对象操作时发生异常。
   * ### The error may involve
   *
   */
  private String object;

  /**
   * 存储异常的概览信息。
   */
  private String message;

  /**
   * Sql信息
   * 这里将\r\n\t都转换成为空格,存储发生日常的 SQL 语句。
   */
  private String sql;

  /**
   * 存储详细的 Java 异常栈日志。
   */
  private Throwable cause;

  /**
   * 私有构造方法，保证线程内部的单例
   */
  private ErrorContext() {
  }

  /**
   * 在同一线程中，ErrorContext 通过该接口提供给外界一个获取其唯一实例的方式。在调用 instance() 时，首先从 LOCAL 中获取，
   * 如果获取到了 ErrorContext 实例，则直接返回该实例；若未获取到，则会调用其构造方法创建一个，并将其存入 LOCAL。
   * @return
   */
  public static ErrorContext instance() {
    //线程内部获取对象的方法，如果为null,则需要创建，并且加到当前线程中
    ErrorContext context = LOCAL.get();
    if (context == null) {
      context = new ErrorContext();
      LOCAL.set(context);
    }
    return context;
  }

  /**
   * stored 变量充当一个中介，在调用 store() 方法时将当前 ErrorContext 保存下来，在调用 recall() 方法时将该 ErrorContext 实例传递给 LOCAL。
   * @return
   */
  public ErrorContext store() {
    ErrorContext newContext = new ErrorContext();
    newContext.stored = this;
    LOCAL.set(newContext);
    return LOCAL.get();
  }

  /**
   * 这个方法的作用应该是当前方法栈结束，将stored对象保存到线程中。
   * @return
   */
  public ErrorContext recall() {
    if (stored != null) {
      LOCAL.set(stored);
      stored = null;
    }
    return LOCAL.get();
  }

  public ErrorContext resource(String resource) {
    this.resource = resource;
    return this;
  }

  public ErrorContext activity(String activity) {
    this.activity = activity;
    return this;
  }

  public ErrorContext object(String object) {
    this.object = object;
    return this;
  }

  public ErrorContext message(String message) {
    this.message = message;
    return this;
  }

  public ErrorContext sql(String sql) {
    this.sql = sql;
    return this;
  }

  public ErrorContext cause(Throwable cause) {
    this.cause = cause;
    return this;
  }

  /**
   * reset() 顾名思义，用来重置变量，为变量赋 null 值，以便 gc 的执行，并清空 LOCAL：
   * @return
   */
  public ErrorContext reset() {
    resource = null;
    activity = null;
    object = null;
    message = null;
    sql = null;
    cause = null;
    LOCAL.remove();
    return this;
  }

  @Override
  public String toString() {
    StringBuilder description = new StringBuilder();

    // message
    if (this.message != null) {
      description.append(LINE_SEPARATOR);
      description.append("### ");
      description.append(this.message);
    }

    // resource
    if (resource != null) {
      description.append(LINE_SEPARATOR);
      description.append("### The error may exist in ");
      description.append(resource);
    }

    // object
    if (object != null) {
      description.append(LINE_SEPARATOR);
      description.append("### The error may involve ");
      description.append(object);
    }

    // activity
    if (activity != null) {
      description.append(LINE_SEPARATOR);
      description.append("### The error occurred while ");
      description.append(activity);
    }

    // sql
    if (sql != null) {
      description.append(LINE_SEPARATOR);
      description.append("### SQL: ");
      description.append(sql.replace('\n', ' ').replace('\r', ' ').replace('\t', ' ').trim());
    }

    // cause
    if (cause != null) {
      description.append(LINE_SEPARATOR);
      description.append("### Cause: ");
      description.append(cause.toString());
    }

    return description.toString();
  }

}
