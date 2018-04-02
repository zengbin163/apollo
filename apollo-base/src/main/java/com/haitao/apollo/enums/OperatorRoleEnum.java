package com.haitao.apollo.enums;

/**
* @ClassName: OperatorRoleEnum 
* @Description: 操作人角色
* @author zengbin
* @date 2016年01月25日
 */
public enum OperatorRoleEnum {
    
    ROLE_PURCHASER(0, "买手"), 
    ROLE_USER(1, "消费者"),
    ROLE_CUSTOMER(2, "客服"),
    ROLE_SYSTEM(3, "系统"),
    ;
    
    private OperatorRoleEnum(Integer code, String name) {
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
    
    public static OperatorRoleEnum getEnum(Integer code) {
        for (OperatorRoleEnum operatorRoleEnum : OperatorRoleEnum.values()) {
            if (operatorRoleEnum.getCode().equals(code)) {
                return operatorRoleEnum;
            }
        }
        return null;
    }
    
}
