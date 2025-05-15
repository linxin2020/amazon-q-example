package com.anycompany.demo.jumping.controller;

import com.anycompany.demo.jumping.common.exception.BusinessException;
import com.anycompany.demo.jumping.common.exception.ErrorCode;
import com.anycompany.demo.jumping.common.result.ResponseResult;
import com.anycompany.demo.jumping.model.UserFeedback;
import com.anycompany.demo.jumping.service.FeedbackCategoryService;
import com.anycompany.demo.jumping.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户反馈控制器
 */
@RestController
@RequestMapping("/api/feedback")
@Validated
public class UserFeedbackController {
    
    @Autowired
    private UserFeedbackService userFeedbackService;
    
    @Autowired
    private FeedbackCategoryService feedbackCategoryService;
    
    /**
     * 获取反馈详情
     *
     * @param id 反馈ID
     * @return 反馈详情
     */
    @GetMapping("/{id}")
    public ResponseResult<UserFeedback> getFeedbackById(@PathVariable Long id) {
        UserFeedback feedback = userFeedbackService.loadFeedbackById(id);
        if (feedback == null) {
            throw new BusinessException(ErrorCode.FEEDBACK_NOT_FOUND);
        }
        return ResponseResult.success(feedback);
    }
    
    /**
     * 搜索反馈列表
     *
     * @param categoryId 类别ID
     * @param title 标题关键词
     * @param email 邮箱
     * @param status 状态
     * @param offset 起始位置
     * @param limit 每页记录数
     * @return 反馈列表
     */
    @GetMapping("/search")
    public ResponseResult<List<UserFeedback>> searchFeedbacks(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        List<UserFeedback> feedbacks = userFeedbackService.searchFeedbacks(categoryId, title, email, status, offset, limit);
        return ResponseResult.success(feedbacks);
    }
    
    /**
     * 根据类别搜索反馈
     *
     * @param categoryId 类别ID
     * @param offset 起始位置
     * @param limit 每页记录数
     * @return 反馈列表
     */
    @GetMapping("/category/{categoryId}")
    public ResponseResult<List<UserFeedback>> searchFeedbacksByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        // 检查类别是否存在
        if (feedbackCategoryService.loadCategoryById(categoryId) == null) {
            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        
        List<UserFeedback> feedbacks = userFeedbackService.searchFeedbacksByCategory(categoryId, offset, limit);
        return ResponseResult.success(feedbacks);
    }
    
    /**
     * 根据用户搜索反馈
     *
     * @param userId 用户ID
     * @param offset 起始位置
     * @param limit 每页记录数
     * @return 反馈列表
     */
    @GetMapping("/user/{userId}")
    public ResponseResult<List<UserFeedback>> searchFeedbacksByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        List<UserFeedback> feedbacks = userFeedbackService.searchFeedbacksByUser(userId, offset, limit);
        return ResponseResult.success(feedbacks);
    }
    
    /**
     * 根据状态搜索反馈
     *
     * @param status 状态
     * @param offset 起始位置
     * @param limit 每页记录数
     * @return 反馈列表
     */
    @GetMapping("/status/{status}")
    public ResponseResult<List<UserFeedback>> searchFeedbacksByStatus(
            @PathVariable Integer status,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        List<UserFeedback> feedbacks = userFeedbackService.searchFeedbacksByStatus(status, offset, limit);
        return ResponseResult.success(feedbacks);
    }
    
    /**
     * 创建反馈
     *
     * @param categoryId 类别ID
     * @param title 标题
     * @param content 内容
     * @param email 联系邮箱
     * @param userId 用户ID
     * @return 创建的反馈ID
     */
    @PostMapping
    public ResponseResult<Long> createFeedback(
            @RequestParam Long categoryId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String email,
            @RequestParam(required = false) Long userId) {
        // 参数校验
        if (title == null || title.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "反馈标题不能为空");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "反馈内容不能为空");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "联系邮箱不能为空");
        }
        
        // 检查类别是否存在
        if (feedbackCategoryService.loadCategoryById(categoryId) == null) {
            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        
        Long feedbackId = userFeedbackService.createFeedback(categoryId, title, content, email, userId);
        return ResponseResult.success(feedbackId);
    }
    
    /**
     * 更新反馈
     *
     * @param id 反馈ID
     * @param categoryId 类别ID
     * @param title 标题
     * @param content 内容
     * @param email 联系邮箱
     * @param status 状态
     * @param userId 用户ID
     * @return 更新的反馈ID
     */
    @PutMapping("/{id}")
    public ResponseResult<Long> updateFeedback(
            @PathVariable Long id,
            @RequestParam Long categoryId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String email,
            @RequestParam Integer status,
            @RequestParam(required = false) Long userId) {
        // 参数校验
        if (title == null || title.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "反馈标题不能为空");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "反馈内容不能为空");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "联系邮箱不能为空");
        }
        
        // 检查反馈是否存在
        UserFeedback feedback = userFeedbackService.loadFeedbackById(id);
        if (feedback == null) {
            throw new BusinessException(ErrorCode.FEEDBACK_NOT_FOUND);
        }
        
        // 检查类别是否存在
        if (feedbackCategoryService.loadCategoryById(categoryId) == null) {
            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        
        Long feedbackId = userFeedbackService.updateFeedback(id, categoryId, title, content, email, status, userId);
        return ResponseResult.success(feedbackId);
    }
    
    /**
     * 更新反馈状态
     *
     * @param id 反馈ID
     * @param status 状态
     * @return 更新的反馈ID
     */
    @PutMapping("/{id}/status")
    public ResponseResult<Long> updateFeedbackStatus(
            @PathVariable Long id,
            @RequestParam Integer status) {
        // 检查反馈是否存在
        UserFeedback feedback = userFeedbackService.loadFeedbackById(id);
        if (feedback == null) {
            throw new BusinessException(ErrorCode.FEEDBACK_NOT_FOUND);
        }
        
        // 状态校验
        if (status != 0 && status != 1) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "无效的反馈状态，应为0或1");
        }
        
        Long feedbackId = userFeedbackService.updateFeedbackStatus(id, status);
        return ResponseResult.success(feedbackId);
    }
    
    /**
     * 删除反馈
     *
     * @param id 反馈ID
     * @return 删除的反馈ID
     */
    @DeleteMapping("/{id}")
    public ResponseResult<Long> deleteFeedback(@PathVariable Long id) {
        // 检查反馈是否存在
        UserFeedback feedback = userFeedbackService.loadFeedbackById(id);
        if (feedback == null) {
            throw new BusinessException(ErrorCode.FEEDBACK_NOT_FOUND);
        }
        
        Long feedbackId = userFeedbackService.deleteFeedback(id);
        return ResponseResult.success(feedbackId);
    }
    
    /**
     * 批量删除反馈
     *
     * @param ids 反馈ID数组
     * @return 删除的反馈ID数组
     */
    @DeleteMapping("/batch")
    public ResponseResult<Long[]> batchDeleteFeedbacks(@RequestParam Long[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "反馈ID不能为空");
        }
        
        Long[] deletedIds = userFeedbackService.batchDeleteFeedbacks(ids);
        return ResponseResult.success(deletedIds);
    }
} 