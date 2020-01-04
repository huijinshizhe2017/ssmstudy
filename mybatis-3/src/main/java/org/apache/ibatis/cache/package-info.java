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
/**
 * Base package for caching stuff
 * 空cache元素定义会生成一个采用最近最少使用算法最多只能存储1024个元素的缓存，而且是可读写的缓存，
 * 即该缓存是全局共享的，任何一个线程在拿到缓存结果后对数据的修改都将影响其它线程获取的缓存结果，
 * 因为它们是共享的，同一个对象。
 *
 * cache元素可指定如下属性，每种属性的指定都是针对都是针对底层Cache的一种装饰，采用的是装饰器的模式。
 *
 * blocking：默认为false，当指定为true时将采用BlockingCache进行封装，blocking，阻塞的意思，
 * 使用BlockingCache会在查询缓存时锁住对应的Key，如果缓存命中了则会释放对应的锁，
 * 否则会在查询数据库以后再释放锁，这样可以阻止并发情况下多个线程同时查询数据，详情可参考BlockingCache的源码。
 * 简单理解，也就是设置true时，在进行增删改之后的并发查询，只会有一条去数据库查询，而不会并发
 *
 * eviction：eviction，驱逐的意思。也就是元素驱逐算法，默认是LRU，对应的就是LruCache，
 * 其默认只保存1024个Key，超出时按照最近最少使用算法进行驱逐，详情请参考LruCache的源码。
 * 如果想使用自己的算法，则可以将该值指定为自己的驱逐算法实现类，只需要自己的类实现Mybatis的Cache接口即可。
 * 除了LRU以外，系统还提供了FIFO（先进先出，对应FifoCache）、SOFT（采用软引用存储Value，便于垃圾回收，
 * 对应SoftCache）和WEAK（采用弱引用存储Value，便于垃圾回收，对应WeakCache）这三种策略。
 * 这里，根据个人需求选择了，没什么要求的话，默认的LRU即可
 *
 * flushInterval：清空缓存的时间间隔，单位是毫秒，默认是不会清空的。
 * 当指定了该值时会再用ScheduleCache包装一次，
 * 其会在每次对缓存进行操作时判断距离最近一次清空缓存的时间是否超过了flushInterval指定的时间，
 * 如果超出了，则清空当前的缓存，详情可参考ScheduleCache的实现。
 *
 * readOnly：是否只读
 * 默认为false。当指定为false时，底层会用SerializedCache包装一次，其会在写缓存的时候将缓存对象进行序列化，
 * 然后在读缓存的时候进行反序列化，这样每次读到的都将是一个新的对象，即使你更改了读取到的结果，
 * 也不会影响原来缓存的对象，即非只读，你每次拿到这个缓存结果都可以进行修改，而不会影响原来的缓存结果；
 * 当指定为true时那就是每次获取的都是同一个引用，对其修改会影响后续的缓存数据获取，
 * 这种情况下是不建议对获取到的缓存结果进行更改，意为只读(不建议设置为true)。
 * 这是Mybatis二级缓存读写和只读的定义，可能与我们通常情况下的只读和读写意义有点不同。
 * 每次都进行序列化和反序列化无疑会影响性能，但是这样的缓存结果更安全，不会被随意更改，
 * 具体可根据实际情况进行选择。详情可参考SerializedCache的源码。
 *
 * size：用来指定缓存中最多保存的Key的数量。其是针对LruCache而言的，LruCache默认只存储最多1024个Key，
 * 可通过该属性来改变默认值，当然，如果你通过eviction指定了自己的驱逐算法，
 * 同时自己的实现里面也有setSize方法，那么也可以通过cache的size属性给自定义的驱逐算法里面的size赋值。
 *
 * type：type属性用来指定当前底层缓存实现类，默认是PerpetualCache，
 * 如果我们想使用自定义的Cache，则可以通过该属性来指定，对应的值是我们自定义的Cache的全路径名称。
 *
 *
 * 二级缓存的使用原则
 *
 * 只能在一个命名空间下使用二级缓存
 * 由于二级缓存中的数据是基于namespace的，即不同namespace中的数据互不干扰。
 * 在多个namespace中若均存在对同一个表的操作，那么这多个namespace中的数据可能就会出现不一致现象。
 *
 * 在单表上使用二级缓存
 * 如果一个表与其它表有关联关系，那么久非常有可能存在多个namespace对同一数据的操作。
 * 而不同namespace中的数据互补干扰，所以就有可能出现多个namespace中的数据不一致现象。
 *
 * 查询多于修改时使用二级缓存
 * 在查询操作远远多于增删改操作的情况下可以使用二级缓存。因为任何增删改操作都将刷新二级缓存，
 * 对二级缓存的频繁刷新将降低系统性能。
 */
package org.apache.ibatis.cache;
