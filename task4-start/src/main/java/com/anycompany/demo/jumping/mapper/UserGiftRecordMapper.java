package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.UserGiftRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户礼物道具记录表数据访问接口
 */
@Mapper
public interface UserGiftRecordMapper {
    
    /**
     * 根据ID查询用户礼物道具记录表
     * @param id ID
     * @return 用户礼物道具记录表信息
     */
    UserGiftRecord getById(@Param("id") Long id);
    
    /**
     * 根据用户ID查询用户礼物道具记录表
     * @param userId 用户ID
     * @return 用户礼物道具记录表信息
     */
    UserGiftRecord getByUserId(@Param("userId") Long userId);
    
    /**
     * 根据活动ID查询用户礼物道具记录表
     * @param activityId 活动ID
     * @return 用户礼物道具记录表信息
     */
    UserGiftRecord getByActivityId(@Param("activityId") Long activityId);
    
    /**
     * 根据格子ID查询用户礼物道具记录表
     * @param cellId 格子ID
     * @return 用户礼物道具记录表信息
     */
    UserGiftRecord getByCellId(@Param("cellId") Long cellId);
    
    /**
     * 根据状态(0:待发放 1:已发放)查询用户礼物道具记录表列表
     * @param status 状态(0:待发放 1:已发放)
     * @return 用户礼物道具记录表列表
     */
    List<UserGiftRecord> findByStatus(@Param("status") Integer status);
    
    /**
     * 查询所有用户礼物道具记录表
     * @return 用户礼物道具记录表列表
     */
    List<UserGiftRecord> findAll();
    
    /**
     * 查询所有启用的用户礼物道具记录表
     * @return 用户礼物道具记录表列表
     */
    List<UserGiftRecord> findAllEnabled();
    
    /**
     * 查询用户礼物道具记录表列表
     * @param userGiftRecord 查询条件
     * @return 用户礼物道具记录表列表
     */
    List<UserGiftRecord> findList(UserGiftRecord userGiftRecord);
    
    /**
     * 分页查询用户礼物道具记录表列表
     * @param userGiftRecord 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 用户礼物道具记录表列表
     */
    List<UserGiftRecord> findListWithPagination(@Param("userGiftRecord") UserGiftRecord userGiftRecord, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入用户礼物道具记录表
     * @param userGiftRecord 用户礼物道具记录表信息
     * @return 影响行数
     */
    int insert(UserGiftRecord userGiftRecord);
    
    /**
     * 更新用户礼物道具记录表
     * @param userGiftRecord 用户礼物道具记录表信息
     * @return 影响行数
     */
    int update(UserGiftRecord userGiftRecord);
    
    /**
     * 更新用户礼物道具记录表状态
     * @param id ID
     * @param status 状态值
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 删除用户礼物道具记录表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除用户礼物道具记录表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
