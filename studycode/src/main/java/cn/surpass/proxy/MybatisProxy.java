package cn.surpass.proxy;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * ssmstudy
 * cn.surpass.proxy
 *
 * @author surpass
 * @date 2020/1/6
 */
public class MybatisProxy {

    public static <T> T getMapper(SqlSessionFactory sqlSessionFactory, Class<T> t) {
        if (!t.isInterface()) {
            throw new IllegalArgumentException("t is not a Interface");
        }
        return (T) newProxyInstance(SqlSessionFactory.class.getClassLoader(),
                new Class[]{t}, new SqlSessionInterceptor<T>(sqlSessionFactory, t));
    }

    private static class SqlSessionInterceptor<T> implements InvocationHandler {

        private Class<T> t;
        private SqlSessionFactory sqlSessionFactory;

        private SqlSessionInterceptor(SqlSessionFactory sqlSessionFactory, Class<T> t) {
            this.sqlSessionFactory = sqlSessionFactory;
            this.t = t;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            T mapperT = sqlSession.getMapper(t);
            try {
                Object result = method.invoke(mapperT, args);
                sqlSession.commit(true);
                return result;
            } catch (Exception e) {
                sqlSession.rollback();
                throw e;
            } finally {
                if (sqlSession != null) {
                    sqlSession.close();
                }
                System.out.println("Session 已经关闭");
            }
        }
    }
}
