package cn.surpass.xml.dao;

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
}
