package com.anycompany.demo.jumping.service.impl;

import com.anycompany.demo.jumping.constant.ActivityConstants;
import com.anycompany.demo.jumping.enumeration.ActivityStatusEnum;
import com.anycompany.demo.jumping.enumeration.RewardTypeEnum;
import com.anycompany.demo.jumping.mapper.ActivityConfigMapper;
import com.anycompany.demo.jumping.mapper.GameCellMapper;
import com.anycompany.demo.jumping.model.ActivityConfig;
import com.anycompany.demo.jumping.model.GameCell;
import com.anycompany.demo.jumping.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 活动服务实现类
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityConfigMapper activityConfigMapper;
    
    @Autowired
    private GameCellMapper gameCellMapper;
    
    @Override
    public ActivityConfig loadActivityById(Long id) {
        return activityConfigMapper.getById(id);
    }
    
    @Override
    public List<ActivityConfig> searchActivities(String name, Integer status, int offset, int limit) {
        // 参数容错处理
        if (offset < 0) {
            offset = 0;
        }
        
        if (limit < ActivityConstants.MIN_LIMIT || limit > ActivityConstants.MAX_LIMIT) {
            limit = ActivityConstants.DEFAULT_LIMIT;
        }
        
        ActivityConfig activityConfig = new ActivityConfig();
        activityConfig.setName(name);
        activityConfig.setStatus(status);
        
        return activityConfigMapper.findListWithPagination(activityConfig, offset, limit);
    }
    
    @Override
    public List<ActivityConfig> searchActivitiesByStatus(Integer status, int offset, int limit) {
        // 参数容错处理
        if (offset < 0) {
            offset = 0;
        }
        
        if (limit < ActivityConstants.MIN_LIMIT || limit > ActivityConstants.MAX_LIMIT) {
            limit = ActivityConstants.DEFAULT_LIMIT;
        }
        
        ActivityConfig activityConfig = new ActivityConfig();
        activityConfig.setStatus(status);
        
        return activityConfigMapper.findListWithPagination(activityConfig, offset, limit);
    }
    
    @Override
    @Transactional
    public Long createActivity(String name, Date startTime, Date endTime, Integer totalCells, Integer dailyGameLimit, Integer maxDicePerTime) {
        // 参数校验和默认值设置
        if (totalCells == null || totalCells <= 0) {
            totalCells = ActivityConstants.DEFAULT_TOTAL_CELLS; // 默认30个格子
        }
        
        if (dailyGameLimit == null || dailyGameLimit <= 0) {
            dailyGameLimit = ActivityConstants.DEFAULT_DAILY_GAME_LIMIT; // 默认每日120次
        }
        
        if (maxDicePerTime == null || maxDicePerTime <= 0) {
            maxDicePerTime = ActivityConstants.DEFAULT_MAX_DICE_PER_TIME; // 默认单次最多10个骰子
        }
        
        // 设置活动状态
        int status = ActivityStatusEnum.NOT_STARTED.getCode();
        Date now = new Date();
        if (startTime != null && endTime != null) {
            if (now.after(startTime) && now.before(endTime)) {
                status = ActivityStatusEnum.IN_PROGRESS.getCode();
            } else if (now.after(endTime)) {
                status = ActivityStatusEnum.ENDED.getCode();
            }
        }
        
        ActivityConfig activityConfig = new ActivityConfig();
        activityConfig.setName(name);
        activityConfig.setStartTime(startTime);
        activityConfig.setEndTime(endTime);
        activityConfig.setTotalCells(totalCells);
        activityConfig.setDailyGameLimit(dailyGameLimit);
        activityConfig.setMaxDicePerTime(maxDicePerTime);
        activityConfig.setStatus(status);
        
        activityConfigMapper.insert(activityConfig);
        return activityConfig.getId();
    }
    
    @Override
    @Transactional
    public Long updateActivity(Long id, String name, Date startTime, Date endTime, Integer totalCells, Integer dailyGameLimit, Integer maxDicePerTime, Integer status) {
        ActivityConfig activityConfig = activityConfigMapper.getById(id);
        if (activityConfig == null) {
            return null;
        }
        
        // 更新活动信息
        if (StringUtils.hasText(name)) {
            activityConfig.setName(name);
        }
        
        if (startTime != null) {
            activityConfig.setStartTime(startTime);
        }
        
        if (endTime != null) {
            activityConfig.setEndTime(endTime);
        }
        
        if (totalCells != null && totalCells > 0) {
            activityConfig.setTotalCells(totalCells);
        }
        
        if (dailyGameLimit != null && dailyGameLimit > 0) {
            activityConfig.setDailyGameLimit(dailyGameLimit);
        }
        
        if (maxDicePerTime != null && maxDicePerTime > 0) {
            activityConfig.setMaxDicePerTime(maxDicePerTime);
        }
        
        // 如果没有指定状态，则根据时间自动计算状态
        if (status == null) {
            Date now = new Date();
            if (now.after(activityConfig.getStartTime()) && now.before(activityConfig.getEndTime())) {
                activityConfig.setStatus(ActivityStatusEnum.IN_PROGRESS.getCode());
            } else if (now.after(activityConfig.getEndTime())) {
                activityConfig.setStatus(ActivityStatusEnum.ENDED.getCode());
            } else {
                activityConfig.setStatus(ActivityStatusEnum.NOT_STARTED.getCode());
            }
        } else {
            // 状态范围校验
            if (status >= ActivityStatusEnum.NOT_STARTED.getCode() && status <= ActivityStatusEnum.ENDED.getCode()) {
                activityConfig.setStatus(status);
            }
        }
        
        activityConfigMapper.update(activityConfig);
        return id;
    }
    
    @Override
    @Transactional
    public Long updateActivityStatus(Long id, Integer status) {
        // 状态范围校验
        if (status < ActivityStatusEnum.NOT_STARTED.getCode() || status > ActivityStatusEnum.ENDED.getCode()) {
            return null;
        }
        
        int result = activityConfigMapper.updateStatus(id, status);
        return result > 0 ? id : null;
    }
    
    @Override
    @Transactional
    public Long deleteActivity(Long id) {
        // 先删除活动相关的游戏格子
        GameCell gameCell = new GameCell();
        gameCell.setActivityId(id);
        List<GameCell> gameCells = gameCellMapper.findList(gameCell);
        
        if (gameCells != null && !gameCells.isEmpty()) {
            Long[] cellIds = gameCells.stream().map(GameCell::getId).toArray(Long[]::new);
            gameCellMapper.batchDelete(cellIds);
        }
        
        // 再删除活动
        int result = activityConfigMapper.deleteById(id);
        return result > 0 ? id : null;
    }
    
    @Override
    @Transactional
    public Long[] batchDeleteActivities(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return new Long[0];
        }
        
        // 先删除活动相关的游戏格子
        for (Long id : ids) {
            GameCell gameCell = new GameCell();
            gameCell.setActivityId(id);
            List<GameCell> gameCells = gameCellMapper.findList(gameCell);
            
            if (gameCells != null && !gameCells.isEmpty()) {
                Long[] cellIds = gameCells.stream().map(GameCell::getId).toArray(Long[]::new);
                gameCellMapper.batchDelete(cellIds);
            }
        }
        
        // 再批量删除活动
        int result = activityConfigMapper.batchDelete(ids);
        return result == ids.length ? ids : null;
    }
    
    @Override
    public GameCell loadGameCellById(Long id) {
        return gameCellMapper.getById(id);
    }
    
    @Override
    public GameCell loadGameCellByActivityAndIndex(Long activityId, Integer cellIndex) {
        GameCell gameCell = new GameCell();
        gameCell.setActivityId(activityId);
        gameCell.setCellIndex(cellIndex);
        
        List<GameCell> cells = gameCellMapper.findList(gameCell);
        return cells != null && !cells.isEmpty() ? cells.get(0) : null;
    }
    
    @Override
    public List<GameCell> getGameCellsByActivityId(Long activityId) {
        GameCell gameCell = new GameCell();
        gameCell.setActivityId(activityId);
        return gameCellMapper.findList(gameCell);
    }
    
    @Override
    @Transactional
    public Long createGameCell(Long activityId, Integer cellIndex, String cellType, String rewardType, 
                              Integer rewardAmount, String rewardId, String rewardDesc, Integer fallbackPoints, String iconUrl) {
        // 参数校验
        ActivityConfig activityConfig = activityConfigMapper.getById(activityId);
        if (activityConfig == null) {
            return null;
        }
        
        // 检查格子索引是否在有效范围内
        if (cellIndex == null || cellIndex < 0 || cellIndex >= activityConfig.getTotalCells()) {
            return null;
        }
        
        // 检查该活动下该索引的格子是否已存在
        GameCell existingCell = loadGameCellByActivityAndIndex(activityId, cellIndex);
        if (existingCell != null) {
            return null; // 已存在相同索引的格子
        }
        
        // 奖励类型校验
        if (!RewardTypeEnum.POINTS.getCode().equals(rewardType) && !RewardTypeEnum.GIFT.getCode().equals(rewardType)) {
            rewardType = RewardTypeEnum.POINTS.getCode(); // 默认为积分奖励
        }
        
        // 奖励数量校验
        if (rewardAmount == null || rewardAmount < 0) {
            rewardAmount = 0;
        }
        
        // 替代积分校验
        if (fallbackPoints == null || fallbackPoints < 0) {
            fallbackPoints = 0;
        }
        
        GameCell gameCell = new GameCell();
        gameCell.setActivityId(activityId);
        gameCell.setCellIndex(cellIndex);
        gameCell.setCellType(cellType);
        gameCell.setRewardType(rewardType);
        gameCell.setRewardAmount(rewardAmount);
        gameCell.setRewardId(rewardId);
        gameCell.setRewardDesc(rewardDesc);
        gameCell.setFallbackPoints(fallbackPoints);
        gameCell.setIconUrl(iconUrl);
        
        gameCellMapper.insert(gameCell);
        return gameCell.getId();
    }
    
    @Override
    @Transactional
    public Long updateGameCell(Long id, String cellType, String rewardType, Integer rewardAmount, 
                              String rewardId, String rewardDesc, Integer fallbackPoints, String iconUrl) {
        GameCell gameCell = gameCellMapper.getById(id);
        if (gameCell == null) {
            return null;
        }
        
        // 更新格子信息
        if (StringUtils.hasText(cellType)) {
            gameCell.setCellType(cellType);
        }
        
        // 奖励类型校验
        if (StringUtils.hasText(rewardType)) {
            if (RewardTypeEnum.POINTS.getCode().equals(rewardType) || RewardTypeEnum.GIFT.getCode().equals(rewardType)) {
                gameCell.setRewardType(rewardType);
            }
        }
        
        // 奖励数量校验
        if (rewardAmount != null && rewardAmount >= 0) {
            gameCell.setRewardAmount(rewardAmount);
        }
        
        if (rewardId != null) {
            gameCell.setRewardId(rewardId);
        }
        
        if (rewardDesc != null) {
            gameCell.setRewardDesc(rewardDesc);
        }
        
        // 替代积分校验
        if (fallbackPoints != null && fallbackPoints >= 0) {
            gameCell.setFallbackPoints(fallbackPoints);
        }
        
        if (iconUrl != null) {
            gameCell.setIconUrl(iconUrl);
        }
        
        gameCellMapper.update(gameCell);
        return id;
    }
    
    @Override
    @Transactional
    public Long deleteGameCell(Long id) {
        int result = gameCellMapper.deleteById(id);
        return result > 0 ? id : null;
    }
    
    @Override
    @Transactional
    public Long[] batchDeleteGameCells(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return new Long[0];
        }
        
        int result = gameCellMapper.batchDelete(ids);
        return result == ids.length ? ids : null;
    }
    
    @Override
    @Transactional
    public List<Long> batchCreateGameCells(Long activityId, List<GameCell> gameCells) {
        if (gameCells == null || gameCells.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 参数校验
        ActivityConfig activityConfig = activityConfigMapper.getById(activityId);
        if (activityConfig == null) {
            return new ArrayList<>();
        }
        
        List<Long> createdIds = new ArrayList<>();
        
        for (GameCell cell : gameCells) {
            // 设置活动ID
            cell.setActivityId(activityId);
            
            // 检查格子索引是否在有效范围内
            if (cell.getCellIndex() == null || cell.getCellIndex() < 0 || cell.getCellIndex() >= activityConfig.getTotalCells()) {
                continue;
            }
            
            // 检查该活动下该索引的格子是否已存在
            GameCell existingCell = loadGameCellByActivityAndIndex(activityId, cell.getCellIndex());
            if (existingCell != null) {
                continue; // 已存在相同索引的格子
            }
            
            // 奖励类型校验
            if (!RewardTypeEnum.POINTS.getCode().equals(cell.getRewardType()) && !RewardTypeEnum.GIFT.getCode().equals(cell.getRewardType())) {
                cell.setRewardType(RewardTypeEnum.POINTS.getCode()); // 默认为积分奖励
            }
            
            // 奖励数量校验
            if (cell.getRewardAmount() == null || cell.getRewardAmount() < 0) {
                cell.setRewardAmount(0);
            }
            
            // 替代积分校验
            if (cell.getFallbackPoints() == null || cell.getFallbackPoints() < 0) {
                cell.setFallbackPoints(0);
            }
            
            gameCellMapper.insert(cell);
            createdIds.add(cell.getId());
        }
        
        return createdIds;
    }
}
