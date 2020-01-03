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
package org.apache.ibatis.session;

import java.sql.Connection;

/**
 * Creates an {@link SqlSession} out of a connection or a DataSource
 * 创建一个SqlSession的连接或者数据源
 * 在这个接口主要包含两类型的方法，一个是打开Session,一个是获取配置文件。
 * 实现这个接口的主要有两个类，包括{@link SqlSessionManager} 和{@link org.apache.ibatis.session.defaults.DefaultSqlSessionFactory}
 * 按照官方的解释，{@link SqlSessionManager}已经被废弃
 * @author Clinton Begin
 */
public interface SqlSessionFactory {


  SqlSession openSession();

  /**
   * 设置是否自动提交来构建SqlSession
   * @param autoCommit
   * @return
   */
  SqlSession openSession(boolean autoCommit);

  SqlSession openSession(Connection connection);

  SqlSession openSession(TransactionIsolationLevel level);

  SqlSession openSession(ExecutorType execType);

  SqlSession openSession(ExecutorType execType, boolean autoCommit);

  /**
   * 通过传入执行类型和设置事务的隔离级别打开Session对象
   * @param execType 执行器类型(SIMPLE|REUSE|BATCH)
   * @param level 事务隔离级别
   * @return
   */
  SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);

  SqlSession openSession(ExecutorType execType, Connection connection);

  /**
   * 获取配置对象
   * @return
   */
  Configuration getConfiguration();

}
