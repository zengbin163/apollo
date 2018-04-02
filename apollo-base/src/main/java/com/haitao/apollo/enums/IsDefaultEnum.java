package com.haitao.apollo.enums;

/**
* @ClassName: IsDefaultEnum 
* @Description: 是否默认
* @author zengbin
* @date 2015年11月26日
 */
public enum IsDefaultEnum {
    
    DEFAULT_NO(0, "非默认"), 
    DEFAULT_YES(1, "默认");
    
    private IsDefaultEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    
    private Integer code;
    
    private String name;
    
    public Integer getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public static IsDefaultEnum getEnum(Integer code) {
        for (IsDefaultEnum isDefaultEnum : IsDefaultEnum.values()) {
            if (isDefaultEnum.getCode().equals(code)) {
                return isDefaultEnum;
            }
        }
        return null;
    }
}
