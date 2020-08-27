package cn.surpass.skill;

import cn.surpass.domain.User;
import cn.surpass.skill.dao.UserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.print.attribute.HashAttributeSet;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ssmstudy
 * cn.surpass.skill
 *
 * @author surpass
 * @date 2020/3/19
 */
public class PlaceholderTest {
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
    public void test1(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.queryUserById("41");
        System.out.println(user);
    }

    @Test
    public void test2(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> users = userDao.queryUserByAddressAndSex("北京三元桥","男");
        users.forEach(System.out::println);
    }

    @Test
    public void test3(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setSex("男");
        user.setAddress("北京三元桥");
        List<User> users = userDao.queryUserByUserAddressAndSex(user);
        users.forEach(System.out::println);
    }

    @Test
    public void test4(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        Map<String,String> userMap = new HashMap();
        userMap.put("sex","男");
        userMap.put("address","北京三元桥");
        List<User> users = userDao.queryUserByMapWithAddressAndSex(userMap);
        users.forEach(System.out::println);
    }

    @Test
    public void test5(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> users = userDao.queryUserWithTableName("USER");
        users.forEach(System.out::println);
    }

    @Test
    public void test7(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        Map<String,String> map = new HashMap();
        map.put("tableName","USER");
        List<User> users = userDao.queryUserByMapWithTableName(map);
        users.forEach(System.out::println);
    }

    @Test
    public void test8(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> users = userDao.queryByUserName("%王%");
        users.forEach(System.out::println);
    }

    @Test
    public void test9(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        userDao.deleteUserByName("%王%");
    }

    @Test
    public void test10(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> user = userDao.queryUsersByNameOrSex("%王%","男");
        user.forEach(System.out::println);
    }

    @Test
    public void test11(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setId(43);
        user.setUsername("李四");
        user.setAddress("河北廊坊");
        userDao.updateUsersById(user);
    }

    @Test
    public void test12(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> users = userDao.queryAll("USERNAME", null);
        users.forEach(System.out::println);
    }

    @Test
    public void test13(){
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<Integer> ids = new ArrayList<>();
        ids.add(42);
        ids.add(48);
        ids.add(45);
        List<User> users = userDao.queryUserByIds(ids);
        users.forEach(System.out::println);
    }


}
