package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import pojo.*;

public class UserDaoImpl implements UserDao {
    public SqlSession sqlSession;

    public UserDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public User selectUserByUserName(String userName) {
        return this.sqlSession.selectOne("UserMapper.selectUserByUserName", userName);
    }

    @Override
    public User selectUserById(int id) {
        return this.sqlSession.selectOne("UserMapper.selectUserById", id);
    }

    @Override
    public void insertUser(User user) {
        this.sqlSession.insert("UserMapper.insertUser", user);
    }

    @Override
    public void updateUser(User user) {
        this.sqlSession.update("UserDao.updateUser", user);
    }

    @Override
    public void deleteUserById(int id) {
        this.sqlSession.delete("UserMapper.deleteUserById", id);
    }

}
