package cn.surpass.paras.test;

import cn.surpass.paras.dao.IUserDao;
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
import java.util.*;

/**
 * ssmstudy
 * cn.surpass.paras.test
 *
 * @author surpass
 * @date 2020/1/3
 */
public class IUserDaoTest {

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
    public void findUsersByIdsAndSex2Test(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<Integer> ids = new ArrayList<>();
        ids.addAll(Arrays.asList(41,43,45,46));
        List<User> users = userDao.findUsersByIdsAndSex2(ids, "女");
        users.forEach(System.out::println);
    }

    @Test
    public void findUsersByIdsAndSex1Test(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<Integer> ids = new ArrayList<>();
        ids.addAll(Arrays.asList(41,43,45,46));
        List<User> users = userDao.findUsersByIdsAndSex1(ids, "女");
        users.forEach(System.out::println);
    }



    @Test
    public void findUsersByIdsAndSex3Test(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<Integer> ids = new ArrayList<>();
        ids.addAll(Arrays.asList(41,43,45,46));
        Map<String,Object> paras = new HashMap<>();
        paras.put("ids",ids);
        paras.put("sex","女");
        List<User> users = userDao.findUsersByIdsAndSex3(paras);
        users.forEach(System.out::println);
    }

    @Test
    public void findUsersByIdsAndSex4Test(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = new User();
        user.setId(41);
        user.setSex("女");
        List<User> users = userDao.findUsersByIdsAndSex4(user);
        users.forEach(System.out::println);
    }


}
