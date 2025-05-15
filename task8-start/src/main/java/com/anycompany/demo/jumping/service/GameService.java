package com.anycompany.demo.jumping.service;

import com.anycompany.demo.jumping.model.GameCell;
import com.anycompany.demo.jumping.model.UserGameData;

import java.util.List;
import java.util.Map;

/**
 * 游戏服务接口
 */
public interface GameService {
    
    /**
     * 获取游戏数据，包括用户进度和格子信息
     * 
     * @param userId 用户ID
     * @param activityId 活动ID
     * @return 包含用户进度和格子信息的Map
     */
    Map<String, Object> getGameData(Long userId, Long activityId);
    
    /**
     * 获取用户游戏进度
     * 
     * @param userId 用户ID
     * @param activityId 活动ID
     * @return 用户游戏进度
     */
    UserGameData getUserGameProgress(Long userId, Long activityId);
    
    /**
     * 获取活动的所有格子信息
     * 
     * @param activityId 活动ID
     * @return 格子信息列表
     */
    List<GameCell> getGameCells(Long activityId);
    
    /**
     * 初始化用户游戏进度
     * 
     * @param userId 用户ID
     * @param activityId 活动ID
     * @return 初始化的用户游戏进度
     */
    UserGameData initUserGameProgress(Long userId, Long activityId);
    
    /**
     * 重置用户每日游戏次数
     * 
     * @param userGameData 用户游戏数据
     * @return 更新后的用户游戏数据
     */
    UserGameData resetDailyGameChances(UserGameData userGameData);
}
