package cn.surpass.lazy.dao;

import cn.surpass.domain.User;

import java.util.List;

/**
 * mybastis
 * cn.surpass.lazy.paras
 *
 * @author surpass
 * @date 2019/8/15
 */
public interface IUserDao {

    List<User> findAll();

    User findById(int uid);
}
