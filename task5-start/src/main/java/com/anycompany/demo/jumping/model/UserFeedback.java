package com.anycompany.demo.jumping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * user_feedback实体类
 */
public class UserFeedback implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 反馈ID
     */
    private Long id;
    
    /**
     * 类别ID
     */
    private Long categoryId;
    
    /**
     * 反馈标题
     */
    private String title;
    
    /**
     * 反馈内容
     */
    private String content;
    
    /**
     * 联系邮箱
     */
    private String email;
    
    /**
     * 状态(0:未处理 1:已处理)
     */
    private Integer status;
    
    /**
     * 用户ID(可选)
     */
    private Long userId;
    
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        return "UserFeedback{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title=" + title +
                ", content=" + content +
                ", email=" + email +
                ", status=" + status +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 
