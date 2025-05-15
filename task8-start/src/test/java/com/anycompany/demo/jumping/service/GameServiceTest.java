package com.anycompany.demo.jumping.service;

import com.anycompany.demo.jumping.base.BaseTest;
import com.anycompany.demo.jumping.mapper.GameCellMapper;
import com.anycompany.demo.jumping.mapper.UserGameDataMapper;
import com.anycompany.demo.jumping.model.ActivityConfig;
import com.anycompany.demo.jumping.model.GameCell;
import com.anycompany.demo.jumping.model.User;
import com.anycompany.demo.jumping.model.UserGameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GameService 测试类
 */
public class GameServiceTest extends BaseTest {

    @Autowired
    private GameService gameService;
    
    @Autowired
    private GameCellMapper gameCellMapper;
    
    @Autowired
    private UserGameDataMapper userGameDataMapper;
    
    private ActivityConfig testActivity;
    private User testUser;
    private List<GameCell> testGameCells;
    
    /**
     * 测试前准备数据
     */
    @BeforeEach
    public void setUp() {
        // 创建测试活动
        testActivity = createAndSaveTestActivity("游戏测试活动");
        
        // 创建测试用户
        testUser = createAndSaveTestUser("game_test");
        
        // 创建测试游戏格子
        testGameCells = createTestGameCells(testActivity.getId());
    }
    
    /**
     * 测试获取游戏数据
     */
    @Test
    public void testGetGameData() {
        // 获取游戏数据
        Map<String, Object> gameData = gameService.getGameData(testActivity.getId(), testUser.getId());
        
        // 验证返回结果
        assertNotNull(gameData, "游戏数据不应为空");
        
        // 验证进度信息
        Map<String, Object> progress = (Map<String, Object>) gameData.get("progress");
        assertNotNull(progress, "进度信息不应为空");
        assertEquals(testUser.getId(), progress.get("userId"));
        assertEquals(testActivity.getId(), progress.get("activityId"));
        assertNotNull(progress.get("currentPosition"));
        assertNotNull(progress.get("remainingChances"));
        assertNotNull(progress.get("dailyChancesUsed"));
        assertNotNull(progress.get("userPoints"));
        assertNotNull(progress.get("gameDate"));
        
        // 验证格子信息
        List<Map<String, Object>> cells = (List<Map<String, Object>>) gameData.get("cells");
        assertNotNull(cells, "格子信息不应为空");
        assertEquals(testGameCells.size(), cells.size(), "格子数量应匹配");
        
        // 验证第一个格子信息
        Map<String, Object> firstCell = cells.get(0);
        assertEquals(0, firstCell.get("cellIndex"));
        assertEquals(1, firstCell.get("cellType")); // START 类型转换为 1
        assertEquals(1, firstCell.get("rewardType")); // POINTS 类型转换为 1
        assertEquals(0, firstCell.get("rewardAmount"));
        assertEquals("起点", firstCell.get("description"));
        
        // 验证最后一个格子信息
        Map<String, Object> lastCell = cells.get(cells.size() - 1);
        assertEquals(testGameCells.size() - 1, lastCell.get("cellIndex"));
        assertEquals(5, lastCell.get("cellType")); // END 类型转换为 5
        assertEquals(1, lastCell.get("rewardType")); // POINTS 类型转换为 1
        assertEquals(100, lastCell.get("rewardAmount"));
        assertEquals("终点", lastCell.get("description"));
        
        System.out.println("获取游戏数据成功: 进度信息和格子信息验证通过");
    }
    
    /**
     * 测试获取用户游戏进度 - 新用户
     */
    @Test
    public void testGetUserGameProgressForNewUser() {
        // 获取用户游戏进度
        UserGameData userGameData = gameService.getUserGameProgress(testActivity.getId(), testUser.getId());
        
        // 验证返回结果
        assertNotNull(userGameData, "用户游戏进度不应为空");
        assertEquals(testUser.getId(), userGameData.getUserId());
        assertEquals(testActivity.getId(), userGameData.getActivityId());
        assertEquals(0, userGameData.getCurrentPosition()); // 新用户起始位置为0
        assertEquals(0, userGameData.getRemainingChances()); // 新用户初始没有游戏机会
        assertEquals(0, userGameData.getDailyUsedChances()); // 新用户初始每日已用机会为0
        assertEquals(0, userGameData.getTotalPoints()); // 新用户初始积分为0
        assertEquals(1, userGameData.getIsNewUser()); // 标记为新用户
        
        System.out.println("获取新用户游戏进度成功: " + userGameData);
    }
    
    /**
     * 测试获取用户游戏进度 - 已有进度的用户
     */
    @Test
    public void testGetUserGameProgressForExistingUser() {
        // 创建用户游戏进度
        UserGameData initialData = new UserGameData();
        initialData.setUserId(testUser.getId());
        initialData.setActivityId(testActivity.getId());
        initialData.setCurrentPosition(5);
        initialData.setRemainingChances(10);
        initialData.setDailyUsedChances(20);
        initialData.setTotalPoints(500);
        initialData.setLastGameTime(new Date());
        initialData.setIsNewUser(0);
        userGameDataMapper.insert(initialData);
        
        // 获取用户游戏进度
        UserGameData userGameData = gameService.getUserGameProgress(testActivity.getId(), testUser.getId());
        
        // 验证返回结果
        assertNotNull(userGameData, "用户游戏进度不应为空");
        assertEquals(testUser.getId(), userGameData.getUserId());
        assertEquals(testActivity.getId(), userGameData.getActivityId());
        assertEquals(5, userGameData.getCurrentPosition());
        assertEquals(10, userGameData.getRemainingChances());
        assertEquals(20, userGameData.getDailyUsedChances());
        assertEquals(500, userGameData.getTotalPoints());
        assertEquals(0, userGameData.getIsNewUser());
        
        System.out.println("获取已有进度用户的游戏进度成功: " + userGameData);
    }
    
    /**
     * 测试获取活动的所有格子信息
     */
    @Test
    public void testGetGameCells() {
        // 获取活动的所有格子信息
        List<GameCell> gameCells = gameService.getGameCells(testActivity.getId());
        
        // 验证返回结果
        assertNotNull(gameCells, "格子信息不应为空");
        assertEquals(testGameCells.size(), gameCells.size(), "格子数量应匹配");
        
        // 验证格子内容
        for (int i = 0; i < testGameCells.size(); i++) {
            GameCell expected = testGameCells.get(i);
            GameCell actual = gameCells.get(i);
            
            assertEquals(expected.getActivityId(), actual.getActivityId());
            assertEquals(expected.getCellIndex(), actual.getCellIndex());
            assertEquals(expected.getCellType(), actual.getCellType());
            assertEquals(expected.getRewardType(), actual.getRewardType());
            assertEquals(expected.getRewardAmount(), actual.getRewardAmount());
            assertEquals(expected.getRewardDesc(), actual.getRewardDesc());
        }
        
        System.out.println("获取活动格子信息成功，共 " + gameCells.size() + " 个格子");
    }
    
    /**
     * 测试初始化用户游戏进度
     */
    @Test
    public void testInitUserGameProgress() {
        // 初始化用户游戏进度
        UserGameData userGameData = gameService.initUserGameProgress(testActivity.getId(), testUser.getId());
        
        // 验证返回结果
        assertNotNull(userGameData, "用户游戏进度不应为空");
        assertNotNull(userGameData.getId(), "用户游戏进度ID不应为空");
        assertEquals(testUser.getId(), userGameData.getUserId());
        assertEquals(testActivity.getId(), userGameData.getActivityId());
        assertEquals(0, userGameData.getCurrentPosition());
        assertEquals(0, userGameData.getRemainingChances());
        assertEquals(0, userGameData.getDailyUsedChances());
        assertEquals(0, userGameData.getTotalPoints());
        assertEquals(1, userGameData.getIsNewUser());
        
        // 验证数据已保存到数据库
        UserGameData savedData = userGameDataMapper.getById(userGameData.getId());
        assertNotNull(savedData, "保存的用户游戏进度不应为空");
        
        System.out.println("初始化用户游戏进度成功: " + userGameData);
    }
    
    /**
     * 测试重置用户每日游戏机会 - 同一天不重置
     */
    @Test
    public void testResetDailyGameChancesSameDay() {
        // 创建用户游戏进度，最后游戏时间为当天
        UserGameData initialData = new UserGameData();
        initialData.setUserId(testUser.getId());
        initialData.setActivityId(testActivity.getId());
        initialData.setCurrentPosition(5);
        initialData.setRemainingChances(10);
        initialData.setDailyUsedChances(20);
        initialData.setTotalPoints(500);
        initialData.setLastGameTime(new Date()); // 当前时间
        initialData.setIsNewUser(0);
        userGameDataMapper.insert(initialData);
        
        // 重置每日游戏机会
        UserGameData userGameData = gameService.resetDailyGameChances(initialData);
        
        // 验证返回结果 - 同一天不应重置
        assertEquals(20, userGameData.getDailyUsedChances(), "同一天不应重置每日已用机会");
        
        System.out.println("同一天不重置每日游戏机会测试通过");
    }
    
    /**
     * 创建测试游戏格子
     * 
     * @param activityId 活动ID
     * @return 游戏格子列表
     */
    private List<GameCell> createTestGameCells(Long activityId) {
        List<GameCell> cells = new ArrayList<>();
        
        // 创建起点格子
        GameCell startCell = createTestGameCellObject(activityId, 0, "START");
        startCell.setRewardAmount(0);
        startCell.setRewardDesc("起点");
        gameCellMapper.insert(startCell);
        cells.add(startCell);
        
        // 创建中间格子
        for (int i = 1; i < 9; i++) {
            String cellType = "NORMAL";
            if (i % 3 == 0) {
                cellType = "GIFT";
            } else if (i % 5 == 0) {
                cellType = "CHANCE";
            }
            
            GameCell cell = createTestGameCellObject(activityId, i, cellType);
            cell.setRewardAmount(10 * i);
            cell.setRewardDesc("测试格子_" + i);
            gameCellMapper.insert(cell);
            cells.add(cell);
        }
        
        // 创建终点格子
        GameCell endCell = createTestGameCellObject(activityId, 9, "END");
        endCell.setRewardAmount(100);
        endCell.setRewardDesc("终点");
        gameCellMapper.insert(endCell);
        cells.add(endCell);
        
        return cells;
    }
}
