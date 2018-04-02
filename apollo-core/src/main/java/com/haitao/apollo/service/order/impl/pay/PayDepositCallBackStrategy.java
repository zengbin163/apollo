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
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.order.PayOrderService;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.status.RewardStatus;
import com.haitao.apollo.vo.order.PostRewardVo;

/** 
* @ClassName: PayDepositCallBackStrategy 
* @Description: 支付定金回写
* @author zengbin
* @date 2015年12月08日 下午3:12:27 
*/
@Component
public class PayDepositCallBackStrategy extends AbstractPayCallBackStrategy {
    
	@Autowired
	private PostRewardService postRewardService;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired 
    private PayOrderService payOrderService;
    @Autowired
    protected MsgTemplate msgTemplate;
    @Autowired
    private PurchaserService purchaserService;

    @Override
    public void execute(Integer postrewardId, String paySerialNo, Integer payType, Integer fundType, BigDecimal payAmount, BigDecimal bigMoney, String subject) {
        Assert.notNull(postrewardId,"悬赏id不能为空");
        if(!FundTypeEnum.DEPOSIT.getCode().equals(fundType)) {
        	return;
        }
		PostReward postreward = this.postRewardService.getPostRewardById(postrewardId);
		if(null == postreward) {
            throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, -10000, String.format("悬赏不存在，postrewardId=%s", postrewardId));
		}
		//预支付悬赏支付定金回写
		if(!RewardStatus.PREPAY_REWARD.equals(postreward.getRewardStatus())){
			return;
		}
		//更新悬赏状态
        PostRewardVo postRewardVo = new PostRewardVo();
        postRewardVo.setId(postrewardId);
        postRewardVo.setRewardStatus(RewardStatus.CREATE_REWARD);
        Integer flag = this.postRewardService.updatePostReward(postrewardId,postRewardVo);
        if(null==flag || flag<=0){
            throw new ApolloBizException(ResultCode.UPDATE_FAIL, getUserId() , String.format("第三方定金支付回写，更新悬赏为已创建失败，postrewardId=%s" , postrewardId));
        }
		//创建支付记录（买手未接单，买手id不存在）
		this.payOrderService.createPayOrder(postrewardId, postreward.getUserId(), payType, fundType, payAmount, bigMoney,IsDefaultEnum.DEFAULT_NO.getCode(), paySerialNo);
		try{
			//发推送
			this.msgTemplate.push(MsgTemplateEnum.POSTREWARD_PUB, null, postreward.getPriorPurchaserId(), null);
			//发短信
			Purchaser purchaser = this.purchaserService.getPurchaserById(postreward.getPriorPurchaserId());
			this.msgTemplate.sms(MsgTemplateEnum.POSTREWARD_PUB, purchaser.getMobile(), null);
			//消息盒子
			this.asyncService.messageBox(MsgTemplateEnum.POSTREWARD_PUB, MessageBoxTypeEnum.MSG_ORDER, null, postreward.getPriorPurchaserId(), null); 
			//记录流水账
			StringBuffer sb = new StringBuffer();
			sb.append("{描述:消费者定金支付成功,");
			sb.append("支付类型:").append(PayTypeEnum.getEnum(payType).getName()).append(",");
			sb.append("款项类型:").append(FundTypeEnum.getEnum(fundType).getName()).append(",");
			sb.append("第三方支付流水号:").append(paySerialNo);
			sb.append("}");
			this.asyncService.orderTrack(getUserId(), null, postreward.getPurchaserId(), postrewardId, RewardStatus.CREATE_REWARD, OrderStatus.NOT_CREATE_ORDER, OrderTrackEnum.FINISH_PAY.getCode(), JSONObject.toJSONString(sb));
		}catch(Exception e){
			e.printStackTrace();
		}
    }
}
