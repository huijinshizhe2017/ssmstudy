package cn.surpass.skill;

import cn.surpass.skill.dao.StudentDao;
import cn.surpass.skill.model.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * ssmstudy
 * cn.surpass.skill
 *
 * @author surpass
 * @date 2020/3/17
 */
public class StudentDaoTest {
    private InputStream in;
    private SqlSession sqlSession;
    private SqlSessionFactory factory;
    @Before
    public void init() throws IOException {
        in= Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
    }

    @After
    public void destroy()throws Exception{
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void queryByAgeTest(){
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Student> students = studentDao.queryByAge(">", 22);
        students.forEach(System.out::println);
    }

    @Test
    public void queryByNameTest(){
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Student> students = studentDao.queryByName("小%");
        students.forEach(System.out::println);
    }


    @Test
    public void queryUsersByNameOrAgeTest(){
        StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
        List<Student> students = studentDao.queryByName("小%");
        students.forEach(System.out::println);
    }
}
