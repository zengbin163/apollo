/*
 *@Project: GZJK
 *@Author: zengbin
 *@Date: 2015年11月19日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.logistics.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.dao.order.LogisticsOrderDao;
import com.haitao.apollo.logistics.LogisticsTrack;
import com.haitao.apollo.logistics.Track;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.LogisticsOrder;
import com.haitao.apollo.service.logistics.LogisticsOrderService;
import com.haitao.apollo.service.logistics.impl.ship.AbstractLogisticsShipStrategy;
import com.haitao.apollo.util.DateUtil;

/**
 * @ClassName: LogisticsOrderServiceImpl
 * @Description: 物流订单相关的service
 * @author zengbin
 * @date 2015年11月19日 上午10:52:25
 */
@Service
public class LogisticsOrderServiceImpl implements LogisticsOrderService {

	@Resource(name = "logisticsOrderDao")
	private LogisticsOrderDao logisticsOrderDao;
	@Autowired
	private LogisticsTrack logisticsTrack;
    @Resource(name = "logisticsOrderShipStrategyMap")
    private Map<String,AbstractLogisticsShipStrategy> logisticsOrderShipStrategyMap;

	public List<Track> getLogisticsTrackList(String trackingNo) {
		Assert.notNull(trackingNo, "物流单号不能为空");
		return this.logisticsTrack.getTrackList(trackingNo);
	}

	public List<LogisticsOrder> getConfirmTimeoutLogisticsOrderList(
			Long timestamp, Integer pageOffset, Integer pageSize) {
		Assert.notNull(timestamp, "确认收货的超时时间戳不能为空");
		Page<?> page = new Page<>();
		page.setPageNo(pageOffset);
		page.setPageSize(pageSize);
		page.setOrder(Page.ASC);
		page.setOrderBy("t2.id");
		return this.logisticsOrderDao.getConfirmTimeoutLogisticsOrderList(
				DateUtil.currentUTCTime(), timestamp, page);
	}

	public Integer countConfirmTimeoutLogisticsOrderList(Long timestamp) {
		Assert.notNull(timestamp, "确认收货的超时时间戳不能为空");
		return this.logisticsOrderDao.countConfirmTimeoutLogisticsOrderList(
				DateUtil.currentUTCTime(), timestamp);
	}
	
	public List<LogisticsOrder> getLogisticsAddressByOrderId(Integer orderId) {
		Assert.notNull(orderId, "订单id不能为空");
		return this.logisticsOrderDao.getLogisticsAddressByOrderId(orderId);
	}
	
	public void shipment(Integer orderId, Integer receiverId, String logisticsCompany, String trackingNo) {
        //校验各种策略
        for(Map.Entry<String, AbstractLogisticsShipStrategy> logisticsShipMap : logisticsOrderShipStrategyMap.entrySet()){
        	AbstractLogisticsShipStrategy logisticsShipStrategy = logisticsShipMap.getValue();
        	logisticsShipStrategy.execute(orderId, receiverId, logisticsCompany, trackingNo);
        }
	}
}
