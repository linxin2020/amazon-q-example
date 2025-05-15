package com.anycompany.demo.jumping.mapper;

import com.anycompany.demo.jumping.model.UserFeedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户反馈表数据访问接口
 */
@Mapper
public interface UserFeedbackMapper {
    
    /**
     * 根据ID查询用户反馈表
     * @param id ID
     * @return 用户反馈表信息
     */
    UserFeedback getById(@Param("id") Long id);
    
    /**
     * 根据类别ID查询用户反馈表
     * @param categoryId 类别ID
     * @return 用户反馈表信息
     */
    UserFeedback getByCategoryId(@Param("categoryId") Long categoryId);
    
    /**
     * 根据联系邮箱查询用户反馈表
     * @param email 联系邮箱
     * @return 用户反馈表信息
     */
    UserFeedback getByEmail(@Param("email") String email);
    
    /**
     * 根据状态(0:未处理 1:已处理)查询用户反馈表
     * @param status 状态(0:未处理 1:已处理)
     * @return 用户反馈表信息
     */
    UserFeedback getByStatus(@Param("status") Integer status);
    
    /**
     * 根据用户ID(可选)查询用户反馈表
     * @param userId 用户ID(可选)
     * @return 用户反馈表信息
     */
    UserFeedback getByUserId(@Param("userId") Long userId);
    
    /**
     * 查询所有用户反馈表
     * @return 用户反馈表列表
     */
    List<UserFeedback> findAll();
    
    /**
     * 查询用户反馈表列表
     * @param userFeedback 查询条件
     * @return 用户反馈表列表
     */
    List<UserFeedback> findList(UserFeedback userFeedback);
    
    /**
     * 分页查询用户反馈表列表
     * @param userFeedback 查询条件
     * @param offset 偏移量
     * @param limit 限制条数
     * @return 用户反馈表列表
     */
    List<UserFeedback> findListWithPagination(@Param("userFeedback") UserFeedback userFeedback, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 插入用户反馈表
     * @param userFeedback 用户反馈表信息
     * @return 影响行数
     */
    int insert(UserFeedback userFeedback);
    
    /**
     * 更新用户反馈表
     * @param userFeedback 用户反馈表信息
     * @return 影响行数
     */
    int update(UserFeedback userFeedback);
    
    /**
     * 更新用户反馈表状态
     * @param id ID
     * @param status 状态值
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 删除用户反馈表
     * @param id ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除用户反馈表
     * @param ids ID数组
     * @return 影响行数
     */
    int batchDelete(@Param("ids") Long[] ids);
}
