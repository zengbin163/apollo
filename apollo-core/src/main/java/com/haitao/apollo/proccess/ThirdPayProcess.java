/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月18日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.proccess;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.enums.MessageBoxTypeEnum;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.OrderTrackEnum;
import com.haitao.apollo.enums.PayTypeEnum;
import com.haitao.apollo.pay.pojo.ThirdPay;
import com.haitao.apollo.pay.pojo.ThirdRefund;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.PayOrder;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.order.PayOrderService;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.service.order.RefundOrderService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.pay.ThirdPayService;
import com.haitao.apollo.service.pay.ThirdRefundService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.status.RewardStatus;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Refund;

/** 
* @ClassName: ThirdPayProcess 
* @Description: 第三方支付流程
* @author zengbin
* @date 2015年11月18日 下午6:49:24 
*/
@Component
public class ThirdPayProcess extends Process{
    
    @Autowired
    private ThirdPayService thirdPayService;
    @Autowired
    private ThirdRefundService thirdRefundService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private RefundOrderService refundOrderService;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostRewardService postRewardService;
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    protected MsgTemplate msgTemplate;

	/**
	 * 预支付并创建charge单
	 * @param amount   支付金额   单位为分
	 * @param bigMoney 大牌币       单位为分
	 * @param currency
	 * @param subject
	 * @param body
	 * @param postrewardId 悬赏id，就是传入第三方支付的商户订单id
	 * @param payType 支付渠道，0:alipay，1:wx
	 * @param clientIp
	 * @param fundType
	 * @return
	 */
    @Transactional
	public ThirdPay pay(BigDecimal amount, BigDecimal bigMoney, String currency,
			String subject, String body, Integer orderNo, Integer payType,
			String clientIp, Integer fundType, String pxxAppId) {
    	Assert.notNull(amount, "支付金额可以为0但是不能为空");
		BigDecimal payAmount = amount;
		boolean flag1 = BigDecimal.ZERO.compareTo(payAmount) == 0;
		boolean flag2 = BigDecimal.ZERO.compareTo(bigMoney) == 0;
		if(flag1 && flag2) {
			throw new ApolloBizException(ResultCode.ILLEGAL_ARGUMENT, getOperatorId(), String.format("支付金额和大牌币不能同时为0，[rewardId=%s]", orderNo));
		}
		if(payAmount.compareTo(bigMoney) == 1) {
			//支付金额大于大牌币（大牌币为0不影响）
			payAmount = amount.subtract(bigMoney);
			Charge charge = this.thirdPayService.pay(payAmount, bigMoney, currency, subject, body, orderNo, PayTypeEnum.getEnum(payType).getName(), clientIp, fundType, pxxAppId);
			return ThirdPay.create(orderNo, charge);
		}else {
			String paySerialNo = com.haitao.apollo.pay.Component.NULL_PAY_SERIAL_NO;
			//支付金额小于等于大牌币，通过大牌币全额支付，不走第三方支付，不会走webhooks回写
			payAmount = BigDecimal.ZERO;
			bigMoney = amount;
			//创建支付记录
	        PostReward postReward = this.postRewardService.getPostRewardById(orderNo);
	        if(null==postReward){
	        	throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, getOperatorId(), String.format("消费者通过大牌币全额支付尾款悬赏不存在，[postrewardId=%s]", orderNo));
	        }
			SaleOrder saleOrder = this.saleOrderService.getSaleOrderByRewardId(postReward.getId());
	        if(null==saleOrder){
	            throw new ApolloBizException(ResultCode.SALEORDER_NOT_EXIST, getOperatorId(), String.format("通过悬赏id查询销售订单不存在，postrewardId=%s", postReward.getId()));
	        }
			this.payOrderService.createPayOrder(orderNo, postReward.getUserId(), payType, fundType, payAmount, bigMoney, IsDefaultEnum.DEFAULT_YES.getCode(), paySerialNo);
			//更新状态为消费者同意发货时间
			this.postRewardService.agreePostReward(postReward.getId());
	    	this.saleOrderService.agreeSaleOrder(saleOrder.getId());
			//扣减消费者的大牌币
			Integer userId = SessionContainer.getSession().getOperatorId();
			this.userService.decreaseUserBigMoney(userId, bigMoney);
			//发推送
			this.msgTemplate.push(MsgTemplateEnum.CONFIRM_SEND_TIME_AGREE, userId, postReward.getPurchaserId(), null);
			//消息盒子
			this.asyncService.messageBox(MsgTemplateEnum.CONFIRM_SEND_TIME_AGREE, MessageBoxTypeEnum.MSG_ORDER, userId, postReward.getPurchaserId(), null); 
			//记录流水账
			StringBuffer sb = new StringBuffer();
			sb.append("{描述:消费者通过大牌币全额支付尾款成功,");
			sb.append("支付类型:").append(PayTypeEnum.getEnum(payType).getName()).append(",");
			sb.append("款项类型:").append(FundTypeEnum.getEnum(fundType).getName()).append(",");
			sb.append("第三方支付流水号:").append(paySerialNo);
			sb.append("}");
			asyncService.orderTrack(userId, null, null, orderNo, RewardStatus.AGREE_TIME, OrderStatus.AGREE_TIME, OrderTrackEnum.AGREE_TIME.getCode(), JSONObject.toJSONString(sb));
			return ThirdPay.create(orderNo, null);
		}
	}
    
	/**
	 * 第三方支付回写
	 * @param postrewardId
     * @param fundType 款项类型  0:定金  1:尾款  2:全款  3:买手充值保证金
	 * @param subject
	 */
    @Transactional
	public void payCallBack(Integer orderNo, String paySerialNo, Integer payType, Integer fundType, BigDecimal payAmount, BigDecimal bigMoney, String subject) {
		this.payOrderService.payCallBack(orderNo, paySerialNo, payType, fundType, payAmount, bigMoney, subject);
	}
    
    /**
     * 根据chargeId查询第三方支付Charge
     * @param chargeId
     * @return
     */
    public Charge query(String chargeId) {
    	return this.thirdPayService.query(chargeId);
    }
    
	/**
	 * 退款
	 * @param chargeId 交易id
	 * @param description 描述
	 * @return
	 */
    public ThirdRefund refund(String chargeId, String description) {
		PayOrder payOrder = this.payOrderService.getPayOrderBySerialNo(chargeId);
		Integer operatorId = -10000;
		if(null!=SessionContainer.getSession()) {
			operatorId = SessionContainer.getSession().getOperatorId();
		} else {
			operatorId = payOrder.getUserId();
		}
		if(null==payOrder){
			throw new ApolloBizException(ResultCode.PAYORDER_NOT_EXISTS, operatorId, String.format("交易订单不存在，[paySerialNo=%s]", chargeId));
		}
		ThirdRefund thirdRefund = null;
		Integer postrewardId = payOrder.getPostrewardId();
		BigDecimal refundAmount = null;
		if(BigDecimal.ZERO.compareTo(payOrder.getBigMoney()) == 0) {
			//如果是大牌币全额支付是不能发起第三方退款的
			Refund refund = this.thirdRefundService.refund(postrewardId, payOrder.getId(), chargeId, description);
			refundAmount = (null == refund.getAmount()) ? null : new BigDecimal(refund.getAmount());
	        thirdRefund = ThirdRefund.create(postrewardId, refundAmount, chargeId, BigDecimal.ZERO);
		} else {
			//大牌币全额支付退款记录
			refundOrderService.createRefundOrder(operatorId, postrewardId, payOrder.getId(), BigDecimal.ZERO, chargeId);
			//交易记录（冗余，方便交易记录查询），就fundType不一样
			this.payOrderService.createPayOrder(postrewardId,
					payOrder.getUserId(), payOrder.getPayType(),
					FundTypeEnum.REFUND_FINAL.getCode(), BigDecimal.ZERO,
					payOrder.getBigMoney(), payOrder.getPayByBig(),
					REFUND_PREFIX + chargeId);
			//返还消费者的大牌币
			this.userService.increaseUserBigMoney(payOrder.getUserId(), payOrder.getBigMoney());
			//记录流水账
			StringBuffer sb = new StringBuffer();
			sb.append("{描述:消费者通过大牌币全额支付退尾款成功,");
			sb.append("支付类型:").append(PayTypeEnum.getEnum(payOrder.getPayType()).getName()).append(",");
			sb.append("款项类型:").append(FundTypeEnum.getEnum(payOrder.getFundType()).getName()).append(",");
			sb.append("第三方支付流水号:").append(chargeId);
			sb.append("}");
			asyncService.orderTrack(getOperatorId(), null, null, postrewardId, RewardStatus.CLOSE_REWARD, OrderStatus.CLOSED_ORDER, OrderTrackEnum.CLOSE_ORDER.getCode(), JSONObject.toJSONString(sb));
	        thirdRefund = ThirdRefund.create(postrewardId, BigDecimal.ZERO, chargeId, payOrder.getBigMoney());
		}
		//发推送
		this.msgTemplate.push(MsgTemplateEnum.REFUND_ORDER, payOrder.getUserId(), null, null);
		//短信
		User user = this.userService.getUserById(payOrder.getUserId());
		this.msgTemplate.sms(MsgTemplateEnum.REFUND_ORDER, user.getMobile(), null);
		//消息盒子
		this.asyncService.messageBox(MsgTemplateEnum.REFUND_ORDER, MessageBoxTypeEnum.MSG_ORDER, payOrder.getUserId(), null, null); 
        return thirdRefund;
    }
    
    /**
     * 根据prepayId（chargeId）和refundId查询退款信息
     * @param prepayId
     * @param refundId
     * @return
     */
    public Refund queryRefund(String chargeId, String refundId) {
    	return this.thirdRefundService.query(chargeId, refundId);
    }
    
    /**
     * 退款成功第三方支付平台回调，退款成功
     * @param refundSerialNo
     */
    @Transactional
    public void refundCallBack(String paySerialNo) {
    	Assert.notNull(paySerialNo, "第三方支付序列号不能为空");
    	this.refundOrderService.refundCallBack(paySerialNo);
    }
    
    /**
     * 通过悬赏id查询支付记录
     * @param postrewardId
     * @return
     */
    public List<PayOrder> getPayOrderByPostrewardId(Integer postrewardId) {
    	Assert.notNull(postrewardId, "悬赏id不能为空");
    	return this.payOrderService.getPayOrderByPostrewardId(postrewardId);
    }
    
    /**
     * 通过第三方支付序列号查询支付记录
     * @param paySerialNo
     * @return
     */
    public PayOrder getPayOrderBySerialNo(String paySerialNo) {
    	Assert.notNull(paySerialNo, "第三方支付序列号不能为空");
    	return this.payOrderService.getPayOrderBySerialNo(paySerialNo);
    }
}

