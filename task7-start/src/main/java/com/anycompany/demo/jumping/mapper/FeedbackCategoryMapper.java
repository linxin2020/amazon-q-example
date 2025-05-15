package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.FeedbackCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 反馈类别表数据访问接口
 */
@Mapper
public interface FeedbackCategoryMapper {
    
    /**
     * 根据ID查询反馈类别表
     * @param id ID
     * @return 反馈类别表信息
     */
    FeedbackCategory getById(@Param("id") Long id);
    
    /**
     * 根据类别名称查询反馈类别表
     * @param name 类别名称
     * @return 反馈类别表信息
     */
    FeedbackCategory getByName(@Param("name") String name);
    
    /**
     * 根据状态(0:禁用 1:启用)查询反馈类别表列表
     * @param status 状态(0:禁用 1:启用)
     * @return 反馈类别表列表
     */
    List<FeedbackCategory> findByStatus(@Param("status") Integer status);
    
    /**
     * 查询所有反馈类别表
     * @return 反馈类别表列表
     */
    List<FeedbackCategory> findAll();
    
    /**
     * 查询所有启用的反馈类别表
     * @return 反馈类别表列表
     */
    List<FeedbackCategory> findAllEnabled();
    
    /**
     * 查询反馈类别表列表
     * @param feedbackCategory 查询条件
     * @return 反馈类别表列表
     */
    List<FeedbackCategory> findList(FeedbackCategory feedbackCategory);
    
    /**
     * 分页查询反馈类别表列表
     * @param feedbackCategory 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 反馈类别表列表
     */
    List<FeedbackCategory> findListWithPagination(@Param("feedbackCategory") FeedbackCategory feedbackCategory, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入反馈类别表
     * @param feedbackCategory 反馈类别表信息
     * @return 影响行数
     */
    int insert(FeedbackCategory feedbackCategory);
    
    /**
     * 更新反馈类别表
     * @param feedbackCategory 反馈类别表信息
     * @return 影响行数
     */
    int update(FeedbackCategory feedbackCategory);
    
    /**
     * 更新反馈类别表状态
     * @param id ID
     * @param status 状态值
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 删除反馈类别表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除反馈类别表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
