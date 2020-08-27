package cn.surpass.skill;

import cn.surpass.skill.dao.IdDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Description
 * @Author SurpassLiang
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/4/17
 */
public class IdTest {
    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<>();
    private final List<String> preList = new ArrayList<>();
    Random df = new Random();
    private InputStream in;
    private SqlSessionFactory factory;

    @Before
    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        preList.add("CI");
        preList.add("BM");
        preList.add("SX");
        preList.add("QA");
        preList.add("TT");
    }

    @After
    public void destroy() throws Exception {
        in.close();
    }

    @Test
    public void testIdUtil() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        final Semaphore semaphore = new Semaphore(1000);
        final CountDownLatch countDownLatch = new CountDownLatch(1000);

        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    generatorId(preList.get(df.nextInt(5)));
                    semaphore.release();
                } catch (Exception e) {
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    private void generatorId(String preFix) {
        SqlSession sqlSession = threadLocal.get();
        if(sqlSession == null){
            sqlSession = factory.openSession(true);
            threadLocal.set(sqlSession);
        }
        IdDao idDao = sqlSession.getMapper(IdDao.class);
        DbIdGeneratorTemp dbIdGeneratorTemp = new DbIdGeneratorTemp(idDao);
        if (Math.random() > 0.5) {
            dbIdGeneratorTemp.getNextIds(preFix, df.nextInt(11)).forEach(System.out::println);
        } else {
            System.out.println(dbIdGeneratorTemp.getNextId(preFix));
        }

        sqlSession.close();
    }


}
