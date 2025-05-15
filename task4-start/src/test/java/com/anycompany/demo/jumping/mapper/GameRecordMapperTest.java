package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.GameRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GameRecordMapper 测试类
 */
@SpringBootTest
@Transactional
public class GameRecordMapperTest {

    @Autowired
    private GameRecordMapper gameRecordMapper;
    
    /**
     * 测试插入游戏记录表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        GameRecord gameRecord = createTestGameRecord("测试插入");
        
        // 执行插入
        int rows = gameRecordMapper.insert(gameRecord);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(gameRecord.getId());
        
        // 验证能否查询到
        GameRecord savedGameRecord = gameRecordMapper.getById(gameRecord.getId());
        assertNotNull(savedGameRecord);
        
        System.out.println("插入游戏记录表成功: " + savedGameRecord);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        GameRecord gameRecord = createTestGameRecord("测试ID查询");
        gameRecordMapper.insert(gameRecord);
        
        // 执行查询
        GameRecord result = gameRecordMapper.getById(gameRecord.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(gameRecord.getId(), result.getId());
        
        System.out.println("根据ID查询游戏记录表: " + result);
    }
    
    /**
     * 测试查询所有游戏记录表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        GameRecord gameRecord1 = createTestGameRecord("测试查询所有1");
        GameRecord gameRecord2 = createTestGameRecord("测试查询所有2");
        gameRecordMapper.insert(gameRecord1);
        gameRecordMapper.insert(gameRecord2);
        
        // 执行查询
        List<GameRecord> gameRecordList = gameRecordMapper.findAll();
        
        // 验证结果
        assertNotNull(gameRecordList);
        assertFalse(gameRecordList.isEmpty());
        
        // 验证包含测试数据
        boolean containsGameRecord1 = gameRecordList.stream().anyMatch(c -> c.getId().equals(gameRecord1.getId()));
        boolean containsGameRecord2 = gameRecordList.stream().anyMatch(c -> c.getId().equals(gameRecord2.getId()));
        assertTrue(containsGameRecord1, "结果中不包含游戏记录表1");
        assertTrue(containsGameRecord2, "结果中不包含游戏记录表2");
        
        System.out.println("查询所有游戏记录表数量: " + gameRecordList.size());
    }
    
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        GameRecord gameRecord = createTestGameRecord("测试条件查询");
        gameRecordMapper.insert(gameRecord);
        
        // 创建查询条件
        GameRecord query = new GameRecord();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<GameRecord> gameRecordList = gameRecordMapper.findList(query);
        
        // 验证结果
        assertNotNull(gameRecordList);
        
        System.out.println("条件查询结果数量: " + gameRecordList.size());
    }
    
    /**
     * 测试更新游戏记录表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        GameRecord gameRecord = createTestGameRecord("测试更新");
        gameRecordMapper.insert(gameRecord);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = gameRecordMapper.update(gameRecord);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        GameRecord updatedGameRecord = gameRecordMapper.getById(gameRecord.getId());
        assertNotNull(updatedGameRecord);
        
        System.out.println("更新游戏记录表成功: " + updatedGameRecord);
    }
    
    /**
     * 测试删除游戏记录表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        GameRecord gameRecord = createTestGameRecord("测试删除");
        gameRecordMapper.insert(gameRecord);
        
        // 验证创建成功
        assertNotNull(gameRecordMapper.getById(gameRecord.getId()));
        
        // 执行删除
        int rows = gameRecordMapper.deleteById(gameRecord.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(gameRecordMapper.getById(gameRecord.getId()));
        
        System.out.println("删除游戏记录表成功, ID: " + gameRecord.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        GameRecord gameRecord1 = createTestGameRecord("测试批量删除1");
        GameRecord gameRecord2 = createTestGameRecord("测试批量删除2");
        gameRecordMapper.insert(gameRecord1);
        gameRecordMapper.insert(gameRecord2);
        
        // 验证创建成功
        assertNotNull(gameRecordMapper.getById(gameRecord1.getId()));
        assertNotNull(gameRecordMapper.getById(gameRecord2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { gameRecord1.getId(), gameRecord2.getId() };
        int rows = gameRecordMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(gameRecordMapper.getById(gameRecord1.getId()));
        assertNull(gameRecordMapper.getById(gameRecord2.getId()));
        
        System.out.println("批量删除游戏记录表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用游戏记录表（不保存到数据库）
     */
    private GameRecord createTestGameRecord(String testName) {
        GameRecord gameRecord = new GameRecord();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        gameRecord.setUserId(timestamp + (long)(Math.random() * 1000));
        gameRecord.setActivityId(timestamp + (long)(Math.random() * 1000));
        gameRecord.setDiceCount((int)(Math.random() * 100) + 1);
        gameRecord.setDicePoints(testName + "_dicePoints");
        gameRecord.setStartPosition((int)(Math.random() * 100) + 1);
        gameRecord.setEndPosition((int)(Math.random() * 100) + 1);
        gameRecord.setRewardInfo(testName + "_rewardInfo");
        gameRecord.setGainedPoints((int)(Math.random() * 100) + 1);
        gameRecord.setGameTime(new Date(System.currentTimeMillis() + (long)(Math.random() * 86400000))); // 随机一天内的时间
        return gameRecord;
    }
}
