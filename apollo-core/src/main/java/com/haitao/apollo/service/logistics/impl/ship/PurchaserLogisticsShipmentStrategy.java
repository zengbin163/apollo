/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月13日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.logistics.impl.ship;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.LogisticsOrderDao;
import com.haitao.apollo.dao.purchaser.PurchaserDao;
import com.haitao.apollo.enums.MessageBoxTypeEnum;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.OrderTrackEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.status.CsStatus;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.status.RewardStatus;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.order.LogisticsOrderVo;

/** 
* @ClassName: PurchaserLogisticsShipmentStrategy 
* @Description: 买手发货的策略校验
* @author zengbin
* @date 2016年04月18日 下午3:12:27 
*/
@Component
public class PurchaserLogisticsShipmentStrategy extends AbstractLogisticsShipStrategy {
	@Resource(name = "purchaserDao")
	private PurchaserDao purchaserDao;
	@Resource(name = "logisticsOrderDao")
	private LogisticsOrderDao logisticsOrderDao;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    protected MsgTemplate msgTemplate;
    @Autowired
    private SaleOrderService saleOrderService;
	@Autowired
	private PostRewardService postRewardService;

	@Override
	public void execute(Integer orderId, Integer receiverId, String logisticsCompany, String trackingNo) {
		Assert.notNull(orderId, "订单id不能为空");
		Assert.notNull(receiverId, "收件人Id不能为空");
		Assert.notNull(logisticsCompany, "物流公司不能为空");
		Assert.notNull(trackingNo, "物流单号不能为空");
        SaleOrder saleOrder = this.saleOrderService.getSaleOrderByOrderId(orderId);
        if(null==saleOrder){
            throw new ApolloBizException(ResultCode.SALEORDER_NOT_EXIST, getUserId(), String.format("销售订单不存在，orderId = %s", orderId));
        }
        Integer purchaserId = saleOrder.getPurchaserId();
        Assert.notNull(purchaserId, "买手id不能为空");
        Purchaser purchaser = this.purchaserDao.getPurchaserById(purchaserId);
        if(null==purchaser){
            throw new ApolloBizException(ResultCode.PURCHASER_NOT_EXISTS, purchaserId , String.format("买手接单失败，买手不存在或者未被激活 , purchaserId=%s" , purchaserId));
        }
        if(CsStatus.IN_CUSTOMER.equals(saleOrder.getCsStatus())){
            throw new ApolloBizException(ResultCode.SALEORDER_BE_SUSPEND, purchaserId , String.format("订单已挂起，请联系客服 , orderId=%s" , orderId));
        }
        //更新状态为已发货
		this.postRewardService.shipmentSaleOrder(saleOrder.getPostrewardId());
        this.saleOrderService.shipmentSaleOrder(orderId);
		LogisticsOrderVo logisticsOrderVo = new LogisticsOrderVo(orderId, receiverId, logisticsCompany, trackingNo, DateUtil.currentUTCTime(), DateUtil.currentUTCTime());
		this.logisticsOrderDao.insertLogisticsOrder(logisticsOrderVo);
		if (null == logisticsOrderVo.getId()) {
			throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("创建物流订单失败，orderId = %s", orderId));
		}
		//发推送
		this.msgTemplate.push(MsgTemplateEnum.POSTREWARD_SHIPMENT, saleOrder.getUserId(), saleOrder.getPurchaserId(), null);
		//消息盒子
		this.asyncService.messageBox(MsgTemplateEnum.POSTREWARD_SHIPMENT, MessageBoxTypeEnum.MSG_ORDER, saleOrder.getUserId(), saleOrder.getPurchaserId(), null); 
		this.asyncService.orderTrack(purchaserId, orderId, saleOrder.getPurchaserId(), saleOrder.getPostrewardId(), RewardStatus.IN_SHIPMENTS, OrderStatus.IN_SHIPMENTS , OrderTrackEnum.PURCHASER_SHIPMENTS.getCode(), null);
	}
    
}
