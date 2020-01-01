package cn.surpass.customdao.test;

import cn.surpass.domain.User;
import cn.surpass.customdao.dao.IUserDao;
import cn.surpass.customdao.dao.impl.UserDaoImpl;
import org.apache.ibatis.io.Resources;
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
 * cn.surpass
 *
 * @author surpass
 * @date 2019/8/12
 */
public class testMybatisTest {

    private InputStream in;
    private SqlSessionFactory factory;


    @Before
    public void init() throws IOException {
        in= Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
    }

    @After
    public void destroy()throws Exception{
        in.close();
    }


    @Test
    public void testFindAll() throws IOException {
        //3.使用工厂生产SqlSession
        IUserDao userDao = new UserDaoImpl(factory);

        //5.使用代理对象执行方法
        List<User> userList = userDao.findAll();
        userList.stream().forEach(System.out::println);
    }

    @Test
    public void testSaveUser(){
        //3.使用工厂生产SqlSession
        IUserDao userDao = new UserDaoImpl(factory);
        User user = new User();
        user.setAddress("save");
        user.setUsername("mybatis saveUser");
        user.setSex("男");
        user.setBirthday(new Date());
        //5.使用代理对象执行方法
        userDao.saveUser(user);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdateUser(){
        //3.使用工厂生产SqlSession
        IUserDao userDao = new UserDaoImpl(factory);
        User user = new User();
        user.setId(42);
        user.setAddress("updateAddress");
        user.setUsername("update username");
        user.setSex("男");
        user.setBirthday(new Date());
        //5.使用代理对象执行方法
        userDao.updateUser(user);
        System.out.println(user.getId());
    }

    @Test
    public void testDeleteUser(){
        //3.使用工厂生产SqlSession
        IUserDao userDao = new UserDaoImpl(factory);
        //5.使用代理对象执行方法
        userDao.deleteUser(49);
    }

    @Test
    public void testFindById(){
        //3.使用工厂生产SqlSession
        IUserDao userDao = new UserDaoImpl(factory);
        //5.使用代理对象执行方法
        User userById = userDao.findUserById(41);
        System.out.println(userById);
    }
}
