package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.ActivityConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动配置表数据访问接口
 */
@Mapper
public interface ActivityConfigMapper {
    
    /**
     * 根据ID查询活动配置表
     * @param id ID
     * @return 活动配置表信息
     */
    ActivityConfig getById(@Param("id") Long id);
    
    /**
     * 根据活动名称查询活动配置表列表
     * @param name 活动名称
     * @return 活动配置表列表
     */
    List<ActivityConfig> findByName(@Param("name") String name);
    
    /**
     * 根据活动状态(0:未开始 1:进行中 2:已结束)查询活动配置表列表
     * @param status 活动状态(0:未开始 1:进行中 2:已结束)
     * @return 活动配置表列表
     */
    List<ActivityConfig> findByStatus(@Param("status") Integer status);
    
    /**
     * 查询所有活动配置表
     * @return 活动配置表列表
     */
    List<ActivityConfig> findAll();
    
    /**
     * 查询所有启用的活动配置表
     * @return 活动配置表列表
     */
    List<ActivityConfig> findAllEnabled();
    
    /**
     * 查询活动配置表列表
     * @param activityConfig 查询条件
     * @return 活动配置表列表
     */
    List<ActivityConfig> findList(ActivityConfig activityConfig);
    
    /**
     * 分页查询活动配置表列表
     * @param activityConfig 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 活动配置表列表
     */
    List<ActivityConfig> findListWithPagination(@Param("activityConfig") ActivityConfig activityConfig, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入活动配置表
     * @param activityConfig 活动配置表信息
     * @return 影响行数
     */
    int insert(ActivityConfig activityConfig);
    
    /**
     * 更新活动配置表
     * @param activityConfig 活动配置表信息
     * @return 影响行数
     */
    int update(ActivityConfig activityConfig);
    
    /**
     * 更新活动配置表状态
     * @param id ID
     * @param status 状态值
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 删除活动配置表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除活动配置表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
