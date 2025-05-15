package com.anycompany.demo.jumping.service.impl;

import com.anycompany.demo.jumping.mapper.FeedbackCategoryMapper;
import com.anycompany.demo.jumping.model.FeedbackCategory;
import com.anycompany.demo.jumping.service.FeedbackCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 反馈类别服务实现类
 */
@Service
public class FeedbackCategoryServiceImpl implements FeedbackCategoryService {

    @Autowired
    private FeedbackCategoryMapper feedbackCategoryMapper;
    
    // 分页常量
    private static final int DEFAULT_LIMIT = 10;
    private static final int MIN_LIMIT = 1;
    private static final int MAX_LIMIT = 100;
    
    @Override
    public FeedbackCategory loadCategoryById(Long id) {
        return feedbackCategoryMapper.getById(id);
    }
    
    @Override
    public FeedbackCategory loadCategoryByName(String name) {
        return feedbackCategoryMapper.getByName(name);
    }
    
    @Override
    public List<FeedbackCategory> getAllCategories() {
        return feedbackCategoryMapper.findAll();
    }
    
    @Override
    public List<FeedbackCategory> getEnabledCategories() {
        return feedbackCategoryMapper.findAllEnabled();
    }
    
    @Override
    public List<FeedbackCategory> searchCategoriesByName(String name, int offset, int limit) {
        // 参数容错处理
        if (offset < 0) {
            offset = 0;
        }
        
        if (limit < MIN_LIMIT || limit > MAX_LIMIT) {
            limit = DEFAULT_LIMIT;
        }
        
        FeedbackCategory category = new FeedbackCategory();
        category.setName(name);
        return feedbackCategoryMapper.findList(category);
    }
    
    @Override
    @Transactional
    public Long createCategory(String name, Integer sortOrder, Integer status) {
        FeedbackCategory category = new FeedbackCategory();
        category.setName(name);
        category.setSortOrder(sortOrder);
        category.setStatus(status);
        
        feedbackCategoryMapper.insert(category);
        return category.getId();
    }
    
    @Override
    @Transactional
    public Long updateCategory(Long id, String name, Integer sortOrder, Integer status) {
        FeedbackCategory category = feedbackCategoryMapper.getById(id);
        if (category == null) {
            return null;
        }
        
        category.setName(name);
        category.setSortOrder(sortOrder);
        category.setStatus(status);
        
        feedbackCategoryMapper.update(category);
        return id;
    }
    
    @Override
    @Transactional
    public Long deleteCategory(Long id) {
        int result = feedbackCategoryMapper.deleteById(id);
        return result > 0 ? id : null;
    }
    
    @Override
    @Transactional
    public Long[] batchDeleteCategories(Long[] ids) {
        int result = feedbackCategoryMapper.batchDelete(ids);
        return result == ids.length ? ids : null;
    }
} 