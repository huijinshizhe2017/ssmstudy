package cn.surpass.cache.dao;

import cn.surpass.domain.User;

import java.util.List;

/**
 * mybastis
 * cn.surpass.curd.dao
 *
 * @author surpass
 * @date 2019/8/13
 */
public interface IUserDao {

    List<User> findAll();

    User findById(int id);

    void updateUser(User user);
}
