/**
 * 多参数传递方法
 * 1.顺序传参法
 * public User selectUser(String name, int deptId);
 * <select id="selectUser" resultMap="UserResultMap">
 *     select * from user
 *     where user_name = #{0} and dept_id = #{1}
 * </select>
 * 2.@Param注解传参法
 * public User selectUser(@Param("userName") String name, int @Param("deptId") deptId);
 * <select id="selectUser" resultMap="UserResultMap">
 *     select * from user
 *     where user_name = #{userName} and dept_id = #{deptId}
 * </select>
 *
 * 3.Map传参法
 * public User selectUser(Map<String, Object> params);
 *
 * <select id="selectUser" parameterType="java.util.Map" resultMap="UserResultMap">
 *     select * from user
 *     where user_name = #{userName} and dept_id = #{deptId}
 * </select>
 *
 * 4.Java Bean传参法
 * public User selectUser(User params);
 * <select id="selectUser" parameterType="com.test.User" resultMap="UserResultMap">
 *     select * from user
 *     where user_name = #{userName} and dept_id = #{deptId}
 * </select>
 */
package cn.surpass.paras.dao;
