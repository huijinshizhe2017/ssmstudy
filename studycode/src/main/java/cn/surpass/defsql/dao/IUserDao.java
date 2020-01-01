package cn.surpass.defsql.dao;

import cn.surpass.domain.QueryVoDef;
import cn.surpass.domain.User;

import java.util.List;

public interface IUserDao {

    List<User> findAll();

    User findUserById(Integer id);

    List<User> findUsersByUsername(String username);

    List<User> findUserByVo(QueryVoDef vo);

    List<User> findUserByCondition(User user);

    List<User> findUsersByVoInIds(QueryVoDef vo);
}
