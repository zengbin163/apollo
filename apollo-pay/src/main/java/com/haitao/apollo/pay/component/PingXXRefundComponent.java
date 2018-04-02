package com.haitao.apollo.pay.component;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haitao.apollo.base.Result;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.pay.RefundComponent;
import com.haitao.apollo.pay.vo.RefundVo;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;

/**
 * @ClassName: PingXXRefundComponent
 * @Description: pingxx退款组件
 * @author zengbin
 * @date 2015年12月07日 下午14:33:43
 */
@Component
public class PingXXRefundComponent extends RefundComponent {
	@Autowired
	private PingXXPayComponent pingXXPayComponent;
	private static final Logger logger = LoggerFactory.getLogger(PingXXPayComponent.class);

	public Result refund(RefundVo refundVo){
		Pingpp.apiKey = API_KEY_VALUE;
		Result result = pingXXPayComponent.query(refundVo.getChargeId());
        if(!ResultCode.SUCCESS.getCode().equals(result.getResultCode())){
			return Result.ERROR(ResultCode.THIRD_PAY_FAIL.getCode(), result.getExceptionMessage());
        }
		Charge charge = (Charge) result.getResult();
        Refund refund = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DESCRIPTION, refundVo.getDescription());
        try {
            refund = charge.getRefunds().create(params);
        } catch (Exception e) {
			logger.error(String.format("Create PingXX Refund Error, [chargeId=%s]", refundVo.getChargeId()), e);
			return Result.ERROR(ResultCode.THIRD_REFUND_FAIL.getCode(), e.getMessage());
        }
        return Result.CREATE(ResultCode.SUCCESS.getCode(), "退款创建成功", refund);
	}
	
	public Result query(String prepayId, String refundId){
		String chargeId = prepayId;
		Assert.notNull(chargeId, "预支付chargeId不能为空");
		Assert.notNull(refundId, "退款refundId不能为空");
		Pingpp.apiKey = API_KEY_VALUE;
		Result result = pingXXPayComponent.query(chargeId);
        if(!ResultCode.SUCCESS.getCode().equals(result.getResultCode())){
			return Result.ERROR(ResultCode.THIRD_PAY_FAIL.getCode(), result.getExceptionMessage());
        }
		Charge charge = (Charge) result.getResult();
		Refund refund = null;
        try {
            refund = charge.getRefunds().retrieve(refundId);
        } catch (Exception e) {
			logger.error(String.format("Query PingXX Refund Error, [chargeId=%s,refundId=%s]", chargeId, refundId), e);
			return Result.ERROR(ResultCode.THIRD_REFUND_FAIL.getCode(), e.getMessage());
		}
        return Result.CREATE(ResultCode.SUCCESS.getCode(), "退款查询成功", refund);
	}


}
