package com.anycompany.demo.jumping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * game_cell实体类
 */
public class GameCell implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 格子ID
     */
    private Long id;
    
    /**
     * 活动ID
     */
    private Long activityId;
    
    /**
     * 格子索引
     */
    private Integer cellIndex;
    
    /**
     * 格子类型
     */
    private String cellType;
    
    /**
     * 奖励类型(POINTS:积分 GIFT:礼物道具)
     */
    private String rewardType;
    
    /**
     * 奖励数量
     */
    private Integer rewardAmount;
    
    /**
     * 礼物道具ID
     */
    private String rewardId;
    
    /**
     * 奖励描述
     */
    private String rewardDesc;
    
    /**
     * 礼物已领取时的替代积分
     */
    private Integer fallbackPoints;
    
    /**
     * 图标URL
     */
    private String iconUrl;
    
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

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getCellIndex() {
        return cellIndex;
    }

    public void setCellIndex(Integer cellIndex) {
        this.cellIndex = cellIndex;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public Integer getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Integer rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getRewardDesc() {
        return rewardDesc;
    }

    public void setRewardDesc(String rewardDesc) {
        this.rewardDesc = rewardDesc;
    }

    public Integer getFallbackPoints() {
        return fallbackPoints;
    }

    public void setFallbackPoints(Integer fallbackPoints) {
        this.fallbackPoints = fallbackPoints;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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
        return "GameCell{" +
                "id=" + id +
                ", activityId=" + activityId +
                ", cellIndex=" + cellIndex +
                ", cellType=" + cellType +
                ", rewardType=" + rewardType +
                ", rewardAmount=" + rewardAmount +
                ", rewardId=" + rewardId +
                ", rewardDesc=" + rewardDesc +
                ", fallbackPoints=" + fallbackPoints +
                ", iconUrl=" + iconUrl +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 
