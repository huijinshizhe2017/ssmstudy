package cn.surpass.lazy.test;

import cn.surpass.domain.User;
import cn.surpass.lazy.dao.IAccountDao;
import cn.surpass.lazy.dao.IUserDao;
import cn.surpass.domain.Account;
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
 * cn.surpass.lazy.test
 *
 * @author surpass
 * @date 2019/8/15
 */
public class LazyTest {

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
    public void lazyFindAll(){
        IAccountDao accountDao = sqlSession.getMapper(IAccountDao.class);
        List<Account> accounts = accountDao.findAll();
        accounts.stream().forEach(System.out::println);

    }

    @Test
    public void lazyFindUserAll(){
        IUserDao accountDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = accountDao.findAll();
        users.stream().forEach(System.out::println);

    }
}
