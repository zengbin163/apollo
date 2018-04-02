/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月13日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl.pay;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.enums.MessageBoxTypeEnum;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.OrderTrackEnum;
import com.haitao.apollo.enums.PayTypeEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.order.PayOrderService;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.status.RewardStatus;

/** 
* @ClassName: PayFinalCallBackStrategy 
* @Description: 支付尾款回写
* @author zengbin
* @date 2015年11月13日 下午3:12:27 
*/
@Component
public class PayFinalCallBackStrategy extends AbstractPayCallBackStrategy {
	
	@Autowired
	private PostRewardService postRewardService;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired 
    private PayOrderService payOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    protected MsgTemplate msgTemplate;

    @Override
    public void execute(Integer postrewardId, String paySerialNo, Integer payType, Integer fundType, BigDecimal payAmount, BigDecimal bigMoney, String subject) {
        Assert.notNull(postrewardId,"悬赏id不能为空");
        if(!FundTypeEnum.FINAL.getCode().equals(fundType)) {
        	return;
        }
		PostReward postreward = this.postRewardService.getPostRewardById(postrewardId);
		if(null == postreward) {
            throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, getUserId(), String.format("悬赏不存在，postrewardId=%s", postrewardId));
		}
        SaleOrder saleOrder = this.saleOrderService.getSaleOrderByRewardId(postrewardId);
        if(null==saleOrder){
            throw new ApolloBizException(ResultCode.SALEORDER_NOT_EXIST, getUserId(), String.format("通过悬赏id查询销售订单不存在，postrewardId=%s", postrewardId));
        }
        Integer orderId = saleOrder.getId();
		//买手已接单支付尾款
		if (!RewardStatus.PURCHASER_ACCEPT.equals(postreward.getRewardStatus())) {
			return;
		}
		//更新悬赏为消费者同意发货时间
		this.postRewardService.agreePostReward(postrewardId);
		//更新订单为消费者同意发货时间
    	this.saleOrderService.agreeSaleOrder(orderId);
		//创建支付记录
		this.payOrderService.createPayOrder(postrewardId, postreward.getUserId(), payType, fundType, payAmount, bigMoney,IsDefaultEnum.DEFAULT_NO.getCode(), paySerialNo);
		if(!(BigDecimal.ZERO.compareTo(bigMoney)==0)) {
			//扣减消费者的大牌币
			Integer userId = SessionContainer.getSession().getOperatorId();
			this.userService.decreaseUserBigMoney(userId, bigMoney);
		}
		try{
			//发推送
			this.msgTemplate.push(MsgTemplateEnum.CONFIRM_SEND_TIME_AGREE, postreward.getUserId(), postreward.getPurchaserId(), null);
			//消息盒子
			this.asyncService.messageBox(MsgTemplateEnum.CONFIRM_SEND_TIME_AGREE, MessageBoxTypeEnum.MSG_ORDER, postreward.getUserId(), postreward.getPurchaserId(), null); 
			//记录流水账
			StringBuffer sb = new StringBuffer();
			sb.append("{描述:消费者尾款支付成功,");
			sb.append("支付类型:").append(PayTypeEnum.getEnum(payType).getName()).append(",");
			sb.append("款项类型:").append(FundTypeEnum.getEnum(fundType).getName()).append(",");
			sb.append("第三方支付流水号:").append(paySerialNo);
			sb.append("}");
			this.asyncService.orderTrack(getUserId(), orderId, postreward.getPurchaserId(), postrewardId, RewardStatus.AGREE_TIME, OrderStatus.AGREE_TIME, OrderTrackEnum.AGREE_TIME.getCode(), JSONObject.toJSONString(sb));
		}catch(Exception e){
			e.printStackTrace();
		}
    }
}
