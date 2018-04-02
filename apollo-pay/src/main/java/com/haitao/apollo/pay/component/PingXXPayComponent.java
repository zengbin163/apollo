/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月18日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pay.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haitao.apollo.base.Result;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.pay.PayComponent;
import com.haitao.apollo.pay.vo.PayVo;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;

/**
 * @ClassName: PingXXPayComponent
 * @Description: pingxx支付组件
 * @author zengbin
 * @date 2015年12月07日 下午14:33:43
 */
@Component
public class PingXXPayComponent extends PayComponent {
	private static final Logger logger = LoggerFactory.getLogger(PingXXPayComponent.class);

	public Result pay(PayVo payVo) {
		Assert.notNull(payVo, "支付输入参数VO为空");
		Pingpp.apiKey = API_KEY_VALUE;
		Charge charge = null;
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		chargeMap.put(AMOUNT, payVo.getAmount());
		chargeMap.put(CURRENCY, payVo.getCurrency());
		chargeMap.put(SUBJECT, payVo.getSubject());
		chargeMap.put(BODY, payVo.getBody());
		String alipayOrderNo = FundTypeEnum.getEnum(payVo.getFundType()).toString() + payVo.getOrderNo();
		chargeMap.put(ORDER_NO, alipayOrderNo);
		chargeMap.put(CHANNEL, payVo.getChannel());
		chargeMap.put(CLIENT_IP, payVo.getClientIp());
		Map<String, String> appMap = new HashMap<String, String>();
		appMap.put(APP_ID, payVo.getAppId());
		chargeMap.put(APP_MAP, appMap);
	    Map<String, String> metadataMap = new HashMap<String, String>();
	    metadataMap.put(FUND_TYPE, payVo.getFundType().toString());
	    metadataMap.put(BIG_MONEY, payVo.getBigMoney().toString());
	    chargeMap.put(METADATA_MAP, metadataMap);
		try {
			charge = Charge.create(chargeMap); //发起交易请求
		} catch (PingppException e) {
			logger.error(String.format("Create PingXX Charge Error, [ApiKey=%s , AppId=%s]", payVo.getApiKey(), payVo.getAppId()), e);
			Result result = Result.ERROR(ResultCode.THIRD_PAY_FAIL.getCode(), e.getMessage());
			return result;
		}
		Result result = Result.CREATE(ResultCode.SUCCESS.getCode(), "第三方预支付创建成功", charge);
		return result;
	}

	public Result query(String prepayId){
		String chargeId = prepayId;
		Assert.notNull(chargeId,"chargeId不能为空");
		Pingpp.apiKey = API_KEY_VALUE;
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            List<String> expande = new ArrayList<String>();
            expande.add("app");
            param.put("expand", expande);
            Charge charge = Charge.retrieve(chargeId, param);
            if (charge.getApp() instanceof App) {
            	return Result.CREATE(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getString(), charge);
            } else {
            	return Result.ERROR(ResultCode.THIRD_PAY_FAIL.getCode(), String.format("Pingxx查询charge不存在，[chargeId=%s]", chargeId));
            }
        } catch (PingppException e) {
			logger.error(String.format("Query PingXX Charge Error, [chargeId=%s]", chargeId), e);
			return Result.ERROR(ResultCode.THIRD_PAY_FAIL.getCode(), e.getMessage());
        }

	}
}
