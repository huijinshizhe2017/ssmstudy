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
package org.apache.ibatis.executor.statement;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility for {@link java.sql.Statement}.
 * 执行器 的使用实现工具
 * @since 3.4.0
 * @author Kazuki Shimizu
 */
public class StatementUtil {

  private StatementUtil() {
    // NOP
  }

  /**
   * Apply a transaction timeout.
   * 应用事务超时。
   * <p>
   * Update a query timeout to apply a transaction timeout.
   * 更新查询超时以应用事务超时。
   * </p>
   * @param statement a target statement 目标执行器
   * @param queryTimeout a query timeout 一个查询的时间
   * @param transactionTimeout a transaction timeout 事务的超时时间
   * @throws SQLException if a database access error occurs, this method is called on a closed <code>Statement</code>
   *    如果数据库允许出现访问错误，这个方法会在关闭一个执行器的时候被调用
   */
  public static void applyTransactionTimeout(Statement statement, Integer queryTimeout, Integer transactionTimeout) throws SQLException {
    if (transactionTimeout == null) {
      return;
    }
    //主要设置执行器的查询超时时间，如果查询超时没有设置(即为null或者0、或者查询时间大于事务超时时间，则按照事务的超时时间计算
    if (queryTimeout == null || queryTimeout == 0 || transactionTimeout < queryTimeout) {
      statement.setQueryTimeout(transactionTimeout);
    }
  }

}
