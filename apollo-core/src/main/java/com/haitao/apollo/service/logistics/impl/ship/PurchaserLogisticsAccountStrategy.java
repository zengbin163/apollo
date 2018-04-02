/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月13日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.logistics.impl.ship;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.global.GlobalConstants;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.purchaser.PurchaserService;

/** 
* @ClassName: PurchaserLogisticsAccountStrategy 
* @Description: 买手账户处理策略
* @author zengbin
* @date 2016年04月18日 下午3:12:27 
*/
@Component
public class PurchaserLogisticsAccountStrategy extends AbstractLogisticsShipStrategy {
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    private PurchaserService purchaserService;

	@Override
	public void execute(Integer orderId, Integer receiverId, String logisticsCompany, String trackingNo) {
		Assert.notNull(orderId, "订单id不能为空");
        SaleOrder saleOrder = this.saleOrderService.getSaleOrderByOrderId(orderId);
        if(null==saleOrder){
            throw new ApolloBizException(ResultCode.SALEORDER_NOT_EXIST, getUserId(), String.format("销售订单不存在，orderId = %s", orderId));
        }
        Integer purchaserId = saleOrder.getPurchaserId();
        //释放买手额度和增加买手的接单量
        this.purchaserService.freePurchaserQuota(purchaserId, saleOrder.getRewardPrice().multiply(new BigDecimal(saleOrder.getProductNum())));
        this.purchaserService.increasePurchaserQuantity(purchaserId);
        //买手发货将消费者剩余的60%的尾款打给买手
		BigDecimal inAccount = saleOrder.getRewardPrice().multiply(new BigDecimal(saleOrder.getProductNum())).multiply(new BigDecimal(GlobalConstants.FINAL_PERCENT)).setScale(0, BigDecimal.ROUND_HALF_UP);
		this.purchaserService.inPurchaserAccount(purchaserId, inAccount, FundTypeEnum.ACC_FINAL.getCode());
	}
    
}
