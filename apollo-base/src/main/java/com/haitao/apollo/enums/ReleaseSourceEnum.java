package com.haitao.apollo.enums;

public enum ReleaseSourceEnum {
	
    PURCHASER_RELEASE(0, "买手在接单处释放到公共池"), 
    USER_REFUSE(1, "消费者在拒绝买手发货时间释放到公共池"),
    USER_AGREE_TIMEOUT(2, "消费者24小时未同意发货时间释放到公共池"),
    ;
    
    private ReleaseSourceEnum(Integer code, String name) {
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
    
    public static ReleaseSourceEnum getEnum(Integer code) {
        for (ReleaseSourceEnum showSourceEnum : ReleaseSourceEnum.values()) {
            if (showSourceEnum.getCode().equals(code)) {
                return showSourceEnum;
            }
        }
        return null;
    }
}
