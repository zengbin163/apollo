/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl.pay;

import java.math.BigDecimal;

/**
 * @ClassName: AbstractPayCallBackStrategy
 * @Description: 支付回写校验策略
 * @author zengbin
 * @date 2015年12月08日 下午8:43:21
 */
public abstract class AbstractPayCallBackStrategy {

	/***
	 * 支付回写，用户未登录
	 * 
	 * @return
	 */
	public Integer getUserId() {
		return -10000;
	}

	public abstract void execute(Integer orderNo, String paySerialNo, Integer payType, Integer fundType, BigDecimal payAmount, BigDecimal bigMoney, String subject);
}
