/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月11日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.enums;

/** 
* @ClassName: FundTypeEnum 
* @Description: 款项类型
* @author zengbin
* @date 2015年11月11日 下午9:02:45 
*/
public enum FundTypeEnum {
    DEPOSIT        (0, "定金", OperatorRoleEnum.ROLE_USER.getCode(), "消费者支付定金"), 
    FINAL          (1, "尾款", OperatorRoleEnum.ROLE_USER.getCode(), "消费者支付尾款"), 
    FULL           (2, "全款", OperatorRoleEnum.ROLE_USER.getCode(), "消费者支付全款"),
    REFUND_DEPOSIT (3, "退定金", OperatorRoleEnum.ROLE_USER.getCode(), "消费者退定金"),
    REFUND_FINAL   (4, "退尾款", OperatorRoleEnum.ROLE_USER.getCode(), "消费者退尾款"),

    GUARANTEE      (5, "充值保证金", OperatorRoleEnum.ROLE_PURCHASER.getCode(), "买手充值保证金"),
    CASH           (6, "账户提现", OperatorRoleEnum.ROLE_PURCHASER.getCode(), "买手申请账户提现"),
    ACC_DEPOSIT    (7, "定金转账", OperatorRoleEnum.ROLE_PURCHASER.getCode(), "买手接单定金转账"),
    ACC_FINAL      (8, "尾款转账", OperatorRoleEnum.ROLE_PURCHASER.getCode(), "买手发货尾款转账"),
    COMPENSATION   (9, "赔偿金", OperatorRoleEnum.ROLE_PURCHASER.getCode(), "买手违约赔偿金"),
    ;
    
    private FundTypeEnum(Integer code, String name, Integer operatorRole, String desc) {
        this.code = code;
        this.name = name;
        this.operatorRole = operatorRole;
        this.desc = desc;
    }
    
    private Integer code;
    
    private String name;
    
    private Integer operatorRole;
    
    private String desc;
    
    public Integer getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public Integer getOperatorRole() {
		return operatorRole;
	}

	public String getDesc() {
		return desc;
	}

	public static FundTypeEnum getEnum(Integer code) {
        for (FundTypeEnum fundTypeEnum : FundTypeEnum.values()) {
            if (fundTypeEnum.getCode().equals(code)) {
                return fundTypeEnum;
            }
        }
        return null;
    }
    
    public static FundTypeEnum getEnumByName(String name) {
    	for (FundTypeEnum fundTypeEnum : FundTypeEnum.values()) {
    		if (fundTypeEnum.getName().equals(name)) {
    			return fundTypeEnum;
    		}
    	}
    	return null;
    }
}
