package com.anycompany.demo.jumping.service.impl;

import com.anycompany.demo.jumping.mapper.UserFeedbackMapper;
import com.anycompany.demo.jumping.model.UserFeedback;
import com.anycompany.demo.jumping.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户反馈服务实现类
 */
@Service
public class UserFeedbackServiceImpl implements UserFeedbackService {

    @Autowired
    private UserFeedbackMapper userFeedbackMapper;
    
    // 分页常量
    private static final int DEFAULT_LIMIT = 10;
    private static final int MIN_LIMIT = 1;
    private static final int MAX_LIMIT = 100;
    
    @Override
    public UserFeedback loadFeedbackById(Long id) {
        return userFeedbackMapper.getById(id);
    }
    
    @Override
    public List<UserFeedback> searchFeedbacks(Long categoryId, String title, String email, Integer status, int offset, int limit) {
        // 参数容错处理
        if (offset < 0) {
            offset = 0;
        }
        
        if (limit < MIN_LIMIT || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }
        
        UserFeedback feedback = new UserFeedback();
        feedback.setCategoryId(categoryId);
        feedback.setTitle(title);
        feedback.setEmail(email);
        feedback.setStatus(status);
        
        return userFeedbackMapper.findListWithPagination(feedback, offset, limit);
    }
    
    @Override
    public List<UserFeedback> searchFeedbacksByCategory(Long categoryId, int offset, int limit) {
        // 参数容错处理
        if (offset < 0) {
            offset = 0;
        }
        
        if (limit < MIN_LIMIT || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }
        
        UserFeedback feedback = new UserFeedback();
        feedback.setCategoryId(categoryId);
        
        return userFeedbackMapper.findListWithPagination(feedback, offset, limit);
    }
    
    @Override
    public List<UserFeedback> searchFeedbacksByUser(Long userId, int offset, int limit) {
        // 参数容错处理
        if (offset < 0) {
            offset = 0;
        }
        
        if (limit < MIN_LIMIT || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }
        
        UserFeedback feedback = new UserFeedback();
        feedback.setUserId(userId);
        
        return userFeedbackMapper.findListWithPagination(feedback, offset, limit);
    }
    
    @Override
    public List<UserFeedback> searchFeedbacksByStatus(Integer status, int offset, int limit) {
        // 参数容错处理
        if (offset < 0) {
            offset = 0;
        }
        
        if (limit < MIN_LIMIT || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }
        
        UserFeedback feedback = new UserFeedback();
        feedback.setStatus(status);
        
        return userFeedbackMapper.findListWithPagination(feedback, offset, limit);
    }
    
    @Override
    @Transactional
    public Long createFeedback(Long categoryId, String title, String content, String email, Long userId) {
        UserFeedback feedback = new UserFeedback();
        feedback.setCategoryId(categoryId);
        feedback.setTitle(title);
        feedback.setContent(content);
        feedback.setEmail(email);
        feedback.setUserId(userId);
        feedback.setStatus(0); // 默认设置为未处理状态
        
        userFeedbackMapper.insert(feedback);
        return feedback.getId();
    }
    
    @Override
    @Transactional
    public Long updateFeedback(Long id, Long categoryId, String title, String content, String email, Integer status, Long userId) {
        UserFeedback feedback = userFeedbackMapper.getById(id);
        if (feedback == null) {
            return null;
        }
        
        feedback.setCategoryId(categoryId);
        feedback.setTitle(title);
        feedback.setContent(content);
        feedback.setEmail(email);
        feedback.setStatus(status);
        feedback.setUserId(userId);
        
        userFeedbackMapper.update(feedback);
        return id;
    }
    
    @Override
    @Transactional
    public Long updateFeedbackStatus(Long id, Integer status) {
        int result = userFeedbackMapper.updateStatus(id, status);
        return result > 0 ? id : null;
    }
    
    @Override
    @Transactional
    public Long deleteFeedback(Long id) {
        int result = userFeedbackMapper.deleteById(id);
        return result > 0 ? id : null;
    }
    
    @Override
    @Transactional
    public Long[] batchDeleteFeedbacks(Long[] ids) {
        int result = userFeedbackMapper.batchDelete(ids);
        return result == ids.length ? ids : null;
    }
} 