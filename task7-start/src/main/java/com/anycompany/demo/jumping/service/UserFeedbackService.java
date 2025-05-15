package com.anycompany.demo.jumping.service;

import com.anycompany.demo.jumping.model.UserFeedback;

import java.util.List;

/**
 * 用户反馈服务接口
 */
public interface UserFeedbackService {
    
    /**
     * 根据ID加载反馈
     * @param id 反馈ID
     * @return 反馈信息
     */
    UserFeedback loadFeedbackById(Long id);
    
    /**
     * 搜索反馈列表，支持分页
     * @param categoryId 类别ID
     * @param title 标题关键词
     * @param email 邮箱
     * @param status 状态
     * @param offset 起始位置，如果为负数将被设置为0
     * @param limit 每页记录数，有最小和最大限制，超出范围将使用默认值
     * @return 反馈列表
     */
    List<UserFeedback> searchFeedbacks(Long categoryId, String title, String email, Integer status, int offset, int limit);
    
    /**
     * 根据类别ID搜索反馈，支持分页
     * @param categoryId 类别ID
     * @param offset 起始位置，如果为负数将被设置为0
     * @param limit 每页记录数，有最小和最大限制，超出范围将使用默认值
     * @return 反馈列表
     */
    List<UserFeedback> searchFeedbacksByCategory(Long categoryId, int offset, int limit);
    
    /**
     * 根据用户ID搜索反馈，支持分页
     * @param userId 用户ID
     * @param offset 起始位置，如果为负数将被设置为0
     * @param limit 每页记录数，有最小和最大限制，超出范围将使用默认值
     * @return 反馈列表
     */
    List<UserFeedback> searchFeedbacksByUser(Long userId, int offset, int limit);
    
    /**
     * 根据状态搜索反馈，支持分页
     * @param status 状态(0:未处理 1:已处理)
     * @param offset 起始位置，如果为负数将被设置为0
     * @param limit 每页记录数，有最小和最大限制，超出范围将使用默认值
     * @return 反馈列表
     */
    List<UserFeedback> searchFeedbacksByStatus(Integer status, int offset, int limit);
    
    /**
     * 创建反馈
     * @param categoryId 类别ID
     * @param title 标题
     * @param content 内容
     * @param email 联系邮箱
     * @param userId 用户ID(可选)
     * @return 创建的反馈ID
     */
    Long createFeedback(Long categoryId, String title, String content, String email, Long userId);
    
    /**
     * 更新反馈
     * @param id 反馈ID
     * @param categoryId 类别ID
     * @param title 标题
     * @param content 内容
     * @param email 联系邮箱
     * @param status 状态
     * @param userId 用户ID
     * @return 更新的反馈ID
     */
    Long updateFeedback(Long id, Long categoryId, String title, String content, String email, Integer status, Long userId);
    
    /**
     * 更新反馈状态
     * @param id 反馈ID
     * @param status 状态(0:未处理 1:已处理)
     * @return 更新的反馈ID
     */
    Long updateFeedbackStatus(Long id, Integer status);
    
    /**
     * 删除反馈
     * @param id 反馈ID
     * @return 删除的反馈ID
     */
    Long deleteFeedback(Long id);
    
    /**
     * 批量删除反馈
     * @param ids 反馈ID数组
     * @return 删除的反馈ID数组
     */
    Long[] batchDeleteFeedbacks(Long[] ids);
} 