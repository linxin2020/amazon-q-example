package com.anycompany.demo.jumping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * point_record实体类
 */
public class PointRecord implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 积分记录ID
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
     * 积分变化
     */
    private Integer points;
    
    /**
     * 操作类型(GAME_REWARD:游戏奖励 FALLBACK_GIFT:礼物替代 BOX_EXCHANGE:宝箱兑换)
     */
    private String operationType;
    
    /**
     * 操作描述
     */
    private String operationDesc;
    
    /**
     * 关联ID
     */
    private Long referenceId;
    
    /**
     * 关联类型(CELL:格子 GIFT:礼物 BOX:宝箱)
     */
    private String referenceType;
    
    /**
     * 操作时间
     */
    private Date operationTime;
    
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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
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
        return "PointRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", activityId=" + activityId +
                ", points=" + points +
                ", operationType=" + operationType +
                ", operationDesc=" + operationDesc +
                ", referenceId=" + referenceId +
                ", referenceType=" + referenceType +
                ", operationTime=" + operationTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 
