package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.TreasureBoxConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 宝箱配置表数据访问接口
 */
@Mapper
public interface TreasureBoxConfigMapper {
    
    /**
     * 根据ID查询宝箱配置表
     * @param id ID
     * @return 宝箱配置表信息
     */
    TreasureBoxConfig getById(@Param("id") Long id);
    
    /**
     * 根据活动ID查询宝箱配置表
     * @param activityId 活动ID
     * @return 宝箱配置表信息
     */
    TreasureBoxConfig getByActivityId(@Param("activityId") Long activityId);
    
    /**
     * 根据宝箱等级查询宝箱配置表
     * @param boxLevel 宝箱等级
     * @return 宝箱配置表信息
     */
    TreasureBoxConfig getByBoxLevel(@Param("boxLevel") String boxLevel);
    
    /**
     * 查询所有宝箱配置表
     * @return 宝箱配置表列表
     */
    List<TreasureBoxConfig> findAll();
    
    /**
     * 查询宝箱配置表列表
     * @param treasureBoxConfig 查询条件
     * @return 宝箱配置表列表
     */
    List<TreasureBoxConfig> findList(TreasureBoxConfig treasureBoxConfig);
    
    /**
     * 分页查询宝箱配置表列表
     * @param treasureBoxConfig 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 宝箱配置表列表
     */
    List<TreasureBoxConfig> findListWithPagination(@Param("treasureBoxConfig") TreasureBoxConfig treasureBoxConfig, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入宝箱配置表
     * @param treasureBoxConfig 宝箱配置表信息
     * @return 影响行数
     */
    int insert(TreasureBoxConfig treasureBoxConfig);
    
    /**
     * 更新宝箱配置表
     * @param treasureBoxConfig 宝箱配置表信息
     * @return 影响行数
     */
    int update(TreasureBoxConfig treasureBoxConfig);
    
    /**
     * 删除宝箱配置表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除宝箱配置表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
