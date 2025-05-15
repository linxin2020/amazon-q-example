package com.anycompany.demo.jumping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * treasure_box_config实体类
 */
public class TreasureBoxConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 宝箱配置ID
     */
    private Long id;
    
    /**
     * 活动ID
     */
    private Long activityId;
    
    /**
     * 宝箱等级
     */
    private String boxLevel;
    
    /**
     * 宝箱名称
     */
    private String boxName;
    
    /**
     * 所需积分
     */
    private Integer requiredPoints;
    
    /**
     * 奖励类型
     */
    private String rewardType;
    
    /**
     * 奖励内容
     */
    private String rewardContent;
    
    /**
     * 奖励描述
     */
    private String rewardDesc;
    
    /**
     * 宝箱图标URL
     */
    private String boxIconUrl;
    
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

    public String getBoxLevel() {
        return boxLevel;
    }

    public void setBoxLevel(String boxLevel) {
        this.boxLevel = boxLevel;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public Integer getRequiredPoints() {
        return requiredPoints;
    }

    public void setRequiredPoints(Integer requiredPoints) {
        this.requiredPoints = requiredPoints;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getRewardContent() {
        return rewardContent;
    }

    public void setRewardContent(String rewardContent) {
        this.rewardContent = rewardContent;
    }

    public String getRewardDesc() {
        return rewardDesc;
    }

    public void setRewardDesc(String rewardDesc) {
        this.rewardDesc = rewardDesc;
    }

    public String getBoxIconUrl() {
        return boxIconUrl;
    }

    public void setBoxIconUrl(String boxIconUrl) {
        this.boxIconUrl = boxIconUrl;
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
        return "TreasureBoxConfig{" +
                "id=" + id +
                ", activityId=" + activityId +
                ", boxLevel=" + boxLevel +
                ", boxName=" + boxName +
                ", requiredPoints=" + requiredPoints +
                ", rewardType=" + rewardType +
                ", rewardContent=" + rewardContent +
                ", rewardDesc=" + rewardDesc +
                ", boxIconUrl=" + boxIconUrl +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 
