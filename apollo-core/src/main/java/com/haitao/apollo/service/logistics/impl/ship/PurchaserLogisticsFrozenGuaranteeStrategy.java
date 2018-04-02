/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月13日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.logistics.impl.ship;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.enums.PayTypeEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.purchaser.PurchaserFrozenCash;
import com.haitao.apollo.service.order.PayOrderService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.purchaser.PurchaserFrozenCashService;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.user.UserService;

/** 
* @ClassName: PurchaserLogisticsFrozenGuaranteeStrategy 
* @Description: 买手冻结保证金的处理策略
* @author zengbin
* @date 2016年04月18日 下午3:12:27 
*/
@Component
public class PurchaserLogisticsFrozenGuaranteeStrategy extends AbstractLogisticsShipStrategy {
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private PurchaserService purchaserService;
    @Autowired
    private PurchaserFrozenCashService purchaserFrozenCashService;

	@Override
	public void execute(Integer orderId, Integer receiverId, String logisticsCompany, String trackingNo) {
		Assert.notNull(orderId, "订单id不能为空");
        SaleOrder saleOrder = this.saleOrderService.getSaleOrderByOrderId(orderId);
        if(null==saleOrder){
            throw new ApolloBizException(ResultCode.SALEORDER_NOT_EXIST, getUserId(), String.format("销售订单不存在，orderId = %s", orderId));
        }
        Integer purchaserId = saleOrder.getPurchaserId();
		//查看是否延期发货被冻结的保证金，如果有一律以大牌币的形式打给消费者，同时考虑定时任务是否处理过
		List<PurchaserFrozenCash> frozenCashList = this.purchaserFrozenCashService.getUnPayPurchaserFrozenCash(purchaserId, saleOrder.getPostrewardId());
		if(!CollectionUtils.isEmpty(frozenCashList)) {
			//扣减保证金处罚
			BigDecimal frozenAmount = BigDecimal.ZERO;
			for(PurchaserFrozenCash frozenCash : frozenCashList) {
				frozenAmount = frozenAmount.add(frozenCash.getFrozenAmount());
			}
        	this.purchaserService.decreasePurchaserGuarantee(saleOrder.getPurchaserId(), frozenAmount);
        	//赔付冻结保证金
        	this.purchaserFrozenCashService.finishPurchaserFrozenCash(saleOrder.getPurchaserId(), saleOrder.getPostrewardId());
        	//增加用户的大牌币
        	this.userService.increaseUserBigMoney(saleOrder.getUserId(), frozenAmount);
        	this.payOrderService.createPayOrder(saleOrder.getPostrewardId(), saleOrder.getUserId(), PayTypeEnum.BIGMONEY.getCode(), FundTypeEnum.COMPENSATION.getCode(), BigDecimal.ZERO, frozenAmount, IsDefaultEnum.DEFAULT_NO.getCode(), com.haitao.apollo.pay.Component.NULL_PAY_SERIAL_NO);
		}
	}
    
}
