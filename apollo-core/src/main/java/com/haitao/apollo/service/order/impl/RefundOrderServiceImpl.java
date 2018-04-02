package com.haitao.apollo.service.order.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.RefundOrderDao;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.enums.OrderTrackEnum;
import com.haitao.apollo.enums.PayTypeEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.PayOrder;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.order.PayOrderService;
import com.haitao.apollo.service.order.RefundOrderService;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.status.RewardStatus;
import com.haitao.apollo.vo.order.RefundOrderVo;

@Service
public class RefundOrderServiceImpl implements RefundOrderService {
	
	@Resource(name = "refundOrderDao")
	private RefundOrderDao refundOrderDao;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private AsyncService asyncService;

	@Override
	public Integer createRefundOrder(Integer userId, Integer postrewardId, Integer payOrderId, BigDecimal refundAmount, String paySerialNo) {
		RefundOrderVo refundOrderVo = new RefundOrderVo(postrewardId, userId, payOrderId, refundAmount, paySerialNo);
		this.refundOrderDao.insertRefundOrder(refundOrderVo);
        if(null==refundOrderVo.getId()){
            throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("创建悬赏单失败，[postrewardId=%s,payOrderId=%s]", postrewardId, payOrderId));
        }
		return refundOrderVo.getId();
	}

	@Override
	public void refundCallBack(String paySerialNo) {
		PayOrder payOrder = this.payOrderService.getPayOrderBySerialNo(paySerialNo);
		if(null==payOrder){
			throw new ApolloBizException(ResultCode.PAYORDER_NOT_EXISTS, -1000, String.format("交易订单不存在，[paySerialNo=%s]", paySerialNo));
		}
		this.createRefundOrder(payOrder.getUserId(), payOrder.getPostrewardId(), payOrder.getId(), payOrder.getPayAmount(), paySerialNo);
		//交易记录（冗余，方便交易记录查询），就fundType不一样
		Integer fundType = (FundTypeEnum.DEPOSIT.getCode().equals(payOrder.getFundType())) ? FundTypeEnum.REFUND_DEPOSIT.getCode() : FundTypeEnum.REFUND_FINAL.getCode();
		this.payOrderService.createPayOrder(payOrder.getPostrewardId(),
				payOrder.getUserId(), payOrder.getPayType(), fundType,
				BigDecimal.ZERO, payOrder.getPayAmount(),
				payOrder.getPayByBig(),
				com.haitao.apollo.proccess.Process.REFUND_PREFIX + paySerialNo);
		//记录流水账
		StringBuffer sb = new StringBuffer();
		sb.append("{描述:消费者退尾款成功,");
		sb.append("支付类型:").append(PayTypeEnum.getEnum(payOrder.getPayType()).getName()).append(",");
		sb.append("款项类型:").append(FundTypeEnum.getEnum(payOrder.getFundType()).getName()).append(",");
		sb.append("第三方支付流水号:").append(com.haitao.apollo.proccess.Process.REFUND_PREFIX + paySerialNo);
		sb.append("}");
		asyncService.orderTrack(payOrder.getUserId(), null, null, payOrder.getPostrewardId(), RewardStatus.CLOSE_REWARD, OrderStatus.CLOSED_ORDER, OrderTrackEnum.CLOSE_ORDER.getCode(), JSONObject.toJSONString(sb));
	}

}
