package cn.surpass.skill.dao;

import cn.surpass.domain.User;
import cn.surpass.domain.UserTemp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {

    /**
     * 通过用户id查找用户信息
     *
     * @param id
     */
    User queryUserById(String id);

    /**
     * 通过住址和性别查询用户
     * @param address 地址
     * @param sex     性别
     * @return 用户的集合
     */
    List<User> queryUserByAddressAndSex(@Param("address") String address, @Param("sex") String sex);

    /**
     * 根据用户名和地址查询用户列表
     * @param user 需要查询的用户
     * @return 返回符合条件的用户列表
     */
    List<User> queryUserByUserAddressAndSex(User user);

    /**
     * 根据用户名和地址查询用户列表
     * @param userTemp 需要查询的用户
     * @return 返回符合条件的用户列表
     */
    List<UserTemp> queryUserByUserTempAddressAndSex(UserTemp userTemp);

    /**
     * 根据用户名和地址查询用户列表
     * @param map
     * @return
     */
    List<User> queryUserByMapWithAddressAndSex(@Param("map") Map<String,String> map);

    /**
     * 通过表名查询数据
     * @param tableName 表名
     * @return
     */
    List<User> queryUserWithTableName(String tableName);

    /**
     * 通过表名查询数据
     * @param map
     * @return
     */
    List<User> queryUserByMapWithTableName(Map map);

    /**
     * 通过用户名名称模糊查询
     * @param name 用户名片段
     * @return 用户集合
     */
    List<User> queryByUserName(String name);

    /**
     *
     * @param name
     */
    void deleteUserByName(String name);


    List<User> queryUsersByNameOrSex(@Param("name") String name,@Param("sex") String sex);


    void updateUsersById(User user);

    List<User> queryAll(@Param("col1") String col1,@Param("col2")String col2);

    List<User> queryUserByIds(@Param("ids") List<Integer> ids);




}
