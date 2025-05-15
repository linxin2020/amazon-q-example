package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.GameCell;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 游戏格子表数据访问接口
 */
@Mapper
public interface GameCellMapper {
    
    /**
     * 根据ID查询游戏格子表
     * @param id ID
     * @return 游戏格子表信息
     */
    GameCell getById(@Param("id") Long id);
    
    /**
     * 根据活动ID查询游戏格子表
     * @param activityId 活动ID
     * @return 游戏格子表信息
     */
    GameCell getByActivityId(@Param("activityId") Long activityId);
    
    /**
     * 根据格子索引查询游戏格子表
     * @param cellIndex 格子索引
     * @return 游戏格子表信息
     */
    GameCell getByCellIndex(@Param("cellIndex") Integer cellIndex);
    
    /**
     * 查询所有游戏格子表
     * @return 游戏格子表列表
     */
    List<GameCell> findAll();
    
    /**
     * 查询游戏格子表列表
     * @param gameCell 查询条件
     * @return 游戏格子表列表
     */
    List<GameCell> findList(GameCell gameCell);
    
    /**
     * 分页查询游戏格子表列表
     * @param gameCell 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 游戏格子表列表
     */
    List<GameCell> findListWithPagination(@Param("gameCell") GameCell gameCell, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入游戏格子表
     * @param gameCell 游戏格子表信息
     * @return 影响行数
     */
    int insert(GameCell gameCell);
    
    /**
     * 更新游戏格子表
     * @param gameCell 游戏格子表信息
     * @return 影响行数
     */
    int update(GameCell gameCell);
    
    /**
     * 删除游戏格子表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除游戏格子表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
