package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import pojo.*;

public class ProductDaoImpl implements ProductDao {
    public SqlSession sqlSession;

    public ProductDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Product selectProductById(int id) {
        return this.sqlSession.selectOne("ProductMapper.selectProductById", id);
    }

    @Override
    public List<Product> selectAllProduct() {
        return this.sqlSession.selectList("ProductMapper.selectAllProduct");
    }

}
