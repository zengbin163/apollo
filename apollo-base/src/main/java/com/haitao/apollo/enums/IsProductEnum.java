package com.haitao.apollo.enums;

/**
* @ClassName: IsProductEnum 
* @Description: 是否产品环境
* @author zengbin
* @date 2015年11月16日
 */
public enum IsProductEnum {
    
    DEVELOPMENT(0, "开发环境"), 
    PRODUCTION(1, "产品环境");
    
    private IsProductEnum(Integer code, String name) {
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
    
    public static IsProductEnum getEnum(Integer code) {
        for (IsProductEnum isProductEnum : IsProductEnum.values()) {
            if (isProductEnum.getCode().equals(code)) {
                return isProductEnum;
            }
        }
        return null;
    }
}
