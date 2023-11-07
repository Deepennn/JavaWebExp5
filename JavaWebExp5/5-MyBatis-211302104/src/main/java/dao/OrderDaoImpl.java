package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import pojo.*;

public class OrderDaoImpl implements OrderDao {
    public SqlSession sqlSession;

    public OrderDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<Order> selectOrderByUser_id(int user_id) {
        return this.sqlSession.selectList("OrderMapper.selectOrderByUser_id",user_id);
    }
    
    @Override
    public void insertOrder(Order order) {
    	this.sqlSession.insert("OrderMapper.insertOrder", order);
    }

    @Override
    public void deleteOrderById(int id) {
        this.sqlSession.delete("OrderMapper.deleteOrderById", id);
    }

}
