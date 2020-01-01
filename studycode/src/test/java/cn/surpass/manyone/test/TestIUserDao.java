package cn.surpass.manyone.test;

import cn.surpass.domain.User;
import cn.surpass.domain.Account;
import cn.surpass.domain.AccountUser;
import cn.surpass.domain.Role;
import cn.surpass.manyone.dao.IAccountDao;
import cn.surpass.manyone.dao.IRoleDao;
import cn.surpass.manyone.dao.IUserDao;
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
 * cn.surpass.manyone.test
 *
 * @author surpass
 * @date 2019/8/14
 */
public class TestIUserDao {

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
        IAccountDao accountDao = sqlSession.getMapper(IAccountDao.class);
        List<Account> accounts = accountDao.findAll();
        accounts.stream().forEach(System.out::println);
    }

    @Test
    public void testFindAllAccount(){
        IAccountDao accountDao = sqlSession.getMapper(IAccountDao.class);
        List<AccountUser> accounts = accountDao.findAllAccount();
        accounts.stream().forEach(System.out::println);
    }

    @Test
    public void testFindAllUser(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> accounts = userDao.findAll();
        accounts.stream().forEach(System.out::println);
    }

    @Test
    public void testFindAllRole(){
        IRoleDao userDao = sqlSession.getMapper(IRoleDao.class);
        List<Role> accounts = userDao.findAll();
        accounts.stream().forEach(System.out::println);
    }

    @Test
    public void testFindUserWithRole(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> accounts = userDao.findAllWithRole();
        accounts.stream().forEach(System.out::println);
    }


}
