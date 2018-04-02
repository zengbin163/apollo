package com.haitao.apollo.enums;

/**
 * 推送和短信模板枚举类
 * @author zengbin
 *
 */
public enum MsgTemplateEnum {
	
    CONFIRM_SEND_TIME_AGREE(0, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.ONLY_PUSH, "消费者确认发货时间同意", "消费者已经同意您的发货时间，定金已经打到您账户，请按时完成采购并发货"), 
    CONFIRM_SEND_TIME_REUSE(1, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.ONLY_PUSH, "消费者确认发货时间拒绝", "消费者拒绝了您的发货时间，订单已经取消"), 
    CONFIRM_RECEIVE(2, OperatorRoleEnum.ROLE_PURCHASER,TemplateTypeEnum.ONLY_PUSH, "消费者确认收货", "消费者已经确认收货"), 
    DELAY_SEND_PURCHASER(3, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.PUSH_SMS, "延期发货", "您有一笔订单已经延期发货，系统将按日扣除您的保证金，详情请打开app查看"), 
    DELAY_SEND_USER(4, OperatorRoleEnum.ROLE_USER, TemplateTypeEnum.PUSH_SMS, "延期发货", "您有一笔订单已经延期发货，系统已经对您进行了赔付，详情请打开app查看"), 
    OVER_SEND_PURCHASER(5, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.PUSH_SMS, "超期发货", "您有一笔订单已经超过发货期限，该笔订单已经被终结，系统将按照约定扣除您的保证金进行赔付，详情请打开app查看"), 
    OVER_SEND_USER(6, OperatorRoleEnum.ROLE_USER, TemplateTypeEnum.PUSH_SMS, "超期发货", "我们十分抱歉，您有一笔订单已经超过发货期限，该笔订单已经被终结，所有钱款已经退还给您，并且对您进行了赔付，详情请打开app查看"), 
    GUARANTEE_NOT_ENOUGH(7, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.PUSH_SMS, "买手保证金不足", "您的保证金已经不足，为了不妨碍您正常接单，请及时补齐，十分感谢"), 
    ACCOUNT_TRANSPORT(8, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.PUSH_SMS, "买手钱款到账", "您有一笔新的款项到账，请打开app查看详情"), 
    CASH_TRANSPORT(9, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.PUSH_SMS, "买手钱款提现", "您有一笔新的提现请求已经提交，请打开app查看详情"), 
    POSTREWARD_PUB(10, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.PUSH_SMS, "与买手关联的消费者发布悬赏", "您的悬赏池中有一笔新的悬赏，请打开app查看详情"), 
    POSTREWARD_ACCEPT(11, OperatorRoleEnum.ROLE_USER, TemplateTypeEnum.ONLY_PUSH, "买手接单", "您的悬赏已经被买手接单，请查看"), 
    POSTREWARD_SHIPMENT(12, OperatorRoleEnum.ROLE_USER, TemplateTypeEnum.ONLY_PUSH, "买手发货", "您的货品已经发货，请耐心等待"), 
    CANCEL_ORDER(13, OperatorRoleEnum.ROLE_USER, TemplateTypeEnum.PUSH_SMS, "买手在发货周期内弃单", "我们十分抱歉，您有一笔订单买手执行了弃单操作，该笔订单已经终结，所有钱款已经退还给您，并且对您进行了赔付，详情请打开app查看"), 
    REFUND_ORDER(14, OperatorRoleEnum.ROLE_USER, TemplateTypeEnum.PUSH_SMS, "退款", "您有一笔新的退款，请打开app查看详情"), 
    FROZEN_ORDER_USER(15, OperatorRoleEnum.ROLE_USER, TemplateTypeEnum.PUSH_SMS, "冻结订单", "您的订单已经被冻结，请打开app查看详情"), 
    FROZEN_ORDER_PURCHASER(16, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.PUSH_SMS, "冻结订单", "您的订单已经被冻结，请打开app查看详情"), 
    
    SMS_REGISTER_USER(17, OperatorRoleEnum.ROLE_USER, TemplateTypeEnum.ONLY_SMS, "消费者注册短信", "您注册的短信验证码为：%s"), 
    SMS_REGISTER_PURCHASER(18, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.ONLY_SMS, "买手注册短信", "您注册的短信验证码为：%s"), 
    SMS_FINDPASS_USER(19, OperatorRoleEnum.ROLE_USER, TemplateTypeEnum.ONLY_SMS, "消费者找回密码短信", "您找回密码的短信验证码为：%s"), 
    SMS_FINDPASS_PURCHASER(20, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.ONLY_SMS, "买手找回密码短信", "您找回密码的短信验证码为：%s"), 

    POSTREWARD_PRAISE_USER(21, OperatorRoleEnum.ROLE_USER, TemplateTypeEnum.ONLY_PUSH, "消费者悬赏被点赞", "您的悬赏被人点赞了，请打开app查看详情"), 
    SHOWORDER_PRAISE_USER(22, OperatorRoleEnum.ROLE_USER, TemplateTypeEnum.ONLY_PUSH, "消费者晒单被点赞", "您的晒单被人点赞了，请打开app查看详情"), 
    SHOWORDER_PRAISE_PURCHASER(23, OperatorRoleEnum.ROLE_PURCHASER, TemplateTypeEnum.ONLY_PUSH, "买手晒单被点赞", "您的晒单被人点赞了，请打开app查看详情"), 
    
    IM_MESSAGE(1000, null, null, "IM消息", "IM消息"),
    ;
    
    private MsgTemplateEnum(Integer code, OperatorRoleEnum operatorEnum, TemplateTypeEnum templateTypeEnum, String name, String desc) {
        this.code = code;
        this.operatorEnum = operatorEnum;
        this.templateTypeEnum = templateTypeEnum;
        this.name = name;
        this.desc = desc;
    }
    
    private Integer code;

    private OperatorRoleEnum operatorEnum;

    private TemplateTypeEnum templateTypeEnum;
    
    private String name;

    private String desc;
    
    public Integer getCode() {
        return code;
    }
    
	public OperatorRoleEnum getOperatorEnum() {
		return operatorEnum;
	}

	public TemplateTypeEnum getTemplateTypeEnum() {
		return templateTypeEnum;
	}

	public String getName() {
        return name;
    }
    
    public String getDesc() {
		return desc;
	}

	public static MsgTemplateEnum getEnum(Integer code) {
        for (MsgTemplateEnum pushTemplateEnum : MsgTemplateEnum.values()) {
            if (pushTemplateEnum.getCode().equals(code)) {
                return pushTemplateEnum;
            }
        }
        return null;
    }
	
    enum TemplateTypeEnum {
    	ONLY_PUSH(0, "仅发推送"),
    	ONLY_SMS(1, "仅发短信"),
    	PUSH_SMS(2, "短信+推送"),
    	;
    	
        private TemplateTypeEnum(Integer code, String name) {
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
    }
}