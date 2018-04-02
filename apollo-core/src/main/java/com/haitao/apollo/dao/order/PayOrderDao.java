/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月12日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.PayOrder;
import com.haitao.apollo.vo.order.PayOrderVo;

/** 
* @ClassName: PayOrderDao 
* @Description: 支付订单DAO
* @author zengbin
* @date 2015年11月17日 下午15:12:29 
*/
public interface PayOrderDao {
    void insertPayOrder(PayOrderVo payOrderVo);
    Integer discardPayOrderById(Integer id);
    PayOrder getPayOrderBySerialNo(String paySerialNo);
    List<PayOrder> getPayOrderByPostrewardId(Integer postrewardId);
    /**
     * 我的账单
     * @param userId 用户id
     * @param month 月份
     * @param page
     * @return
     */
    List<PayOrder> getMyPayOrderListByUserId(@Param(value = "userId") Integer userId, @Param(value = "month") Integer month, @Param(value = "page") Page<?> page);
}