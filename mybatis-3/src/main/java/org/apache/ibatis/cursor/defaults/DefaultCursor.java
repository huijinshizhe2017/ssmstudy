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
package org.apache.ibatis.cursor.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetWrapper;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * This is the default implementation of a MyBatis Cursor.
 * 这是MyBatis光标的默认实现。
 * This implementation is not thread safe.
 * 此实现不是线程安全的。
 *
 * @author Guillaume Darmont / guillaume@dropinocean.com
 */
public class DefaultCursor<T> implements Cursor<T> {

  /**
   * ResultSetHandler stuff
   */
  private final DefaultResultSetHandler resultSetHandler;
  private final ResultMap resultMap;
  private final ResultSetWrapper rsw;
  private final RowBounds rowBounds;
  protected final ObjectWrapperResultHandler<T> objectWrapperResultHandler = new ObjectWrapperResultHandler<>();

  private final CursorIterator cursorIterator = new CursorIterator();
  private boolean iteratorRetrieved;

  private CursorStatus status = CursorStatus.CREATED;
  private int indexWithRowBound = -1;

  /**
   * 游标的状态
   */
  private enum CursorStatus {

    /**
     * A freshly created cursor, database ResultSet consuming has not started.
     * 刚刚创建的游标，数据库的结果集还没有被消费
     */
    CREATED,
    /**
     * A cursor currently in use, database ResultSet consuming has started.
     * 游标已经被使用，同时结果集数据已经开始被使用了
     */
    OPEN,
    /**
     * A closed cursor, not fully consumed.
     * 游标被关闭，可能并没有全部被消费掉
     */
    CLOSED,
    /**
     * A fully consumed cursor, a consumed cursor is always closed.
     * 表示游标已经遍历所有数据集，这个消费完游标一直将会被关闭掉
     */
    CONSUMED
  }

  /**
   * 创建一个默认的游标
   * @param resultSetHandler 默认结果集处理类
   * @param resultMap 结果映射map
   * @param rsw 结果集包装类
   * @param rowBounds 分页对象
   */
  public DefaultCursor(DefaultResultSetHandler resultSetHandler, ResultMap resultMap, ResultSetWrapper rsw, RowBounds rowBounds) {
    this.resultSetHandler = resultSetHandler;
    this.resultMap = resultMap;
    this.rsw = rsw;
    this.rowBounds = rowBounds;
  }

  /**
   * 判断是否被打开
   * @return
   */
  @Override
  public boolean isOpen() {
    return status == CursorStatus.OPEN;
  }

  /**
   * 判断游标是否被消费掉
   * @return
   */
  @Override
  public boolean isConsumed() {
    return status == CursorStatus.CONSUMED;
  }

  /**
   * 获取当前的索引
   * 分页偏移量+游标迭代器索引位置
   * @return 当前索引位置
   */
  @Override
  public int getCurrentIndex() {
    return rowBounds.getOffset() + cursorIterator.iteratorIndex;
  }

  /**
   * 进行获取迭代器
   * @return 迭代器
   */
  @Override
  public Iterator<T> iterator() {
    //判断是否已经获取过一次迭代器了
    //判断迭代器是否关闭
    if (iteratorRetrieved) {
      throw new IllegalStateException("Cannot open more than one iterator on a Cursor");
    }
    if (isClosed()) {
      throw new IllegalStateException("A Cursor is already closed.");
    }
    iteratorRetrieved = true;
    return cursorIterator;
  }

  @Override
  public void close() {
    //如果游标已经关闭直接返回
    //获取结果集，如果结果集不为空，直接关闭，忽略关闭后异常
    //修改游标的状态为关闭
    if (isClosed()) {
      return;
    }

    ResultSet rs = rsw.getResultSet();
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      // ignore
    } finally {
      status = CursorStatus.CLOSED;
    }
  }

  protected T fetchNextUsingRowBound() {
    // 过滤到偏移量的位置
    T result = fetchNextObjectFromDatabase();
    while (objectWrapperResultHandler.fetched && indexWithRowBound < rowBounds.getOffset()) {
      result = fetchNextObjectFromDatabase();
    }
    return result;
  }

  /**
   * 从数据库中获取下一个对象
   * @return
   */
  protected T fetchNextObjectFromDatabase() {
    //判断游标是否已经关闭，已关闭返回null
    if (isClosed()) {
      return null;
    }

    // 设置当前状态是游标打开状态
    // 如果结果集包装类不是已经关闭
    // 把结果放入objectWrapperResultHandler对象的result中
    try {
      objectWrapperResultHandler.fetched = false;
      status = CursorStatus.OPEN;
      if (!rsw.getResultSet().isClosed()) {
        resultSetHandler.handleRowValues(rsw, resultMap, objectWrapperResultHandler, RowBounds.DEFAULT, null);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    // 获取对象包装处理的结果
    // 如果结果不为空结果， 索引++
    T next = objectWrapperResultHandler.result;
    if (objectWrapperResultHandler.fetched) {
      indexWithRowBound++;
    }
    // No more object or limit reached
    //next为null或者读取条数等于偏移量+限制条数
    if (!objectWrapperResultHandler.fetched || getReadItemsCount() == rowBounds.getOffset() + rowBounds.getLimit()) {
      close();
      status = CursorStatus.CONSUMED;
    }
    //把结果设置为null
    objectWrapperResultHandler.result = null;

    return next;
  }

  /**
   * 判断是否关闭
   * 游标本身处于关闭状态，或者已经取出结果的所有元素
   * @return
   */
  private boolean isClosed() {
    return status == CursorStatus.CLOSED || status == CursorStatus.CONSUMED;
  }

  /**
   * 下一个读取索引位置
   * @return
   */
  private int getReadItemsCount() {
    return indexWithRowBound + 1;
  }

  /**
   * 对象结果集包装类
   * @param <T>
   */
  protected static class ObjectWrapperResultHandler<T> implements ResultHandler<T> {

    /**
     * 结果集
     */
    protected T result;
    protected boolean fetched;

    /**
     * 上下文中获取结果集
     * @param context
     */
    @Override
    public void handleResult(ResultContext<? extends T> context) {
      this.result = context.getResultObject();
      context.stop();
      fetched = true;
    }
  }

  /**
   * 游标的迭代器
   */
  protected class CursorIterator implements Iterator<T> {

    /**
     * Holder for the next object to be returned.
     * 下一个要返回的对象的持有人。
     */
    T object;

    /**
     * Index of objects returned using next(), and as such, visible to users.
     * 使用next（）返回的对象的索引，因此对用户可见。
     */
    int iteratorIndex = -1;

    @Override
    public boolean hasNext() {
      if (!objectWrapperResultHandler.fetched) {
        object = fetchNextUsingRowBound();
      }
      return objectWrapperResultHandler.fetched;
    }

    /**
     * 下一个元素
     * @return
     */
    @Override
    public T next() {
      // Fill next with object fetched from hasNext()
      T next = object;

      if (!objectWrapperResultHandler.fetched) {
        next = fetchNextUsingRowBound();
      }

      /**
       *
       */
      if (objectWrapperResultHandler.fetched) {
        objectWrapperResultHandler.fetched = false;
        object = null;
        iteratorIndex++;
        return next;
      }
      throw new NoSuchElementException();
    }

    /**
     * 不支持移除减肥
     */
    @Override
    public void remove() {
      throw new UnsupportedOperationException("Cannot remove element from Cursor");
    }
  }
}
