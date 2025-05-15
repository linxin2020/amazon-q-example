package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.LeaderboardSnapshot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 排行榜快照表数据访问接口
 */
@Mapper
public interface LeaderboardSnapshotMapper {
    
    /**
     * 根据ID查询排行榜快照表
     * @param id ID
     * @return 排行榜快照表信息
     */
    LeaderboardSnapshot getById(@Param("id") Long id);
    
    /**
     * 查询所有排行榜快照表
     * @return 排行榜快照表列表
     */
    List<LeaderboardSnapshot> findAll();
    
    /**
     * 查询排行榜快照表列表
     * @param leaderboardSnapshot 查询条件
     * @return 排行榜快照表列表
     */
    List<LeaderboardSnapshot> findList(LeaderboardSnapshot leaderboardSnapshot);
    
    /**
     * 分页查询排行榜快照表列表
     * @param leaderboardSnapshot 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 排行榜快照表列表
     */
    List<LeaderboardSnapshot> findListWithPagination(@Param("leaderboardSnapshot") LeaderboardSnapshot leaderboardSnapshot, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入排行榜快照表
     * @param leaderboardSnapshot 排行榜快照表信息
     * @return 影响行数
     */
    int insert(LeaderboardSnapshot leaderboardSnapshot);
    
    /**
     * 更新排行榜快照表
     * @param leaderboardSnapshot 排行榜快照表信息
     * @return 影响行数
     */
    int update(LeaderboardSnapshot leaderboardSnapshot);
    
    /**
     * 删除排行榜快照表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除排行榜快照表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
