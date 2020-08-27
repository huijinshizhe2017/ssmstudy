package cn.surpass.skill;

import cn.surpass.skill.dao.PinDao;
import cn.surpass.skill.model.IdsConnector;
import cn.surpass.skill.model.IdsPin;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * ssmstudy
 * cn.surpass.skill
 *
 * @author surpass
 * @date 2020/3/15
 */
public class IdsPinTest {

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
    public void testInsert(){
        IdsPin idsPin = new IdsPin();
        idsPin.setType("type1");
        IdsConnector connector = new IdsConnector();
        connector.setId(1);
        connector.setName("连接器");
        idsPin.setConn(connector);
        PinDao pinDao = sqlSession.getMapper(PinDao.class);
        pinDao.insertPin(idsPin);
    }
}
