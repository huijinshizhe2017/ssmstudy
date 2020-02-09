package cn.surpass.proxy;

import cn.surpass.cache.dao.IUserDao;
import cn.surpass.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * ssmstudy
 * cn.surpass.proxy
 *
 * @author surpass
 * @date 2020/1/6
 */
public class MybatisProxyTest {
    private SqlSessionFactory factory = null;

    @Before
    public void init() throws IOException {
        InputStream in= Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
    }

    @Test
    public void test1(){
        IUserDao mapper = MybatisProxy.getMapper(factory, IUserDao.class);
        List<User> users = mapper.findAll();
        users.stream().forEach(System.out::println);
        User byId = mapper.findById(41);
        System.out.println(byId);
    }


}
