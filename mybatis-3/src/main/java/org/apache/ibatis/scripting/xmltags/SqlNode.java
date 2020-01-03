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
package org.apache.ibatis.scripting.xmltags;

/**
 * @author Clinton Begin
 * Sql的Node节点，主要实现包括
 *     ChooseSqlNode|ForEachSqlNode|IfSqlNode|MixedSqlNode|SetSqlNode|WhereSqlNode
 *     这里面的实现主要针对MapperXml文件中的Sql中的不同的标签。
 *     其中，SetSqlNode和WhereSqlNode实现的功能一致，所以采用共有的父类TrimSqlNode.
 *     MixedSqlNode为混合SqlNode,通过contents集合存放多个SqlNode.
 */
public interface SqlNode {
  boolean apply(DynamicContext context);
}
