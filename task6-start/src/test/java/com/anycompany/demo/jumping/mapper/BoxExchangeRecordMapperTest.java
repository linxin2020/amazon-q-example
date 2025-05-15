package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.BoxExchangeRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BoxExchangeRecordMapper 测试类
 */
@SpringBootTest
@Transactional
public class BoxExchangeRecordMapperTest {

    @Autowired
    private BoxExchangeRecordMapper boxExchangeRecordMapper;
    
    /**
     * 测试插入宝箱兑换记录表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        BoxExchangeRecord boxExchangeRecord = createTestBoxExchangeRecord("测试插入");
        
        // 执行插入
        int rows = boxExchangeRecordMapper.insert(boxExchangeRecord);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(boxExchangeRecord.getId());
        
        // 验证能否查询到
        BoxExchangeRecord savedBoxExchangeRecord = boxExchangeRecordMapper.getById(boxExchangeRecord.getId());
        assertNotNull(savedBoxExchangeRecord);
        
        System.out.println("插入宝箱兑换记录表成功: " + savedBoxExchangeRecord);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        BoxExchangeRecord boxExchangeRecord = createTestBoxExchangeRecord("测试ID查询");
        boxExchangeRecordMapper.insert(boxExchangeRecord);
        
        // 执行查询
        BoxExchangeRecord result = boxExchangeRecordMapper.getById(boxExchangeRecord.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(boxExchangeRecord.getId(), result.getId());
        
        System.out.println("根据ID查询宝箱兑换记录表: " + result);
    }
    
    /**
     * 测试根据状态(0:处理中 1:成功 2:失败)查询列表
     */
    @Test
    public void testFindByStatus() {
        // 创建测试数据
        BoxExchangeRecord boxExchangeRecord1 = createTestBoxExchangeRecord("测试状态(0:处理中 1:成功 2:失败)查询1");
        BoxExchangeRecord boxExchangeRecord2 = createTestBoxExchangeRecord("测试状态(0:处理中 1:成功 2:失败)查询2");
        // 设置相同的状态(0:处理中 1:成功 2:失败)，确保能查到多条记录
        boxExchangeRecord1.setStatus(boxExchangeRecord2.getStatus());
        boxExchangeRecordMapper.insert(boxExchangeRecord1);
        boxExchangeRecordMapper.insert(boxExchangeRecord2);
        
        // 执行查询
        List<BoxExchangeRecord> results = boxExchangeRecordMapper.findByStatus(boxExchangeRecord1.getStatus());
        
        // 验证结果
        assertNotNull(results);
        assertTrue(results.size() >= 2);
        
        // 验证包含测试数据
        boolean containsBoxExchangeRecord1 = results.stream().anyMatch(c -> c.getId().equals(boxExchangeRecord1.getId()));
        boolean containsBoxExchangeRecord2 = results.stream().anyMatch(c -> c.getId().equals(boxExchangeRecord2.getId()));
        assertTrue(containsBoxExchangeRecord1, "结果中不包含宝箱兑换记录表1");
        assertTrue(containsBoxExchangeRecord2, "结果中不包含宝箱兑换记录表2");
        
        System.out.println("根据状态(0:处理中 1:成功 2:失败)查询宝箱兑换记录表列表: " + results.size() + "条记录");
    }
    
    /**
     * 测试查询所有宝箱兑换记录表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        BoxExchangeRecord boxExchangeRecord1 = createTestBoxExchangeRecord("测试查询所有1");
        BoxExchangeRecord boxExchangeRecord2 = createTestBoxExchangeRecord("测试查询所有2");
        boxExchangeRecordMapper.insert(boxExchangeRecord1);
        boxExchangeRecordMapper.insert(boxExchangeRecord2);
        
        // 执行查询
        List<BoxExchangeRecord> boxExchangeRecordList = boxExchangeRecordMapper.findAll();
        
        // 验证结果
        assertNotNull(boxExchangeRecordList);
        assertFalse(boxExchangeRecordList.isEmpty());
        
        // 验证包含测试数据
        boolean containsBoxExchangeRecord1 = boxExchangeRecordList.stream().anyMatch(c -> c.getId().equals(boxExchangeRecord1.getId()));
        boolean containsBoxExchangeRecord2 = boxExchangeRecordList.stream().anyMatch(c -> c.getId().equals(boxExchangeRecord2.getId()));
        assertTrue(containsBoxExchangeRecord1, "结果中不包含宝箱兑换记录表1");
        assertTrue(containsBoxExchangeRecord2, "结果中不包含宝箱兑换记录表2");
        
        System.out.println("查询所有宝箱兑换记录表数量: " + boxExchangeRecordList.size());
    }
    
    /**
     * 测试查询所有启用的宝箱兑换记录表
     */
    @Test
    public void testFindAllEnabled() {
        // 创建启用的测试数据
        BoxExchangeRecord enabledBoxExchangeRecord = createTestBoxExchangeRecord("测试启用宝箱兑换记录表");
        enabledBoxExchangeRecord.setStatus(1);
        boxExchangeRecordMapper.insert(enabledBoxExchangeRecord);
        
        // 创建禁用的测试数据
        BoxExchangeRecord disabledBoxExchangeRecord = createTestBoxExchangeRecord("测试禁用宝箱兑换记录表");
        disabledBoxExchangeRecord.setStatus(0);
        boxExchangeRecordMapper.insert(disabledBoxExchangeRecord);
        
        // 执行查询
        List<BoxExchangeRecord> boxExchangeRecordList = boxExchangeRecordMapper.findAllEnabled();
        
        // 验证结果
        assertNotNull(boxExchangeRecordList);
        assertFalse(boxExchangeRecordList.isEmpty());
        
        // 验证所有结果都是启用状态且包含启用测试数据
        boolean containsEnabledBoxExchangeRecord = false;
        for (BoxExchangeRecord boxExchangeRecord : boxExchangeRecordList) {
            assertEquals(1, boxExchangeRecord.getStatus(), "查询到非启用状态的宝箱兑换记录表");
            if (boxExchangeRecord.getId().equals(enabledBoxExchangeRecord.getId())) {
                containsEnabledBoxExchangeRecord = true;
            }
        }
        assertTrue(containsEnabledBoxExchangeRecord, "结果中不包含测试创建的启用宝箱兑换记录表");
        
        // 验证不包含禁用的测试数据
        boolean containsDisabledBoxExchangeRecord = boxExchangeRecordList.stream()
                .anyMatch(c -> c.getId().equals(disabledBoxExchangeRecord.getId()));
        assertFalse(containsDisabledBoxExchangeRecord, "结果中不应包含禁用宝箱兑换记录表");
        
        System.out.println("查询所有启用宝箱兑换记录表数量: " + boxExchangeRecordList.size());
    }
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        BoxExchangeRecord boxExchangeRecord = createTestBoxExchangeRecord("测试条件查询");
        boxExchangeRecordMapper.insert(boxExchangeRecord);
        
        // 创建查询条件
        BoxExchangeRecord query = new BoxExchangeRecord();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<BoxExchangeRecord> boxExchangeRecordList = boxExchangeRecordMapper.findList(query);
        
        // 验证结果
        assertNotNull(boxExchangeRecordList);
        
        System.out.println("条件查询结果数量: " + boxExchangeRecordList.size());
    }
    
    /**
     * 测试更新宝箱兑换记录表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        BoxExchangeRecord boxExchangeRecord = createTestBoxExchangeRecord("测试更新");
        boxExchangeRecordMapper.insert(boxExchangeRecord);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = boxExchangeRecordMapper.update(boxExchangeRecord);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        BoxExchangeRecord updatedBoxExchangeRecord = boxExchangeRecordMapper.getById(boxExchangeRecord.getId());
        assertNotNull(updatedBoxExchangeRecord);
        
        System.out.println("更新宝箱兑换记录表成功: " + updatedBoxExchangeRecord);
    }
    
    /**
     * 测试删除宝箱兑换记录表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        BoxExchangeRecord boxExchangeRecord = createTestBoxExchangeRecord("测试删除");
        boxExchangeRecordMapper.insert(boxExchangeRecord);
        
        // 验证创建成功
        assertNotNull(boxExchangeRecordMapper.getById(boxExchangeRecord.getId()));
        
        // 执行删除
        int rows = boxExchangeRecordMapper.deleteById(boxExchangeRecord.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(boxExchangeRecordMapper.getById(boxExchangeRecord.getId()));
        
        System.out.println("删除宝箱兑换记录表成功, ID: " + boxExchangeRecord.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        BoxExchangeRecord boxExchangeRecord1 = createTestBoxExchangeRecord("测试批量删除1");
        BoxExchangeRecord boxExchangeRecord2 = createTestBoxExchangeRecord("测试批量删除2");
        boxExchangeRecordMapper.insert(boxExchangeRecord1);
        boxExchangeRecordMapper.insert(boxExchangeRecord2);
        
        // 验证创建成功
        assertNotNull(boxExchangeRecordMapper.getById(boxExchangeRecord1.getId()));
        assertNotNull(boxExchangeRecordMapper.getById(boxExchangeRecord2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { boxExchangeRecord1.getId(), boxExchangeRecord2.getId() };
        int rows = boxExchangeRecordMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(boxExchangeRecordMapper.getById(boxExchangeRecord1.getId()));
        assertNull(boxExchangeRecordMapper.getById(boxExchangeRecord2.getId()));
        
        System.out.println("批量删除宝箱兑换记录表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用宝箱兑换记录表（不保存到数据库）
     */
    private BoxExchangeRecord createTestBoxExchangeRecord(String testName) {
        BoxExchangeRecord boxExchangeRecord = new BoxExchangeRecord();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        boxExchangeRecord.setUserId(timestamp + (long)(Math.random() * 1000));
        boxExchangeRecord.setActivityId(timestamp + (long)(Math.random() * 1000));
        boxExchangeRecord.setBoxId(timestamp + (long)(Math.random() * 1000));
        boxExchangeRecord.setBoxLevel(testName + "_boxLevel");
        boxExchangeRecord.setCostPoints((int)(Math.random() * 100) + 1);
        boxExchangeRecord.setRewardInfo(testName + "_rewardInfo");
        boxExchangeRecord.setStatus(1); // 默认启用状态
        boxExchangeRecord.setExchangeTime(new Date(System.currentTimeMillis() + (long)(Math.random() * 86400000))); // 随机一天内的时间
        return boxExchangeRecord;
    }
}
