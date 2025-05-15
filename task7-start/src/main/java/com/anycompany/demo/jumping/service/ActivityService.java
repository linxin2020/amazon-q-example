package com.anycompany.demo.jumping.service;

import com.anycompany.demo.jumping.model.ActivityConfig;
import com.anycompany.demo.jumping.model.GameCell;

import java.util.Date;
import java.util.List;

/**
 * 活动服务接口
 */
public interface ActivityService {
    
    /**
     * 根据ID加载活动配置
     * @param id 活动ID
     * @return 活动配置信息
     */
    ActivityConfig loadActivityById(Long id);
    
    /**
     * 搜索活动列表，支持分页
     * @param name 活动名称关键词
     * @param status 活动状态(0:未开始 1:进行中 2:已结束)
     * @param offset 起始位置，如果为负数将被设置为0
     * @param limit 每页记录数，有最小和最大限制，超出范围将使用默认值
     * @return 活动列表
     */
    List<ActivityConfig> searchActivities(String name, Integer status, int offset, int limit);
    
    /**
     * 根据状态搜索活动，支持分页
     * @param status 状态(0:未开始 1:进行中 2:已结束)
     * @param offset 起始位置，如果为负数将被设置为0
     * @param limit 每页记录数，有最小和最大限制，超出范围将使用默认值
     * @return 活动列表
     */
    List<ActivityConfig> searchActivitiesByStatus(Integer status, int offset, int limit);
    
    /**
     * 创建活动
     * @param name 活动名称
     * @param startTime 活动开始时间
     * @param endTime 活动结束时间
     * @param totalCells 总格子数
     * @param dailyGameLimit 每日游戏次数上限
     * @param maxDicePerTime 单次最大使用骰子数
     * @return 创建的活动ID
     */
    Long createActivity(String name, Date startTime, Date endTime, Integer totalCells, Integer dailyGameLimit, Integer maxDicePerTime);
    
    /**
     * 更新活动
     * @param id 活动ID
     * @param name 活动名称
     * @param startTime 活动开始时间
     * @param endTime 活动结束时间
     * @param totalCells 总格子数
     * @param dailyGameLimit 每日游戏次数上限
     * @param maxDicePerTime 单次最大使用骰子数
     * @param status 活动状态
     * @return 更新的活动ID
     */
    Long updateActivity(Long id, String name, Date startTime, Date endTime, Integer totalCells, Integer dailyGameLimit, Integer maxDicePerTime, Integer status);
    
    /**
     * 更新活动状态
     * @param id 活动ID
     * @param status 状态(0:未开始 1:进行中 2:已结束)
     * @return 更新的活动ID
     */
    Long updateActivityStatus(Long id, Integer status);
    
    /**
     * 删除活动
     * @param id 活动ID
     * @return 删除的活动ID
     */
    Long deleteActivity(Long id);
    
    /**
     * 批量删除活动
     * @param ids 活动ID数组
     * @return 删除的活动ID数组
     */
    Long[] batchDeleteActivities(Long[] ids);
    
    /**
     * 根据ID加载游戏格子
     * @param id 格子ID
     * @return 游戏格子信息
     */
    GameCell loadGameCellById(Long id);
    
    /**
     * 根据活动ID和格子索引加载游戏格子
     * @param activityId 活动ID
     * @param cellIndex 格子索引
     * @return 游戏格子信息
     */
    GameCell loadGameCellByActivityAndIndex(Long activityId, Integer cellIndex);
    
    /**
     * 根据活动ID获取所有游戏格子
     * @param activityId 活动ID
     * @return 游戏格子列表
     */
    List<GameCell> getGameCellsByActivityId(Long activityId);
    
    /**
     * 创建游戏格子
     * @param activityId 活动ID
     * @param cellIndex 格子索引
     * @param cellType 格子类型
     * @param rewardType 奖励类型(POINTS:积分 GIFT:礼物道具)
     * @param rewardAmount 奖励数量
     * @param rewardId 礼物道具ID
     * @param rewardDesc 奖励描述
     * @param fallbackPoints 礼物已领取时的替代积分
     * @param iconUrl 图标URL
     * @return 创建的格子ID
     */
    Long createGameCell(Long activityId, Integer cellIndex, String cellType, String rewardType, 
                        Integer rewardAmount, String rewardId, String rewardDesc, Integer fallbackPoints, String iconUrl);
    
    /**
     * 更新游戏格子
     * @param id 格子ID
     * @param cellType 格子类型
     * @param rewardType 奖励类型(POINTS:积分 GIFT:礼物道具)
     * @param rewardAmount 奖励数量
     * @param rewardId 礼物道具ID
     * @param rewardDesc 奖励描述
     * @param fallbackPoints 礼物已领取时的替代积分
     * @param iconUrl 图标URL
     * @return 更新的格子ID
     */
    Long updateGameCell(Long id, String cellType, String rewardType, Integer rewardAmount, 
                        String rewardId, String rewardDesc, Integer fallbackPoints, String iconUrl);
    
    /**
     * 删除游戏格子
     * @param id 格子ID
     * @return 删除的格子ID
     */
    Long deleteGameCell(Long id);
    
    /**
     * 批量删除游戏格子
     * @param ids 格子ID数组
     * @return 删除的格子ID数组
     */
    Long[] batchDeleteGameCells(Long[] ids);
    
    /**
     * 批量创建游戏格子
     * @param activityId 活动ID
     * @param gameCells 游戏格子列表
     * @return 创建的格子ID列表
     */
    List<Long> batchCreateGameCells(Long activityId, List<GameCell> gameCells);
}
