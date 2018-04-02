/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月12日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order;

import java.math.BigDecimal;
import java.util.List;

import com.haitao.apollo.pojo.order.ShowOrder;

/**
 * @ClassName: ShowOrderService
 * @Description: 晒单相关的service
 * @author zengbin
 * @date 2015年11月22日 上午10:43:16
 */
public interface ShowOrderService {
	Integer createShowOrder(Integer orderId, Integer role,
			BigDecimal showPrice, String content, String picAddr1,
			String picAddr2, String picAddr3, String picAddr4, String picAddr5,
			String picAddr6, String picAddr7, String picAddr8, String picAddr9,
			Integer brandId, Integer categoryId);
	ShowOrder getShowOrderByShowOrderId(Integer id);
	List<ShowOrder> getshowOrderByOrderId(Integer orderId);

	/**
	 * 消费者端晒单池
	 * @param role  0:买手主动发布晒单，1:消费者好评默认晒单
	 * @param operatorId 如果为空表示全部晒单信息
	 * @param categoryId
	 * @return
	 */
	List<ShowOrder> getShowOrderPoolListByOperator(Integer role, Integer operatorId, Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize);
}
