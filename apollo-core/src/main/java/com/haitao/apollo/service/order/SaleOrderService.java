/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月12日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order;

import java.math.BigDecimal;
import java.util.List;

import com.haitao.apollo.pojo.order.OrderCount;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.vo.order.SaleOrderVo;

/** 
* @ClassName: SaleOrderService 
* @Description: 销售订单相关的service
* @author zengbin
* @date 2015年11月12日 上午10:43:16 
*/
public interface SaleOrderService {
	Integer createSaleOrder(Integer postrewardId, Integer purchaserId, Integer userId, Integer orderStatus, BigDecimal rewardPrice, Integer productNum);
    SaleOrder getSaleOrderByRewardId(Integer postrewardId);
    SaleOrder getSaleOrderByOrderId(Integer orderId);
    SaleOrder getSaleOrderDetailByOrderId(Integer orderId);
    Integer updateSaleOrder(Integer orderId,SaleOrderVo saleOrderVo);
    /**
     * 消费者同意发货时间
     * @param orderId
     * @return
     */
    Integer agreeSaleOrder(Integer orderId);
    /**
     * 买手发货
     * @param orderId
     * @return
     */
    Integer shipmentSaleOrder(Integer orderId);
    /**
     * 完成订单
     * @param orderId
     * @return
     */
    Integer finishSaleOrder(Integer orderId);
    /**
     * 取消订单
     * @param orderId
     * @return
     */
    Integer closeSaleOrder(Integer orderId);
    /**
     * 挂起订单（客服操作）
     * @param orderId
     * @return
     */
    void suspendSaleOrder(Integer orderId);
    /**
     * 还原订单（取消挂起，客服操作）
     * @param orderId
     * @return
     */
    void cancelSuspendSaleOrder(Integer orderId);
    /**
     * 结束订单（关闭订单走买卖双方线下处理，客服操作）
     * @param orderId
     * @return
     */
    void endSaleOrder(Integer orderId);
	/**
	 * 消费者或者买手端我的订单列表
	 * @param userId
	 * @param purchaserId
	 * @param rewardStatus
	 * @param orderStatus
	 * @param refundStatus
	 * @param csStatus
	 * @param needAppraise
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
    List<SaleOrder> getSaleOrderList(Integer userId, Integer purchaserId,
			Integer rewardStatus, Integer orderStatus, Integer refundStatus,
			Integer csStatus, Integer needAppraise, Integer pageOffset,
			Integer pageSize);
    /**
     * 我的订单列表上面每个状态的订单数
     * @param userId
     * @param purchaserId
     * @return   101待接单，102待确认，103待发货，104待收货，105已完成，500待评价，301退款售后
     */
    List<OrderCount> getMyOrderCount(Integer userId, Integer purchaserId);
    /**
     * 查询48小时内未做评价的订单列表
     * @param currentTime
     * @param timestamp
     * @param pageOffset
     * @param pageSize
     * @return
     */
    List<SaleOrder> getAppraiseTimeoutSaleOrderList(Long timestamp, Integer pageOffset, Integer pageSize);
	Integer countAppraiseTimeoutSaleOrderList(Long timestamp);
	/**
	 * 查询订单列表，给客服系统使用
	 * @param orderId
	 * @param rewardStatus
	 * @param orderStatus
	 * @param refundStatus
	 * @param csStatus
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	List<SaleOrder> getSaleOrderListForCs(Integer orderId,
			Integer rewardStatus, Integer orderStatus, Integer refundStatus,
			Integer csStatus, Integer pageOffset, Integer pageSize);
}
