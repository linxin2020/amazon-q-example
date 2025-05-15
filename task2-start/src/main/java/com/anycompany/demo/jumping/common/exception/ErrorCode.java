package com.anycompany.demo.jumping.common.exception;

/**
 * 错误码枚举类
 * 根据API.md中定义的错误码规范
 */
public enum ErrorCode {
    // 成功
    SUCCESS(0, "成功"),

    PARAMETER_ERROR(501, "参数错误"),
    UNAUTHORIZED_ACCESS(502, "未授权访问"),

    USER_NOT_FOUND(800, "用户不存在"),
    INVALID_USERNAME(801, "无效的用户名"),

    FEEDBACK_NOT_FOUND(600, "反馈不存在"),
    FEEDBACK_ALREADY_PROCESSED(601, "反馈已处理"),

    CATEGORY_NOT_FOUND(700, "类别不存在"),
    CATEGORY_NAME_DUPLICATE(701, "类别名称已存在"),
    CATEGORY_IN_USE(702, "类别正在使用中，无法删除");
    
    private final int code;
    private final String message;
    
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
}
