package com.anycompany.demo.jumping.service;

import com.anycompany.demo.jumping.model.FeedbackCategory;

import java.util.List;

/**
 * 反馈类别服务接口
 */
public interface FeedbackCategoryService {
    
    /**
     * 根据ID加载反馈类别
     * @param id 类别ID
     * @return 反馈类别信息
     */
    FeedbackCategory loadCategoryById(Long id);
    
    /**
     * 根据名称加载反馈类别
     * @param name 类别名称
     * @return 反馈类别信息
     */
    FeedbackCategory loadCategoryByName(String name);
    
    /**
     * 获取所有反馈类别
     * @return 反馈类别列表
     */
    List<FeedbackCategory> getAllCategories();
    
    /**
     * 获取所有启用的反馈类别
     * @return 反馈类别列表
     */
    List<FeedbackCategory> getEnabledCategories();
    
    /**
     * 根据名称搜索反馈类别，支持分页
     * @param name 类别名称（模糊匹配）
     * @param offset 起始位置，如果为负数将被设置为0
     * @param limit 每页记录数，有最小和最大限制，超出范围将使用默认值
     * @return 反馈类别列表
     */
    List<FeedbackCategory> searchCategoriesByName(String name, int offset, int limit);
    
    /**
     * 创建反馈类别
     * @param name 类别名称
     * @param sortOrder 排序顺序
     * @param status 状态(0:禁用 1:启用)
     * @return 创建的类别ID
     */
    Long createCategory(String name, Integer sortOrder, Integer status);
    
    /**
     * 更新反馈类别
     * @param id 类别ID
     * @param name 类别名称
     * @param sortOrder 排序顺序
     * @param status 状态(0:禁用 1:启用)
     * @return 更新的类别ID
     */
    Long updateCategory(Long id, String name, Integer sortOrder, Integer status);
    
    /**
     * 删除反馈类别
     * @param id 类别ID
     * @return 删除的类别ID
     */
    Long deleteCategory(Long id);
    
    /**
     * 批量删除反馈类别
     * @param ids 类别ID数组
     * @return 删除的类别ID数组
     */
    Long[] batchDeleteCategories(Long[] ids);
} 