package com.anycompany.demo.jumping.enumeration;

/**
 * 奖励类型枚举
 */
public enum RewardTypeEnum {
    POINTS("POINTS", "积分"),
    GIFT("GIFT", "礼物");
    
    private final String code;
    private final String desc;
    
    RewardTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
    
    /**
     * 根据code获取枚举实例
     * 
     * @param code 类型码
     * @return 对应的枚举实例，如果不存在则返回null
     */
    public static RewardTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        
        for (RewardTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
