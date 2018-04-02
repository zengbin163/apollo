package com.haitao.apollo.enums;

/**
* @ClassName: IsPublicEnum 
* @Description: 是否公共池
* @author zengbin
* @date 2015年11月16日
 */
public enum IsPublicEnum {
    
    POOL_PURCHASER(0, "买手优先"), 
    POOL_PUBLIC(1, "公共池优先"),
    POOL_GARBAGE(2, "垃圾堆");
    
    private IsPublicEnum(Integer code, String name) {
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
    
    public static IsPublicEnum getEnum(Integer code) {
        for (IsPublicEnum isPublicEnum : IsPublicEnum.values()) {
            if (isPublicEnum.getCode().equals(code)) {
                return isPublicEnum;
            }
        }
        return null;
    }
}
