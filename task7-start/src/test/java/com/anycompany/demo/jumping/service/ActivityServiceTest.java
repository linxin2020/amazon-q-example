package com.anycompany.demo.jumping.service;

import com.anycompany.demo.jumping.base.BaseTest;
import com.anycompany.demo.jumping.constant.ActivityConstants;
import com.anycompany.demo.jumping.enumeration.ActivityStatusEnum;
import com.anycompany.demo.jumping.enumeration.RewardTypeEnum;
import com.anycompany.demo.jumping.model.ActivityConfig;
import com.anycompany.demo.jumping.model.GameCell;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ActivityService 测试类
 */
public class ActivityServiceTest extends BaseTest {

    @Autowired
    private ActivityService activityService;
    
    /**
     * 测试创建和加载活动
     */
    @Test
    public void testCreateAndLoadActivity() {
        // 创建测试数据
        String name = "测试活动_" + System.currentTimeMillis();
        Date startTime = getDateAfterDays(-1); // 昨天开始
        Date endTime = getDateAfterDays(7);    // 一周后结束
        Integer totalCells = ActivityConstants.DEFAULT_TOTAL_CELLS;
        Integer dailyGameLimit = ActivityConstants.DEFAULT_DAILY_GAME_LIMIT;
        Integer maxDicePerTime = ActivityConstants.DEFAULT_MAX_DICE_PER_TIME;
        
        // 创建活动
        Long activityId = activityService.createActivity(name, startTime, endTime, totalCells, dailyGameLimit, maxDicePerTime);
        
        // 验证创建结果
        assertNotNull(activityId);
        
        // 加载活动
        ActivityConfig activity = activityService.loadActivityById(activityId);
        
        // 验证加载结果
        assertNotNull(activity);
        assertEquals(name, activity.getName());
        // 不直接比较日期对象，而是比较时间戳，避免毫秒级差异
        assertEquals(startTime.getTime() / 10000, activity.getStartTime().getTime() / 10000, "Start time should match");
        assertEquals(endTime.getTime() / 10000, activity.getEndTime().getTime() / 10000, "End time should match");
        assertEquals(totalCells, activity.getTotalCells());
        assertEquals(dailyGameLimit, activity.getDailyGameLimit());
        assertEquals(maxDicePerTime, activity.getMaxDicePerTime());
        assertEquals(ActivityStatusEnum.IN_PROGRESS.getCode(), activity.getStatus()); // 应该是进行中状态
        
        System.out.println("创建并加载活动: " + activity);
    }
    
    /**
     * 测试创建活动时的状态计算
     */
    @Test
    public void testCreateActivityWithDifferentStatus() {
        // 测试未开始状态
        String name1 = "未开始活动_" + System.currentTimeMillis();
        Date startTime1 = getDateAfterDays(1);  // 明天开始
        Date endTime1 = getDateAfterDays(8);    // 8天后结束
        
        Long activityId1 = activityService.createActivity(name1, startTime1, endTime1, 
                ActivityConstants.DEFAULT_TOTAL_CELLS, 
                ActivityConstants.DEFAULT_DAILY_GAME_LIMIT, 
                ActivityConstants.DEFAULT_MAX_DICE_PER_TIME);
        ActivityConfig activity1 = activityService.loadActivityById(activityId1);
        
        assertNotNull(activity1);
        assertEquals(ActivityStatusEnum.NOT_STARTED.getCode(), activity1.getStatus()); // 未开始状态
        
        // 测试进行中状态
        String name2 = "进行中活动_" + System.currentTimeMillis();
        Date startTime2 = getDateAfterDays(-1); // 昨天开始
        Date endTime2 = getDateAfterDays(7);    // 一周后结束
        
        Long activityId2 = activityService.createActivity(name2, startTime2, endTime2, 
                ActivityConstants.DEFAULT_TOTAL_CELLS, 
                ActivityConstants.DEFAULT_DAILY_GAME_LIMIT, 
                ActivityConstants.DEFAULT_MAX_DICE_PER_TIME);
        ActivityConfig activity2 = activityService.loadActivityById(activityId2);
        
        assertNotNull(activity2);
        assertEquals(ActivityStatusEnum.IN_PROGRESS.getCode(), activity2.getStatus()); // 进行中状态
        
        // 测试已结束状态
        String name3 = "已结束活动_" + System.currentTimeMillis();
        Date startTime3 = getDateAfterDays(-10); // 10天前开始
        Date endTime3 = getDateAfterDays(-1);    // 昨天结束
        
        Long activityId3 = activityService.createActivity(name3, startTime3, endTime3, 
                ActivityConstants.DEFAULT_TOTAL_CELLS, 
                ActivityConstants.DEFAULT_DAILY_GAME_LIMIT, 
                ActivityConstants.DEFAULT_MAX_DICE_PER_TIME);
        ActivityConfig activity3 = activityService.loadActivityById(activityId3);
        
        assertNotNull(activity3);
        assertEquals(ActivityStatusEnum.ENDED.getCode(), activity3.getStatus()); // 已结束状态
        
        System.out.println("创建不同状态的活动测试通过");
    }
    
    /**
     * 测试创建游戏格子
     */
    @Test
    public void testCreateGameCell() {
        // 创建测试活动
        ActivityConfig activity = createTestActivity();
        
        // 创建游戏格子
        Integer cellIndex = 1;
        String cellType = "NORMAL";
        String rewardType = RewardTypeEnum.POINTS.getCode();
        Integer rewardAmount = 100;
        String rewardId = null;
        String rewardDesc = "积分奖励";
        Integer fallbackPoints = 0;
        String iconUrl = "/images/cell_normal.png";
        
        Long cellId = activityService.createGameCell(
                activity.getId(), cellIndex, cellType, rewardType, 
                rewardAmount, rewardId, rewardDesc, fallbackPoints, iconUrl);
        
        // 验证创建结果
        assertNotNull(cellId);
        
        // 加载游戏格子
        GameCell gameCell = activityService.loadGameCellById(cellId);
        
        // 验证加载结果
        assertNotNull(gameCell);
        assertEquals(activity.getId(), gameCell.getActivityId());
        assertEquals(cellIndex, gameCell.getCellIndex());
        assertEquals(cellType, gameCell.getCellType());
        assertEquals(rewardType, gameCell.getRewardType());
        assertEquals(rewardAmount, gameCell.getRewardAmount());
        assertEquals(rewardDesc, gameCell.getRewardDesc());
        assertEquals(fallbackPoints, gameCell.getFallbackPoints());
        assertEquals(iconUrl, gameCell.getIconUrl());
        
        System.out.println("创建并加载游戏格子: " + gameCell);
    }
    
    /**
     * 测试创建礼物道具类型的游戏格子
     */
    @Test
    public void testCreateGiftGameCell() {
        // 创建测试活动
        ActivityConfig activity = createTestActivity();
        
        // 创建礼物道具类型的游戏格子
        Integer cellIndex = 5;
        String cellType = "GIFT";
        String rewardType = RewardTypeEnum.GIFT.getCode();
        Integer rewardAmount = 1;
        String rewardId = "gift_123";
        String rewardDesc = "限定头像框";
        Integer fallbackPoints = 50;
        String iconUrl = "/images/cell_gift.png";
        
        Long cellId = activityService.createGameCell(
                activity.getId(), cellIndex, cellType, rewardType, 
                rewardAmount, rewardId, rewardDesc, fallbackPoints, iconUrl);
        
        // 验证创建结果
        assertNotNull(cellId);
        
        // 加载游戏格子
        GameCell gameCell = activityService.loadGameCellById(cellId);
        
        // 验证加载结果
        assertNotNull(gameCell);
        assertEquals(activity.getId(), gameCell.getActivityId());
        assertEquals(cellIndex, gameCell.getCellIndex());
        assertEquals(cellType, gameCell.getCellType());
        assertEquals(rewardType, gameCell.getRewardType());
        assertEquals(rewardAmount, gameCell.getRewardAmount());
        assertEquals(rewardId, gameCell.getRewardId());
        assertEquals(rewardDesc, gameCell.getRewardDesc());
        assertEquals(fallbackPoints, gameCell.getFallbackPoints());
        assertEquals(iconUrl, gameCell.getIconUrl());
        
        System.out.println("创建并加载礼物道具类型的游戏格子: " + gameCell);
    }
    
    /**
     * 测试根据活动ID获取所有游戏格子
     */
    @Test
    public void testGetGameCellsByActivityId() {
        // 创建测试活动
        ActivityConfig activity = createTestActivity();
        
        // 创建多个游戏格子
        createTestGameCell(activity.getId(), 0, "START", RewardTypeEnum.POINTS.getCode(), 0);
        createTestGameCell(activity.getId(), 1, "NORMAL", RewardTypeEnum.POINTS.getCode(), 50);
        createTestGameCell(activity.getId(), 2, "GIFT", RewardTypeEnum.GIFT.getCode(), 1);
        
        // 获取活动的所有游戏格子
        List<GameCell> gameCells = activityService.getGameCellsByActivityId(activity.getId());
        
        // 验证结果
        assertNotNull(gameCells);
        assertEquals(3, gameCells.size());
        
        // 验证格子索引是否正确
        boolean hasIndex0 = false;
        boolean hasIndex1 = false;
        boolean hasIndex2 = false;
        
        for (GameCell cell : gameCells) {
            if (cell.getCellIndex() == 0) hasIndex0 = true;
            if (cell.getCellIndex() == 1) hasIndex1 = true;
            if (cell.getCellIndex() == 2) hasIndex2 = true;
        }
        
        assertTrue(hasIndex0);
        assertTrue(hasIndex1);
        assertTrue(hasIndex2);
        
        System.out.println("获取活动的所有游戏格子: " + gameCells.size() + "个");
    }
    
    /**
     * 测试批量创建游戏格子
     */
    @Test
    public void testBatchCreateGameCells() {
        // 创建测试活动
        ActivityConfig activity = createTestActivity();
        
        // 准备批量创建的游戏格子
        List<GameCell> gameCells = new ArrayList<>();
        
        // 起点格子
        GameCell startCell = new GameCell();
        startCell.setCellIndex(0);
        startCell.setCellType("START");
        startCell.setRewardType(RewardTypeEnum.POINTS.getCode());
        startCell.setRewardAmount(0);
        startCell.setRewardDesc("起点");
        startCell.setFallbackPoints(0);
        startCell.setIconUrl("/images/cell_start.png");
        gameCells.add(startCell);
        
        // 普通格子
        GameCell normalCell = new GameCell();
        normalCell.setCellIndex(1);
        normalCell.setCellType("NORMAL");
        normalCell.setRewardType(RewardTypeEnum.POINTS.getCode());
        normalCell.setRewardAmount(50);
        normalCell.setRewardDesc("积分奖励");
        normalCell.setFallbackPoints(0);
        normalCell.setIconUrl("/images/cell_normal.png");
        gameCells.add(normalCell);
        
        // 礼物格子
        GameCell giftCell = new GameCell();
        giftCell.setCellIndex(2);
        giftCell.setCellType("GIFT");
        giftCell.setRewardType(RewardTypeEnum.GIFT.getCode());
        giftCell.setRewardAmount(1);
        giftCell.setRewardId("gift_456");
        giftCell.setRewardDesc("限定头像框");
        giftCell.setFallbackPoints(100);
        giftCell.setIconUrl("/images/cell_gift.png");
        gameCells.add(giftCell);
        
        // 批量创建游戏格子
        List<Long> cellIds = activityService.batchCreateGameCells(activity.getId(), gameCells);
        
        // 验证创建结果
        assertNotNull(cellIds);
        assertEquals(3, cellIds.size());
        
        // 获取活动的所有游戏格子
        List<GameCell> createdCells = activityService.getGameCellsByActivityId(activity.getId());
        
        // 验证结果
        assertNotNull(createdCells);
        assertEquals(3, createdCells.size());
        
        System.out.println("批量创建游戏格子: " + cellIds.size() + "个");
    }
    
    /**
     * 测试创建格子时的参数校验
     */
    @Test
    public void testCreateGameCellValidation() {
        // 创建测试活动
        ActivityConfig activity = createTestActivity();
        
        // 测试无效的格子索引（负数）
        Long cellId1 = activityService.createGameCell(
                activity.getId(), -1, "NORMAL", RewardTypeEnum.POINTS.getCode(), 
                100, null, "测试", 0, "/images/test.png");
        
        assertNull(cellId1, "应该拒绝创建负数索引的格子");
        
        // 测试无效的格子索引（超出范围）
        Long cellId2 = activityService.createGameCell(
                activity.getId(), activity.getTotalCells() + 1, "NORMAL", RewardTypeEnum.POINTS.getCode(), 
                100, null, "测试", 0, "/images/test.png");
        
        assertNull(cellId2, "应该拒绝创建超出范围的格子");
        
        // 测试无效的奖励类型
        Long cellId3 = activityService.createGameCell(
                activity.getId(), 10, "NORMAL", "INVALID_TYPE", 
                100, null, "测试", 0, "/images/test.png");
        
        assertNotNull(cellId3, "应该接受创建并修正奖励类型");
        
        GameCell cell3 = activityService.loadGameCellById(cellId3);
        assertEquals(RewardTypeEnum.POINTS.getCode(), cell3.getRewardType(), "应该将无效的奖励类型修正为POINTS");
        
        System.out.println("格子创建参数校验测试通过");
    }
    
    /**
     * 测试创建重复索引的格子
     */
    @Test
    public void testCreateDuplicateIndexCell() {
        // 创建测试活动
        ActivityConfig activity = createTestActivity();
        
        // 创建第一个格子
        Integer cellIndex = 15;
        Long cellId1 = activityService.createGameCell(
                activity.getId(), cellIndex, "NORMAL", RewardTypeEnum.POINTS.getCode(), 
                100, null, "测试1", 0, "/images/test1.png");
        
        assertNotNull(cellId1);
        
        // 尝试创建相同索引的第二个格子
        Long cellId2 = activityService.createGameCell(
                activity.getId(), cellIndex, "GIFT", RewardTypeEnum.GIFT.getCode(), 
                1, "gift_789", "测试2", 50, "/images/test2.png");
        
        assertNull(cellId2, "应该拒绝创建重复索引的格子");
        
        // 验证只有一个格子被创建
        List<GameCell> cells = activityService.getGameCellsByActivityId(activity.getId());
        assertEquals(1, cells.size());
        
        System.out.println("重复索引格子创建测试通过");
    }
    
    /**
     * 创建测试活动
     * 
     * @return 创建的活动配置
     */
    private ActivityConfig createTestActivity() {
        String name = "测试活动_" + System.currentTimeMillis();
        Date startTime = getDateAfterDays(-1);
        Date endTime = getDateAfterDays(7);
        Integer totalCells = ActivityConstants.DEFAULT_TOTAL_CELLS;
        Integer dailyGameLimit = ActivityConstants.DEFAULT_DAILY_GAME_LIMIT;
        Integer maxDicePerTime = ActivityConstants.DEFAULT_MAX_DICE_PER_TIME;
        
        Long activityId = activityService.createActivity(name, startTime, endTime, totalCells, dailyGameLimit, maxDicePerTime);
        return activityService.loadActivityById(activityId);
    }
    
    /**
     * 创建测试游戏格子
     * 
     * @param activityId 活动ID
     * @param cellIndex 格子索引
     * @param cellType 格子类型
     * @param rewardType 奖励类型
     * @param rewardAmount 奖励数量
     * @return 创建的游戏格子
     */
    private GameCell createTestGameCell(Long activityId, Integer cellIndex, String cellType, String rewardType, Integer rewardAmount) {
        String rewardId = RewardTypeEnum.GIFT.getCode().equals(rewardType) ? "gift_" + System.currentTimeMillis() : null;
        String rewardDesc = "测试格子_" + cellIndex;
        Integer fallbackPoints = RewardTypeEnum.GIFT.getCode().equals(rewardType) ? 50 : 0;
        String iconUrl = "/images/cell_" + cellType.toLowerCase() + ".png";
        
        Long cellId = activityService.createGameCell(
                activityId, cellIndex, cellType, rewardType, 
                rewardAmount, rewardId, rewardDesc, fallbackPoints, iconUrl);
        
        return activityService.loadGameCellById(cellId);
    }
    
    /**
     * 获取指定天数后的日期
     * 
     * @param days 天数，可以为负数表示过去的日期
     * @return 计算后的日期
     */
    private Date getDateAfterDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
}
