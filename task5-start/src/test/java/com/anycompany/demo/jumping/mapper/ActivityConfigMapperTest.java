package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.ActivityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ActivityConfigMapper 测试类
 */
@SpringBootTest
@Transactional
public class ActivityConfigMapperTest {

    @Autowired
    private ActivityConfigMapper activityConfigMapper;
    
    /**
     * 测试插入活动配置表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        ActivityConfig activityConfig = createTestActivityConfig("测试插入");
        
        // 执行插入
        int rows = activityConfigMapper.insert(activityConfig);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(activityConfig.getId());
        
        // 验证能否查询到
        ActivityConfig savedActivityConfig = activityConfigMapper.getById(activityConfig.getId());
        assertNotNull(savedActivityConfig);
        
        System.out.println("插入活动配置表成功: " + savedActivityConfig);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        ActivityConfig activityConfig = createTestActivityConfig("测试ID查询");
        activityConfigMapper.insert(activityConfig);
        
        // 执行查询
        ActivityConfig result = activityConfigMapper.getById(activityConfig.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(activityConfig.getId(), result.getId());
        
        System.out.println("根据ID查询活动配置表: " + result);
    }
    
    /**
     * 测试根据活动名称查询列表
     */
    @Test
    public void testFindByName() {
        // 创建测试数据
        ActivityConfig activityConfig1 = createTestActivityConfig("测试活动名称查询1");
        ActivityConfig activityConfig2 = createTestActivityConfig("测试活动名称查询2");
        // 设置相同的活动名称，确保能查到多条记录
        activityConfig1.setName(activityConfig2.getName());
        activityConfigMapper.insert(activityConfig1);
        activityConfigMapper.insert(activityConfig2);
        
        // 执行查询
        List<ActivityConfig> results = activityConfigMapper.findByName(activityConfig1.getName());
        
        // 验证结果
        assertNotNull(results);
        assertTrue(results.size() >= 2);
        
        // 验证包含测试数据
        boolean containsActivityConfig1 = results.stream().anyMatch(c -> c.getId().equals(activityConfig1.getId()));
        boolean containsActivityConfig2 = results.stream().anyMatch(c -> c.getId().equals(activityConfig2.getId()));
        assertTrue(containsActivityConfig1, "结果中不包含活动配置表1");
        assertTrue(containsActivityConfig2, "结果中不包含活动配置表2");
        
        System.out.println("根据活动名称查询活动配置表列表: " + results.size() + "条记录");
    }
    
    /**
     * 测试根据活动状态(0:未开始 1:进行中 2:已结束)查询列表
     */
    @Test
    public void testFindByStatus() {
        // 创建测试数据
        ActivityConfig activityConfig1 = createTestActivityConfig("测试活动状态(0:未开始 1:进行中 2:已结束)查询1");
        ActivityConfig activityConfig2 = createTestActivityConfig("测试活动状态(0:未开始 1:进行中 2:已结束)查询2");
        // 设置相同的活动状态(0:未开始 1:进行中 2:已结束)，确保能查到多条记录
        activityConfig1.setStatus(activityConfig2.getStatus());
        activityConfigMapper.insert(activityConfig1);
        activityConfigMapper.insert(activityConfig2);
        
        // 执行查询
        List<ActivityConfig> results = activityConfigMapper.findByStatus(activityConfig1.getStatus());
        
        // 验证结果
        assertNotNull(results);
        assertTrue(results.size() >= 2);
        
        // 验证包含测试数据
        boolean containsActivityConfig1 = results.stream().anyMatch(c -> c.getId().equals(activityConfig1.getId()));
        boolean containsActivityConfig2 = results.stream().anyMatch(c -> c.getId().equals(activityConfig2.getId()));
        assertTrue(containsActivityConfig1, "结果中不包含活动配置表1");
        assertTrue(containsActivityConfig2, "结果中不包含活动配置表2");
        
        System.out.println("根据活动状态(0:未开始 1:进行中 2:已结束)查询活动配置表列表: " + results.size() + "条记录");
    }
    
    /**
     * 测试查询所有活动配置表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        ActivityConfig activityConfig1 = createTestActivityConfig("测试查询所有1");
        ActivityConfig activityConfig2 = createTestActivityConfig("测试查询所有2");
        activityConfigMapper.insert(activityConfig1);
        activityConfigMapper.insert(activityConfig2);
        
        // 执行查询
        List<ActivityConfig> activityConfigList = activityConfigMapper.findAll();
        
        // 验证结果
        assertNotNull(activityConfigList);
        assertFalse(activityConfigList.isEmpty());
        
        // 验证包含测试数据
        boolean containsActivityConfig1 = activityConfigList.stream().anyMatch(c -> c.getId().equals(activityConfig1.getId()));
        boolean containsActivityConfig2 = activityConfigList.stream().anyMatch(c -> c.getId().equals(activityConfig2.getId()));
        assertTrue(containsActivityConfig1, "结果中不包含活动配置表1");
        assertTrue(containsActivityConfig2, "结果中不包含活动配置表2");
        
        System.out.println("查询所有活动配置表数量: " + activityConfigList.size());
    }
    
    /**
     * 测试查询所有启用的活动配置表
     */
    @Test
    public void testFindAllEnabled() {
        // 创建启用的测试数据
        ActivityConfig enabledActivityConfig = createTestActivityConfig("测试启用活动配置表");
        enabledActivityConfig.setStatus(1);
        activityConfigMapper.insert(enabledActivityConfig);
        
        // 创建禁用的测试数据
        ActivityConfig disabledActivityConfig = createTestActivityConfig("测试禁用活动配置表");
        disabledActivityConfig.setStatus(0);
        activityConfigMapper.insert(disabledActivityConfig);
        
        // 执行查询
        List<ActivityConfig> activityConfigList = activityConfigMapper.findAllEnabled();
        
        // 验证结果
        assertNotNull(activityConfigList);
        assertFalse(activityConfigList.isEmpty());
        
        // 验证所有结果都是启用状态且包含启用测试数据
        boolean containsEnabledActivityConfig = false;
        for (ActivityConfig activityConfig : activityConfigList) {
            assertEquals(1, activityConfig.getStatus(), "查询到非启用状态的活动配置表");
            if (activityConfig.getId().equals(enabledActivityConfig.getId())) {
                containsEnabledActivityConfig = true;
            }
        }
        assertTrue(containsEnabledActivityConfig, "结果中不包含测试创建的启用活动配置表");
        
        // 验证不包含禁用的测试数据
        boolean containsDisabledActivityConfig = activityConfigList.stream()
                .anyMatch(c -> c.getId().equals(disabledActivityConfig.getId()));
        assertFalse(containsDisabledActivityConfig, "结果中不应包含禁用活动配置表");
        
        System.out.println("查询所有启用活动配置表数量: " + activityConfigList.size());
    }
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        ActivityConfig activityConfig = createTestActivityConfig("测试条件查询");
        activityConfigMapper.insert(activityConfig);
        
        // 创建查询条件
        ActivityConfig query = new ActivityConfig();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<ActivityConfig> activityConfigList = activityConfigMapper.findList(query);
        
        // 验证结果
        assertNotNull(activityConfigList);
        
        System.out.println("条件查询结果数量: " + activityConfigList.size());
    }
    
    /**
     * 测试更新活动配置表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        ActivityConfig activityConfig = createTestActivityConfig("测试更新");
        activityConfigMapper.insert(activityConfig);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = activityConfigMapper.update(activityConfig);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        ActivityConfig updatedActivityConfig = activityConfigMapper.getById(activityConfig.getId());
        assertNotNull(updatedActivityConfig);
        
        System.out.println("更新活动配置表成功: " + updatedActivityConfig);
    }
    
    /**
     * 测试删除活动配置表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        ActivityConfig activityConfig = createTestActivityConfig("测试删除");
        activityConfigMapper.insert(activityConfig);
        
        // 验证创建成功
        assertNotNull(activityConfigMapper.getById(activityConfig.getId()));
        
        // 执行删除
        int rows = activityConfigMapper.deleteById(activityConfig.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(activityConfigMapper.getById(activityConfig.getId()));
        
        System.out.println("删除活动配置表成功, ID: " + activityConfig.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        ActivityConfig activityConfig1 = createTestActivityConfig("测试批量删除1");
        ActivityConfig activityConfig2 = createTestActivityConfig("测试批量删除2");
        activityConfigMapper.insert(activityConfig1);
        activityConfigMapper.insert(activityConfig2);
        
        // 验证创建成功
        assertNotNull(activityConfigMapper.getById(activityConfig1.getId()));
        assertNotNull(activityConfigMapper.getById(activityConfig2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { activityConfig1.getId(), activityConfig2.getId() };
        int rows = activityConfigMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(activityConfigMapper.getById(activityConfig1.getId()));
        assertNull(activityConfigMapper.getById(activityConfig2.getId()));
        
        System.out.println("批量删除活动配置表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用活动配置表（不保存到数据库）
     */
    private ActivityConfig createTestActivityConfig(String testName) {
        ActivityConfig activityConfig = new ActivityConfig();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        activityConfig.setName(testName + "_name");
        activityConfig.setStartTime(new Date(System.currentTimeMillis() + (long)(Math.random() * 86400000))); // 随机一天内的时间
        activityConfig.setEndTime(new Date(System.currentTimeMillis() + (long)(Math.random() * 86400000))); // 随机一天内的时间
        activityConfig.setTotalCells((int)(Math.random() * 100) + 1);
        activityConfig.setDailyGameLimit((int)(Math.random() * 100) + 1);
        activityConfig.setMaxDicePerTime((int)(Math.random() * 100) + 1);
        activityConfig.setStatus(1); // 默认启用状态
        return activityConfig;
    }
}
