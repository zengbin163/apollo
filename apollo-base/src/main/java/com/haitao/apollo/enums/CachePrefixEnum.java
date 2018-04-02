package com.haitao.apollo.enums;

/**
* @ClassName: CachePrefixEnum 
* @Description: 缓存里面各种前缀
* @author zengbin
* @date 2015年11月9日
 */
public enum CachePrefixEnum {
    
    //PREFIX_REG_SMS_(100, "用户注册短信验证码前缀"), 
	//PREFIX_PASS_SMS_(101, "找回密码短信验证码前缀"), 
    PREFIX_PURCH_INVITE_(102, "买手邀请码前缀"), 
    PREFIX_USER_INVITE_(103, "会员邀请码前缀"), 
    PREFIX_SESSION_(104,"登录session前缀"),
    PREFIX_USER_INFO_(105,"用户信息前缀"),
    PREFIX_BACK_OPERATOR_SESSION_(106,"登录后台系统session前缀"),

    PREFIX_PURCHASER_INFO_(200,"买手信息前缀"),
    PREFIX_PURCHASER_VIP_ALL(201,"买手晋升标准"),
    //PREFIX_PUR_REG_SMS_(202, "买手注册短信验证码前缀"), 
    //PREFIX_PUR_PASS_SMS_(203, "买手找回密码短信验证码前缀"), 

    PREFIX_ALL_CATEGORY_300(300,"所有类目"),
    PREFIX_ALL_BRAND_301(301,"所有品牌"),
    PREFIX_CATEGORY_(302,"单个类目"),
    PREFIX_BRAND_(303,"单个品牌"),
    PREFIX_BRAND_CATEGORY_(304,"某个品牌下面的所有类目"),
    PREFIX_PURCHASER_CATEGORY_(305,"某个买手下面的所有类目"),
    ;
    
    private CachePrefixEnum(Integer code, String name) {
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
    
    public static CachePrefixEnum getEnum(Integer code) {
        for (CachePrefixEnum prefixEnum : CachePrefixEnum.values()) {
            if (prefixEnum.getCode().equals(code)) {
                return prefixEnum;
            }
        }
        return null;
    }
}
