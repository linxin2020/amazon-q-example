package com.anycompany.demo.jumping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * feedback_category实体类
 */
public class FeedbackCategory implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 类别ID
     */
    private Long id;
    
    /**
     * 类别名称
     */
    private String name;
    
    /**
     * 排序顺序
     */
    private Integer sortOrder;
    
    /**
     * 状态(0:禁用 1:启用)
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

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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
        return "FeedbackCategory{" +
                "id=" + id +
                ", name=" + name +
                ", sortOrder=" + sortOrder +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 
