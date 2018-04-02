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

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.purchaser.PurchaserDayAccountService;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.purchaser.PurchaserVipService;

/** 
* @ClassName: PayRechargeCallBackStrategy 
* @Description: 买手充值回写
* @author zengbin
* @date 2016年02月23日 下午3:12:27 
*/
@Component
public class PayRechargeCallBackStrategy extends AbstractPayCallBackStrategy {
	
	@Autowired
	private PurchaserService purchaserService;
    @Autowired
    private PurchaserVipService purchaserVipService;
	@Autowired
    private AsyncService asyncService;
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired 
    private PurchaserDayAccountService purchaserDayAccountService;

    //买手充值不考虑升级，接单额度是保证金的2倍，并且只能充值一次
    @Override
    public void execute(Integer purchaserId, String paySerialNo, Integer payType, Integer fundType, BigDecimal payAmount, BigDecimal bigMoney, String subject) {
        Assert.notNull(purchaserId,"买手id不能为空");//买手充值，传入的是买手id
        if(!FundTypeEnum.GUARANTEE.getCode().equals(fundType)) {
        	return;
        }
        //买手是否存在
        Purchaser purchaser = this.purchaserService.getPurchaserById(purchaserId);
        if(null == purchaser) {
        	throw new ApolloBizException(ResultCode.PURCHASER_NOT_EXISTS, purchaserId, String.format("买手不存在[purchaserId=%s]", purchaserId));
        }
        //更新买手平台账户的保证金和买手接单额度
        this.purchaserService.increasePurchaserGuarantee(purchaserId, payAmount);
        this.purchaserService.freePurchaserQuota(purchaserId, payAmount.multiply(new BigDecimal(2)));
		//买手充值成功后如果满足条件需要升级买手等级， @TODO 一期不考虑升级
		//this.purchaserVipService.upgradePurchaserVip(purchaserId, payAmount);
		//创建买手充值记录
		this.purchaserDayAccountService.createDayAccount(purchaserId, null, null, payType, fundType, payAmount, paySerialNo);
    }
}
