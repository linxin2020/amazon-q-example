package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.TreasureBoxConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TreasureBoxConfigMapper 测试类
 */
@SpringBootTest
@Transactional
public class TreasureBoxConfigMapperTest {

    @Autowired
    private TreasureBoxConfigMapper treasureBoxConfigMapper;
    
    /**
     * 测试插入宝箱配置表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        TreasureBoxConfig treasureBoxConfig = createTestTreasureBoxConfig("测试插入");
        
        // 执行插入
        int rows = treasureBoxConfigMapper.insert(treasureBoxConfig);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(treasureBoxConfig.getId());
        
        // 验证能否查询到
        TreasureBoxConfig savedTreasureBoxConfig = treasureBoxConfigMapper.getById(treasureBoxConfig.getId());
        assertNotNull(savedTreasureBoxConfig);
        
        System.out.println("插入宝箱配置表成功: " + savedTreasureBoxConfig);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        TreasureBoxConfig treasureBoxConfig = createTestTreasureBoxConfig("测试ID查询");
        treasureBoxConfigMapper.insert(treasureBoxConfig);
        
        // 执行查询
        TreasureBoxConfig result = treasureBoxConfigMapper.getById(treasureBoxConfig.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(treasureBoxConfig.getId(), result.getId());
        
        System.out.println("根据ID查询宝箱配置表: " + result);
    }
    
    /**
     * 测试根据活动ID查询
     */
    @Test
    public void testGetByActivityId() {
        // 创建测试数据
        TreasureBoxConfig treasureBoxConfig = createTestTreasureBoxConfig("测试活动ID查询");
        treasureBoxConfigMapper.insert(treasureBoxConfig);
        
        // 执行查询
        TreasureBoxConfig result = treasureBoxConfigMapper.getByActivityId(treasureBoxConfig.getActivityId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(treasureBoxConfig.getId(), result.getId());
        assertEquals(treasureBoxConfig.getActivityId(), result.getActivityId());
        
        System.out.println("根据活动ID查询宝箱配置表: " + result);
    }
    
    /**
     * 测试根据宝箱等级查询
     */
    @Test
    public void testGetByBoxLevel() {
        // 创建测试数据
        TreasureBoxConfig treasureBoxConfig = createTestTreasureBoxConfig("测试宝箱等级查询");
        treasureBoxConfigMapper.insert(treasureBoxConfig);
        
        // 执行查询
        TreasureBoxConfig result = treasureBoxConfigMapper.getByBoxLevel(treasureBoxConfig.getBoxLevel());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(treasureBoxConfig.getId(), result.getId());
        assertEquals(treasureBoxConfig.getBoxLevel(), result.getBoxLevel());
        
        System.out.println("根据宝箱等级查询宝箱配置表: " + result);
    }
    
    /**
     * 测试查询所有宝箱配置表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        TreasureBoxConfig treasureBoxConfig1 = createTestTreasureBoxConfig("测试查询所有1");
        TreasureBoxConfig treasureBoxConfig2 = createTestTreasureBoxConfig("测试查询所有2");
        treasureBoxConfigMapper.insert(treasureBoxConfig1);
        treasureBoxConfigMapper.insert(treasureBoxConfig2);
        
        // 执行查询
        List<TreasureBoxConfig> treasureBoxConfigList = treasureBoxConfigMapper.findAll();
        
        // 验证结果
        assertNotNull(treasureBoxConfigList);
        assertFalse(treasureBoxConfigList.isEmpty());
        
        // 验证包含测试数据
        boolean containsTreasureBoxConfig1 = treasureBoxConfigList.stream().anyMatch(c -> c.getId().equals(treasureBoxConfig1.getId()));
        boolean containsTreasureBoxConfig2 = treasureBoxConfigList.stream().anyMatch(c -> c.getId().equals(treasureBoxConfig2.getId()));
        assertTrue(containsTreasureBoxConfig1, "结果中不包含宝箱配置表1");
        assertTrue(containsTreasureBoxConfig2, "结果中不包含宝箱配置表2");
        
        System.out.println("查询所有宝箱配置表数量: " + treasureBoxConfigList.size());
    }
    
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        TreasureBoxConfig treasureBoxConfig = createTestTreasureBoxConfig("测试条件查询");
        treasureBoxConfigMapper.insert(treasureBoxConfig);
        
        // 创建查询条件
        TreasureBoxConfig query = new TreasureBoxConfig();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<TreasureBoxConfig> treasureBoxConfigList = treasureBoxConfigMapper.findList(query);
        
        // 验证结果
        assertNotNull(treasureBoxConfigList);
        
        System.out.println("条件查询结果数量: " + treasureBoxConfigList.size());
    }
    
    /**
     * 测试更新宝箱配置表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        TreasureBoxConfig treasureBoxConfig = createTestTreasureBoxConfig("测试更新");
        treasureBoxConfigMapper.insert(treasureBoxConfig);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = treasureBoxConfigMapper.update(treasureBoxConfig);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        TreasureBoxConfig updatedTreasureBoxConfig = treasureBoxConfigMapper.getById(treasureBoxConfig.getId());
        assertNotNull(updatedTreasureBoxConfig);
        
        System.out.println("更新宝箱配置表成功: " + updatedTreasureBoxConfig);
    }
    
    /**
     * 测试删除宝箱配置表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        TreasureBoxConfig treasureBoxConfig = createTestTreasureBoxConfig("测试删除");
        treasureBoxConfigMapper.insert(treasureBoxConfig);
        
        // 验证创建成功
        assertNotNull(treasureBoxConfigMapper.getById(treasureBoxConfig.getId()));
        
        // 执行删除
        int rows = treasureBoxConfigMapper.deleteById(treasureBoxConfig.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(treasureBoxConfigMapper.getById(treasureBoxConfig.getId()));
        
        System.out.println("删除宝箱配置表成功, ID: " + treasureBoxConfig.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        TreasureBoxConfig treasureBoxConfig1 = createTestTreasureBoxConfig("测试批量删除1");
        TreasureBoxConfig treasureBoxConfig2 = createTestTreasureBoxConfig("测试批量删除2");
        treasureBoxConfigMapper.insert(treasureBoxConfig1);
        treasureBoxConfigMapper.insert(treasureBoxConfig2);
        
        // 验证创建成功
        assertNotNull(treasureBoxConfigMapper.getById(treasureBoxConfig1.getId()));
        assertNotNull(treasureBoxConfigMapper.getById(treasureBoxConfig2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { treasureBoxConfig1.getId(), treasureBoxConfig2.getId() };
        int rows = treasureBoxConfigMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(treasureBoxConfigMapper.getById(treasureBoxConfig1.getId()));
        assertNull(treasureBoxConfigMapper.getById(treasureBoxConfig2.getId()));
        
        System.out.println("批量删除宝箱配置表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用宝箱配置表（不保存到数据库）
     */
    private TreasureBoxConfig createTestTreasureBoxConfig(String testName) {
        TreasureBoxConfig treasureBoxConfig = new TreasureBoxConfig();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        treasureBoxConfig.setActivityId(timestamp + (long)(Math.random() * 1000));
        // 唯一索引字段，确保值唯一
        treasureBoxConfig.setBoxLevel(testName + "_boxLevel" + randomSuffix);
        treasureBoxConfig.setBoxName(testName + "_boxName");
        treasureBoxConfig.setRequiredPoints((int)(Math.random() * 100) + 1);
        treasureBoxConfig.setRewardType(testName + "_rewardType");
        treasureBoxConfig.setRewardContent(testName + "_rewardContent");
        treasureBoxConfig.setRewardDesc(testName + "_rewardDesc");
        treasureBoxConfig.setBoxIconUrl(testName + "_boxIconUrl");
        return treasureBoxConfig;
    }
}
