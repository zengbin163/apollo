package com.haitao.apollo.enums;

/**
* @ClassName: FrozenCashCauseEnum 
* @Description: 保证金冻结原因
* @author zengbin
* @date 2016年03月23日
 */
public enum FrozenCashCauseEnum {
    
    SHIPMENT_DELAY(0, "延期发货"), 
    CANCEL_ORDER(1, "买手主动取消订单");
    
    private FrozenCashCauseEnum(Integer code, String name) {
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
    
    public static FrozenCashCauseEnum getEnum(Integer code) {
        for (FrozenCashCauseEnum frozenCashCauseEnum : FrozenCashCauseEnum.values()) {
            if (frozenCashCauseEnum.getCode().equals(code)) {
                return frozenCashCauseEnum;
            }
        }
        return null;
    }
}
