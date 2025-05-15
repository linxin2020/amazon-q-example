package com.anycompany.demo.jumping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * activity_config实体类
 */
public class ActivityConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 活动ID
     */
    private Long id;
    
    /**
     * 活动名称
     */
    private String name;
    
    /**
     * 活动开始时间
     */
    private Date startTime;
    
    /**
     * 活动结束时间
     */
    private Date endTime;
    
    /**
     * 总格子数
     */
    private Integer totalCells;
    
    /**
     * 每日游戏次数上限
     */
    private Integer dailyGameLimit;
    
    /**
     * 单次最大使用骰子数
     */
    private Integer maxDicePerTime;
    
    /**
     * 活动状态(0:未开始 1:进行中 2:已结束)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTotalCells() {
        return totalCells;
    }

    public void setTotalCells(Integer totalCells) {
        this.totalCells = totalCells;
    }

    public Integer getDailyGameLimit() {
        return dailyGameLimit;
    }

    public void setDailyGameLimit(Integer dailyGameLimit) {
        this.dailyGameLimit = dailyGameLimit;
    }

    public Integer getMaxDicePerTime() {
        return maxDicePerTime;
    }

    public void setMaxDicePerTime(Integer maxDicePerTime) {
        this.maxDicePerTime = maxDicePerTime;
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
        return "ActivityConfig{" +
                "id=" + id +
                ", name=" + name +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalCells=" + totalCells +
                ", dailyGameLimit=" + dailyGameLimit +
                ", maxDicePerTime=" + maxDicePerTime +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 
