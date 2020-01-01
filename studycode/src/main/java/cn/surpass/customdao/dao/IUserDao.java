package cn.surpass.customdao.dao;

import cn.surpass.domain.User;

import java.util.List;

/**
 * mybastis
 * cn.surpassdomain
 *
 * @author surpass
 * @date 2019/8/12
 */
public interface IUserDao {

    List<User> findAll();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(Integer id);

    User findUserById(Integer id);
}
