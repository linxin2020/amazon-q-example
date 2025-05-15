package com.anycompany.demo.jumping.constant;

/**
 * 错误码常量
 */
public class ErrorConstants {

    /**
     * 通用错误码
     */
    public static final int ACTIVITY_NOT_FOUND = 1003;
    
    /**
     * 活动状态错误码
     */
    public static final int ACTIVITY_NOT_STARTED = 2001;
    public static final int ACTIVITY_ENDED = 2002;
    
    /**
     * 错误信息
     */
    public static final String ACTIVITY_NOT_FOUND_MSG = "活动不存在";
    public static final String ACTIVITY_NOT_STARTED_MSG = "活动未开始";
    public static final String ACTIVITY_ENDED_MSG = "活动已结束";
}
