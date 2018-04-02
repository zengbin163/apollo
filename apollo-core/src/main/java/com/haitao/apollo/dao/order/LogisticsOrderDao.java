/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月18日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.LogisticsOrder;
import com.haitao.apollo.vo.order.LogisticsOrderVo;

/** 
* @ClassName: LogisticsOrderDao 
* @Description: 物流订单DAO
* @author zengbin
* @date 2015年11月18日 下午9:15:23 
*/
public interface LogisticsOrderDao {
    Integer insertLogisticsOrder(LogisticsOrderVo logisticsOrderVo);
    List<LogisticsOrder> getConfirmTimeoutLogisticsOrderList(@Param(value = "currentTime") Long currentTime, @Param(value = "timestamp") Long timestamp, @Param(value = "page") Page<?> page);
    Integer countConfirmTimeoutLogisticsOrderList(@Param(value = "currentTime") Long currentTime, @Param(value = "timestamp") Long timestamp);
    List<LogisticsOrder> getLogisticsAddressByOrderId(Integer orderId);
}

