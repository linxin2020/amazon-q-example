package com.anycompany.demo.jumping.common.exception;

/**
 * 活动相关错误码枚举类
 */
public enum ActivityErrorCode {
    // 活动相关错误
    ACTIVITY_NOT_FOUND(1003, "活动不存在"),
    ACTIVITY_NOT_STARTED(2001, "活动未开始"),
    ACTIVITY_ENDED(2002, "活动已结束"),
    
    // 游戏相关错误
    INSUFFICIENT_GAME_CHANCES(3001, "游戏机会不足"),
    DAILY_GAME_LIMIT_EXCEEDED(3002, "超出每日游戏次数限制"),
    
    // 宝箱相关错误
    INSUFFICIENT_POINTS(4001, "积分不足"),
    BOX_ALREADY_EXCHANGED(4002, "宝箱已兑换");
    
    private final int code;
    private final String message;
    
    ActivityErrorCode(int code, String message) {
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
