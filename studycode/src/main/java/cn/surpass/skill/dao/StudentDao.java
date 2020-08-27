package cn.surpass.skill.dao;

import cn.surpass.skill.model.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ssmstudy
 * cn.surpass.skill.dao
 *
 * @author surpass
 * @date 2020/3/17
 */
public interface StudentDao {

    /**
     *
     * @param code
     * @param age
     * @return
     */
    List<Student> queryByAge(@Param("code") String code, @Param("age")Integer age);


    List<Student> queryByName(@Param("name")String name);

}
