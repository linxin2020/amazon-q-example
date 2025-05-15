package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.LeaderboardSnapshot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LeaderboardSnapshotMapper 测试类
 */
@SpringBootTest
@Transactional
public class LeaderboardSnapshotMapperTest {

    @Autowired
    private LeaderboardSnapshotMapper leaderboardSnapshotMapper;
    
    /**
     * 测试插入排行榜快照表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        LeaderboardSnapshot leaderboardSnapshot = createTestLeaderboardSnapshot("测试插入");
        
        // 执行插入
        int rows = leaderboardSnapshotMapper.insert(leaderboardSnapshot);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(leaderboardSnapshot.getId());
        
        // 验证能否查询到
        LeaderboardSnapshot savedLeaderboardSnapshot = leaderboardSnapshotMapper.getById(leaderboardSnapshot.getId());
        assertNotNull(savedLeaderboardSnapshot);
        
        System.out.println("插入排行榜快照表成功: " + savedLeaderboardSnapshot);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        LeaderboardSnapshot leaderboardSnapshot = createTestLeaderboardSnapshot("测试ID查询");
        leaderboardSnapshotMapper.insert(leaderboardSnapshot);
        
        // 执行查询
        LeaderboardSnapshot result = leaderboardSnapshotMapper.getById(leaderboardSnapshot.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(leaderboardSnapshot.getId(), result.getId());
        
        System.out.println("根据ID查询排行榜快照表: " + result);
    }
    
    /**
     * 测试查询所有排行榜快照表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        LeaderboardSnapshot leaderboardSnapshot1 = createTestLeaderboardSnapshot("测试查询所有1");
        LeaderboardSnapshot leaderboardSnapshot2 = createTestLeaderboardSnapshot("测试查询所有2");
        leaderboardSnapshotMapper.insert(leaderboardSnapshot1);
        leaderboardSnapshotMapper.insert(leaderboardSnapshot2);
        
        // 执行查询
        List<LeaderboardSnapshot> leaderboardSnapshotList = leaderboardSnapshotMapper.findAll();
        
        // 验证结果
        assertNotNull(leaderboardSnapshotList);
        assertFalse(leaderboardSnapshotList.isEmpty());
        
        // 验证包含测试数据
        boolean containsLeaderboardSnapshot1 = leaderboardSnapshotList.stream().anyMatch(c -> c.getId().equals(leaderboardSnapshot1.getId()));
        boolean containsLeaderboardSnapshot2 = leaderboardSnapshotList.stream().anyMatch(c -> c.getId().equals(leaderboardSnapshot2.getId()));
        assertTrue(containsLeaderboardSnapshot1, "结果中不包含排行榜快照表1");
        assertTrue(containsLeaderboardSnapshot2, "结果中不包含排行榜快照表2");
        
        System.out.println("查询所有排行榜快照表数量: " + leaderboardSnapshotList.size());
    }
    
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        LeaderboardSnapshot leaderboardSnapshot = createTestLeaderboardSnapshot("测试条件查询");
        leaderboardSnapshotMapper.insert(leaderboardSnapshot);
        
        // 创建查询条件
        LeaderboardSnapshot query = new LeaderboardSnapshot();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<LeaderboardSnapshot> leaderboardSnapshotList = leaderboardSnapshotMapper.findList(query);
        
        // 验证结果
        assertNotNull(leaderboardSnapshotList);
        
        System.out.println("条件查询结果数量: " + leaderboardSnapshotList.size());
    }
    
    /**
     * 测试更新排行榜快照表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        LeaderboardSnapshot leaderboardSnapshot = createTestLeaderboardSnapshot("测试更新");
        leaderboardSnapshotMapper.insert(leaderboardSnapshot);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = leaderboardSnapshotMapper.update(leaderboardSnapshot);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        LeaderboardSnapshot updatedLeaderboardSnapshot = leaderboardSnapshotMapper.getById(leaderboardSnapshot.getId());
        assertNotNull(updatedLeaderboardSnapshot);
        
        System.out.println("更新排行榜快照表成功: " + updatedLeaderboardSnapshot);
    }
    
    /**
     * 测试删除排行榜快照表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        LeaderboardSnapshot leaderboardSnapshot = createTestLeaderboardSnapshot("测试删除");
        leaderboardSnapshotMapper.insert(leaderboardSnapshot);
        
        // 验证创建成功
        assertNotNull(leaderboardSnapshotMapper.getById(leaderboardSnapshot.getId()));
        
        // 执行删除
        int rows = leaderboardSnapshotMapper.deleteById(leaderboardSnapshot.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(leaderboardSnapshotMapper.getById(leaderboardSnapshot.getId()));
        
        System.out.println("删除排行榜快照表成功, ID: " + leaderboardSnapshot.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        LeaderboardSnapshot leaderboardSnapshot1 = createTestLeaderboardSnapshot("测试批量删除1");
        LeaderboardSnapshot leaderboardSnapshot2 = createTestLeaderboardSnapshot("测试批量删除2");
        leaderboardSnapshotMapper.insert(leaderboardSnapshot1);
        leaderboardSnapshotMapper.insert(leaderboardSnapshot2);
        
        // 验证创建成功
        assertNotNull(leaderboardSnapshotMapper.getById(leaderboardSnapshot1.getId()));
        assertNotNull(leaderboardSnapshotMapper.getById(leaderboardSnapshot2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { leaderboardSnapshot1.getId(), leaderboardSnapshot2.getId() };
        int rows = leaderboardSnapshotMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(leaderboardSnapshotMapper.getById(leaderboardSnapshot1.getId()));
        assertNull(leaderboardSnapshotMapper.getById(leaderboardSnapshot2.getId()));
        
        System.out.println("批量删除排行榜快照表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用排行榜快照表（不保存到数据库）
     */
    private LeaderboardSnapshot createTestLeaderboardSnapshot(String testName) {
        LeaderboardSnapshot leaderboardSnapshot = new LeaderboardSnapshot();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        leaderboardSnapshot.setActivityId(timestamp + (long)(Math.random() * 1000));
        leaderboardSnapshot.setSnapshotType(testName + "_snapshotType");
        leaderboardSnapshot.setRankData(testName + "_rankData");
        leaderboardSnapshot.setSnapshotTime(new Date(System.currentTimeMillis() + (long)(Math.random() * 86400000))); // 随机一天内的时间
        return leaderboardSnapshot;
    }
}
