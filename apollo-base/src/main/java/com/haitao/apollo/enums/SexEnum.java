package com.haitao.apollo.enums;

/**
* @ClassName: SexEnum 
* @Description: 性别枚举
* @author zengbin
* @date 2015年11月16日
 */
public enum SexEnum {
    
    FEMALE(0, "女性"), 
    MALE(1, "男性");
    
    private SexEnum(Integer code, String name) {
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
    
    public static SexEnum getEnum(Integer code) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (sexEnum.getCode().equals(code)) {
                return sexEnum;
            }
        }
        return null;
    }
}
