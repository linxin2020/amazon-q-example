package com.anycompany.demo.jumping.enumeration;

/**
 * 游戏状态枚举
 */
public enum GameStatusEnum {
    NOT_STARTED(0, "未开始"),
    IN_PROGRESS(1, "进行中"),
    COMPLETED(2, "已完成");
    
    private final int code;
    private final String desc;
    
    GameStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
    
    /**
     * 根据code获取枚举实例
     * 
     * @param code 状态码
     * @return 对应的枚举实例，如果不存在则返回null
     */
    public static GameStatusEnum getByCode(int code) {
        for (GameStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
