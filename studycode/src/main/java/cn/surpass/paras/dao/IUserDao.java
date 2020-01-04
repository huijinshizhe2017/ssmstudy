package cn.surpass.paras.dao;

import cn.surpass.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ssmstudy
 * cn.surpass.paras
 * 多参数传递
 * @author surpass
 * @date 2020/1/3
 */
public interface IUserDao {

    /**
     * 方法一:顺序传递
     * @param ids
     * @param sex
     * @return
     */
    List<User> findUsersByIdsAndSex1(List<Integer> ids, String sex);

    /**
     * 方法二:注解方式:@Param
     * @param ids
     * @param sex
     * @return
     */
    List<User> findUsersByIdsAndSex2(@Param("ids") List<Integer> ids, @Param("sex") String sex);

    /**
     * 方法三:Map
     * @param map ids:List集合；sex:性别
     * @return
     */
    List<User> findUsersByIdsAndSex3(Map<String,Object> map);

    /**
     * 方法四:JavaBean
     * @param user
     * @return
     */
    List<User> findUsersByIdsAndSex4(User user);
}
