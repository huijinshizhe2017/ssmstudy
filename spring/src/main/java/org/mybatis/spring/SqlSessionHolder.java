/**
 * Copyright 2010-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.spring;

import static org.springframework.util.Assert.notNull;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.transaction.support.ResourceHolderSupport;

/**
 * Used to keep current {@code SqlSession} in {@code TransactionSynchronizationManager}. The {@code SqlSessionFactory}
 * that created that {@code SqlSession} is used as a key. {@code ExecutorType} is also kept to be able to check if the
 * user is trying to change it during a TX (that is not allowed) and throw a Exception in that case.
 * 用于将当前的{@code SqlSession}保留在{@code TransactionSynchronizationManager}中。
 * 创建该{@code SqlSession}的{@code SqlSessionFactory}用作键。
 * {@code ExecutorType}还可以检查用户是否在TX期间尝试更改它（不允许），并在这种情况下引发Exception。
 * @author Hunter Presnall
 * @author Eduardo Macarron
 */
public final class SqlSessionHolder extends ResourceHolderSupport {

  private final SqlSession sqlSession;

  private final ExecutorType executorType;

  /**
   * 异常转换
   */
  private final PersistenceExceptionTranslator exceptionTranslator;

  /**
   * Creates a new holder instance.
   *
   * @param sqlSession
   *          the {@code SqlSession} has to be hold.
   * @param executorType
   *          the {@code ExecutorType} has to be hold.
   * @param exceptionTranslator
   *          the {@code PersistenceExceptionTranslator} has to be hold.
   */
  public SqlSessionHolder(SqlSession sqlSession, ExecutorType executorType,
      PersistenceExceptionTranslator exceptionTranslator) {

    notNull(sqlSession, "SqlSession must not be null");
    notNull(executorType, "ExecutorType must not be null");

    this.sqlSession = sqlSession;
    this.executorType = executorType;
    this.exceptionTranslator = exceptionTranslator;
  }

  public SqlSession getSqlSession() {
    return sqlSession;
  }

  public ExecutorType getExecutorType() {
    return executorType;
  }

  public PersistenceExceptionTranslator getPersistenceExceptionTranslator() {
    return exceptionTranslator;
  }

}
