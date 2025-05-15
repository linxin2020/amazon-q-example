package com.anycompany.demo.jumping.common.result;

/**
 * 统一响应结果
 * @param <T> 数据类型
 */
public class ResponseResult<T> {
    
    private Integer code;
    private String message;
    private T data;
    
    /**
     * 私有构造函数
     */
    private ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 成功响应（带数据）
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(0, "success", data);
    }
    
    /**
     * 成功响应（无数据）
     */
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(0, "success", null);
    }
    
    /**
     * 失败响应
     */
    public static <T> ResponseResult<T> error(Integer code, String message) {
        return new ResponseResult<>(code, message, null);
    }
    
    /**
     * 获取状态码
     */
    public Integer getCode() {
        return code;
    }
    
    /**
     * 获取消息
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * 获取数据
     */
    public T getData() {
        return data;
    }
} 