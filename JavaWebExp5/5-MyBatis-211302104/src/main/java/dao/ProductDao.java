package dao;

import java.util.List;

import pojo.*;

public interface ProductDao {

    /**
     * 根据id查询产品信息
     *
     * @param id
     * @return
     */
    public Product selectProductById(int id);

    /**
     * 查询所有产品信息
     *
     * @return
     */
    public List<Product> selectAllProduct();

}
