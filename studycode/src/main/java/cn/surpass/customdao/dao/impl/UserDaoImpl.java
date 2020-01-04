package cn.surpass.customdao.dao.impl;

import cn.surpass.domain.User;
import cn.surpass.customdao.dao.IUserDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * mybastis
 * cn.surpass.customdao.paras.impl
 *
 * @author surpass
 * @date 2019/8/12
 */
public class UserDaoImpl implements IUserDao {

    private SqlSessionFactory factory;


    public UserDaoImpl(SqlSessionFactory factory){
        this.factory = factory;
    }

    @Override
    public List<User> findAll() {
        SqlSession sqlSession = factory.openSession();
        List<User> users = sqlSession.selectList("cn.surpass.customdao.paras.IUserDao.findAll");
        sqlSession.close();
        return users;
    }

    @Override
    public void saveUser(User user) {
        SqlSession sqlSession = factory.openSession();
        sqlSession.insert("cn.surpass.customdao.paras.IUserDao.saveUser",user);
        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public void updateUser(User user) {
        SqlSession sqlSession = factory.openSession();
        sqlSession.insert("cn.surpass.customdao.paras.IUserDao.updateUser",user);
        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public void deleteUser(Integer id) {
        SqlSession sqlSession = factory.openSession();
        sqlSession.delete("cn.surpass.customdao.paras.IUserDao.deleteUser",id);
        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public User findUserById(Integer id) {
        SqlSession sqlSession = factory.openSession();
        User user = sqlSession.selectOne("cn.surpass.customdao.paras.IUserDao.findAll",id);
        sqlSession.close();
        return user;
    }
}
