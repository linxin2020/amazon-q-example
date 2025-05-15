package com.anycompany.demo.jumping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * leaderboard_snapshot实体类
 */
public class LeaderboardSnapshot implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 快照ID
     */
    private Long id;
    
    /**
     * 活动ID
     */
    private Long activityId;
    
    /**
     * 快照类型(HOURLY:每小时 DAILY:每日)
     */
    private String snapshotType;
    
    /**
     * 排名数据(JSON)
     */
    private String rankData;
    
    /**
     * 快照时间
     */
    private Date snapshotTime;
    
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

    public String getSnapshotType() {
        return snapshotType;
    }

    public void setSnapshotType(String snapshotType) {
        this.snapshotType = snapshotType;
    }

    public String getRankData() {
        return rankData;
    }

    public void setRankData(String rankData) {
        this.rankData = rankData;
    }

    public Date getSnapshotTime() {
        return snapshotTime;
    }

    public void setSnapshotTime(Date snapshotTime) {
        this.snapshotTime = snapshotTime;
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
        return "LeaderboardSnapshot{" +
                "id=" + id +
                ", activityId=" + activityId +
                ", snapshotType=" + snapshotType +
                ", rankData=" + rankData +
                ", snapshotTime=" + snapshotTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 
