package com.haitao.apollo.enums;

/**
 * 消息盒子模板
 * @author zengbin
 *
 */
public enum MessageBoxTypeEnum {
    MSG_ORDER(0, "订单通知"), 
    MSG_PAY(1, "结算通知"),
    MSG_SOCIAL(2, "社交通知"),
    MSG_IM(3, "IM聊天记录"),
    ;
    
    private MessageBoxTypeEnum(Integer code, String name) {
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
    
    public static MessageBoxTypeEnum getEnum(Integer code) {
        for (MessageBoxTypeEnum messageBoxEnum : MessageBoxTypeEnum.values()) {
            if (messageBoxEnum.getCode().equals(code)) {
                return messageBoxEnum;
            }
        }
        return null;
    }
}
