package com.anycompany.demo.jumping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * game_record实体类
 */
public class GameRecord implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 游戏记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 活动ID
     */
    private Long activityId;
    
    /**
     * 使用骰子数
     */
    private Integer diceCount;
    
    /**
     * 骰子点数(逗号分隔)
     */
    private String dicePoints;
    
    /**
     * 起始位置
     */
    private Integer startPosition;
    
    /**
     * 结束位置
     */
    private Integer endPosition;
    
    /**
     * 奖励信息(JSON)
     */
    private String rewardInfo;
    
    /**
     * 获得积分
     */
    private Integer gainedPoints;
    
    /**
     * 游戏时间
     */
    private Date gameTime;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getDiceCount() {
        return diceCount;
    }

    public void setDiceCount(Integer diceCount) {
        this.diceCount = diceCount;
    }

    public String getDicePoints() {
        return dicePoints;
    }

    public void setDicePoints(String dicePoints) {
        this.dicePoints = dicePoints;
    }

    public Integer getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }

    public Integer getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Integer endPosition) {
        this.endPosition = endPosition;
    }

    public String getRewardInfo() {
        return rewardInfo;
    }

    public void setRewardInfo(String rewardInfo) {
        this.rewardInfo = rewardInfo;
    }

    public Integer getGainedPoints() {
        return gainedPoints;
    }

    public void setGainedPoints(Integer gainedPoints) {
        this.gainedPoints = gainedPoints;
    }

    public Date getGameTime() {
        return gameTime;
    }

    public void setGameTime(Date gameTime) {
        this.gameTime = gameTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", activityId=" + activityId +
                ", diceCount=" + diceCount +
                ", dicePoints=" + dicePoints +
                ", startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                ", rewardInfo=" + rewardInfo +
                ", gainedPoints=" + gainedPoints +
                ", gameTime=" + gameTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 
