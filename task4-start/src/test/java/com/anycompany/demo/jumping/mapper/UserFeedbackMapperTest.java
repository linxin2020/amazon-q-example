package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.UserFeedback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserFeedbackMapper 测试类
 */
@SpringBootTest
@Transactional
public class UserFeedbackMapperTest {

    @Autowired
    private UserFeedbackMapper userFeedbackMapper;
    
    /**
     * 测试插入用户反馈表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        UserFeedback userFeedback = createTestUserFeedback("测试插入");
        
        // 执行插入
        int rows = userFeedbackMapper.insert(userFeedback);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(userFeedback.getId());
        
        // 验证能否查询到
        UserFeedback savedUserFeedback = userFeedbackMapper.getById(userFeedback.getId());
        assertNotNull(savedUserFeedback);
        
        System.out.println("插入用户反馈表成功: " + savedUserFeedback);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        UserFeedback userFeedback = createTestUserFeedback("测试ID查询");
        userFeedbackMapper.insert(userFeedback);
        
        // 执行查询
        UserFeedback result = userFeedbackMapper.getById(userFeedback.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(userFeedback.getId(), result.getId());
        
        System.out.println("根据ID查询用户反馈表: " + result);
    }
    
    /**
     * 测试根据状态(0:未处理 1:已处理)查询列表
     */
    @Test
    public void testFindByStatus() {
        // 创建测试数据
        UserFeedback userFeedback1 = createTestUserFeedback("测试状态(0:未处理 1:已处理)查询1");
        UserFeedback userFeedback2 = createTestUserFeedback("测试状态(0:未处理 1:已处理)查询2");
        // 设置相同的状态(0:未处理 1:已处理)，确保能查到多条记录
        userFeedback1.setStatus(userFeedback2.getStatus());
        userFeedbackMapper.insert(userFeedback1);
        userFeedbackMapper.insert(userFeedback2);
        
        // 执行查询
        List<UserFeedback> results = userFeedbackMapper.findByStatus(userFeedback1.getStatus());
        
        // 验证结果
        assertNotNull(results);
        assertTrue(results.size() >= 2);
        
        // 验证包含测试数据
        boolean containsUserFeedback1 = results.stream().anyMatch(c -> c.getId().equals(userFeedback1.getId()));
        boolean containsUserFeedback2 = results.stream().anyMatch(c -> c.getId().equals(userFeedback2.getId()));
        assertTrue(containsUserFeedback1, "结果中不包含用户反馈表1");
        assertTrue(containsUserFeedback2, "结果中不包含用户反馈表2");
        
        System.out.println("根据状态(0:未处理 1:已处理)查询用户反馈表列表: " + results.size() + "条记录");
    }
    
    /**
     * 测试查询所有用户反馈表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        UserFeedback userFeedback1 = createTestUserFeedback("测试查询所有1");
        UserFeedback userFeedback2 = createTestUserFeedback("测试查询所有2");
        userFeedbackMapper.insert(userFeedback1);
        userFeedbackMapper.insert(userFeedback2);
        
        // 执行查询
        List<UserFeedback> userFeedbackList = userFeedbackMapper.findAll();
        
        // 验证结果
        assertNotNull(userFeedbackList);
        assertFalse(userFeedbackList.isEmpty());
        
        // 验证包含测试数据
        boolean containsUserFeedback1 = userFeedbackList.stream().anyMatch(c -> c.getId().equals(userFeedback1.getId()));
        boolean containsUserFeedback2 = userFeedbackList.stream().anyMatch(c -> c.getId().equals(userFeedback2.getId()));
        assertTrue(containsUserFeedback1, "结果中不包含用户反馈表1");
        assertTrue(containsUserFeedback2, "结果中不包含用户反馈表2");
        
        System.out.println("查询所有用户反馈表数量: " + userFeedbackList.size());
    }
    
    /**
     * 测试查询所有启用的用户反馈表
     */
    @Test
    public void testFindAllEnabled() {
        // 创建启用的测试数据
        UserFeedback enabledUserFeedback = createTestUserFeedback("测试启用用户反馈表");
        enabledUserFeedback.setStatus(1);
        userFeedbackMapper.insert(enabledUserFeedback);
        
        // 创建禁用的测试数据
        UserFeedback disabledUserFeedback = createTestUserFeedback("测试禁用用户反馈表");
        disabledUserFeedback.setStatus(0);
        userFeedbackMapper.insert(disabledUserFeedback);
        
        // 执行查询
        List<UserFeedback> userFeedbackList = userFeedbackMapper.findAllEnabled();
        
        // 验证结果
        assertNotNull(userFeedbackList);
        assertFalse(userFeedbackList.isEmpty());
        
        // 验证所有结果都是启用状态且包含启用测试数据
        boolean containsEnabledUserFeedback = false;
        for (UserFeedback userFeedback : userFeedbackList) {
            assertEquals(1, userFeedback.getStatus(), "查询到非启用状态的用户反馈表");
            if (userFeedback.getId().equals(enabledUserFeedback.getId())) {
                containsEnabledUserFeedback = true;
            }
        }
        assertTrue(containsEnabledUserFeedback, "结果中不包含测试创建的启用用户反馈表");
        
        // 验证不包含禁用的测试数据
        boolean containsDisabledUserFeedback = userFeedbackList.stream()
                .anyMatch(c -> c.getId().equals(disabledUserFeedback.getId()));
        assertFalse(containsDisabledUserFeedback, "结果中不应包含禁用用户反馈表");
        
        System.out.println("查询所有启用用户反馈表数量: " + userFeedbackList.size());
    }
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        UserFeedback userFeedback = createTestUserFeedback("测试条件查询");
        userFeedbackMapper.insert(userFeedback);
        
        // 创建查询条件
        UserFeedback query = new UserFeedback();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<UserFeedback> userFeedbackList = userFeedbackMapper.findList(query);
        
        // 验证结果
        assertNotNull(userFeedbackList);
        
        System.out.println("条件查询结果数量: " + userFeedbackList.size());
    }
    
    /**
     * 测试更新用户反馈表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        UserFeedback userFeedback = createTestUserFeedback("测试更新");
        userFeedbackMapper.insert(userFeedback);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = userFeedbackMapper.update(userFeedback);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        UserFeedback updatedUserFeedback = userFeedbackMapper.getById(userFeedback.getId());
        assertNotNull(updatedUserFeedback);
        
        System.out.println("更新用户反馈表成功: " + updatedUserFeedback);
    }
    
    /**
     * 测试删除用户反馈表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        UserFeedback userFeedback = createTestUserFeedback("测试删除");
        userFeedbackMapper.insert(userFeedback);
        
        // 验证创建成功
        assertNotNull(userFeedbackMapper.getById(userFeedback.getId()));
        
        // 执行删除
        int rows = userFeedbackMapper.deleteById(userFeedback.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(userFeedbackMapper.getById(userFeedback.getId()));
        
        System.out.println("删除用户反馈表成功, ID: " + userFeedback.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        UserFeedback userFeedback1 = createTestUserFeedback("测试批量删除1");
        UserFeedback userFeedback2 = createTestUserFeedback("测试批量删除2");
        userFeedbackMapper.insert(userFeedback1);
        userFeedbackMapper.insert(userFeedback2);
        
        // 验证创建成功
        assertNotNull(userFeedbackMapper.getById(userFeedback1.getId()));
        assertNotNull(userFeedbackMapper.getById(userFeedback2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { userFeedback1.getId(), userFeedback2.getId() };
        int rows = userFeedbackMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(userFeedbackMapper.getById(userFeedback1.getId()));
        assertNull(userFeedbackMapper.getById(userFeedback2.getId()));
        
        System.out.println("批量删除用户反馈表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用用户反馈表（不保存到数据库）
     */
    private UserFeedback createTestUserFeedback(String testName) {
        UserFeedback userFeedback = new UserFeedback();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        userFeedback.setCategoryId(timestamp + (long)(Math.random() * 1000));
        userFeedback.setTitle(testName + "_title");
        userFeedback.setContent(testName + "_content");
        userFeedback.setEmail(testName + "_email");
        userFeedback.setStatus(1); // 默认启用状态
        userFeedback.setUserId(timestamp + (long)(Math.random() * 1000));
        return userFeedback;
    }
}
