/**
 *    Copyright 2009-2018 the original author or authors.
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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

/**
 * @author Clinton Begin
 */
public class ReuseExecutor extends BaseExecutor {

  /**
   * 这个变量是本类的关键所在，用于存放执行器的缓冲
   * key:执行的sql
   * value:执行的执行器
   */
  private final Map<String, Statement> statementMap = new HashMap<>();

  public ReuseExecutor(Configuration configuration, Transaction transaction) {
    super(configuration, transaction);
  }

  /**
   * 这里体现缓冲的思想，程序执行最终并没有关闭执行器
   * 可以参照{@link SimpleExecutor#doUpdate(MappedStatement, Object)}
   *
   * eg:
   *    try{
   *        ...
   *    }finally{
   *        closeStatement(stmt);
   *    }
   *
   * @param ms 映射状态对象
   * @param parameter 参数对象
   * @return 更新类型
   * @throws SQLException Sql问题
   */
  @Override
  public int doUpdate(MappedStatement ms, Object parameter) throws SQLException {
    Configuration configuration = ms.getConfiguration();
    StatementHandler handler = configuration.newStatementHandler(this, ms, parameter, RowBounds.DEFAULT, null, null);
    Statement stmt = prepareStatement(handler, ms.getStatementLog());
    return handler.update(stmt);
  }

  @Override
  public <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
    Configuration configuration = ms.getConfiguration();
    StatementHandler handler = configuration.newStatementHandler(wrapper, ms, parameter, rowBounds, resultHandler, boundSql);
    Statement stmt = prepareStatement(handler, ms.getStatementLog());
    return handler.query(stmt, resultHandler);
  }

  @Override
  protected <E> Cursor<E> doQueryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds, BoundSql boundSql) throws SQLException {
    Configuration configuration = ms.getConfiguration();
    StatementHandler handler = configuration.newStatementHandler(wrapper, ms, parameter, rowBounds, null, boundSql);
    Statement stmt = prepareStatement(handler, ms.getStatementLog());
    return handler.queryCursor(stmt);
  }

  /**
   * @param isRollback
   *          这里对于ReuseExecutor和SimpleExecutor没有作用
   *          只是在BatchExecutor起作用
   * @return 批处理结果
   */
  @Override
  public List<BatchResult> doFlushStatements(boolean isRollback) {
    //关闭缓冲中的所有执行器
    for (Statement stmt : statementMap.values()) {
      closeStatement(stmt);
    }
    //清空缓冲
    statementMap.clear();
    //返回空的批处理集合
    return Collections.emptyList();
  }

  /**
   * 准备执行器，通过{@link #hasStatementFor(String)}判断是否有缓存，
   * 如果有则继续使用缓冲，负责执行过程和SimpleExecutor的此方法一致。
   * @param handler 状态处理器
   * @param statementLog 状态日志
   * @return 通过StatementHandler创建的执行器
   * @throws SQLException Sql异常
   */
  private Statement prepareStatement(StatementHandler handler, Log statementLog) throws SQLException {
    Statement stmt;
    BoundSql boundSql = handler.getBoundSql();
    String sql = boundSql.getSql();

    //是否有缓存
    if (hasStatementFor(sql)) {
      stmt = getStatement(sql);
      //设置事务的超时时间
      applyTransactionTimeout(stmt);
    } else {
      //如果么有直接创建
      Connection connection = getConnection(statementLog);
      stmt = handler.prepare(connection, transaction.getTimeout());
      //将sql和执行器保存到statementMap
      putStatement(sql, stmt);
    }
    //处理执行器的参数化
    handler.parameterize(stmt);
    return stmt;
  }

  private boolean hasStatementFor(String sql) {
    try {
      return statementMap.keySet().contains(sql) && !statementMap.get(sql).getConnection().isClosed();
    } catch (SQLException e) {
      return false;
    }
  }

  private Statement getStatement(String s) {
    return statementMap.get(s);
  }

  private void putStatement(String sql, Statement stmt) {
    statementMap.put(sql, stmt);
  }

}
