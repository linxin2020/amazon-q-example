package com.anycompany.demo.jumping.common.util;

import com.anycompany.demo.jumping.common.exception.BusinessException;
import com.anycompany.demo.jumping.common.exception.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户上下文工具类
 * 用于从请求中获取用户信息
 */
public class UserContext {

    /**
     * 从请求头中获取用户ID
     *
     * @param request HTTP请求
     * @return 用户ID
     * @throws BusinessException 如果用户ID不存在或格式无效
     */
    public static Long getUserId(HttpServletRequest request) {
        // 从请求头中获取用户ID
        String userIdStr = request.getHeader("USER_ID");
        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS.getCode(), "用户未登录或身份验证失败");
        }
        
        // 将用户ID转换为Long类型
        try {
            return Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new BusinessException(ErrorCode.INVALID_USERNAME.getCode(), "无效的用户ID格式");
        }
    }
    
    /**
     * 检查用户ID是否存在
     *
     * @param request HTTP请求
     * @return 如果用户ID存在且有效则返回true，否则返回false
     */
    public static boolean hasUserId(HttpServletRequest request) {
        String userIdStr = request.getHeader("USER_ID");
        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            return false;
        }
        
        try {
            Long.parseLong(userIdStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}