package com.haitao.apollo.service.user.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.user.UserRightsDao;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.user.UserRightsService;
import com.haitao.apollo.status.CsStatus;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.user.UserRightsVo;

@Service
public class UserRightsServiceImpl implements UserRightsService {
	
    @Resource(name = "userRightsDao")
    private UserRightsDao userRightsDao;
    @Autowired
    private SaleOrderService saleOrderService; 
    
	@Override
	@Transactional
	public Integer applyUserRights(Integer orderId, String reason, String picAddr1, String picAddr2, String picAddr3) {
		Assert.notNull(orderId, "订单id不能为空");
		Assert.notNull(reason, "申请售后的原因不能为空");
		Assert.notNull(picAddr1, "售后申请图片至少有一张");
		SaleOrder saleOrder = this.saleOrderService.getSaleOrderByOrderId(orderId);
		if(null == saleOrder) {
			throw new ApolloBizException(ResultCode.SALEORDER_NOT_EXIST, SessionContainer.getSession().getOperatorId(), String.format("销售订单不存在[orderId=%s]", orderId));
		}
		if(!OrderStatus.IN_SHIPMENTS.equals(saleOrder.getOrderStatus()) &&
				!OrderStatus.FINISHED_ORDER.equals(saleOrder.getOrderStatus())) {
			throw new ApolloBizException(ResultCode.SALEORDER_STATUS_ERROR, SessionContainer.getSession().getOperatorId(), String.format("销售订单状态不正确，只有已发货或者已完成订单才能申请售后操作[orderId=%s]", orderId));
		}
		Integer count = this.userRightsDao.getUserRightsCountByOrderId(orderId);
		if(count > 0) {
			throw new ApolloBizException(ResultCode.RIGHTS_ONLY_ONE, saleOrder.getUserId(), String.format("销售订单只能发起一次售后申请，[orderId=%s]", orderId));
		}
		UserRightsVo userRightsVo = new UserRightsVo(saleOrder.getUserId(), saleOrder.getPurchaserId(), orderId, reason, picAddr1, picAddr2, picAddr3, CsStatus.IN_CUSTOMER, DateUtil.currentUTCTime(), DateUtil.currentUTCTime());
		this.userRightsDao.insertUserRights(userRightsVo);
		if(userRightsVo.getId() == null) {
            throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId() , String.format("消费者申请售后失败，[userId=%s]", SessionContainer.getSession().getOperatorId()));
		}
		//挂起订单
		this.saleOrderService.suspendSaleOrder(orderId);
		return userRightsVo.getId();
	}

}
