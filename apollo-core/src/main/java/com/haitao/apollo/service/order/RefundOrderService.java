/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月12日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order;

import java.math.BigDecimal;

/** 
* @ClassName: RefundOrderService 
* @Description: 退款订单相关的service
* @author zengbin
* @date 2015年12月10日 下午14:51:16 
*/
public interface RefundOrderService {
	
	Integer createRefundOrder(Integer userId, Integer postrewardId, Integer payOrderId, BigDecimal refundAmount, String paySerialNo);
	
	void refundCallBack(String paySerialNo);
}
