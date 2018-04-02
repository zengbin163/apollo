package com.haitao.apollo.service.order.impl.show;

import java.util.List;

import org.springframework.stereotype.Component;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.SaleOrderDao;
import com.haitao.apollo.dao.order.ShowOrderDao;
import com.haitao.apollo.enums.ShowSourceEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.order.ShowOrder;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.util.SpringContextUtil;

@Component
public class UserShowStrategy extends AbstractShowStrategy {

	@Override
	public void execute(Integer orderId, Integer role) {
		if(!ShowSourceEnum.USER_APPRAISE.getCode().equals(role)){
			return;
		}
		SaleOrderDao saleOrderDao = SpringContextUtil.getBean("saleOrderDao");
    	SaleOrder saleOrder = saleOrderDao.getSaleOrderByOrderId(orderId);
    	if(null==saleOrder){
    		throw new ApolloBizException(ResultCode.SALEORDER_NOT_EXIST, getUserId(), String.format("销售订单不存在,orderId=%s", orderId));
    	}
    	if(!OrderStatus.FINISHED_ORDER.equals(saleOrder.getOrderStatus())){
    		throw new ApolloBizException(ResultCode.SALEORDER_STATUS_ERROR, getUserId(), String.format("只有已完成的订单才能发布晒单,orderId=%s", orderId));
    	}
    	ShowOrderDao showOrderDao = SpringContextUtil.getBean("showOrderDao");
    	List<ShowOrder> showOrderList = showOrderDao.getshowOrderByOrderId(orderId);
    	if(null!=showOrderList && 0!=showOrderList.size()){
    		throw new ApolloBizException(ResultCode.DATA_IS_ALREADY_EXISTS, getUserId(), String.format("晒单只能发布一次,orderId=%s", orderId));
    	}
	}

}
