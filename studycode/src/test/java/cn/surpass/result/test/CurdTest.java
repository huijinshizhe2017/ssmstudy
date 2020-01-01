package cn.surpass.result.test;

import cn.surpass.domain.User;
import cn.surpass.domain.QueryVo;
import cn.surpass.result.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * mybastis
 * cn.surpass.curd.test
 *
 * @author surpass
 * @date 2019/8/13
 */
public class CurdTest {


    private InputStream in;
    private SqlSession sqlSession;


    @Before
    public void init() throws IOException {
        in= Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
    }

    @After
    public void destroy()throws Exception{
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void testFindAll(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = userDao.findAll();
        users.stream().forEach(System.out::println);
    }

    @Test
    public void testSave(){
        User user = new User();
        user.setAddress("save");
        user.setUsername("mybatis saveUser");
        user.setSex("男");
        user.setBirthday(new Date());
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        userDao.saveUser(user);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setAddress("save");
        user.setUsername("mybatis saveUser");
        user.setSex("男");
        user.setBirthday(new Date());
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        userDao.updateUser(user);
    }

    @Test
    public void testDelete(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        userDao.deleteUser(50);
    }

    @Test
    public void testfindOne(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = userDao.findUserById(48);
        System.out.println(user);
    }

    @Test
    public void testfindUsersByUsername(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = userDao.findUsersByUsername("' or 1=1 or '王");
        users.stream().forEach(System.out::println);
    }

    @Test
    public void testCountUser(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        Integer count = userDao.countUser();
        System.out.println(count);
    }

    @Test
    public void testOGNLTest(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        QueryVo queryVo = new QueryVo();
        User user = new User();
        user.setUsername("王");
        queryVo.setUser(user);
        List<User> userByVo = userDao.findUserByVo(queryVo);
        userByVo.stream().forEach(System.out::println);
    }

}
