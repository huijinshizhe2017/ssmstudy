package cn.surpass.anno.dao;


import cn.surpass.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mybastis
 * cn.surpass.anno
 *
 * @author surpass
 * @date 2019/8/12
 */
public interface IUserDao {

    @Select("select * from user")
    List<User> findAll();
}
