package dao;

import java.util.List;

import pojo.*;

public interface UserDao {

	/**
     * 根据userName查询用户信息
     *
     * @param userName
     * @return
     */
    public User selectUserByUserName(String userName);
    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    public User selectUserById(int id);

    /**
     * 新增用户
     *
     * @param user
     */
    public void insertUser(User user);

    /**
     * 更新用户信息
     *
     * @param user
     */
    public void updateUser(User user);

    /**
     * 根据id删除用户信息
     *
     * @param id
     */
    public void deleteUserById(int id);
}
