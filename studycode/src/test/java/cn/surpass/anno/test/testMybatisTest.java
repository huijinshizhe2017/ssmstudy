package cn.surpass.anno.test;

import cn.surpass.anno.dao.IUserDao;
import cn.surpass.domain.User;
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
 * mybastis
 * cn.surpass
 *
 * @author surpass
 * @date 2019/8/12
 */
public class testMybatisTest {

    private InputStream in;
    private SqlSession sqlSession;


    @Before
    public void init() throws IOException {
        in= Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //这里DefaultSqlSessionFactory默认的提交方式是false
        sqlSession = factory.openSession();
    }

    @After
    public void destroy()throws Exception{
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void testAnnoFirst(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = userDao.findAll();
        users.stream().forEach(System.out::println);


    }

}
