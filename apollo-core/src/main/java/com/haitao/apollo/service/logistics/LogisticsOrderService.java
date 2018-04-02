/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月12日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.logistics;

import java.util.List;

import com.haitao.apollo.logistics.Track;
import com.haitao.apollo.pojo.order.LogisticsOrder;

/** 
* @ClassName: LogisticsOrderService 
* @Description: 物流订单相关的service
* @author zengbin
* @date 2015年11月19日 上午10:15:16 
*/
public interface LogisticsOrderService {
    List<Track> getLogisticsTrackList(String trackingNo);
    List<LogisticsOrder> getConfirmTimeoutLogisticsOrderList(Long timestamp, Integer pageOffset, Integer pageSize);
    Integer countConfirmTimeoutLogisticsOrderList(Long timestamp);
    List<LogisticsOrder> getLogisticsAddressByOrderId(Integer orderId);
    void shipment(Integer orderId, Integer receiverId, String logisticsCompany, String trackingNo);
}