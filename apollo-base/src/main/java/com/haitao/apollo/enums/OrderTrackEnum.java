/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月14日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.enums;

/** 
* @ClassName: OrderTrack 
* @Description: 订单规矩
* @author zengbin
* @date 2015年11月14日 下午1:50:13 
*/
public enum OrderTrackEnum {
    
    /**正常流程**/
	PREPAY_REWARD("BIZ100100", "用户发起预支付，悬赏状态为预支付"), 
    CREATE_REWARD("BIZ100101", "悬赏中，用户支付40%定金"), 
    PURCHASER_ACCEPT("BIZ100102", "买手已接单，创建订单"),
    RELEASE_PUBLIC_POOL("BIZ100103", "已将悬赏释放到公共池"),
    AGREE_TIME("BIZ100104", "消费者同意买手发货时间"),
    PURCHASER_SHIPMENTS("BIZ100105", "买手已发货"),
    FINISH_ORDER("BIZ100106","用户确认收货，订单完成 , 悬赏完成"),
    ADJUST_PRICE("BIZ100107","用户修改价格"),
    CLOSE_REWARD("BIZ100108","买手接单前取消悬赏"),
    CLOSE_ORDER("BIZ100109","取消订单，订单关闭，悬赏关闭"),
    FINISH_SHOW_ORDER("BIZ100110","完成晒单"),
    FINISH_PAY("BIZ100111","完成付款"),
    FINISH_RECHARGE("BIZ100112","买手完成保证金充值"),
    
    /**异常流程**/
    PURCHASER_ACCEPT_TIMEOUT("BIZ100200","买手24小时不接单释放到公共池"),
    POOL_ACCEPT_TIMEOUT("BIZ100201","公共池48小时不接单取消悬赏"),
    CONFIRM_TIME_OUT("BIZ100202","消费者确实发货时间超时"),
    PAY_FINAL_TIME_OUT("BIZ100203","消费者支付尾款超时"),
    SHIPPMENT_TIME_OUT("BIZ100204","买手发货超时 ，扣除部分保证金做悬赏赔偿"),
    FINISH_ORDER_TIME_OUT("BIZ100205","用户确认收货超时，系统默认确认收货，订单完成"),
    APPRAISE_TIME_OUT("BIZ100206","消费者评价超时，默认好评并晒单"),
    ;
    
    private OrderTrackEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    private String code;
    
    private String desc;
    
    public String getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }

    public static OrderTrackEnum getEnum(String code) {
        for (OrderTrackEnum orderTrackEnum : OrderTrackEnum.values()) {
            if (orderTrackEnum.getCode().equals(code)) {
                return orderTrackEnum;
            }
        }
        return null;
    }
    
}
