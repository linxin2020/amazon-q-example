package com.anycompany.demo.jumping.common.exception;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {
    
    private final int code;
    
    /**
     * 创建业务异常
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    /**
     * 创建业务异常
     * @param errorCode 错误码枚举
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
    
    /**
     * 获取错误码
     * @return 错误码
     */
    public int getCode() {
        return code;
    }
} 