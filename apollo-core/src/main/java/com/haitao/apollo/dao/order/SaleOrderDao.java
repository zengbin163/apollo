/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月12日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.OrderCount;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.vo.order.SaleOrderVo;

/** 
* @ClassName: SaleOrderDao 
* @Description: 销售订单DAO
* @author zengbin
* @date 2015年11月12日 上午9:42:29 
*/
@MapperScan
public interface SaleOrderDao {
    void insertSaleOrder(SaleOrderVo saleOrderVo);
    SaleOrder getSaleOrderByRewardId(Integer postrewardId);
    SaleOrder getSaleOrderByOrderId(Integer id);
    /**
     * 订单详情
     * @param id 订单id
     * @return
     */
    SaleOrder getSaleOrderDetailByOrderId(Integer id);
    Integer updateSaleOrder(@Param(value = "saleOrderVo") SaleOrderVo saleOrderVo);
    List<SaleOrder> getSaleOrderList(@Param(value = "saleOrderVo") SaleOrderVo saleOrderVo, @Param(value = "page") Page<?> page);
    List<OrderCount> getMyOrderCount(@Param(value = "userId") Integer userId, @Param(value = "purchaserId") Integer purchaserId);
    Integer getMyAppraiseOrderCount(@Param(value = "userId") Integer userId, @Param(value = "purchaserId") Integer purchaserId);
    Integer getMyRefundOrderCount(@Param(value = "userId") Integer userId, @Param(value = "purchaserId") Integer purchaserId);
    List<SaleOrder> getAppraiseTimeoutSaleOrderList(
			@Param(value = "currentTime") Long currentTime,
			@Param(value = "timestamp") Long timestamp,
			@Param(value = "page") Page<?> page);
	Integer countAppraiseTimeoutSaleOrderList(
			@Param(value = "currentTime") Long currentTime,
			@Param(value = "timestamp") Long timestamp);
}