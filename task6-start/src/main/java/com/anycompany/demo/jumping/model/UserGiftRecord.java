package com.anycompany.demo.jumping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * user_gift_record实体类
 */
public class UserGiftRecord implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 礼物记录ID
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
     * 格子ID
     */
    private Long cellId;
    
    /**
     * 礼物道具ID
     */
    private String giftId;
    
    /**
     * 礼物道具类型
     */
    private String giftType;
    
    /**
     * 道具数量
     */
    private Integer giftAmount;
    
    /**
     * 领取时间
     */
    private Date receiveTime;
    
    /**
     * 状态(0:待发放 1:已发放)
     */
    private Integer status;
    
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

    public Long getCellId() {
        return cellId;
    }

    public void setCellId(Long cellId) {
        this.cellId = cellId;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftType() {
        return giftType;
    }

    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }

    public Integer getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(Integer giftAmount) {
        this.giftAmount = giftAmount;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "UserGiftRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", activityId=" + activityId +
                ", cellId=" + cellId +
                ", giftId=" + giftId +
                ", giftType=" + giftType +
                ", giftAmount=" + giftAmount +
                ", receiveTime=" + receiveTime +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 
