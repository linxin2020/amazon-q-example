package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.UserGameData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserGameDataMapper 测试类
 */
@SpringBootTest
@Transactional
public class UserGameDataMapperTest {

    @Autowired
    private UserGameDataMapper userGameDataMapper;
    
    /**
     * 测试插入用户游戏数据表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        UserGameData userGameData = createTestUserGameData("测试插入");
        
        // 执行插入
        int rows = userGameDataMapper.insert(userGameData);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(userGameData.getId());
        
        // 验证能否查询到
        UserGameData savedUserGameData = userGameDataMapper.getById(userGameData.getId());
        assertNotNull(savedUserGameData);
        
        System.out.println("插入用户游戏数据表成功: " + savedUserGameData);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        UserGameData userGameData = createTestUserGameData("测试ID查询");
        userGameDataMapper.insert(userGameData);
        
        // 执行查询
        UserGameData result = userGameDataMapper.getById(userGameData.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(userGameData.getId(), result.getId());
        
        System.out.println("根据ID查询用户游戏数据表: " + result);
    }
    
    /**
     * 测试根据用户ID查询
     */
    @Test
    public void testGetByUserId() {
        // 创建测试数据
        UserGameData userGameData = createTestUserGameData("测试用户ID查询");
        userGameDataMapper.insert(userGameData);
        
        // 执行查询
        UserGameData result = userGameDataMapper.getByUserId(userGameData.getUserId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(userGameData.getId(), result.getId());
        assertEquals(userGameData.getUserId(), result.getUserId());
        
        System.out.println("根据用户ID查询用户游戏数据表: " + result);
    }
    
    /**
     * 测试根据活动ID查询
     */
    @Test
    public void testGetByActivityId() {
        // 创建测试数据
        UserGameData userGameData = createTestUserGameData("测试活动ID查询");
        userGameDataMapper.insert(userGameData);
        
        // 执行查询
        UserGameData result = userGameDataMapper.getByActivityId(userGameData.getActivityId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(userGameData.getId(), result.getId());
        assertEquals(userGameData.getActivityId(), result.getActivityId());
        
        System.out.println("根据活动ID查询用户游戏数据表: " + result);
    }
    
    /**
     * 测试查询所有用户游戏数据表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        UserGameData userGameData1 = createTestUserGameData("测试查询所有1");
        UserGameData userGameData2 = createTestUserGameData("测试查询所有2");
        userGameDataMapper.insert(userGameData1);
        userGameDataMapper.insert(userGameData2);
        
        // 执行查询
        List<UserGameData> userGameDataList = userGameDataMapper.findAll();
        
        // 验证结果
        assertNotNull(userGameDataList);
        assertFalse(userGameDataList.isEmpty());
        
        // 验证包含测试数据
        boolean containsUserGameData1 = userGameDataList.stream().anyMatch(c -> c.getId().equals(userGameData1.getId()));
        boolean containsUserGameData2 = userGameDataList.stream().anyMatch(c -> c.getId().equals(userGameData2.getId()));
        assertTrue(containsUserGameData1, "结果中不包含用户游戏数据表1");
        assertTrue(containsUserGameData2, "结果中不包含用户游戏数据表2");
        
        System.out.println("查询所有用户游戏数据表数量: " + userGameDataList.size());
    }
    
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        UserGameData userGameData = createTestUserGameData("测试条件查询");
        userGameDataMapper.insert(userGameData);
        
        // 创建查询条件
        UserGameData query = new UserGameData();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<UserGameData> userGameDataList = userGameDataMapper.findList(query);
        
        // 验证结果
        assertNotNull(userGameDataList);
        
        System.out.println("条件查询结果数量: " + userGameDataList.size());
    }
    
    /**
     * 测试更新用户游戏数据表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        UserGameData userGameData = createTestUserGameData("测试更新");
        userGameDataMapper.insert(userGameData);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = userGameDataMapper.update(userGameData);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        UserGameData updatedUserGameData = userGameDataMapper.getById(userGameData.getId());
        assertNotNull(updatedUserGameData);
        
        System.out.println("更新用户游戏数据表成功: " + updatedUserGameData);
    }
    
    /**
     * 测试删除用户游戏数据表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        UserGameData userGameData = createTestUserGameData("测试删除");
        userGameDataMapper.insert(userGameData);
        
        // 验证创建成功
        assertNotNull(userGameDataMapper.getById(userGameData.getId()));
        
        // 执行删除
        int rows = userGameDataMapper.deleteById(userGameData.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(userGameDataMapper.getById(userGameData.getId()));
        
        System.out.println("删除用户游戏数据表成功, ID: " + userGameData.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        UserGameData userGameData1 = createTestUserGameData("测试批量删除1");
        UserGameData userGameData2 = createTestUserGameData("测试批量删除2");
        userGameDataMapper.insert(userGameData1);
        userGameDataMapper.insert(userGameData2);
        
        // 验证创建成功
        assertNotNull(userGameDataMapper.getById(userGameData1.getId()));
        assertNotNull(userGameDataMapper.getById(userGameData2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { userGameData1.getId(), userGameData2.getId() };
        int rows = userGameDataMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(userGameDataMapper.getById(userGameData1.getId()));
        assertNull(userGameDataMapper.getById(userGameData2.getId()));
        
        System.out.println("批量删除用户游戏数据表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用用户游戏数据表（不保存到数据库）
     */
    private UserGameData createTestUserGameData(String testName) {
        UserGameData userGameData = new UserGameData();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        userGameData.setUserId(timestamp + (long)(Math.random() * 1000));
        userGameData.setActivityId(timestamp + (long)(Math.random() * 1000));
        userGameData.setCurrentPosition((int)(Math.random() * 100) + 1);
        userGameData.setRemainingChances((int)(Math.random() * 100) + 1);
        userGameData.setDailyUsedChances((int)(Math.random() * 100) + 1);
        userGameData.setTotalPoints((int)(Math.random() * 100) + 1);
        userGameData.setLastGameTime(new Date(System.currentTimeMillis() + (long)(Math.random() * 86400000))); // 随机一天内的时间
        userGameData.setIsNewUser((int)(Math.random() * 100) + 1);
        return userGameData;
    }
}
