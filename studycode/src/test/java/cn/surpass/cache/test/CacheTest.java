package cn.surpass.cache.test;

import cn.surpass.domain.User;
import cn.surpass.cache.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mybastis
 * cn.surpass.cache.test
 *
 * @author surpass
 * @date 2019/8/15
 */
public class CacheTest {

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
    public void findAllCacheTest(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user1 = userDao.findById(41);
        System.out.println(user1);

        IUserDao userDao1 = sqlSession.getMapper(IUserDao.class);
        System.out.println(userDao == userDao1);


    }

    @Test
    public void findAllCacheTest2(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user1 = userDao.findById(41);
        System.out.println(user1);
        sqlSession.clearCache();
        IUserDao userDao1 = sqlSession.getMapper(IUserDao.class);
        User user2 = userDao1.findById(41);
        System.out.println(user2);
        System.out.println(user1 == user2);

    }

    @Test
    public void findAllCacheTest3(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user1 = userDao.findById(41);
        System.out.println(user1);

        User user = new User();
        user.setId(41);
        user.setUsername("张三");
        userDao.updateUser(user);

        User user2 = userDao.findById(41);
        System.out.println(user2);
        System.out.println(user1 == user2);

    }

    @Test
    public void findAllCacheTest4(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user1 = userDao.findById(41);
        System.out.println(user1);

        sqlSession.close();

        sqlSession = factory.openSession();
        IUserDao userDao2 = sqlSession.getMapper(IUserDao.class);
        User user2 = userDao2.findById(41);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    @Test
    public void findUserByIdAndSexTest(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = userDao.findUserByIdAndSex(41, "女");
        users.forEach(System.out::println);
    }

    @Test
    public void findUserByIdsAndSexTest(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<Integer> ids = new ArrayList<>();
        ids.addAll(Arrays.asList(41,43,45,46));
        List<User> users = userDao.findUserByIdsAndSex(ids, "女");
        users.forEach(System.out::println);
    }
}
