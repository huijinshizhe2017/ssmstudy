package cn.surpass.manyone.dao;

import cn.surpass.domain.User;

import java.util.List;

/**
 * mybastis
 * cn.surpass.manyone.dao
 *
 * @author surpass
 * @date 2019/8/14
 */
public interface IUserDao {

    List<User> findAll();

    List<User> findAllWithRole();

    User findUserById(Integer id);
}
