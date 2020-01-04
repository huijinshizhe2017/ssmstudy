package cn.surpass.result.dao;

import cn.surpass.domain.User;
import cn.surpass.domain.QueryVo;

import java.util.List;

/**
 * mybastis
 * cn.surpass.result.paras
 *
 * @author surpass
 * @date 2019/8/13
 */
public interface IUserDao {

    List<User> findAll();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(Integer id);

    User findUserById(Integer id);

    List<User> findUsersByUsername(String username);

    Integer countUser();

    List<User> findUserByVo(QueryVo vo);
}
