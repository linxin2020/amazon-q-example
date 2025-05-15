package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.UserGiftRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserGiftRecordMapper 测试类
 */
@SpringBootTest
@Transactional
public class UserGiftRecordMapperTest {

    @Autowired
    private UserGiftRecordMapper userGiftRecordMapper;
    
    /**
     * 测试插入用户礼物道具记录表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        UserGiftRecord userGiftRecord = createTestUserGiftRecord("测试插入");
        
        // 执行插入
        int rows = userGiftRecordMapper.insert(userGiftRecord);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(userGiftRecord.getId());
        
        // 验证能否查询到
        UserGiftRecord savedUserGiftRecord = userGiftRecordMapper.getById(userGiftRecord.getId());
        assertNotNull(savedUserGiftRecord);
        
        System.out.println("插入用户礼物道具记录表成功: " + savedUserGiftRecord);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        UserGiftRecord userGiftRecord = createTestUserGiftRecord("测试ID查询");
        userGiftRecordMapper.insert(userGiftRecord);
        
        // 执行查询
        UserGiftRecord result = userGiftRecordMapper.getById(userGiftRecord.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(userGiftRecord.getId(), result.getId());
        
        System.out.println("根据ID查询用户礼物道具记录表: " + result);
    }
    
    /**
     * 测试根据用户ID查询
     */
    @Test
    public void testGetByUserId() {
        // 创建测试数据
        UserGiftRecord userGiftRecord = createTestUserGiftRecord("测试用户ID查询");
        userGiftRecordMapper.insert(userGiftRecord);
        
        // 执行查询
        UserGiftRecord result = userGiftRecordMapper.getByUserId(userGiftRecord.getUserId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(userGiftRecord.getId(), result.getId());
        assertEquals(userGiftRecord.getUserId(), result.getUserId());
        
        System.out.println("根据用户ID查询用户礼物道具记录表: " + result);
    }
    
    /**
     * 测试根据活动ID查询
     */
    @Test
    public void testGetByActivityId() {
        // 创建测试数据
        UserGiftRecord userGiftRecord = createTestUserGiftRecord("测试活动ID查询");
        userGiftRecordMapper.insert(userGiftRecord);
        
        // 执行查询
        UserGiftRecord result = userGiftRecordMapper.getByActivityId(userGiftRecord.getActivityId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(userGiftRecord.getId(), result.getId());
        assertEquals(userGiftRecord.getActivityId(), result.getActivityId());
        
        System.out.println("根据活动ID查询用户礼物道具记录表: " + result);
    }
    
    /**
     * 测试根据格子ID查询
     */
    @Test
    public void testGetByCellId() {
        // 创建测试数据
        UserGiftRecord userGiftRecord = createTestUserGiftRecord("测试格子ID查询");
        userGiftRecordMapper.insert(userGiftRecord);
        
        // 执行查询
        UserGiftRecord result = userGiftRecordMapper.getByCellId(userGiftRecord.getCellId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(userGiftRecord.getId(), result.getId());
        assertEquals(userGiftRecord.getCellId(), result.getCellId());
        
        System.out.println("根据格子ID查询用户礼物道具记录表: " + result);
    }
    
    /**
     * 测试根据状态(0:待发放 1:已发放)查询列表
     */
    @Test
    public void testFindByStatus() {
        // 创建测试数据
        UserGiftRecord userGiftRecord1 = createTestUserGiftRecord("测试状态(0:待发放 1:已发放)查询1");
        UserGiftRecord userGiftRecord2 = createTestUserGiftRecord("测试状态(0:待发放 1:已发放)查询2");
        // 设置相同的状态(0:待发放 1:已发放)，确保能查到多条记录
        userGiftRecord1.setStatus(userGiftRecord2.getStatus());
        userGiftRecordMapper.insert(userGiftRecord1);
        userGiftRecordMapper.insert(userGiftRecord2);
        
        // 执行查询
        List<UserGiftRecord> results = userGiftRecordMapper.findByStatus(userGiftRecord1.getStatus());
        
        // 验证结果
        assertNotNull(results);
        assertTrue(results.size() >= 2);
        
        // 验证包含测试数据
        boolean containsUserGiftRecord1 = results.stream().anyMatch(c -> c.getId().equals(userGiftRecord1.getId()));
        boolean containsUserGiftRecord2 = results.stream().anyMatch(c -> c.getId().equals(userGiftRecord2.getId()));
        assertTrue(containsUserGiftRecord1, "结果中不包含用户礼物道具记录表1");
        assertTrue(containsUserGiftRecord2, "结果中不包含用户礼物道具记录表2");
        
        System.out.println("根据状态(0:待发放 1:已发放)查询用户礼物道具记录表列表: " + results.size() + "条记录");
    }
    
    /**
     * 测试查询所有用户礼物道具记录表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        UserGiftRecord userGiftRecord1 = createTestUserGiftRecord("测试查询所有1");
        UserGiftRecord userGiftRecord2 = createTestUserGiftRecord("测试查询所有2");
        userGiftRecordMapper.insert(userGiftRecord1);
        userGiftRecordMapper.insert(userGiftRecord2);
        
        // 执行查询
        List<UserGiftRecord> userGiftRecordList = userGiftRecordMapper.findAll();
        
        // 验证结果
        assertNotNull(userGiftRecordList);
        assertFalse(userGiftRecordList.isEmpty());
        
        // 验证包含测试数据
        boolean containsUserGiftRecord1 = userGiftRecordList.stream().anyMatch(c -> c.getId().equals(userGiftRecord1.getId()));
        boolean containsUserGiftRecord2 = userGiftRecordList.stream().anyMatch(c -> c.getId().equals(userGiftRecord2.getId()));
        assertTrue(containsUserGiftRecord1, "结果中不包含用户礼物道具记录表1");
        assertTrue(containsUserGiftRecord2, "结果中不包含用户礼物道具记录表2");
        
        System.out.println("查询所有用户礼物道具记录表数量: " + userGiftRecordList.size());
    }
    
    /**
     * 测试查询所有启用的用户礼物道具记录表
     */
    @Test
    public void testFindAllEnabled() {
        // 创建启用的测试数据
        UserGiftRecord enabledUserGiftRecord = createTestUserGiftRecord("测试启用用户礼物道具记录表");
        enabledUserGiftRecord.setStatus(1);
        userGiftRecordMapper.insert(enabledUserGiftRecord);
        
        // 创建禁用的测试数据
        UserGiftRecord disabledUserGiftRecord = createTestUserGiftRecord("测试禁用用户礼物道具记录表");
        disabledUserGiftRecord.setStatus(0);
        userGiftRecordMapper.insert(disabledUserGiftRecord);
        
        // 执行查询
        List<UserGiftRecord> userGiftRecordList = userGiftRecordMapper.findAllEnabled();
        
        // 验证结果
        assertNotNull(userGiftRecordList);
        assertFalse(userGiftRecordList.isEmpty());
        
        // 验证所有结果都是启用状态且包含启用测试数据
        boolean containsEnabledUserGiftRecord = false;
        for (UserGiftRecord userGiftRecord : userGiftRecordList) {
            assertEquals(1, userGiftRecord.getStatus(), "查询到非启用状态的用户礼物道具记录表");
            if (userGiftRecord.getId().equals(enabledUserGiftRecord.getId())) {
                containsEnabledUserGiftRecord = true;
            }
        }
        assertTrue(containsEnabledUserGiftRecord, "结果中不包含测试创建的启用用户礼物道具记录表");
        
        // 验证不包含禁用的测试数据
        boolean containsDisabledUserGiftRecord = userGiftRecordList.stream()
                .anyMatch(c -> c.getId().equals(disabledUserGiftRecord.getId()));
        assertFalse(containsDisabledUserGiftRecord, "结果中不应包含禁用用户礼物道具记录表");
        
        System.out.println("查询所有启用用户礼物道具记录表数量: " + userGiftRecordList.size());
    }
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        UserGiftRecord userGiftRecord = createTestUserGiftRecord("测试条件查询");
        userGiftRecordMapper.insert(userGiftRecord);
        
        // 创建查询条件
        UserGiftRecord query = new UserGiftRecord();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<UserGiftRecord> userGiftRecordList = userGiftRecordMapper.findList(query);
        
        // 验证结果
        assertNotNull(userGiftRecordList);
        
        System.out.println("条件查询结果数量: " + userGiftRecordList.size());
    }
    
    /**
     * 测试更新用户礼物道具记录表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        UserGiftRecord userGiftRecord = createTestUserGiftRecord("测试更新");
        userGiftRecordMapper.insert(userGiftRecord);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = userGiftRecordMapper.update(userGiftRecord);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        UserGiftRecord updatedUserGiftRecord = userGiftRecordMapper.getById(userGiftRecord.getId());
        assertNotNull(updatedUserGiftRecord);
        
        System.out.println("更新用户礼物道具记录表成功: " + updatedUserGiftRecord);
    }
    
    /**
     * 测试删除用户礼物道具记录表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        UserGiftRecord userGiftRecord = createTestUserGiftRecord("测试删除");
        userGiftRecordMapper.insert(userGiftRecord);
        
        // 验证创建成功
        assertNotNull(userGiftRecordMapper.getById(userGiftRecord.getId()));
        
        // 执行删除
        int rows = userGiftRecordMapper.deleteById(userGiftRecord.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(userGiftRecordMapper.getById(userGiftRecord.getId()));
        
        System.out.println("删除用户礼物道具记录表成功, ID: " + userGiftRecord.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        UserGiftRecord userGiftRecord1 = createTestUserGiftRecord("测试批量删除1");
        UserGiftRecord userGiftRecord2 = createTestUserGiftRecord("测试批量删除2");
        userGiftRecordMapper.insert(userGiftRecord1);
        userGiftRecordMapper.insert(userGiftRecord2);
        
        // 验证创建成功
        assertNotNull(userGiftRecordMapper.getById(userGiftRecord1.getId()));
        assertNotNull(userGiftRecordMapper.getById(userGiftRecord2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { userGiftRecord1.getId(), userGiftRecord2.getId() };
        int rows = userGiftRecordMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(userGiftRecordMapper.getById(userGiftRecord1.getId()));
        assertNull(userGiftRecordMapper.getById(userGiftRecord2.getId()));
        
        System.out.println("批量删除用户礼物道具记录表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用用户礼物道具记录表（不保存到数据库）
     */
    private UserGiftRecord createTestUserGiftRecord(String testName) {
        UserGiftRecord userGiftRecord = new UserGiftRecord();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        userGiftRecord.setUserId(timestamp + (long)(Math.random() * 1000));
        userGiftRecord.setActivityId(timestamp + (long)(Math.random() * 1000));
        userGiftRecord.setCellId(timestamp + (long)(Math.random() * 1000));
        userGiftRecord.setGiftId(testName + "_giftId");
        userGiftRecord.setGiftType(testName + "_giftType");
        userGiftRecord.setGiftAmount((int)(Math.random() * 100) + 1);
        userGiftRecord.setReceiveTime(new Date(System.currentTimeMillis() + (long)(Math.random() * 86400000))); // 随机一天内的时间
        userGiftRecord.setStatus(1); // 默认启用状态
        return userGiftRecord;
    }
}
