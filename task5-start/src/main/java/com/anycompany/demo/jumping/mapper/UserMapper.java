package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表数据访问接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据ID查询用户表
     * @param id ID
     * @return 用户表信息
     */
    User getById(@Param("id") Long id);
    
    /**
     * 根据用户名查询用户表
     * @param username 用户名
     * @return 用户表信息
     */
    User getByUsername(@Param("username") String username);
    
    /**
     * 根据邮箱查询用户表
     * @param email 邮箱
     * @return 用户表信息
     */
    User getByEmail(@Param("email") String email);
    
    /**
     * 查询所有用户表
     * @return 用户表列表
     */
    List<User> findAll();
    
    /**
     * 查询用户表列表
     * @param user 查询条件
     * @return 用户表列表
     */
    List<User> findList(User user);
    
    /**
     * 分页查询用户表列表
     * @param user 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 用户表列表
     */
    List<User> findListWithPagination(@Param("user") User user, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入用户表
     * @param user 用户表信息
     * @return 影响行数
     */
    int insert(User user);
    
    /**
     * 更新用户表
     * @param user 用户表信息
     * @return 影响行数
     */
    int update(User user);
    
    /**
     * 删除用户表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除用户表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
