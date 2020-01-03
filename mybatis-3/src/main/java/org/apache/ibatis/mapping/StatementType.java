/**
 *    Copyright 2009-2015 the original author or authors.
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
package org.apache.ibatis.mapping;

/**
 * @author Clinton Begin
 * 执行器的类型
 * Statement继承自Wrapper、PreparedStatement继承自Statement、CallableStatement继承自PreparedStatement。
 */
public enum StatementType {

  /**
   *Statement接口提供了执行语句和获取结果的基本方法；
   * {@link java.sql.Statement}
   */
  STATEMENT,

  /**
   * 接口添加了处理 IN 参数的方法；
   *{@link java.sql.PreparedStatement}
   */
  PREPARED,

  /**
   * 接口添加了处理 OUT 参数的方法。
   *{@link java.sql.CallableStatement}
   */
  CALLABLE
}
