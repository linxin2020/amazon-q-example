package com.anycompany.demo.jumping.service;

import com.anycompany.demo.jumping.model.ActivityConfig;
import com.anycompany.demo.jumping.model.GameCell;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 复杂活动创建测试
 * 该测试用例创建一个包含30个格子的复杂活动，并将活动ID固定为100
 * 测试不会回滚，数据将保留在数据库中
 */
@SpringBootTest
public class ComplexActivityCreationTest {

    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final Long TARGET_ACTIVITY_ID = 100L;
    private static final int TOTAL_CELLS = 30;
    private static final int MIN_POINTS = 10;
    private static final int MAX_POINTS = 50;
    private static final int MIN_GIFT_CELLS = 3;
    private static final int MAX_GIFT_CELLS = 5;
    
    /**
     * 道具类型列表
     */
    private static final String[] GIFT_TYPES = {
        "avatar-frame", "entry-effect", "chat-bubble", "badge", "title"
    };
    
    /**
     * 道具风格列表
     */
    private static final String[] GIFT_STYLES = {
        "rabbit", "dragon", "tiger", "snake", "horse", 
        "city-ranger", "ocean-explorer", "space-traveler", "forest-guardian", "desert-nomad"
    };
    
    /**
     * 格子类型列表
     */
    private static final String[] CELL_TYPES = {
        "NORMAL", "BONUS", "SPECIAL", "GIFT", "CHANCE"
    };
    
    /**
     * 测试创建复杂活动
     * 该测试不会回滚，数据将保留在数据库中
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCreateComplexActivity() {
        // 1. 删除ID=100的活动及其相关数据
        cleanupExistingActivity();
        
        // 2. 创建新活动
        ActivityConfig activity = createActivity();
        
        // 3. 创建格子
        List<GameCell> cells = createGameCells(activity.getId());
        
        // 4. 将活动ID更新为100
        updateActivityId(activity.getId());
        
        // 5. 验证数据
        verifyActivityData();
    }
    
    /**
     * 清理已存在的ID=100的活动及其相关数据
     */
    private void cleanupExistingActivity() {
        // 删除相关的游戏格子
        jdbcTemplate.update("DELETE FROM demo_game_cell WHERE activity_id = ?", TARGET_ACTIVITY_ID);
        
        // 删除活动配置
        jdbcTemplate.update("DELETE FROM demo_activity_config WHERE id = ?", TARGET_ACTIVITY_ID);
        
        // 验证删除成功
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM demo_activity_config WHERE id = ?", 
                Integer.class, 
                TARGET_ACTIVITY_ID);
        assertEquals(0, count);
        
        System.out.println("已清理ID=" + TARGET_ACTIVITY_ID + "的活动及相关数据");
    }
    
    /**
     * 创建活动
     */
    private ActivityConfig createActivity() {
        // 设置活动时间：昨天开始，下周结束
        Calendar startCal = Calendar.getInstance();
        startCal.add(Calendar.DAY_OF_MONTH, -1);
        Date startTime = startCal.getTime();
        
        Calendar endCal = Calendar.getInstance();
        endCal.add(Calendar.WEEK_OF_YEAR, 1);
        Date endTime = endCal.getTime();
        
        String activityName = "跳跳棋欢乐季_" + System.currentTimeMillis();
        
        // 创建活动
        Long activityId = activityService.createActivity(
                activityName, 
                startTime, 
                endTime, 
                TOTAL_CELLS, 
                120, // 每日游戏次数上限
                10   // 单次最大使用骰子数
        );
        
        // 验证创建成功
        assertNotNull(activityId);
        
        // 加载活动
        ActivityConfig activity = activityService.loadActivityById(activityId);
        assertNotNull(activity);
        
        System.out.println("创建活动成功，临时ID=" + activityId + ", 名称=" + activityName);
        return activity;
    }
    
    /**
     * 创建游戏格子
     */
    private List<GameCell> createGameCells(Long activityId) {
        List<GameCell> cells = new ArrayList<>();
        
        // 决定有多少个格子是礼物格子
        int giftCellsCount = ThreadLocalRandom.current().nextInt(MIN_GIFT_CELLS, MAX_GIFT_CELLS + 1);
        
        // 随机选择哪些格子是礼物格子
        Set<Integer> giftCellIndices = new HashSet<>();
        while (giftCellIndices.size() < giftCellsCount) {
            // 避免起点(0)和终点(TOTAL_CELLS-1)是礼物格子
            int index = ThreadLocalRandom.current().nextInt(1, TOTAL_CELLS - 1);
            giftCellIndices.add(index);
        }
        
        // 创建起点格子
        GameCell startCell = new GameCell();
        startCell.setActivityId(activityId);
        startCell.setCellIndex(0);
        startCell.setCellType("START");
        startCell.setRewardType("POINTS");
        startCell.setRewardAmount(0);
        startCell.setRewardDesc("起点");
        startCell.setFallbackPoints(0);
        startCell.setIconUrl("/images/cell_start.png");
        
        Long startCellId = activityService.createGameCell(
                activityId,
                startCell.getCellIndex(),
                startCell.getCellType(),
                startCell.getRewardType(),
                startCell.getRewardAmount(),
                startCell.getRewardId(),
                startCell.getRewardDesc(),
                startCell.getFallbackPoints(),
                startCell.getIconUrl()
        );
        assertNotNull(startCellId);
        cells.add(startCell);
        
        // 创建中间格子
        for (int i = 1; i < TOTAL_CELLS - 1; i++) {
            GameCell cell = new GameCell();
            cell.setActivityId(activityId);
            cell.setCellIndex(i);
            
            if (giftCellIndices.contains(i)) {
                // 礼物格子
                cell.setCellType("GIFT");
                cell.setRewardType("GIFT");
                cell.setRewardAmount(1);
                
                // 生成道具ID: {type}/{style}/{id}
                String giftType = GIFT_TYPES[ThreadLocalRandom.current().nextInt(GIFT_TYPES.length)];
                String giftStyle = GIFT_STYLES[ThreadLocalRandom.current().nextInt(GIFT_STYLES.length)];
                int giftId = ThreadLocalRandom.current().nextInt(100, 1000);
                String rewardId = giftType + "/" + giftStyle + "/" + giftId;
                
                cell.setRewardId(rewardId);
                cell.setRewardDesc(giftType + " - " + giftStyle);
                cell.setFallbackPoints(ThreadLocalRandom.current().nextInt(MIN_POINTS, MAX_POINTS + 1));
                cell.setIconUrl("/images/cell_gift_" + giftType + ".png");
            } else {
                // 积分格子
                cell.setCellType(CELL_TYPES[ThreadLocalRandom.current().nextInt(CELL_TYPES.length)]);
                cell.setRewardType("POINTS");
                cell.setRewardAmount(ThreadLocalRandom.current().nextInt(MIN_POINTS, MAX_POINTS + 1));
                cell.setRewardDesc("积分奖励");
                cell.setFallbackPoints(0);
                cell.setIconUrl("/images/cell_" + cell.getCellType().toLowerCase() + ".png");
            }
            
            Long cellId = activityService.createGameCell(
                    activityId,
                    cell.getCellIndex(),
                    cell.getCellType(),
                    cell.getRewardType(),
                    cell.getRewardAmount(),
                    cell.getRewardId(),
                    cell.getRewardDesc(),
                    cell.getFallbackPoints(),
                    cell.getIconUrl()
            );
            assertNotNull(cellId);
            cells.add(cell);
        }
        
        // 创建终点格子
        GameCell endCell = new GameCell();
        endCell.setActivityId(activityId);
        endCell.setCellIndex(TOTAL_CELLS - 1);
        endCell.setCellType("END");
        endCell.setRewardType("POINTS");
        endCell.setRewardAmount(100); // 终点给予较高积分
        endCell.setRewardDesc("终点");
        endCell.setFallbackPoints(0);
        endCell.setIconUrl("/images/cell_end.png");
        
        Long endCellId = activityService.createGameCell(
                activityId,
                endCell.getCellIndex(),
                endCell.getCellType(),
                endCell.getRewardType(),
                endCell.getRewardAmount(),
                endCell.getRewardId(),
                endCell.getRewardDesc(),
                endCell.getFallbackPoints(),
                endCell.getIconUrl()
        );
        assertNotNull(endCellId);
        cells.add(endCell);
        
        System.out.println("创建了" + cells.size() + "个格子，其中" + giftCellsCount + "个礼物格子");
        return cells;
    }
    
    /**
     * 将活动ID更新为目标ID
     */
    private void updateActivityId(Long currentId) {
        // 先更新游戏格子的活动ID
        int cellsUpdated = jdbcTemplate.update(
                "UPDATE demo_game_cell SET activity_id = ? WHERE activity_id = ?",
                TARGET_ACTIVITY_ID, currentId);
        
        // 再更新活动ID
        int activityUpdated = jdbcTemplate.update(
                "UPDATE demo_activity_config SET id = ? WHERE id = ?",
                TARGET_ACTIVITY_ID, currentId);
        
        System.out.println("已将活动ID从" + currentId + "更新为" + TARGET_ACTIVITY_ID);
        System.out.println("更新了" + cellsUpdated + "个格子和" + activityUpdated + "个活动配置");
    }
    
    /**
     * 验证活动数据
     */
    private void verifyActivityData() {
        // 验证活动存在
        ActivityConfig activity = activityService.loadActivityById(TARGET_ACTIVITY_ID);
        assertNotNull(activity);
        assertEquals(TARGET_ACTIVITY_ID, activity.getId());
        assertEquals(TOTAL_CELLS, activity.getTotalCells());
        
        // 验证格子数量
        List<GameCell> cells = activityService.getGameCellsByActivityId(TARGET_ACTIVITY_ID);
        assertEquals(TOTAL_CELLS, cells.size());
        
        // 验证起点和终点
        Optional<GameCell> startCell = cells.stream()
                .filter(cell -> cell.getCellIndex() == 0)
                .findFirst();
        assertTrue(startCell.isPresent());
        assertEquals("START", startCell.get().getCellType());
        
        Optional<GameCell> endCell = cells.stream()
                .filter(cell -> cell.getCellIndex() == TOTAL_CELLS - 1)
                .findFirst();
        assertTrue(endCell.isPresent());
        assertEquals("END", endCell.get().getCellType());
        
        // 验证礼物格子
        long giftCellsCount = cells.stream()
                .filter(cell -> "GIFT".equals(cell.getRewardType()))
                .count();
        assertTrue(giftCellsCount >= MIN_GIFT_CELLS && giftCellsCount <= MAX_GIFT_CELLS);
        
        // 打印活动信息
        System.out.println("活动验证成功：ID=" + activity.getId() + 
                ", 名称=" + activity.getName() + 
                ", 格子数=" + cells.size() + 
                ", 礼物格子数=" + giftCellsCount);
        
        // 打印礼物格子信息
        cells.stream()
                .filter(cell -> "GIFT".equals(cell.getRewardType()))
                .forEach(cell -> System.out.println("礼物格子：索引=" + cell.getCellIndex() + 
                        ", 道具ID=" + cell.getRewardId() + 
                        ", 描述=" + cell.getRewardDesc() + 
                        ", 替代积分=" + cell.getFallbackPoints()));
    }
}
