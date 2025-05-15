package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.GameCell;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GameCellMapper 测试类
 */
@SpringBootTest
@Transactional
public class GameCellMapperTest {

    @Autowired
    private GameCellMapper gameCellMapper;
    
    /**
     * 测试插入游戏格子表
     */
    @Test
    public void testInsert() {
        // 创建测试数据
        GameCell gameCell = createTestGameCell("测试插入");
        
        // 执行插入
        int rows = gameCellMapper.insert(gameCell);
        
        // 验证结果
        assertEquals(1, rows);
        assertNotNull(gameCell.getId());
        
        // 验证能否查询到
        GameCell savedGameCell = gameCellMapper.getById(gameCell.getId());
        assertNotNull(savedGameCell);
        
        System.out.println("插入游戏格子表成功: " + savedGameCell);
    }
    
    /**
     * 测试根据ID查询
     */
    @Test
    public void testGetById() {
        // 创建测试数据
        GameCell gameCell = createTestGameCell("测试ID查询");
        gameCellMapper.insert(gameCell);
        
        // 执行查询
        GameCell result = gameCellMapper.getById(gameCell.getId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(gameCell.getId(), result.getId(), "Game cell ID should match");
        
        System.out.println("根据ID查询游戏格子表: " + result);
    }
    
    /**
     * 测试根据活动ID查询
     */
    @Test
    public void testGetByActivityId() {
        // 创建测试数据
        GameCell gameCell = createTestGameCell("测试活动ID查询");
        gameCellMapper.insert(gameCell);
        
        // 执行查询
        GameCell result = gameCellMapper.getByActivityId(gameCell.getActivityId());
        
        // 验证结果
        assertNotNull(result);
        assertEquals(gameCell.getId(), result.getId(), "Game cell ID should match");
        assertEquals(gameCell.getActivityId(), result.getActivityId());
        
        System.out.println("根据活动ID查询游戏格子表: " + result);
    }
    
    /**
     * 测试根据格子索引查询
     */
    @Test
    public void testGetByCellIndex() {
        // 创建测试数据
        GameCell gameCell = createTestGameCell("测试格子索引查询");
        gameCellMapper.insert(gameCell);
        
        // 执行查询
        GameCell result = gameCellMapper.getByCellIndex(gameCell.getCellIndex());
        
        // 验证结果
        assertNotNull(result);
        // 不比较ID，因为可能有多个相同cellIndex的记录，只要cellIndex匹配即可
        assertEquals(gameCell.getCellIndex(), result.getCellIndex(), "Cell index should match");
        
        System.out.println("根据格子索引查询游戏格子表: " + result);
    }
    
    /**
     * 测试查询所有游戏格子表
     */
    @Test
    public void testFindAll() {
        // 创建测试数据
        GameCell gameCell1 = createTestGameCell("测试查询所有1");
        GameCell gameCell2 = createTestGameCell("测试查询所有2");
        gameCellMapper.insert(gameCell1);
        gameCellMapper.insert(gameCell2);
        
        // 执行查询
        List<GameCell> gameCellList = gameCellMapper.findAll();
        
        // 验证结果
        assertNotNull(gameCellList);
        assertFalse(gameCellList.isEmpty());
        
        // 验证包含测试数据
        boolean containsGameCell1 = gameCellList.stream().anyMatch(c -> c.getId().equals(gameCell1.getId()));
        boolean containsGameCell2 = gameCellList.stream().anyMatch(c -> c.getId().equals(gameCell2.getId()));
        assertTrue(containsGameCell1, "结果中不包含游戏格子表1");
        assertTrue(containsGameCell2, "结果中不包含游戏格子表2");
        
        System.out.println("查询所有游戏格子表数量: " + gameCellList.size());
    }
    
    
    /**
     * 测试条件查询
     */
    @Test
    public void testFindList() {
        // 创建测试数据
        GameCell gameCell = createTestGameCell("测试条件查询");
        gameCellMapper.insert(gameCell);
        
        // 创建查询条件
        GameCell query = new GameCell();
        // 设置查询条件，这里根据实际情况设置
        
        // 执行查询
        List<GameCell> gameCellList = gameCellMapper.findList(query);
        
        // 验证结果
        assertNotNull(gameCellList);
        
        System.out.println("条件查询结果数量: " + gameCellList.size());
    }
    
    /**
     * 测试更新游戏格子表
     */
    @Test
    public void testUpdate() {
        // 创建测试数据
        GameCell gameCell = createTestGameCell("测试更新");
        gameCellMapper.insert(gameCell);
        
        // 修改数据
        // 这里根据实际情况修改字段值
        
        // 执行更新
        int rows = gameCellMapper.update(gameCell);
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证更新是否生效
        GameCell updatedGameCell = gameCellMapper.getById(gameCell.getId());
        assertNotNull(updatedGameCell);
        
        System.out.println("更新游戏格子表成功: " + updatedGameCell);
    }
    
    /**
     * 测试删除游戏格子表
     */
    @Test
    public void testDeleteById() {
        // 创建测试数据
        GameCell gameCell = createTestGameCell("测试删除");
        gameCellMapper.insert(gameCell);
        
        // 验证创建成功
        assertNotNull(gameCellMapper.getById(gameCell.getId()));
        
        // 执行删除
        int rows = gameCellMapper.deleteById(gameCell.getId());
        
        // 验证结果
        assertEquals(1, rows);
        
        // 验证是否已删除
        assertNull(gameCellMapper.getById(gameCell.getId()));
        
        System.out.println("删除游戏格子表成功, ID: " + gameCell.getId());
    }
    
    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        // 创建测试数据
        GameCell gameCell1 = createTestGameCell("测试批量删除1");
        GameCell gameCell2 = createTestGameCell("测试批量删除2");
        gameCellMapper.insert(gameCell1);
        gameCellMapper.insert(gameCell2);
        
        // 验证创建成功
        assertNotNull(gameCellMapper.getById(gameCell1.getId()));
        assertNotNull(gameCellMapper.getById(gameCell2.getId()));
        
        // 执行批量删除
        Long[] ids = new Long[] { gameCell1.getId(), gameCell2.getId() };
        int rows = gameCellMapper.batchDelete(ids);
        
        // 验证结果
        assertEquals(2, rows);
        
        // 验证是否已删除
        assertNull(gameCellMapper.getById(gameCell1.getId()));
        assertNull(gameCellMapper.getById(gameCell2.getId()));
        
        System.out.println("批量删除游戏格子表成功, 数量: " + ids.length);
    }
    
    /**
     * 创建测试用游戏格子表（不保存到数据库）
     */
    private GameCell createTestGameCell(String testName) {
        GameCell gameCell = new GameCell();
        // 设置测试数据，使用随机值确保唯一性
        long timestamp = System.currentTimeMillis();
        String randomSuffix = "_" + timestamp + "_" + (int)(Math.random() * 10000);
        
        gameCell.setActivityId(timestamp + (long)(Math.random() * 1000));
        gameCell.setCellIndex((int)(Math.random() * 100) + 1);
        gameCell.setCellType(testName + "_cellType");
        gameCell.setRewardType(testName + "_rewardType");
        gameCell.setRewardAmount((int)(Math.random() * 100) + 1);
        gameCell.setRewardId(testName + "_rewardId");
        gameCell.setRewardDesc(testName + "_rewardDesc");
        gameCell.setFallbackPoints((int)(Math.random() * 100) + 1);
        gameCell.setIconUrl(testName + "_iconUrl");
        return gameCell;
    }
}
