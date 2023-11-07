package dao;

import java.util.List;

import pojo.*;

public interface OrderDao {

    /**
     * 根据user_id查询订单信息
     *
     * @return
     */
    public List<Order> selectOrderByUser_id(int user_id);
    
    /**
     * 新增订单
     *
     * @param order
     */
    public void insertOrder(Order order);

    /**
     * 根据id删除订单信息
     *
     * @param id
     */
    public void deleteOrderById(int id);
}
