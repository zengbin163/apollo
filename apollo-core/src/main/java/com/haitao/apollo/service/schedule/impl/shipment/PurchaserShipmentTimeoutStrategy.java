package com.haitao.apollo.service.schedule.impl.shipment;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.haitao.apollo.enums.FrozenCashCauseEnum;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.enums.MessageBoxTypeEnum;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.enums.PayTypeEnum;
import com.haitao.apollo.global.GlobalConstants;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.pojo.purchaser.PurchaserFrozenCash;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.proccess.OrderProcess;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.order.PayOrderService;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.purchaser.PurchaserFrozenCashService;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.util.LoggerUtil;

/**
 * 发货倒计时 ---消费者支付尾款 + 发货时长 （1天、3天、7天）
 *	在超过采购时长第一天，冻结订单金额1%的保证金
 *  第二天，冻结订单金额的2%的保证金
 *  第三天，冻结订单金额的3%的保证金
 *  超过72小时扣除订单金额的6%的保证金给用户，同时退款给用户
 * @author zengbin
 */
@Component
public class PurchaserShipmentTimeoutStrategy extends AbstractShipmentTimeoutStrategy {
    @Autowired
    private PostRewardService postRewardService;
    @Autowired
    private PurchaserFrozenCashService purchaserFrozenCashService;
    @Autowired
    private PurchaserService purchaserService;
    @Autowired
    private OrderProcess orderProcess;
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    protected MsgTemplate msgTemplate;
    @Autowired
    private AsyncService asyncService;

    private static final Long TIME_00 = 0L;
    private static final Long TIME_24 = 24 * 60 * 60 * 1000L;
    private static final Long TIME_48 = 48 * 60 * 60 * 1000L;
    private static final Long TIME_72 = 72 * 60 * 60 * 1000L;
    
    private static final Logger logger = LoggerFactory.getLogger(PurchaserShipmentTimeoutStrategy.class);
    
    @Transactional
	@Override
	public void execute() {
		Integer sum = this.postRewardService.countShipmentTimeoutPostRewardList();
		if(Page.DEFAULT_SUM.equals(sum)){
			return;
		}
		int totalPages = Page.getTotalPages(sum);
		for (int i = 0; i < totalPages; i++) {
			List<PostReward> postrewardList = this.postRewardService.getShipmentTimeoutPostRewardList(i * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			for(PostReward postReward : postrewardList){
				long timestamp = postReward.getFinalTime() + postReward.getPurchaserDay() * 24 * 60 * 60 * 1000;
				long currentTime = DateUtil.currentUTCTime();
				long time = currentTime - timestamp;
				Integer purchaserId = postReward.getPurchaserId();
				Integer postRewardId = postReward.getId();
				User user = this.userService.getUserById(postReward.getUserId());
				Purchaser purchaser = this.purchaserService.getPurchaserById(purchaserId);
				if (time > TIME_00 && time <= TIME_24) {
					//1.在超过采购时长第一天，冻结订单金额1%的保证金
					List<PurchaserFrozenCash> purchaserFrozenList = this.purchaserFrozenCashService.getPurchaserFrozenCash(purchaserId, postRewardId);
					/********防呆，防止重复冻结买手保证金------start*********/
					if(!CollectionUtils.isEmpty(purchaserFrozenList)) {
						continue;
					}
					if(isFinishPurchaserFrozenCash(purchaserFrozenList)) {
						continue;
					}
					/********防呆，防止重复冻结买手保证金------end*********/
					BigDecimal frozenAmount = postReward.getRewardPrice().multiply(new BigDecimal(postReward.getProductNum())).multiply(new BigDecimal(GlobalConstants.FROZEN_PERCENT_1)).setScale(0, BigDecimal.ROUND_HALF_UP);
					this.purchaserFrozenCashService.createPurchaserFrozenCash(postReward.getPurchaserId(), postReward.getId(), frozenAmount, FrozenCashCauseEnum.SHIPMENT_DELAY.getCode(), IsDefaultEnum.DEFAULT_NO.getCode());
					//发推送和短信
					this.sendDelayMsg(user.getId(), purchaserId, user.getMobile(), purchaser.getMobile());
				} else if(time > TIME_24 && time <= TIME_48) {
					//2.第二天，冻结订单金额的2%的保证金
					List<PurchaserFrozenCash> purchaserFrozenList = this.purchaserFrozenCashService.getPurchaserFrozenCash(purchaserId, postRewardId);
					/********防呆，防止重复冻结买手保证金------start*********/
					if(!CollectionUtils.isEmpty(purchaserFrozenList) && purchaserFrozenList.size() > 1) {
						continue;
					}
					if(isFinishPurchaserFrozenCash(purchaserFrozenList)) {
						continue;
					}
					/********防呆，防止重复冻结买手保证金------end*********/
					BigDecimal frozenAmount = postReward.getRewardPrice().multiply(new BigDecimal(postReward.getProductNum())).multiply(new BigDecimal(GlobalConstants.FROZEN_PERCENT_2)).setScale(0, BigDecimal.ROUND_HALF_UP);
					this.purchaserFrozenCashService.createPurchaserFrozenCash(postReward.getPurchaserId(), postReward.getId(), frozenAmount, FrozenCashCauseEnum.SHIPMENT_DELAY.getCode(), IsDefaultEnum.DEFAULT_NO.getCode());
					//发推送和短信
					this.sendDelayMsg(user.getId(), purchaserId, user.getMobile(), purchaser.getMobile());
				} else if(time > TIME_48 && time <= TIME_72) {
					//3.第三天，冻结订单金额的3%的保证金
					List<PurchaserFrozenCash> purchaserFrozenList = this.purchaserFrozenCashService.getPurchaserFrozenCash(purchaserId, postRewardId);
					/********防呆，防止重复冻结买手保证金------start*********/
					if(!CollectionUtils.isEmpty(purchaserFrozenList) && purchaserFrozenList.size() > 2) {
						continue;
					}
					if(isFinishPurchaserFrozenCash(purchaserFrozenList)) {
						continue;
					}
					/********防呆，防止重复冻结买手保证金------end*********/
					BigDecimal frozenAmount = postReward.getRewardPrice().multiply(new BigDecimal(postReward.getProductNum())).multiply(new BigDecimal(GlobalConstants.FROZEN_PERCENT_3)).setScale(0, BigDecimal.ROUND_HALF_UP);
					this.purchaserFrozenCashService.createPurchaserFrozenCash(postReward.getPurchaserId(), postReward.getId(), frozenAmount, FrozenCashCauseEnum.SHIPMENT_DELAY.getCode(), IsDefaultEnum.DEFAULT_NO.getCode());
					//发推送和短信
					this.sendDelayMsg(user.getId(), purchaserId, user.getMobile(), purchaser.getMobile());
				} else if(time > TIME_72) {
					//4.超过72小时扣除订单金额的6%的保证金给用户，同时退款给用户
					List<PurchaserFrozenCash> purchaserFrozenList = this.purchaserFrozenCashService.getPurchaserFrozenCash(purchaserId, postRewardId);
					/********防呆，防止重复冻结买手保证金------start*********/
					if(!CollectionUtils.isEmpty(purchaserFrozenList) && purchaserFrozenList.size() > 3) {
						continue;
					}
					if(isFinishPurchaserFrozenCash(purchaserFrozenList)) {
						continue;
					}
					/********防呆，防止重复冻结买手保证金------end*********/
					//扣减保证金处罚
					BigDecimal frozenAmount = postReward.getRewardPrice().multiply(new BigDecimal(postReward.getProductNum())).multiply(new BigDecimal(GlobalConstants.FROZEN_PERCENT_6)).setScale(0, BigDecimal.ROUND_HALF_UP);
		        	this.purchaserService.decreasePurchaserGuarantee(purchaserId, frozenAmount);
		        	//赔付冻结保证金
		        	this.purchaserFrozenCashService.finishPurchaserFrozenCash(purchaserId, postRewardId);
		        	SaleOrder saleOrder = this.saleOrderService.getSaleOrderByRewardId(postRewardId);
		        	//取消订单（买手主动取消才会在接口里面增加大牌币）
		        	this.orderProcess.cancelOrder(saleOrder.getId(), OperatorRoleEnum.ROLE_SYSTEM.getCode());
		        	//增加消费者的大牌币，新增支付记录
		        	this.userService.increaseUserBigMoney(saleOrder.getUserId(), frozenAmount);
		        	this.payOrderService.createPayOrder(postRewardId, postReward.getUserId(), PayTypeEnum.BIGMONEY.getCode(), FundTypeEnum.COMPENSATION.getCode(), BigDecimal.ZERO, frozenAmount, IsDefaultEnum.DEFAULT_NO.getCode(), com.haitao.apollo.pay.Component.NULL_PAY_SERIAL_NO);
					//发推送和短信
					this.sendOverMsg(user.getId(), purchaserId, user.getMobile(), purchaser.getMobile());
				} else {
					
				}
			}
		}
    }
    
    private void sendDelayMsg(Integer userId, Integer purchaserId, String userMobile, String purchaserMobile) {
		//发推送
		this.msgTemplate.push(MsgTemplateEnum.DELAY_SEND_PURCHASER, userId, purchaserId, null);
		this.msgTemplate.push(MsgTemplateEnum.DELAY_SEND_USER, userId, purchaserId, null);
		//发短信
		this.msgTemplate.sms(MsgTemplateEnum.DELAY_SEND_PURCHASER, purchaserMobile, null);
		this.msgTemplate.sms(MsgTemplateEnum.DELAY_SEND_USER, userMobile, null);
		//消息盒子
		this.asyncService.messageBox(MsgTemplateEnum.DELAY_SEND_PURCHASER, MessageBoxTypeEnum.MSG_ORDER, userId, purchaserId, null); 
		this.asyncService.messageBox(MsgTemplateEnum.DELAY_SEND_USER, MessageBoxTypeEnum.MSG_ORDER, userId, purchaserId, null); 
    }

    private void sendOverMsg(Integer userId, Integer purchaserId, String userMobile, String purchaserMobile) {
    	//发推送
    	this.msgTemplate.push(MsgTemplateEnum.OVER_SEND_PURCHASER, userId, purchaserId, null);
    	this.msgTemplate.push(MsgTemplateEnum.OVER_SEND_USER, userId, purchaserId, null);
    	//发短信
    	this.msgTemplate.sms(MsgTemplateEnum.OVER_SEND_PURCHASER, purchaserMobile, null);
    	this.msgTemplate.sms(MsgTemplateEnum.OVER_SEND_USER, userMobile, null);
		//消息盒子
		this.asyncService.messageBox(MsgTemplateEnum.OVER_SEND_PURCHASER, MessageBoxTypeEnum.MSG_ORDER, userId, purchaserId, null); 
		this.asyncService.messageBox(MsgTemplateEnum.OVER_SEND_USER, MessageBoxTypeEnum.MSG_ORDER, userId, purchaserId, null); 
    }

    private boolean isFinishPurchaserFrozenCash(List<PurchaserFrozenCash> purchaserFrozenList) {
    	if(CollectionUtils.isEmpty(purchaserFrozenList)) {
    		return false;
    	}
		for(PurchaserFrozenCash frozenCash : purchaserFrozenList) {
			if(frozenCash.getPayStatus() == IsDefaultEnum.DEFAULT_YES.getCode()) {
				LoggerUtil.WARN(logger, String.format("冻结买手保证金失败，已经有保证金赔付记录，[purchaserId=%s,postrewardId=%s]", frozenCash.getPurchaserId(), frozenCash.getPostrewardId()));
				return true;
			}
		}
		return false;
    }
}
