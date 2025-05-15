package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserMapper 测试类
 */
@SpringBootTest
@Transactional
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    
    /**
     * 测试插入用户表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        User user = createTestUser("测试插入");
        
        // 执行插入
        int rows = userMapper.insert(user);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(user.getId());
        
        // 验证能否查询到
        User savedUser = userMapper.getById(user.getId());
        assertNotNull(savedUser);
        
        System.out.println("插入用户表成功: " + savedUser);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        User user = createTestUser("测试ID查询");
        userMapper.insert(user);
        
        // 执行查询
        User result = userMapper.getById(user.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        
        System.out.println("根据ID查询用户表: " + result);
    }
    
    /**
     * 测试根据用户名查询
     */
    @Test
    public void testGetByUsername() {
        // 创建测试数据
        User user = createTestUser("测试用户名查询");
        userMapper.insert(user);
        
        // 执行查询
        User result = userMapper.getByUsername(user.getUsername());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        
        System.out.println("根据用户名查询用户表: " + result);
    }
    
    /**
     * 测试根据邮箱查询
     */
    @Test
    public void testGetByEmail() {
        // 创建测试数据
        User user = createTestUser("测试邮箱查询");
        userMapper.insert(user);
        
        // 执行查询
        User result = userMapper.getByEmail(user.getEmail());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        
        System.out.println("根据邮箱查询用户表: " + result);
    }
    
    /**
     * 测试查询所有用户表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        User user1 = createTestUser("测试查询所有1");
        User user2 = createTestUser("测试查询所有2");
        userMapper.insert(user1);
        userMapper.insert(user2);
        
        // 执行查询
        List<User> userList = userMapper.findAll();
        
        // 验证结果
        assertNotNull(userList);
        assertFalse(userList.isEmpty());
        
        // 验证包含测试数据
        boolean containsUser1 = userList.stream().anyMatch(c -> c.getId().equals(user1.getId()));
        boolean containsUser2 = userList.stream().anyMatch(c -> c.getId().equals(user2.getId()));
        assertTrue(containsUser1, "结果中不包含用户表1");
        assertTrue(containsUser2, "结果中不包含用户表2");
        
        System.out.println("查询所有用户表数量: " + userList.size());
    }
    
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        User user = createTestUser("测试条件查询");
        userMapper.insert(user);
        
        // 创建查询条件
        User query = new User();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<User> userList = userMapper.findList(query);
        
        // 验证结果
        assertNotNull(userList);
        
        System.out.println("条件查询结果数量: " + userList.size());
    }
    
    /**
     * 测试更新用户表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        User user = createTestUser("测试更新");
        userMapper.insert(user);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = userMapper.update(user);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        User updatedUser = userMapper.getById(user.getId());
        assertNotNull(updatedUser);
        
        System.out.println("更新用户表成功: " + updatedUser);
    }
    
    /**
     * 测试删除用户表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        User user = createTestUser("测试删除");
        userMapper.insert(user);
        
        // 验证创建成功
        assertNotNull(userMapper.getById(user.getId()));
        
        // 执行删除
        int rows = userMapper.deleteById(user.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(userMapper.getById(user.getId()));
        
        System.out.println("删除用户表成功, ID: " + user.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        User user1 = createTestUser("测试批量删除1");
        User user2 = createTestUser("测试批量删除2");
        userMapper.insert(user1);
        userMapper.insert(user2);
        
        // 验证创建成功
        assertNotNull(userMapper.getById(user1.getId()));
        assertNotNull(userMapper.getById(user2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { user1.getId(), user2.getId() };
        int rows = userMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(userMapper.getById(user1.getId()));
        assertNull(userMapper.getById(user2.getId()));
        
        System.out.println("批量删除用户表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用用户表（不保存到数据库）
     */
    private User createTestUser(String testName) {
        User user = new User();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        // 唯一索引字段，确保值唯一
        user.setUsername(testName + "_username" + randomSuffix);
        user.setPassword(testName + "_password");
        user.setNickname(testName + "_nickname");
        // 唯一索引字段，确保值唯一
        user.setEmail(testName + "_email" + randomSuffix);
        user.setPhone(testName + "_phone");
        user.setAvatarUrl(testName + "_avatarUrl");
        return user;
    }
}
