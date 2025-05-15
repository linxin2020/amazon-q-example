package com.anycompany.demo.jumping.common.exception;

import com.anycompany.demo.jumping.common.result.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<?> handleBusinessException(BusinessException e) {
        log.warn("业务异常: {}", e.getMessage());
        return ResponseResult.error(e.getCode(), e.getMessage());
    }
    
    /**
     * 处理方法参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "请求参数错误";
        log.warn("参数校验异常: {}", message);
        return ResponseResult.error(ErrorCode.PARAMETER_ERROR.getCode(), message);
    }
    
    /**
     * 处理绑定异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult<?> handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "请求参数错误";
        log.warn("参数绑定异常: {}", message);
        return ResponseResult.error(ErrorCode.PARAMETER_ERROR.getCode(), message);
    }
    
    /**
     * 处理所有未处理的异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<?> handleException(Exception e) {
        log.error("系统异常", e);
        return ResponseResult.error(500, "系统繁忙，请稍后再试");
    }
} 