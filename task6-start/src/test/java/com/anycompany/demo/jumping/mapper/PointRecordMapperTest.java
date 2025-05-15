package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.PointRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PointRecordMapper 测试类
 */
@SpringBootTest
@Transactional
public class PointRecordMapperTest {

    @Autowired
    private PointRecordMapper pointRecordMapper;
    
    /**
     * 测试插入积分记录表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        PointRecord pointRecord = createTestPointRecord("测试插入");
        
        // 执行插入
        int rows = pointRecordMapper.insert(pointRecord);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(pointRecord.getId());
        
        // 验证能否查询到
        PointRecord savedPointRecord = pointRecordMapper.getById(pointRecord.getId());
        assertNotNull(savedPointRecord);
        
        System.out.println("插入积分记录表成功: " + savedPointRecord);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        PointRecord pointRecord = createTestPointRecord("测试ID查询");
        pointRecordMapper.insert(pointRecord);
        
        // 执行查询
        PointRecord result = pointRecordMapper.getById(pointRecord.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(pointRecord.getId(), result.getId());
        
        System.out.println("根据ID查询积分记录表: " + result);
    }
    
    /**
     * 测试查询所有积分记录表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        PointRecord pointRecord1 = createTestPointRecord("测试查询所有1");
        PointRecord pointRecord2 = createTestPointRecord("测试查询所有2");
        pointRecordMapper.insert(pointRecord1);
        pointRecordMapper.insert(pointRecord2);
        
        // 执行查询
        List<PointRecord> pointRecordList = pointRecordMapper.findAll();
        
        // 验证结果
        assertNotNull(pointRecordList);
        assertFalse(pointRecordList.isEmpty());
        
        // 验证包含测试数据
        boolean containsPointRecord1 = pointRecordList.stream().anyMatch(c -> c.getId().equals(pointRecord1.getId()));
        boolean containsPointRecord2 = pointRecordList.stream().anyMatch(c -> c.getId().equals(pointRecord2.getId()));
        assertTrue(containsPointRecord1, "结果中不包含积分记录表1");
        assertTrue(containsPointRecord2, "结果中不包含积分记录表2");
        
        System.out.println("查询所有积分记录表数量: " + pointRecordList.size());
    }
    
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        PointRecord pointRecord = createTestPointRecord("测试条件查询");
        pointRecordMapper.insert(pointRecord);
        
        // 创建查询条件
        PointRecord query = new PointRecord();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<PointRecord> pointRecordList = pointRecordMapper.findList(query);
        
        // 验证结果
        assertNotNull(pointRecordList);
        
        System.out.println("条件查询结果数量: " + pointRecordList.size());
    }
    
    /**
     * 测试更新积分记录表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        PointRecord pointRecord = createTestPointRecord("测试更新");
        pointRecordMapper.insert(pointRecord);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = pointRecordMapper.update(pointRecord);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        PointRecord updatedPointRecord = pointRecordMapper.getById(pointRecord.getId());
        assertNotNull(updatedPointRecord);
        
        System.out.println("更新积分记录表成功: " + updatedPointRecord);
    }
    
    /**
     * 测试删除积分记录表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        PointRecord pointRecord = createTestPointRecord("测试删除");
        pointRecordMapper.insert(pointRecord);
        
        // 验证创建成功
        assertNotNull(pointRecordMapper.getById(pointRecord.getId()));
        
        // 执行删除
        int rows = pointRecordMapper.deleteById(pointRecord.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(pointRecordMapper.getById(pointRecord.getId()));
        
        System.out.println("删除积分记录表成功, ID: " + pointRecord.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        PointRecord pointRecord1 = createTestPointRecord("测试批量删除1");
        PointRecord pointRecord2 = createTestPointRecord("测试批量删除2");
        pointRecordMapper.insert(pointRecord1);
        pointRecordMapper.insert(pointRecord2);
        
        // 验证创建成功
        assertNotNull(pointRecordMapper.getById(pointRecord1.getId()));
        assertNotNull(pointRecordMapper.getById(pointRecord2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { pointRecord1.getId(), pointRecord2.getId() };
        int rows = pointRecordMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(pointRecordMapper.getById(pointRecord1.getId()));
        assertNull(pointRecordMapper.getById(pointRecord2.getId()));
        
        System.out.println("批量删除积分记录表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用积分记录表（不保存到数据库）
     */
    private PointRecord createTestPointRecord(String testName) {
        PointRecord pointRecord = new PointRecord();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        pointRecord.setUserId(timestamp + (long)(Math.random() * 1000));
        pointRecord.setActivityId(timestamp + (long)(Math.random() * 1000));
        pointRecord.setPoints((int)(Math.random() * 100) + 1);
        pointRecord.setOperationType(testName + "_operationType");
        pointRecord.setOperationDesc(testName + "_operationDesc");
        pointRecord.setReferenceId(timestamp + (long)(Math.random() * 1000));
        pointRecord.setReferenceType(testName + "_referenceType");
        pointRecord.setOperationTime(new Date(System.currentTimeMillis() + (long)(Math.random() * 86400000))); // 随机一天内的时间
        return pointRecord;
    }
}
