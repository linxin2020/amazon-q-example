package com.anycompany.demo.jumping.enumeration;

/**
 * 格子类型枚举
 */
public enum CellTypeEnum {
    NORMAL("NORMAL", "普通格子"),
    REWARD("REWARD", "奖励格子"),
    PENALTY("PENALTY", "惩罚格子"),
    JUMP("JUMP", "跳跃格子"),
    START("START", "起点"),
    END("END", "终点");
    
    private final String code;
    private final String desc;
    
    CellTypeEnum(String code, String desc) {
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
    public static CellTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        
        for (CellTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
