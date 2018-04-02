/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月14日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.login.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.annotation.FromUser;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.proccess.OrderProcess;
import com.haitao.apollo.proccess.PageIndexProcess;
import com.haitao.apollo.proccess.PageListProcess;
import com.haitao.apollo.web.BaseAction;

/** 
* @ClassName: SaleOrderAction 
* @Description: 销售订单action
* @author zengbin
* @date 2015年11月14日 下午3:30:37 
*/
public class SaleOrderAction extends BaseAction {
    
    private static final long serialVersionUID = 7994045709174257232L;
    
    @Autowired
    private OrderProcess orderProcess;
    @Autowired
    private PageIndexProcess pageIndexProcess;
    @Autowired
    private PageListProcess pageListProcess;

    /**
     * 取消订单
     * @param orderId
     * @param role
     * @return
     */
    @FromPurchaser
    @FromUser
    public String cancelOrder(){
        Integer orderId = this.getIntParameter(request, "orderId", null);
        Integer role = this.getIntParameter(request, "role", null);
        boolean cancelOrder = this.orderProcess.cancelOrder(orderId, role);
        returnFastJSON(toMap("cancelFlag", cancelOrder));
        return null;
    }
    
    /**
     * 确认收货
     * @param orderId
     * @return
     */
    @FromUser
    public String confirm(){
    	Integer orderId = this.getIntParameter(request, "orderId", null);
    	boolean confirmOrder = this.orderProcess.confirm(orderId, "用户通过手机app确认收货");
    	returnFastJSON(toMap("confirmFlag", confirmOrder));
    	return null;
    }
    
    /**
     * 
    * @Description 订单详情
    * @param orderId 订单id
    * @return
     */
    @FromPurchaser
    @FromUser
    public String saleOrderDetail(){
        Integer orderId = this.getIntParameter(request, "orderId", null);
        SaleOrder saleOrder = this.pageIndexProcess.orderDetail(orderId);
        returnFastJSON(saleOrder);
        return null;
    }
    
	/**
	 * 根据订单状态或者退款状态查询订单列表
	 * @param role 0：买手   1：消费者  @OperatorRoleEnum
	 * @param rewardStatus 悬赏状态  @RewardStatus
	 * @param orderStatus 订单状态  @OrderStatus
	 * @param refundStatus 退款状态  @RefundStatus
	 * @param csStatus 客服状态  @CsStatus
	 * @param needAppraise 0表示待评价，1表示已评价  @IsDefaultEnum
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
    @FromPurchaser
    @FromUser
    public String userOrderList(){
		Integer role = this.getIntParameter(request, "role", null);
		Integer orderStatus = this.getIntParameter(request, "orderStatus", null);
		Integer refundStatus = this.getIntParameter(request, "refundStatus", null);
		Integer rewardStatus = this.getIntParameter(request, "rewardStatus", null);
		Integer csStatus = this.getIntParameter(request, "csStatus", null);
		Integer needAppraise = this.getIntParameter(request, "needAppraise", null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
    	returnFastJSON(this.pageListProcess.getSaleOrderList(role, rewardStatus, orderStatus, refundStatus, csStatus, needAppraise, pageOffset, pageSize));
    	return null;
    }
    
	/**
	 * 根据订单状态或者退款状态查询列表每个订单数
	 * @param role 0：买手   1：消费者  @OperatorRoleEnum
	 * @return  101待接单，102待确认，103待发货，104待收货，105已完成，500待评价，301退款售后
	 */
    @FromPurchaser
    @FromUser
    public String userOrderCount() {
		Integer role = this.getIntParameter(request, "role", null);
    	returnFastJSON(this.pageListProcess.getMyOrderCount(role));
    	return null;
    }
}