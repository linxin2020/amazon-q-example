package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.GameRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 游戏记录表数据访问接口
 */
@Mapper
public interface GameRecordMapper {
    
    /**
     * 根据ID查询游戏记录表
     * @param id ID
     * @return 游戏记录表信息
     */
    GameRecord getById(@Param("id") Long id);
    
    /**
     * 查询所有游戏记录表
     * @return 游戏记录表列表
     */
    List<GameRecord> findAll();
    
    /**
     * 查询游戏记录表列表
     * @param gameRecord 查询条件
     * @return 游戏记录表列表
     */
    List<GameRecord> findList(GameRecord gameRecord);
    
    /**
     * 分页查询游戏记录表列表
     * @param gameRecord 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 游戏记录表列表
     */
    List<GameRecord> findListWithPagination(@Param("gameRecord") GameRecord gameRecord, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入游戏记录表
     * @param gameRecord 游戏记录表信息
     * @return 影响行数
     */
    int insert(GameRecord gameRecord);
    
    /**
     * 更新游戏记录表
     * @param gameRecord 游戏记录表信息
     * @return 影响行数
     */
    int update(GameRecord gameRecord);
    
    /**
     * 删除游戏记录表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除游戏记录表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
