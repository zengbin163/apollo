/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月13日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl.accept;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.PostRewardDao;
import com.haitao.apollo.dao.purchaser.PurchaserDao;
import com.haitao.apollo.enums.MessageBoxTypeEnum;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.pojo.purchaser.PurchaserVip;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.purchaser.PurchaserVipService;
import com.haitao.apollo.service.template.MsgTemplate;

/** 
* @ClassName: PurchaserStrategy 
* @Description: 买手相关的策略校验
* @author zengbin
* @date 2015年11月13日 下午3:12:27 
*/
@Component
public class PurchaserStrategy extends AbstractPurchaserAcceptStrategy {
	@Resource(name = "purchaserDao")
	private PurchaserDao purchaserDao;
    @Resource(name = "postRewardDao")
    private PostRewardDao postRewardDao;
	@Autowired
    private PurchaserVipService purchaserVipService;
    @Autowired
    protected MsgTemplate msgTemplate;
    @Autowired
    private AsyncService asyncService;

	@Override
	public void execute(Integer purchaserId, Integer postrewardId) {
        Assert.notNull(purchaserId,"买手id不能为空");
        Purchaser purchaser = this.purchaserDao.getPurchaserById(purchaserId);
        if(null==purchaser){
            throw new ApolloBizException(ResultCode.PURCHASER_NOT_EXISTS, getUserId() , String.format("买手接单失败，买手不存在或者未被激活 , purchaserId=%s" , purchaserId));
        }
        PurchaserVip purchaserVip = purchaserVipService.getPurchaserVip(purchaser.getVip());
        if(null == purchaserVip) {
        	throw new ApolloBizException(ResultCode.PURCHASER_VIP_EXCEPTION, getUserId(), String.format("买手接单时等级出现异常，[purchaserId=%s]", getUserId()));
        }
        //校验买手保证金
        BigDecimal vipGuarantee = purchaserVip.getGuarantee();
        if(purchaser.getGuarantee().compareTo(vipGuarantee) == -1) {
    		//发推送
    		this.msgTemplate.push(MsgTemplateEnum.GUARANTEE_NOT_ENOUGH, null, purchaserId, null);
    		//发短信
    		this.msgTemplate.sms(MsgTemplateEnum.GUARANTEE_NOT_ENOUGH, purchaser.getMobile(), null);
    		//消息盒子
    		this.asyncService.messageBox(MsgTemplateEnum.GUARANTEE_NOT_ENOUGH, MessageBoxTypeEnum.MSG_PAY, null, purchaserId, null); 
        	throw new ApolloBizException(ResultCode.PURCHASER_GUARANTEE_NOT_ENOUGH, getUserId(), String.format("买手接单时保证金不足，[purchaserId=%s]", getUserId()));
        }
        PostReward postreward = this.postRewardDao.getPostRewardById(postrewardId);
        //校验买手额度
        BigDecimal rewardPrice = postreward.getRewardPrice().multiply(new BigDecimal(postreward.getProductNum()));
        if(purchaser.getQuota().compareTo(rewardPrice) == -1) {
        	throw new ApolloBizException(ResultCode.PURCHASER_QUOTA_NOT_ENOUGH, getUserId(), String.format("买手接单时额度不够，[purchaserId=%s]", getUserId()));
        }
	}
    
}
