package cn.surpass.cache.dao;

import cn.surpass.domain.User;

import java.util.List;

/**
 * mybastis
 * cn.surpass.curd.paras
 *
 * @author surpass
 * @date 2019/8/13
 */
public interface IUserDao {

    List<User> findAll();

    User findById(int id);

    void updateUser(User user);

    List<User> findUserByIdAndSex(int id,String sex);

    List<User> findUserByIdsAndSex(List<Integer> ids,String sex);
}
