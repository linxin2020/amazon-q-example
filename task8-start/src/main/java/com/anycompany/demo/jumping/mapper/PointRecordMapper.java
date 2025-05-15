package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.PointRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 积分记录表数据访问接口
 */
@Mapper
public interface PointRecordMapper {
    
    /**
     * 根据ID查询积分记录表
     * @param id ID
     * @return 积分记录表信息
     */
    PointRecord getById(@Param("id") Long id);
    
    /**
     * 查询所有积分记录表
     * @return 积分记录表列表
     */
    List<PointRecord> findAll();
    
    /**
     * 查询积分记录表列表
     * @param pointRecord 查询条件
     * @return 积分记录表列表
     */
    List<PointRecord> findList(PointRecord pointRecord);
    
    /**
     * 分页查询积分记录表列表
     * @param pointRecord 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 积分记录表列表
     */
    List<PointRecord> findListWithPagination(@Param("pointRecord") PointRecord pointRecord, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入积分记录表
     * @param pointRecord 积分记录表信息
     * @return 影响行数
     */
    int insert(PointRecord pointRecord);
    
    /**
     * 更新积分记录表
     * @param pointRecord 积分记录表信息
     * @return 影响行数
     */
    int update(PointRecord pointRecord);
    
    /**
     * 删除积分记录表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除积分记录表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
