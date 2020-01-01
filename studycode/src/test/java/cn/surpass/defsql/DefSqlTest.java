package cn.surpass.defsql;

import cn.surpass.domain.QueryVoDef;
import cn.surpass.domain.User;
import cn.surpass.defsql.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * mybastis
 * cn.surpass.defsql
 *
 * @author surpass
 * @date 2019/8/13
 */
public class DefSqlTest {

    private InputStream in;
    private SqlSession sqlSession;


    @Before
    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
    }

    @After
    public void destroy() throws Exception {
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void testfindOne() {
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = userDao.findUserById(48);
        System.out.println(user);
    }

    @Test
    public void testfindUsersByUsername() {
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = userDao.findUsersByUsername("' or 1=1 or '王");
        users.stream().forEach(System.out::println);
    }

    @Test
    public void testFindByCondition(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = new User();
        user.setUsername("老王");
        List<User> users = userDao.findUserByCondition(user);
        users.stream().forEach(System.out::println);
    }

    @Test
    public void testFindUsersByVoInIds(){
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<Integer> ids = Arrays.asList(42,43,45);
        QueryVoDef queryVo = new QueryVoDef();
        queryVo.setIds(ids);
        List<User> users = userDao.findUsersByVoInIds(queryVo);
        users.stream().forEach(System.out::println);
    }

}
