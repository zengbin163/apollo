package com.haitao.apollo.enums;

public enum ShowSourceEnum {
    PURCHASER(0, "买手主动发布晒单"), 
    USER_APPRAISE(1, "消费者好评默认晒单");
    
    private ShowSourceEnum(Integer code, String name) {
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
    
    public static ShowSourceEnum getEnum(Integer code) {
        for (ShowSourceEnum showSourceEnum : ShowSourceEnum.values()) {
            if (showSourceEnum.getCode().equals(code)) {
                return showSourceEnum;
            }
        }
        return null;
    }
}
