/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月12日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order;

import java.math.BigDecimal;
import java.util.List;

import com.haitao.apollo.pojo.order.PayOrder;

/** 
* @ClassName: PayOrderService 
* @Description: 支付订单相关的service
* @author zengbin
* @date 2015年11月17日 下午15:44:16 
*/
public interface PayOrderService {
	
	Integer createPayOrder(Integer postrewardId, Integer userId,
			Integer payType, Integer fundType, BigDecimal payAmount,
			BigDecimal bigMoney, Integer payByBig, String paySerialNo);
	void payCallBack(Integer orderNo, String paySerialNo, Integer payType, Integer fundType, BigDecimal payAmount, BigDecimal bigMoney, String subject);
	PayOrder getPayOrderBySerialNo(String paySerialNo);
	List<PayOrder> getPayOrderByPostrewardId(Integer postrewardId);
	
	/**
	 * 废弃支付订单
	 * @param id
	 * @return
	 */
	Integer discardPayOrderById(Integer id);
	
	/**
	 * 查询我的账单
	 * @param userId
	 * @param page
	 * @return
	 */
    List<PayOrder> myBillList(Integer userId, Integer month, Integer pageOffset, Integer pageSize);
}
