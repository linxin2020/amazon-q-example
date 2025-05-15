package com.anycompany.demo.jumping.service.impl;

import com.anycompany.demo.jumping.mapper.GameCellMapper;
import com.anycompany.demo.jumping.mapper.UserGameDataMapper;
import com.anycompany.demo.jumping.model.GameCell;
import com.anycompany.demo.jumping.model.UserGameData;
import com.anycompany.demo.jumping.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 游戏服务实现类
 */
@Service
public class GameServiceImpl implements GameService {
    
    @Autowired
    private GameCellMapper gameCellMapper;
    
    @Autowired
    private UserGameDataMapper userGameDataMapper;
    
    @Override
    public Map<String, Object> getGameData(Long userId, Long activityId) {
        // 获取用户游戏进度
        UserGameData userGameProgress = getUserGameProgress(userId, activityId);
        
        // 获取活动的所有格子信息
        List<GameCell> gameCells = getGameCells(activityId);
        
        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        
        // 构建进度信息
        Map<String, Object> progress = new HashMap<>();
        progress.put("userId", userGameProgress.getUserId());
        progress.put("activityId", userGameProgress.getActivityId());
        progress.put("currentPosition", userGameProgress.getCurrentPosition());
        progress.put("remainingChances", userGameProgress.getRemainingChances());
        progress.put("dailyChancesUsed", userGameProgress.getDailyUsedChances());
        progress.put("userPoints", userGameProgress.getTotalPoints()); // 用户当前积分
        
        // 格式化日期为 yyyy-MM-dd 格式
        String gameDate = "";
        if (userGameProgress.getLastGameTime() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(userGameProgress.getLastGameTime());
            gameDate = String.format("%d-%02d-%02d", 
                cal.get(Calendar.YEAR), 
                cal.get(Calendar.MONTH) + 1, 
                cal.get(Calendar.DAY_OF_MONTH));
        } else {
            // 如果没有最后游戏时间，使用当前日期
            Calendar cal = Calendar.getInstance();
            gameDate = String.format("%d-%02d-%02d", 
                cal.get(Calendar.YEAR), 
                cal.get(Calendar.MONTH) + 1, 
                cal.get(Calendar.DAY_OF_MONTH));
        }
        progress.put("gameDate", gameDate);
        
        // 构建格子信息列表
        List<Map<String, Object>> cells = new ArrayList<>();
        for (GameCell cell : gameCells) {
            Map<String, Object> cellMap = new HashMap<>();
            cellMap.put("cellIndex", cell.getCellIndex());
            
            // 将格子类型转换为数字类型
            int cellType = convertCellTypeToNumber(cell.getCellType());
            cellMap.put("cellType", cellType);
            
            // 将奖励类型转换为数字类型
            int rewardType = "POINTS".equals(cell.getRewardType()) ? 1 : 2;
            cellMap.put("rewardType", rewardType);
            
            cellMap.put("rewardAmount", cell.getRewardAmount());
            cellMap.put("description", cell.getRewardDesc());
            
            cells.add(cellMap);
        }
        
        // 组装最终结果
        result.put("progress", progress);
        result.put("cells", cells);
        
        return result;
    }
    
    @Override
    public UserGameData getUserGameProgress(Long userId, Long activityId) {
        // 创建查询条件
        UserGameData query = new UserGameData();
        query.setUserId(userId);
        query.setActivityId(activityId);
        
        // 查询用户游戏进度
        List<UserGameData> userGameDataList = userGameDataMapper.findList(query);
        
        // 如果没有找到用户游戏进度，则初始化一个
        if (userGameDataList == null || userGameDataList.isEmpty()) {
            return initUserGameProgress(userId, activityId);
        }
        
        UserGameData userGameData = userGameDataList.get(0);
        
        // 检查是否需要重置每日游戏机会
        return resetDailyGameChances(userGameData);
    }
    
    @Override
    public List<GameCell> getGameCells(Long activityId) {
        // 创建查询条件
        GameCell query = new GameCell();
        query.setActivityId(activityId);
        
        // 查询活动的所有格子信息
        return gameCellMapper.findList(query);
    }
    
    @Override
    public UserGameData initUserGameProgress(Long userId, Long activityId) {
        // 创建新的用户游戏进度
        UserGameData userGameData = new UserGameData();
        userGameData.setUserId(userId);
        userGameData.setActivityId(activityId);
        userGameData.setCurrentPosition(0); // 起始位置为0
        userGameData.setRemainingChances(0); // 初始没有游戏机会
        userGameData.setDailyUsedChances(0); // 初始每日已用机会为0
        userGameData.setTotalPoints(0); // 初始积分为0
        userGameData.setLastGameTime(null); // 初始没有最后游戏时间
        userGameData.setIsNewUser(1); // 标记为新用户
        
        // 插入数据库
        userGameDataMapper.insert(userGameData);
        
        return userGameData;
    }
    
    @Override
    public UserGameData resetDailyGameChances(UserGameData userGameData) {
        // 如果没有最后游戏时间，不需要重置
        if (userGameData.getLastGameTime() == null) {
            return userGameData;
        }
        
        // 获取最后游戏时间的日期部分
        Calendar lastGameCal = Calendar.getInstance();
        lastGameCal.setTime(userGameData.getLastGameTime());
        lastGameCal.set(Calendar.HOUR_OF_DAY, 0);
        lastGameCal.set(Calendar.MINUTE, 0);
        lastGameCal.set(Calendar.SECOND, 0);
        lastGameCal.set(Calendar.MILLISECOND, 0);
        
        // 获取当前时间的日期部分
        Calendar currentCal = Calendar.getInstance();
        currentCal.set(Calendar.HOUR_OF_DAY, 0);
        currentCal.set(Calendar.MINUTE, 0);
        currentCal.set(Calendar.SECOND, 0);
        currentCal.set(Calendar.MILLISECOND, 0);
        
        // 如果不是同一天，重置每日已用机会
        if (currentCal.after(lastGameCal)) {
            userGameData.setDailyUsedChances(0);
            userGameDataMapper.update(userGameData);
        }
        
        return userGameData;
    }
    
    /**
     * 将格子类型字符串转换为数字类型
     * 
     * @param cellType 格子类型字符串
     * @return 格子类型数字
     */
    private int convertCellTypeToNumber(String cellType) {
        switch (cellType) {
            case "START":
                return 1; // 起点格子
            case "NORMAL":
                return 1; // 普通格子
            case "GIFT":
                return 2; // 奖励格子
            case "CHANCE":
            case "BONUS":
                return 3; // 惩罚格子或机会格子
            case "SPECIAL":
                return 4; // 机会格子
            case "END":
                return 5; // 终点格子
            default:
                return 1; // 默认为普通格子
        }
    }
}
