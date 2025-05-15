package com.anycompany.demo.jumping.service;

import com.anycompany.demo.jumping.model.User;

import java.util.Date;
import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 根据ID加载用户
     * @param id 用户ID
     * @return 用户信息
     */
    User loadUserById(Long id);
    
    /**
     * 根据用户名加载用户
     * @param username 用户名
     * @return 用户信息
     */
    User loadUserByUsername(String username);
    
    /**
     * 根据邮箱加载用户
     * @param email 邮箱
     * @return 用户信息
     */
    User loadUserByEmail(String email);
    
    /**
     * 根据用户名搜索用户列表，支持分页
     * @param username 用户名（模糊匹配）
     * @param offset 起始位置，如果为负数将被设置为0
     * @param limit 每页记录数，有最小和最大限制，超出范围将使用默认值
     * @return 用户列表
     */
    List<User> searchUsersByUsername(String username, int offset, int limit);
    
    /**
     * 根据昵称搜索用户列表，支持分页
     * @param nickname 昵称（模糊匹配）
     * @param offset 起始位置，如果为负数将被设置为0
     * @param limit 每页记录数，有最小和最大限制，超出范围将使用默认值
     * @return 用户列表
     */
    List<User> searchUsersByNickname(String nickname, int offset, int limit);
    
    /**
     * 根据邮箱搜索用户列表，支持分页
     * @param email 邮箱（模糊匹配）
     * @param offset 起始位置，如果为负数将被设置为0
     * @param limit 每页记录数，有最小和最大限制，超出范围将使用默认值
     * @return 用户列表
     */
    List<User> searchUsersByEmail(String email, int offset, int limit);
    
    /**
     * 根据手机号搜索用户列表，支持分页
     * @param phone 手机号（模糊匹配）
     * @param offset 起始位置，如果为负数将被设置为0
     * @param limit 每页记录数，有最小和最大限制，超出范围将使用默认值
     * @return 用户列表
     */
    List<User> searchUsersByPhone(String phone, int offset, int limit);
    
    /**
     * 创建用户
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @param email 邮箱
     * @param phone 手机号
     * @param avatarUrl 头像URL
     * @return 创建成功的用户ID
     */
    Long createUser(String username, String password, String nickname, String email, String phone, String avatarUrl);
    
    /**
     * 更新用户
     * @param id 用户ID
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @param email 邮箱
     * @param phone 手机号
     * @param avatarUrl 头像URL
     * @return 更新的用户ID
     */
    Long updateUser(Long id, String username, String password, String nickname, String email, String phone, String avatarUrl);
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 删除的用户ID
     */
    Long deleteUser(Long id);
    
    /**
     * 批量删除用户
     * @param ids 用户ID数组
     * @return 删除的用户ID数组
     */
    Long[] batchDeleteUsers(Long[] ids);
} 