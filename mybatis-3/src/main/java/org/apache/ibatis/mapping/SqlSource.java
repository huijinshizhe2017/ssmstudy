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
package org.apache.ibatis.mapping;

/**
 * Represents the content of a mapped statement read from an XML file or an annotation.
 * It creates the SQL that will be passed to the database out of the input parameter received from the user.
 * 表示从XML文件或注解中读取的映射语句的内容。
 * 它创建将从用户收到的输入参数之外的SQL传递给数据库。
 * 他的几个实现类:DynamicSqlSource|ProviderSqlSource|RawSqlSource|StaticSqlSource|VelocitySqlSource
 * DynamicSqlSource:动态SQL表示这个SQL节点中含有${}或是其他动态的标签(比如，if，trim，foreach，choose，bind节点等),
 *                  需要在运行时根据传入的条件才能确定SQL，因此对于动态SQL的MappedStatement的解析过程应该是在运行时。
 *                  {@link org.apache.ibatis.scripting.xmltags.DynamicSqlSource}
 * ProviderSqlSource:
 *                  {@link org.apache.ibatis.builder.annotation.ProviderSqlSource}
 * RawSqlSource:静态SQL是不含以上这个节点的SQL，能直接解析得到含有占位符形式的SQL语句，而不需要根据传入的条件确定SQL，因此可以在加载时就完成解析。
 *              所在在执行效率上要高于动态SQL。
 *              {@link org.apache.ibatis.scripting.defaults.RawSqlSource}
 * StaticSqlSource:
 *               {@link org.apache.ibatis.builder.StaticSqlSource}
 * @author Clinton Begin
 */
public interface SqlSource {

  BoundSql getBoundSql(Object parameterObject);

}
