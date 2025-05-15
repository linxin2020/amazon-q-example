package com.anycompany.demo.jumping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * user_game_data实体类
 */
public class UserGameData implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 游戏数据ID
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
     * 当前位置
     */
    private Integer currentPosition;
    
    /**
     * 剩余游戏机会
     */
    private Integer remainingChances;
    
    /**
     * 当日已用机会
     */
    private Integer dailyUsedChances;
    
    /**
     * 总积分
     */
    private Integer totalPoints;
    
    /**
     * 最后游戏时间
     */
    private Date lastGameTime;
    
    /**
     * 是否新用户(0:否 1:是)
     */
    private Integer isNewUser;
    
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

    public Integer getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Integer currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Integer getRemainingChances() {
        return remainingChances;
    }

    public void setRemainingChances(Integer remainingChances) {
        this.remainingChances = remainingChances;
    }

    public Integer getDailyUsedChances() {
        return dailyUsedChances;
    }

    public void setDailyUsedChances(Integer dailyUsedChances) {
        this.dailyUsedChances = dailyUsedChances;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Date getLastGameTime() {
        return lastGameTime;
    }

    public void setLastGameTime(Date lastGameTime) {
        this.lastGameTime = lastGameTime;
    }

    public Integer getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(Integer isNewUser) {
        this.isNewUser = isNewUser;
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
        return "UserGameData{" +
                "id=" + id +
                ", userId=" + userId +
                ", activityId=" + activityId +
                ", currentPosition=" + currentPosition +
                ", remainingChances=" + remainingChances +
                ", dailyUsedChances=" + dailyUsedChances +
                ", totalPoints=" + totalPoints +
                ", lastGameTime=" + lastGameTime +
                ", isNewUser=" + isNewUser +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 
