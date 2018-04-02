/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月13日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.proccess;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.FrozenCashCauseEnum;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.enums.MessageBoxTypeEnum;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.enums.OrderTrackEnum;
import com.haitao.apollo.enums.ReleaseSourceEnum;
import com.haitao.apollo.enums.ShowSourceEnum;
import com.haitao.apollo.global.GlobalConstants;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.order.PayOrder;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.order.PayOrderService;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.order.ShowOrderService;
import com.haitao.apollo.service.purchaser.PurchaserFrozenCashService;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.status.CsStatus;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.status.RewardStatus;
import com.haitao.apollo.vo.order.PostRewardVo;

/** 
* @ClassName: OrderProcess 
* @Description: 订单流程
* @author zengbin
* @date 2015年11月13日 上午11:03:10 
*/
@Component
public class OrderProcess extends Process{
    
	@Autowired
	private ThirdPayProcess thirdPayProcess;
    @Autowired
    private PostRewardService postRewardService;
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private ShowOrderService showOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private PurchaserService purchaserService;
    @Autowired
    private PurchaserFrozenCashService purchaserFrozenCashService;
    @Autowired
    protected MsgTemplate msgTemplate;

    /**
    * @Description  预支付，悬赏状态为预支付
    * @param rewardPrice      悬赏单价（支付金额+红包+优惠券+优惠码）
    * @param productNum       产品数量
    * @param content          悬赏内容
    * @param receiverId       收货人id
    * @param picAddr1  
    * @param picAddr2
    * @param picAddr3
    * @param picAddr4
    * @param picAddr5
    * @param picAddr6
    * @param picAddr7
    * @param picAddr8
    * @param picAddr9
    * @param brandId         品牌id
    * @param categoryId      类目id
    * @param source          0.晒单页发起  1.自主发起  2.悬赏页跟单
    * @param sourceId        晒单发起悬赏，填入晒单id；悬赏跟单，填入被跟悬赏id；自主发起传入用户id
    * @return
     */
    @Transactional
	public Integer prePostReward(BigDecimal rewardPrice, Integer productNum,
			String content, Integer receiverId, String picAddr1,
			String picAddr2, String picAddr3, String picAddr4, String picAddr5,
			String picAddr6, String picAddr7, String picAddr8, String picAddr9,
			Integer brandId, Integer categoryId, Integer source,
			Integer sourceId, Integer requireDay) {
		Integer rewardId = postRewardService.postReward(rewardPrice,
				productNum, content, receiverId, RewardStatus.PREPAY_REWARD,
				picAddr1, picAddr2, picAddr3, picAddr4, picAddr5, picAddr6,
				picAddr7, picAddr8, picAddr9, brandId, categoryId, source,
				sourceId, requireDay);
        if(null==rewardId){
            throw new ApolloBizException(ResultCode.SAVE_FAIL, getOperatorId() , String.format("生成悬赏失败，userId = ", this.getOperatorId()));
        }
        asyncService.orderTrack(getOperatorId(), null, null, rewardId, RewardStatus.PREPAY_REWARD, OrderStatus.NOT_CREATE_ORDER , OrderTrackEnum.PREPAY_REWARD.getCode(), null);
        return rewardId;
    }
    
    /**
     * 买手接悬赏单
     * @param purchaserId   买手id
     * @param postrewardId  悬赏id
     * @param acceptTime    接单时间
     * @param purchaseTime  采购时间
     * @return
     */
    @Transactional
    public Integer accept(Integer purchaserId , Integer postrewardId, Long acceptTime, Integer purchaserDay){
        Assert.notNull(purchaserId,"买手id不能为空");
        Assert.notNull(postrewardId,"悬赏id不能为空");
        Assert.notNull(purchaserDay,"发货时间不能为空");
        
        PostReward postReward = this.postRewardService.getPostRewardById(postrewardId);
        if(null==postReward){
        	throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, purchaserId, String.format("买手接单失败，悬赏不存在，[postrewardId=%s,purchaserId=%s]", postrewardId, purchaserId));
        }
    	Integer orderId = this.postRewardService.accept(purchaserId, postrewardId, acceptTime, purchaserDay);
        this.asyncService.orderTrack(getOperatorId(), orderId, purchaserId, postrewardId,RewardStatus.PURCHASER_ACCEPT, OrderStatus.CREATE_ORDER, OrderTrackEnum.PURCHASER_ACCEPT.getCode(), null);
        return orderId;
    }
    
    /**
     * 释放悬赏到公共池
     * @param postrewardId    悬赏id
     * @param source  
     *     0:买手在接单处释放到公共池，1:消费者在拒绝买手发货时间释放到公共池，2:消费者24小时未同意发货时间释放到公共池
     * @param releaseTime     释放时间 
     * @return
     */
    @Transactional
    public Integer release(Integer postrewardId, Integer source, Long releaseTime){
        Assert.notNull(postrewardId,"悬赏id不能为空");
        Assert.notNull(source,"释放公共池来源不能为空");
        PostReward postReward = this.postRewardService.getPostRewardById(postrewardId);
        if(null==postReward){
        	throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, getOperatorId(), String.format("释放悬赏失败，悬赏不存在，[postrewardId=%s,operatorId=%s,source=%s]", postrewardId, getOperatorId(), source));
        }
        boolean flag1 = RewardStatus.CREATE_REWARD.equals(postReward.getRewardStatus()) && ReleaseSourceEnum.PURCHASER_RELEASE.getCode().equals(source);//买手在接单处释放到公共池
        boolean flag2 = RewardStatus.PURCHASER_ACCEPT.equals(postReward.getRewardStatus()) && ReleaseSourceEnum.USER_REFUSE.getCode().equals(source);//消费者在拒绝买手发货时间释放到公共池
        boolean flag3 = RewardStatus.PURCHASER_ACCEPT.equals(postReward.getRewardStatus()) && ReleaseSourceEnum.USER_AGREE_TIMEOUT.getCode().equals(source);//消费者24小时未同意发货时间释放到公共池
        if(!flag1 && !flag2 && !flag3) {
            throw new ApolloBizException(ResultCode.POSTREWARD_STATUS_ERROR, getOperatorId() , String.format("悬赏状态异常不能释放到公共池, postrewardId=%s" , postrewardId));
        }
        SaleOrder saleOrder = this.saleOrderService.getSaleOrderByRewardId(postrewardId);
        if(null!=saleOrder && CsStatus.IN_CUSTOMER.equals(saleOrder.getCsStatus())){
            throw new ApolloBizException(ResultCode.SALEORDER_BE_SUSPEND, getOperatorId() , String.format("订单已挂起，请联系客服 , postrewardId=%s" , postrewardId));
        }
		this.postRewardService.release(postrewardId, source, releaseTime);
		String track = null;
		switch (ReleaseSourceEnum.getEnum(source)) {
			case PURCHASER_RELEASE:
				track = "买手在接单处释放到公共池，source=" + source;
				break;
			case USER_REFUSE:
				track = "消费者在拒绝买手发货时间释放到公共池，source=" + source;
				break;
			case USER_AGREE_TIMEOUT:
				track = "消费者24小时未同意发货时间释放到公共池，source=" + source;
				break;
			default:
				track = "释放到公共池异常，source=" + source;
				break;
		}
        asyncService.orderTrack(getOperatorId(), null, null, postrewardId,RewardStatus.CREATE_REWARD, OrderStatus.NOT_CREATE_ORDER, OrderTrackEnum.RELEASE_PUBLIC_POOL.getCode(), track);
        return postrewardId;
    }
    
	/**
	 * 消费者修改价格
	 * @param postrewardId
	 * @return
	 */
    @Transactional
    public Integer adjustPrice(Integer postrewardId, BigDecimal rewardPrice) {
    	Assert.notNull(postrewardId, "悬赏id不能为空");
    	Assert.notNull(rewardPrice, "悬赏价格不能为空");
        PostReward postReward = this.postRewardService.getPostRewardById(postrewardId);
        if(null==postReward){
        	throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, getOperatorId(), String.format("释放悬赏失败，悬赏不存在，[postrewardId=%s,operatorId=%s]", postrewardId, getOperatorId()));
        }
        if(!RewardStatus.CREATE_REWARD.equals(postReward.getRewardStatus())){
        	throw new ApolloBizException(ResultCode.POSTREWARD_STATUS_ERROR, getOperatorId(), String.format("只有悬赏中的悬赏才能修改价格，[postrewardId=%s,operatorId=%s]", postrewardId, getOperatorId()));
        }
        //作废次悬赏下面的交易订单
    	List<PayOrder> payOrderList = this.payOrderService.getPayOrderByPostrewardId(postrewardId);
    	if(CollectionUtils.isEmpty(payOrderList)) {
        	throw new ApolloBizException(ResultCode.PAYORDER_NOT_EXISTS, getOperatorId(), String.format("消费者修改价格交易订单不存在，[postrewardId=%s,operatorId=%s]", postrewardId, getOperatorId()));
    	}
    	for(PayOrder payOrder : payOrderList) {
        	//退款
        	this.thirdPayProcess.refund(payOrder.getPaySerialNo(), "消费者修改价格，先做退款");
        	//废弃支付订单
        	this.payOrderService.discardPayOrderById(payOrder.getId());
    	}
    	//修改悬赏价格
    	PostRewardVo postrewardVo = new PostRewardVo();
    	postrewardVo.setId(postrewardId);
    	postrewardVo.setRewardPrice(rewardPrice);
    	this.postRewardService.updatePostReward(postrewardId, postrewardVo);
    	//记账
    	this.asyncService.orderTrack(getOperatorId(), null, null, postrewardId,RewardStatus.CREATE_REWARD, null, OrderTrackEnum.ADJUST_PRICE.getCode(), null);
    	return postrewardId;
	}
    
    /**
     * 消费者同意同时支付尾款
     * @param postrewardId
     * @return
     */
    public Map<String,Object> agreeAndPrePayFinal(Integer postrewardId){
    	Assert.notNull(postrewardId, "悬赏id不能为空");
        PostReward postReward = this.postRewardService.getPostRewardById(postrewardId);
        if(null==postReward){
        	throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, getOperatorId(), String.format("消费者同意发货时间失败，悬赏不存在，[postrewardId=%s]", postrewardId));
        }
        if(null==postReward.getRewardPrice()){
        	throw new ApolloBizException(ResultCode.ILLEGAL_DATA, getOperatorId(), String.format("悬赏价格为空，[postrewardId=%s]", postrewardId));
        }
        if(!RewardStatus.PURCHASER_ACCEPT.equals(postReward.getRewardStatus())){
            throw new ApolloBizException(ResultCode.POSTREWARD_STATUS_ERROR, getOperatorId() , String.format("只有买手已接单才能同意发货时间并支付尾款 , postrewardId=%s , rewardStatus=%s" , postrewardId , postReward.getRewardStatus()));
        }
        SaleOrder saleOrder = this.saleOrderService.getSaleOrderByRewardId(postrewardId);
        if(CsStatus.IN_CUSTOMER.equals(saleOrder.getCsStatus())){
            throw new ApolloBizException(ResultCode.SALEORDER_BE_SUSPEND, getOperatorId() , String.format("订单已挂起，请联系客服 , postrewardId=%s , rewardStatus=%s" , postrewardId , postReward.getRewardStatus()));
        }
        //买手接单将消费者40%的定金打给买手
		BigDecimal inAccount = postReward.getRewardPrice().multiply(new BigDecimal(postReward.getProductNum())).multiply(new BigDecimal(GlobalConstants.DEPOSIT_PERCENT)).setScale(0, BigDecimal.ROUND_HALF_UP);
		this.purchaserService.inPurchaserAccount(postReward.getPurchaserId(), inAccount, FundTypeEnum.ACC_DEPOSIT.getCode());
        User user = userService.getUserById(this.getOperatorId());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("rewardPrice", postReward.getRewardPrice());
        map.put("depositMoney", postReward.getRewardPrice().multiply(new BigDecimal(GlobalConstants.DEPOSIT_PERCENT)).setScale(0, BigDecimal.ROUND_HALF_UP));
		map.put("finalMoney", postReward.getRewardPrice().multiply(new BigDecimal(GlobalConstants.FINAL_PERCENT)).setScale(0, BigDecimal.ROUND_HALF_UP));
		map.put("bigMoney", user.getBigMoney());
		map.put("postrewardId", postrewardId);
        return map;
    }
    
    /**
     * 取消悬赏，只能取消待接单悬赏
     * @param postrewardId 悬赏id
     * @return
     */
    public boolean cancelPostreward(Integer postrewardId){
    	PostReward postreward = this.postRewardService.getPostRewardById(postrewardId);
		if (null == postreward) {
			throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, getOperatorId(), String.format("悬赏不存在，[postrewardId=%s]", postrewardId));
		}
		if (!RewardStatus.CREATE_REWARD.equals(postreward.getRewardStatus())) {
			throw new ApolloBizException(ResultCode.POSTREWARD_STATUS_ERROR, getOperatorId(), String.format("只有待接单悬赏能被取消，[postrewardId=%s]", postrewardId));
		}
		Integer flag = this.postRewardService.closePostReward(postrewardId);
        if(flag <=0){
            throw new ApolloBizException(ResultCode.UPDATE_FAIL, getOperatorId(), String.format("悬赏取消失败，rewardId=%s", postrewardId));
        }
		asyncService.orderTrack(getOperatorId(), null, null, postrewardId,
				RewardStatus.CLOSE_REWARD, null,
				OrderTrackEnum.CLOSE_REWARD.getCode(), null);
        List<PayOrder> payOrderList = this.thirdPayProcess.getPayOrderByPostrewardId(postrewardId);
        if(CollectionUtils.isEmpty(payOrderList)){
        	throw new ApolloBizException(ResultCode.PAYORDER_NOT_EXISTS, getOperatorId(), String.format("支付订单不存在，悬赏未产生任何支付，[postrewardId=%s]", postrewardId));
        }
        //退款
        for(PayOrder payOrder : payOrderList){
            this.thirdPayProcess.refund(payOrder.getPaySerialNo(), "取消悬赏产生退款请求");
        }
        return Boolean.TRUE;
    }

    /**
     * 取消订单
     * @param orderId
     * @param role 操作人角色 @OperatorRoleEnum
     * @return
     */
    @Transactional
    public boolean cancelOrder(Integer orderId, Integer role) {
        Assert.notNull(orderId,"订单id不能为空");
        Assert.notNull(role,"操作者角色不能为空");
		if (!OperatorRoleEnum.ROLE_PURCHASER.getCode().equals(role)
			 && !OperatorRoleEnum.ROLE_USER.getCode().equals(role)
			 && !OperatorRoleEnum.ROLE_CUSTOMER.getCode().equals(role)
			 && !OperatorRoleEnum.ROLE_SYSTEM.getCode().equals(role)) {
            throw new ApolloBizException(ResultCode.ILLEGAL_ARGUMENT, getOperatorId(), String.format("角色错误，orderId=%s", orderId));
        }
        SaleOrder saleOrder = this.saleOrderService.getSaleOrderByOrderId(orderId);
        if(null==saleOrder){
            throw new ApolloBizException(ResultCode.SALEORDER_NOT_EXIST, getOperatorId(), String.format("销售订单不存在，orderId=%s", orderId));
        }
		if (!OrderStatus.CREATE_ORDER.equals(saleOrder.getOrderStatus())
				&& !OrderStatus.AGREE_TIME.equals(saleOrder.getOrderStatus())) {
			throw new ApolloBizException(ResultCode.SALEORDER_STATUS_ERROR, getOperatorId(), String.format("只能在接单状态或者同意发货时间状态时取消订单，orderId=%s", orderId));
		}
        if(CsStatus.IN_CUSTOMER.equals(saleOrder.getCsStatus())){
            throw new ApolloBizException(ResultCode.SALEORDER_BE_SUSPEND, getOperatorId() , String.format("订单已挂起，请联系客服 , orderId=%s" , orderId));
        }
        this.postRewardService.closePostReward(saleOrder.getPostrewardId());
        this.saleOrderService.closeSaleOrder(orderId);
		String track = null;
		switch (OperatorRoleEnum.getEnum(role)) {
			case ROLE_PURCHASER:
				track = "买手发起的取消订单，role=" + role;
				break;
			case ROLE_USER:
				track = "消费者发起的取消订单，role=" + role;
				break;
			case ROLE_CUSTOMER:
				track = "客服发起的取消订单，role=" + role;
				break;
			case ROLE_SYSTEM:
				track = "系统发起的取消订单，role=" + role;
				break;
			default:
				track = "取消订单发起来源异常，role=" + role;
				break;
		}
        //释放买手额度
        this.purchaserService.freePurchaserQuota(saleOrder.getPurchaserId(), saleOrder.getRewardPrice().multiply(new BigDecimal(saleOrder.getProductNum())));
        //如果是买手主动取消订单，处罚买手，扣除1%的保证金，上限500
        if(OperatorRoleEnum.ROLE_PURCHASER.getCode().equals(role)) {
        	Purchaser purchaser = this.purchaserService.getPurchaserById(saleOrder.getPurchaserId());
        	BigDecimal dedu = purchaser.getGuarantee().multiply(new BigDecimal(GlobalConstants.DEDU_PERCENT)).setScale(0, BigDecimal.ROUND_HALF_UP);
        	if(dedu.compareTo(new BigDecimal(GlobalConstants.DEDU_UPPER)) == 1) {
        		dedu = new BigDecimal(GlobalConstants.DEDU_UPPER);
        	}
        	//扣减买手保证金
        	this.purchaserService.decreasePurchaserGuarantee(saleOrder.getPurchaserId(), dedu);
        	this.purchaserFrozenCashService.createPurchaserFrozenCash(saleOrder.getPurchaserId(), saleOrder.getPostrewardId(), dedu, FrozenCashCauseEnum.CANCEL_ORDER.getCode(), IsDefaultEnum.DEFAULT_YES.getCode());
        	//消费者增加大牌币
        	this.userService.increaseUserBigMoney(saleOrder.getUserId(), dedu);
        	try{
        		//发推送
        		this.msgTemplate.push(MsgTemplateEnum.CANCEL_ORDER, saleOrder.getUserId(), saleOrder.getPurchaserId(), null);
        		//短信
        		User user = this.userService.getUserById(saleOrder.getUserId());
        		this.msgTemplate.sms(MsgTemplateEnum.CANCEL_ORDER, user.getMobile(), null);
        		//消息盒子
        		this.asyncService.messageBox(MsgTemplateEnum.CANCEL_ORDER, MessageBoxTypeEnum.MSG_ORDER, saleOrder.getUserId(), saleOrder.getPurchaserId(), null); 
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        }else {
        	try{
				//发推送
				this.msgTemplate.push(MsgTemplateEnum.CONFIRM_SEND_TIME_REUSE, saleOrder.getUserId(), saleOrder.getPurchaserId(), null);
				//消息盒子
				this.asyncService.messageBox(MsgTemplateEnum.CONFIRM_SEND_TIME_REUSE, MessageBoxTypeEnum.MSG_ORDER, saleOrder.getUserId(), saleOrder.getPurchaserId(), null); 
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        }
        this.asyncService.orderTrack(getOperatorId(), orderId, saleOrder.getPurchaserId(), saleOrder.getPostrewardId(),RewardStatus.CLOSE_REWARD, OrderStatus.CLOSED_ORDER,OrderTrackEnum.CLOSE_ORDER.getCode(), track);
        //退款
        List<PayOrder> payOrderList = this.thirdPayProcess.getPayOrderByPostrewardId(saleOrder.getPostrewardId());
        if(CollectionUtils.isEmpty(payOrderList)){
        	throw new ApolloBizException(ResultCode.PAYORDER_NOT_EXISTS, getOperatorId(), String.format("支付订单不存在，悬赏未产生任何支付，[postrewardId=%s]", saleOrder.getPostrewardId()));
        }
        for(PayOrder payOrder : payOrderList){
            this.thirdPayProcess.refund(payOrder.getPaySerialNo(), track + " 产生退款请求");
        }
        return Boolean.TRUE;
    }
    
    /**
     * 完成订单，确认收货
     * @param orderId 订单id
     * @param description 描述
     * @return
     */
    @Transactional
    public boolean confirm(Integer orderId, String description) {
    	Assert.notNull(orderId, "订单id不能为空");
    	SaleOrder saleOrder = this.saleOrderService.getSaleOrderByOrderId(orderId);
    	if(null==saleOrder){
    		throw new ApolloBizException(ResultCode.SALEORDER_NOT_EXIST, getOperatorId(), String.format("销售订单不存在，orderId=%s", orderId));
    	}
        if(!OrderStatus.IN_SHIPMENTS.equals(saleOrder.getOrderStatus())) {
            throw new ApolloBizException(ResultCode.SALEORDER_STATUS_ERROR, getOperatorId(), String.format("只有买手已发货的订单才能确认收货，orderId=%s", orderId));
        }
        if(CsStatus.IN_CUSTOMER.equals(saleOrder.getCsStatus())){
            throw new ApolloBizException(ResultCode.SALEORDER_BE_SUSPEND, getOperatorId() , String.format("订单已挂起，请联系客服 , orderId=%s" , orderId));
        }
    	this.postRewardService.finishPostReward(saleOrder.getPostrewardId());
        this.saleOrderService.finishSaleOrder(orderId);
		//发推送
		this.msgTemplate.push(MsgTemplateEnum.CONFIRM_RECEIVE, saleOrder.getUserId(), saleOrder.getPurchaserId(), null);
		//消息盒子
		this.asyncService.messageBox(MsgTemplateEnum.CONFIRM_RECEIVE, MessageBoxTypeEnum.MSG_ORDER, saleOrder.getUserId(), saleOrder.getPurchaserId(), null); 
		this.asyncService.orderTrack(getOperatorId(), orderId, saleOrder.getPurchaserId(), saleOrder.getPostrewardId(),RewardStatus.FINISH_REWARD, OrderStatus.FINISHED_ORDER,OrderTrackEnum.FINISH_ORDER.getCode(), description);
    	return Boolean.TRUE;
    }
    
    /**
     * 用户或者买手晒单
     * @param orderId 订单id
     * @param role  来源  0买手主动发布晒单   1消费者好评默认晒单
     * @param showPrice  晒单价
     * @param content    晒单内容
     * @param picAddr1
     * @param picAddr2
     * @param picAddr3
     * @param picAddr4
     * @param picAddr5
     * @param picAddr6
     * @param picAddr7
     * @param picAddr8
     * @param picAddr9
     * @param brandId   品牌id
     * @param categoryId   分类id
     * @return
     */
    @Transactional
	public Integer showOrder(Integer orderId, Integer role,
			BigDecimal showPrice, String content, String picAddr1,
			String picAddr2, String picAddr3, String picAddr4, String picAddr5,
			String picAddr6, String picAddr7, String picAddr8, String picAddr9,
			Integer brandId, Integer categoryId) {
		Integer showOrderId = this.showOrderService.createShowOrder(orderId,
				role, showPrice, content, picAddr1, picAddr2, picAddr3,
				picAddr4, picAddr5, picAddr6, picAddr7, picAddr8, picAddr9,
				brandId, categoryId);
		if(ShowSourceEnum.USER_APPRAISE.getCode().equals(role)){
			SaleOrder saleOrder = this.saleOrderService.getSaleOrderByOrderId(orderId);
			asyncService.orderTrack(getOperatorId(), orderId, saleOrder.getPurchaserId(), saleOrder.getPostrewardId(), RewardStatus.FINISH_REWARD, OrderStatus.FINISHED_ORDER, OrderTrackEnum.FINISH_SHOW_ORDER.getCode(), "用户好评默认晒单");
		}else{
			asyncService.orderTrack(getOperatorId(), orderId, getOperatorId(), null, RewardStatus.FINISH_REWARD, OrderStatus.FINISHED_ORDER, OrderTrackEnum.FINISH_SHOW_ORDER.getCode(), "买手主动晒单");
		}
		return showOrderId;
	}
}
