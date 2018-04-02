/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月18日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.pay;

import java.math.BigDecimal;

import com.pingplusplus.model.Charge;

/** 
* @ClassName: ThirdPayService 
* @Description: 第三方支付服务层
* @author zengbin
* @date 2015年11月18日 下午4:44:40 
*/
public interface ThirdPayService {
	
	
    
	/**
	 * 预支付创建charge单
	 * @param amount   支付金额   单位为分
	 * @param bigMoney 大牌币       单位为分
	 * @param currency
	 * @param subject
	 * @param body
	 * @param postrewardId 悬赏id，就是传入第三方支付的商户订单id
	 * @param channel
	 * @param clientIp
	 * @param fundType
	 * @param pxxAppId
	 * @return
	 */
	Charge pay(BigDecimal amount, BigDecimal bigMoney, String currency,
			String subject, String body, Integer postrewardId, String channel,
			String clientIp, Integer fundType, String pxxAppId);

	/**
     * 根据预支付id查询预支付charge对象
     * @param chargeId
     * @return
     */
    Charge query(String chargeId);
}
