package com.haitao.apollo.enums;

public enum CurrencyEnum {
    
    RMB(0, "cny");
    
    private CurrencyEnum(Integer code, String name) {
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
    
    public static CurrencyEnum getEnum(Integer code) {
        for (CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            if (currencyEnum.getCode().equals(code)) {
                return currencyEnum;
            }
        }
        return null;
    }
}
