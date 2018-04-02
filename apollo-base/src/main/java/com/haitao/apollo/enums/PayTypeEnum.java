/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月11日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.enums;

/** 
* @ClassName: PayTypeEnum 
* @Description: 支付类型
* @author zengbin
* @date 2015年11月11日 下午9:02:45 
*/
public enum PayTypeEnum {
    
    ALIPAY(0, "alipay"), 
    WEIXIN(1, "wx"),
    OFFLINE(2, "offline"),
    BIGMONEY(3, "bigMoney"),
    ;
    
    private PayTypeEnum(Integer code, String name) {
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
    
    public static PayTypeEnum getEnum(Integer code) {
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            if (payTypeEnum.getCode().equals(code)) {
                return payTypeEnum;
            }
        }
        return null;
    }

    public static PayTypeEnum getEnumByName(String name) {
    	for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
    		if (payTypeEnum.getName().equals(name)) {
    			return payTypeEnum;
    		}
    	}
    	return OFFLINE;
    }
}
