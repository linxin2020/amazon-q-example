package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.BoxExchangeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 宝箱兑换记录表数据访问接口
 */
@Mapper
public interface BoxExchangeRecordMapper {
    
    /**
     * 根据ID查询宝箱兑换记录表
     * @param id ID
     * @return 宝箱兑换记录表信息
     */
    BoxExchangeRecord getById(@Param("id") Long id);
    
    /**
     * 根据状态(0:处理中 1:成功 2:失败)查询宝箱兑换记录表列表
     * @param status 状态(0:处理中 1:成功 2:失败)
     * @return 宝箱兑换记录表列表
     */
    List<BoxExchangeRecord> findByStatus(@Param("status") Integer status);
    
    /**
     * 查询所有宝箱兑换记录表
     * @return 宝箱兑换记录表列表
     */
    List<BoxExchangeRecord> findAll();
    
    /**
     * 查询所有启用的宝箱兑换记录表
     * @return 宝箱兑换记录表列表
     */
    List<BoxExchangeRecord> findAllEnabled();
    
    /**
     * 查询宝箱兑换记录表列表
     * @param boxExchangeRecord 查询条件
     * @return 宝箱兑换记录表列表
     */
    List<BoxExchangeRecord> findList(BoxExchangeRecord boxExchangeRecord);
    
    /**
     * 分页查询宝箱兑换记录表列表
     * @param boxExchangeRecord 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 宝箱兑换记录表列表
     */
    List<BoxExchangeRecord> findListWithPagination(@Param("boxExchangeRecord") BoxExchangeRecord boxExchangeRecord, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入宝箱兑换记录表
     * @param boxExchangeRecord 宝箱兑换记录表信息
     * @return 影响行数
     */
    int insert(BoxExchangeRecord boxExchangeRecord);
    
    /**
     * 更新宝箱兑换记录表
     * @param boxExchangeRecord 宝箱兑换记录表信息
     * @return 影响行数
     */
    int update(BoxExchangeRecord boxExchangeRecord);
    
    /**
     * 更新宝箱兑换记录表状态
     * @param id ID
     * @param status 状态值
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 删除宝箱兑换记录表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除宝箱兑换记录表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
