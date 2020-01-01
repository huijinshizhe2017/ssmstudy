package cn.surpass.xml.test;

import cn.surpass.domain.User;
import cn.surpass.xml.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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

    public static void main(String[] args) throws IOException {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工程
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂生产SqlSession
        SqlSession sqlSession = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象\
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        System.out.println(userDao.getClass());
        //5.使用代理对象执行方法
        List<User> userList = userDao.findAll();
        userList.stream().forEach(System.out::println);
        //6.释放资源
        sqlSession.close();
        in.close();
    }
}
