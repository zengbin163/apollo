/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月18日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.pay.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.base.Result;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.pay.component.PingXXPayComponent;
import com.haitao.apollo.pay.vo.PayVo;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.service.pay.ThirdPayService;
import com.pingplusplus.model.Charge;

/** 
* @ClassName: ThirdPayServiceImpl 
* @Description: 第三方支付服务层
* @author zengbin
* @date 2015年11月18日 下午4:45:00 
*/
@Service
public class ThirdPayServiceImpl implements ThirdPayService {
    
    @Autowired
    private PingXXPayComponent pingXXPayComponent;

	public Charge pay(BigDecimal amount, BigDecimal bigMoney, String currency,
			String subject, String body, Integer postrewardId, String channel,
			String clientIp, Integer fundType, String pxxAppId) {
		Assert.notNull(amount,"支付金额不能为空");
		Assert.notNull(bigMoney,"大牌币可以为0但是不能为空");
		Assert.notNull(currency,"币种类型不能为空");
		Assert.notNull(postrewardId,"悬赏id不能为空");
		Assert.notNull(channel,"支付渠道不能为空");
		Assert.notNull(fundType,"款型类型不能为空");
		Assert.notNull(pxxAppId,"pingxx app_id不能为空");
		PayVo payVo = new PayVo();
		payVo.setAmount(amount);
		payVo.setBigMoney(bigMoney);
		payVo.setCurrency(currency);
		payVo.setSubject(subject);
		payVo.setBody(body);
		payVo.setOrderNo(postrewardId.toString());
		payVo.setChannel(channel);
		payVo.setClientIp(clientIp);
		payVo.setFundType(fundType);
		payVo.setAppId(pxxAppId);
		Result result = this.pingXXPayComponent.pay(payVo);
		if(ResultCode.SUCCESS.getCode().equals(result.getResultCode())){
			Charge charge = (Charge) result.getResult();
			return charge;
		}else{
			throw new ApolloBizException(ResultCode.getEnum(result.getResultCode()), SessionContainer.getSession().getOperatorId(), result.getExceptionMessage());
		}
	}

	public Charge query(String chargeId){
		Assert.notNull(chargeId,"chargeId不能为空");
		Result result = this.pingXXPayComponent.query(chargeId);
		if(ResultCode.SUCCESS.getCode().equals(result.getResultCode())){
			return (Charge) result.getResult();
		}else{
			throw new ApolloBizException(ResultCode.getEnum(result.getResultCode()), SessionContainer.getSession().getOperatorId(), result.getExceptionMessage());
		}
	}

}

