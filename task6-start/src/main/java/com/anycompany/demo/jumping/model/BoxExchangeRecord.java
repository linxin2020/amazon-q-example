package com.anycompany.demo.jumping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * box_exchange_record实体类
 */
public class BoxExchangeRecord implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 兑换记录ID
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
     * 宝箱ID
     */
    private Long boxId;
    
    /**
     * 宝箱等级
     */
    private String boxLevel;
    
    /**
     * 消耗积分
     */
    private Integer costPoints;
    
    /**
     * 奖励信息(JSON)
     */
    private String rewardInfo;
    
    /**
     * 状态(0:处理中 1:成功 2:失败)
     */
    private Integer status;
    
    /**
     * 兑换时间
     */
    private Date exchangeTime;
    
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

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public String getBoxLevel() {
        return boxLevel;
    }

    public void setBoxLevel(String boxLevel) {
        this.boxLevel = boxLevel;
    }

    public Integer getCostPoints() {
        return costPoints;
    }

    public void setCostPoints(Integer costPoints) {
        this.costPoints = costPoints;
    }

    public String getRewardInfo() {
        return rewardInfo;
    }

    public void setRewardInfo(String rewardInfo) {
        this.rewardInfo = rewardInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
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
        return "BoxExchangeRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", activityId=" + activityId +
                ", boxId=" + boxId +
                ", boxLevel=" + boxLevel +
                ", costPoints=" + costPoints +
                ", rewardInfo=" + rewardInfo +
                ", status=" + status +
                ", exchangeTime=" + exchangeTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 
