package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.UserGameData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户游戏数据表数据访问接口
 */
@Mapper
public interface UserGameDataMapper {
    
    /**
     * 根据ID查询用户游戏数据表
     * @param id ID
     * @return 用户游戏数据表信息
     */
    UserGameData getById(@Param("id") Long id);
    
    /**
     * 根据用户ID查询用户游戏数据表
     * @param userId 用户ID
     * @return 用户游戏数据表信息
     */
    UserGameData getByUserId(@Param("userId") Long userId);
    
    /**
     * 根据活动ID查询用户游戏数据表
     * @param activityId 活动ID
     * @return 用户游戏数据表信息
     */
    UserGameData getByActivityId(@Param("activityId") Long activityId);
    
    /**
     * 查询所有用户游戏数据表
     * @return 用户游戏数据表列表
     */
    List<UserGameData> findAll();
    
    /**
     * 查询用户游戏数据表列表
     * @param userGameData 查询条件
     * @return 用户游戏数据表列表
     */
    List<UserGameData> findList(UserGameData userGameData);
    
    /**
     * 分页查询用户游戏数据表列表
     * @param userGameData 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 用户游戏数据表列表
     */
    List<UserGameData> findListWithPagination(@Param("userGameData") UserGameData userGameData, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入用户游戏数据表
     * @param userGameData 用户游戏数据表信息
     * @return 影响行数
     */
    int insert(UserGameData userGameData);
    
    /**
     * 更新用户游戏数据表
     * @param userGameData 用户游戏数据表信息
     * @return 影响行数
     */
    int update(UserGameData userGameData);
    
    /**
     * 删除用户游戏数据表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除用户游戏数据表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
