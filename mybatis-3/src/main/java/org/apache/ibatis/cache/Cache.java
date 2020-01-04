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
package org.apache.ibatis.cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * SPI for cache providers.
 * 用于缓存提供程序的SPI。
 * <p>
 * One instance of cache will be created for each namespace.
 * 为每个名称空间创建一个缓存实例。
 * <p>
 * The cache implementation must have a constructor that receives the cache id as an String parameter.
 * 缓存实现必须具有一个接收缓存ID作为String参数的构造函数。
 * <p>
 * MyBatis will pass the namespace as id to the constructor.
 * MyBatis会将名称空间作为ID传递给构造函数。
 *
 * <pre>
 * public MyCache(final String id) {
 *  if (id == null) {
 *    throw new IllegalArgumentException("Cache instances require an ID");
 *  }
 *  this.id = id;
 *  initialize();
 * }
 * </pre>
 *
 * @author Clinton Begin
 */

public interface Cache {

  /**
   * @return The identifier of this cache
   */
  String getId();

  /**
   * @param key Can be any object but usually it is a {@link CacheKey}
   * @param value The result of a select.
   */
  void putObject(Object key, Object value);

  /**
   * @param key The key
   * @return The object stored in the cache.
   */
  Object getObject(Object key);

  /**
   * As of 3.3.0 this method is only called during a rollback
   * for any previous value that was missing in the cache.
   * This lets any blocking cache to release the lock that
   * may have previously put on the key.
   * A blocking cache puts a lock when a value is null
   * and releases it when the value is back again.
   * This way other threads will wait for the value to be
   * available instead of hitting the database.
   * 从3.3.0版本开始，仅在回滚期间针对缓存中丢失的任何先前值调用此方法。
   * 这样，任何阻塞式高速缓存都可以释放先前可能已放置在密钥上的锁。
   * 阻塞高速缓存在值为null时放置锁，并在值再次返回时释放锁。这样，其他线程将等待该值可用，
   * 而不是访问数据库。
   *
   * @param key The key
   * @return Not used
   */
  Object removeObject(Object key);

  /**
   * Clears this cache instance.
   */
  void clear();

  /**
   * Optional. This method is not called by the core.可选的。内核不调用此方法。
   *
   * @return The number of elements stored in the cache (not its capacity).
   */
  int getSize();

  /**
   * Optional. As of 3.2.6 this method is no longer called by the core.
   * 可选的。从3.2.6版本开始，内核不再调用此方法。
   * <p>
   * Any locking needed by the cache must be provided internally by the cache provider.
   * 缓存所需的任何锁定都必须​​由缓存提供程序内部提供。
   * @return A ReadWriteLock
   */
  default ReadWriteLock getReadWriteLock() {
    return null;
  }

}
