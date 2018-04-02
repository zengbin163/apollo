package com.haitao.apollo.service.pay.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.base.Result;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.pay.component.PingXXRefundComponent;
import com.haitao.apollo.pay.vo.RefundVo;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.service.order.RefundPreviewService;
import com.haitao.apollo.service.pay.ThirdRefundService;
import com.pingplusplus.model.Refund;

@Service
public class ThirdRefundServiceImpl implements ThirdRefundService {
	
	@Autowired
	private PingXXRefundComponent pingXXRefundComponent;
	@Autowired
	private RefundPreviewService refundPreviewService;
	
	@Override
	public Refund refund(Integer postrewardId, Integer payOrderId, String chargeId, String description) {
		Assert.notNull(postrewardId, "悬赏id不能为空");
		Assert.notNull(payOrderId, "支付订单id不能为空");
		Assert.notNull(chargeId, "第三方交易订单号不能为空");
		this.refundPreviewService.createRefundPreview(postrewardId, payOrderId, chargeId);
		RefundVo refundVo = new RefundVo();
		refundVo.setOrderNo(postrewardId.toString());
		refundVo.setPayOrderId(payOrderId);
		refundVo.setChargeId(chargeId);
		refundVo.setDescription(description);
		Result result = pingXXRefundComponent.refund(refundVo);
		if(ResultCode.SUCCESS.getCode().equals(result.getResultCode())){
			Refund refund = (Refund) result.getResult();
			return refund;
		}else{
			throw new ApolloBizException(ResultCode.getEnum(result.getResultCode()), SessionContainer.getSession().getOperatorId(), result.getExceptionMessage());
		}	
	}

	@Override
	public Refund query(String chargeId, String refundId) {
		Result result = this.pingXXRefundComponent.query(chargeId, refundId);
		if(ResultCode.SUCCESS.getCode().equals(result.getResultCode())){
			return (Refund) result.getResult();
		}else{
			throw new ApolloBizException(ResultCode.getEnum(result.getResultCode()), SessionContainer.getSession().getOperatorId(), result.getExceptionMessage());
		}	
	}

}
