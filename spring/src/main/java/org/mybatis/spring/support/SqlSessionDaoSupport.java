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
package org.mybatis.spring.support;

import static org.springframework.util.Assert.notNull;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;

/**
 * Convenient super class for MyBatis SqlSession data access objects. It gives you access to the template which can then
 * be used to execute SQL methods.
 * MyBatis SqlSession数据访问对象的便捷超类。它使您可以访问模板，然后该模板可用于执行SQL方法。
 * <p>
 * This class needs a SqlSessionTemplate or a SqlSessionFactory. If both are set the SqlSessionFactory will be ignored.
 * <p>
 *  此类需要SqlSessionTemplate或SqlSessionFactory。如果两者都设置，则SqlSessionFactory将被忽略。
 * @author Putthiphong Boonphong
 * @author Eduardo Macarron
 *
 * @see #setSqlSessionFactory
 * @see #setSqlSessionTemplate
 * @see SqlSessionTemplate
 */
public abstract class SqlSessionDaoSupport extends DaoSupport {

  private SqlSessionTemplate sqlSessionTemplate;

  /**
   * Set MyBatis SqlSessionFactory to be used by this dao. Will automatically create SqlSessionTemplate for the given
   * SqlSessionFactory.
   * 设置要由该DAO使用的MyBatis SqlSessionFactory。将自动为给定的SqlSessionFactory创建SqlSessionTemplate。
   *
   * @param sqlSessionFactory
   *          a factory of SqlSession
   */
  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    if (this.sqlSessionTemplate == null || sqlSessionFactory != this.sqlSessionTemplate.getSqlSessionFactory()) {
      this.sqlSessionTemplate = createSqlSessionTemplate(sqlSessionFactory);
    }
  }

  /**
   * Create a SqlSessionTemplate for the given SqlSessionFactory. Only invoked if populating the dao with a
   * SqlSessionFactory reference!
   * 为给定的SqlSessionFactory创建一个SqlSessionTemplate。仅在使用SqlSessionFactory引用填充DAO时调用！
   * <p>
   * Can be overridden in subclasses to provide a SqlSessionTemplate instance with different configuration, or a custom
   * SqlSessionTemplate subclass.
   * 可以在子类中重写以提供具有不同配置的SqlSessionTemplate实例或自定义SqlSessionTemplate子类。
   * 
   * @param sqlSessionFactory
   *          the MyBatis SqlSessionFactory to create a SqlSessionTemplate for
   * @return the new SqlSessionTemplate instance
   * @see #setSqlSessionFactory
   */
  @SuppressWarnings("WeakerAccess")
  protected SqlSessionTemplate createSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  /**
   * Return the MyBatis SqlSessionFactory used by this dao.
   *
   * @return a factory of SqlSession
   */
  public final SqlSessionFactory getSqlSessionFactory() {
    return (this.sqlSessionTemplate != null ? this.sqlSessionTemplate.getSqlSessionFactory() : null);
  }

  /**
   * Set the SqlSessionTemplate for this dao explicitly, as an alternative to specifying a SqlSessionFactory.
   * 显式设置此DAO的SqlSessionTemplate，作为指定SqlSessionFactory的替代方法。
   * @param sqlSessionTemplate
   *          a template of SqlSession
   * @see #setSqlSessionFactory
   */
  public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
    this.sqlSessionTemplate = sqlSessionTemplate;
  }

  /**
   * Users should use this method to get a SqlSession to call its statement methods This is SqlSession is managed by
   * spring. Users should not commit/rollback/close it because it will be automatically done.
   * 用户应该使用此方法获取一个SqlSession来调用其语句方法。这是SqlSession由spring管理的。
   * 用户不应提交/回滚/关闭它，因为它将自动完成。
   * @return Spring managed thread safe SqlSession
   */
  public SqlSession getSqlSession() {
    return this.sqlSessionTemplate;
  }

  /**
   * Return the SqlSessionTemplate for this dao, pre-initialized with the SessionFactory or set explicitly.
   * 返回此DAO的SqlSessionTemplate，并使用SessionFactory对其进行预先初始化或进行显式设置。
   * <p>
   * <b>Note: The returned SqlSessionTemplate is a shared instance.</b> You may introspect its configuration, but not
   * modify the configuration (other than from within an {@link #initDao} implementation). Consider creating a custom
   * SqlSessionTemplate instance via {@code new SqlSessionTemplate(getSqlSessionFactory())}, in which case you're
   * allowed to customize the settings on the resulting instance.
   * 注意：返回的SqlSessionTemplate是一个共享实例。</ b>您可以内省其配置，
   * 但不能修改配置（除了在{@link #initDao}实现中）。
   * 考虑通过{@code new SqlSessionTemplate（getSqlSessionFactory（））}创建自定义SqlSessionTemplate实例，
   * 在这种情况下，您可以自定义结果实例的设置。
   * @return a template of SqlSession
   */
  public SqlSessionTemplate getSqlSessionTemplate() {
    return this.sqlSessionTemplate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void checkDaoConfig() {
    notNull(this.sqlSessionTemplate, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
  }

}
