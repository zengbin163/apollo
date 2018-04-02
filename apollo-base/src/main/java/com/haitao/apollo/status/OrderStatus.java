/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月12日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.status;

import java.io.Serializable;

/** 
* @ClassName: OrderStatus 
* @Description: 订单状态
* @author zengbin
* @date 2015年11月12日 下午9:19:31 
*/
public class OrderStatus implements Serializable{
    private static final long serialVersionUID = 8669864894310446822L;
    public static final Integer NOT_CREATE_ORDER=       200;// 订单未创建
    public static final Integer CREATE_ORDER=           201;// 买手已接单，创建订单
    public static final Integer AGREE_TIME=             202;// 消费者确认发货时间并支付尾款
    public static final Integer IN_SHIPMENTS=           203;// 买手已发货 
    public static final Integer FINISHED_ORDER=         204;// 订单已完成 
    public static final Integer CLOSED_ORDER=           205;// 订单已关闭
}

