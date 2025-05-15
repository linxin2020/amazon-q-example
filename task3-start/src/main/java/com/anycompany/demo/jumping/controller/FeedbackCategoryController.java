package com.anycompany.demo.jumping.controller;

import com.anycompany.demo.jumping.common.exception.BusinessException;
import com.anycompany.demo.jumping.common.exception.ErrorCode;
import com.anycompany.demo.jumping.common.result.ResponseResult;
import com.anycompany.demo.jumping.model.FeedbackCategory;
import com.anycompany.demo.jumping.service.FeedbackCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 反馈类别控制器
 */
@RestController
@RequestMapping("/api/feedback/categories")
@Validated
public class FeedbackCategoryController {
    
    @Autowired
    private FeedbackCategoryService feedbackCategoryService;
    
    /**
     * 获取类别详情
     *
     * @param id 类别ID
     * @return 类别详情
     */
    @GetMapping("/{id}")
    public ResponseResult<FeedbackCategory> getCategoryById(@PathVariable Long id) {
        FeedbackCategory category = feedbackCategoryService.loadCategoryById(id);
        if (category == null) {
            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        return ResponseResult.success(category);
    }
    
    /**
     * 获取所有类别
     *
     * @return 类别列表
     */
    @GetMapping
    public ResponseResult<List<FeedbackCategory>> getAllCategories() {
        List<FeedbackCategory> categories = feedbackCategoryService.getAllCategories();
        return ResponseResult.success(categories);
    }
    
    /**
     * 获取所有启用的类别
     *
     * @return 启用的类别列表
     */
    @GetMapping("/enabled")
    public ResponseResult<List<FeedbackCategory>> getEnabledCategories() {
        List<FeedbackCategory> categories = feedbackCategoryService.getEnabledCategories();
        return ResponseResult.success(categories);
    }
    
    /**
     * 根据名称搜索类别
     *
     * @param name 类别名称
     * @param offset 起始位置
     * @param limit 每页记录数
     * @return 类别列表
     */
    @GetMapping("/search")
    public ResponseResult<List<FeedbackCategory>> searchCategoriesByName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        List<FeedbackCategory> categories = feedbackCategoryService.searchCategoriesByName(name, offset, limit);
        return ResponseResult.success(categories);
    }
    
    /**
     * 创建类别
     *
     * @param name 类别名称
     * @param sortOrder 排序顺序
     * @param status 状态
     * @return 创建的类别ID
     */
    @PostMapping
    public ResponseResult<Long> createCategory(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") Integer sortOrder,
            @RequestParam(defaultValue = "1") Integer status) {
        // 参数校验
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "类别名称不能为空");
        }
        
        // 检查名称是否已存在
        FeedbackCategory existingCategory = feedbackCategoryService.loadCategoryByName(name);
        if (existingCategory != null) {
            throw new BusinessException(ErrorCode.CATEGORY_NAME_DUPLICATE);
        }
        
        Long categoryId = feedbackCategoryService.createCategory(name, sortOrder, status);
        return ResponseResult.success(categoryId);
    }
    
    /**
     * 更新类别
     *
     * @param id 类别ID
     * @param name 类别名称
     * @param sortOrder 排序顺序
     * @param status 状态
     * @return 更新的类别ID
     */
    @PutMapping("/{id}")
    public ResponseResult<Long> updateCategory(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam Integer sortOrder,
            @RequestParam Integer status) {
        // 参数校验
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "类别名称不能为空");
        }
        
        // 检查类别是否存在
        FeedbackCategory category = feedbackCategoryService.loadCategoryById(id);
        if (category == null) {
            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        
        // 检查名称是否已存在（排除当前类别）
        FeedbackCategory existingCategory = feedbackCategoryService.loadCategoryByName(name);
        if (existingCategory != null && !existingCategory.getId().equals(id)) {
            throw new BusinessException(ErrorCode.CATEGORY_NAME_DUPLICATE);
        }
        
        Long categoryId = feedbackCategoryService.updateCategory(id, name, sortOrder, status);
        return ResponseResult.success(categoryId);
    }
    
    /**
     * 删除类别
     *
     * @param id 类别ID
     * @return 删除的类别ID
     */
    @DeleteMapping("/{id}")
    public ResponseResult<Long> deleteCategory(@PathVariable Long id) {
        // 检查类别是否存在
        FeedbackCategory category = feedbackCategoryService.loadCategoryById(id);
        if (category == null) {
            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        
        Long categoryId = feedbackCategoryService.deleteCategory(id);
        return ResponseResult.success(categoryId);
    }
    
    /**
     * 批量删除类别
     *
     * @param ids 类别ID数组
     * @return 删除的类别ID数组
     */
    @DeleteMapping("/batch")
    public ResponseResult<Long[]> batchDeleteCategories(@RequestParam Long[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR.getCode(), "类别ID不能为空");
        }
        
        Long[] deletedIds = feedbackCategoryService.batchDeleteCategories(ids);
        return ResponseResult.success(deletedIds);
    }
} 