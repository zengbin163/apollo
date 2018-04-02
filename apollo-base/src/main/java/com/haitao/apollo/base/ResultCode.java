/*
 * @Project: GZJK
 * @Author: bin
 * @Date: 2015年4月21日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.base;

/** 
* @ClassName: ResultCode 
* @Description: 返回码
* @author bin
* @date 2015年4月21日 下午2:31:54 
*/
public enum ResultCode {
    
    SUCCESS(200, "响应正常"), 

    NO_LOGIN(401,"用户未登录"),
    LOGIN_ERROR(402,"手机号码或者密码错误"),
    REG_CODE_ERROR(403,"手机验证码错误"),
    NOT_EXISTS(404, "数据不存在"), 
    INVITE_CODE_ERROR(405, "邀请码不存在"), 
    INVITE_CODE_IS_USED(406, "邀请码已经被使用了"), 
    MOBILE_IS_USED(407, "手机号码已经被注册"), 
    NOT_USER_REQUEST(408, "非消费者端请求"),
    FORBID_LOGIN(409, "消费者被禁止登录"),
    FORBID_POST(410, "消费者被禁止悬赏"),
    FORBID_SHOW(411, "消费者被禁止晒单"),
    NO_REGISTER(412, "号码未注册"), 
    
    UPDATE_FAIL(500, "更新失败"), 
    SAVE_FAIL(501, "新增失败"), 
    DATA_IS_ALREADY_EXISTS(502,"数据已经存在了"),
    DATA_NOT_EXISTS(503,"数据不存在"),
    ILLEGAL_DATA(504,"非法数据"),
    ILLEGAL_ARGUMENT(510,"参数错误或者缺少必要参数"), 
    SYSTEM_ERROR(555,"系统异常"), 
    
    ORDER_REWARD_SALE_PRICE_ERROR(601,"悬赏价格小于销售价格"),
    ORDER_SALE_PRICE_COMPUTE_ERROR(601,"销售价格计算错误（销售价=悬赏价-红包-优惠券-优惠码）"),
    
    POSTREWARD_NOT_EXIST(701,"悬赏不存在"),
    SALEORDER_NOT_EXIST(702,"销售订单不存在"),
    SALEORDER_STATUS_ERROR(703,"销售订单状态不正确"),
    SHOWORDER_NOT_EXIST(704,"晒单不存在"),
    POSTREWARD_STATUS_ERROR(705,"悬赏单状态不正确"),
    PAYORDER_NOT_EXISTS(706,"交易订单不存在"),
    SALEORDER_BE_APPRAISE(707,"销售订单已经被评论"),
    SALEORDER_BE_SUSPEND(708,"订单已挂起，请联系客服"),
    RIGHTS_ONLY_ONE(709,"销售订单只能发起一次售后申请"),

    THIRD_PAY_FAIL(801, "第三方预支付异常"),   
    THIRD_REFUND_FAIL(802, "第三方退款异常"),   
    
    PURCHASER_NOT_EXISTS(901, "买手不存在"),
    NOT_PURCHASER_REQUEST(902, "非买手端请求"),
    PURCHASER_ILLEGAL(903, "非法买手"),
    PURCHASER_EXCEPTION(904, "买手端异常"),
    PURCHASER_VIP_EXCEPTION(905, "买手端等级异常或者买手等级配置表配置错误"),
    PURCHASER_GUARANTEE_NOT_ENOUGH(906, "买手保证金不足"),
    PURCHASER_QUOTA_NOT_ENOUGH(907, "买手额度不足"),
    PURCHASER_GUARANTEE_RECHARGE(908, "赏购一期买手保证金只能充值一次"),
    
    SIGNATURE_VERIFY_ERROR(1000, "签名验证失败"),
    PLATFORM_ERROR(1001, "非法请求，登录状态只接受来自买手端或者消费者端的请求"),
    DEVICE_ID_ERROR(1002, "deviceId非法"),
    ;

    private Integer code;
    private String string;
    
    private ResultCode(Integer code, String string) {
        this.code = code;
        this.string = string;
    }
    
    public static ResultCode getEnum(Integer code) {
        for (ResultCode retEnum : ResultCode.values()) {
            if (retEnum.getCode().equals(code)) {
                return retEnum;
            }
        }
        return null;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getString() {
        return string;
    }
    
    public void setString(String string) {
        this.string = string;
    }
    
}
