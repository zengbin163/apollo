/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月12日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.SaleOrderDao;
import com.haitao.apollo.dao.user.UserRightsDao;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.OrderCount;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.order.impl.order.AbstractCreateOrderStrategy;
import com.haitao.apollo.service.product.CategoryService;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.status.CsStatus;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.status.RefundStatus;
import com.haitao.apollo.status.RewardStatus;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.order.SaleOrderVo;
import com.haitao.apollo.vo.user.UserRightsVo;

/** 
* @ClassName: SaleOrderServiceImpl 
* @Description: 销售订单相关service
* @author zengbin
* @date 2015年11月12日 下午9:40:23 
*/
@Service
public class SaleOrderServiceImpl implements SaleOrderService {
    @Resource(name = "saleOrderDao")
    private SaleOrderDao saleOrderDao;
    @Resource(name = "userRightsDao")
    private UserRightsDao userRightsDao;
    @Resource(name = "createOrderStrategyMap")
    private Map<String,AbstractCreateOrderStrategy> createOrderStrategyMap;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostRewardService postRewardService;

    @Override
	public Integer createSaleOrder(Integer postrewardId, Integer purchaserId, Integer userId, Integer orderStatus, BigDecimal rewardPrice, Integer productNum) {
        Assert.notNull(rewardPrice,"悬赏价格不能为空");
        //校验各种策略
        for(Map.Entry<String, AbstractCreateOrderStrategy> createOrderMap : createOrderStrategyMap.entrySet()){
        	AbstractCreateOrderStrategy createOrderStrategy = createOrderMap.getValue();
        	createOrderStrategy.execute(postrewardId);
        }
        Long currentTime = DateUtil.currentUTCTime();
        SaleOrderVo saleOrderVo = new SaleOrderVo(postrewardId, userId, purchaserId,orderStatus, rewardPrice, productNum, currentTime, currentTime);
        this.saleOrderDao.insertSaleOrder(saleOrderVo);
        if(null == saleOrderVo.getId()) {
            throw new ApolloBizException(ResultCode.SAVE_FAIL, userId, String.format("创建销售订单失败，[postrewardId=%s]", postrewardId));
        }
        return saleOrderVo.getId();
    }
    
    public SaleOrder getSaleOrderByRewardId(Integer postrewardId) {
        Assert.notNull(postrewardId,"悬赏id不能为空");
        return this.saleOrderDao.getSaleOrderByRewardId(postrewardId);
    }
    
    public SaleOrder getSaleOrderByOrderId(Integer orderId){
        Assert.notNull(orderId,"订单id不能为空");
        return this.saleOrderDao.getSaleOrderByOrderId(orderId);
    }
    
    public SaleOrder getSaleOrderDetailByOrderId(Integer orderId) {
        Assert.notNull(orderId,"订单id不能为空");
        return this.saleOrderDao.getSaleOrderDetailByOrderId(orderId);
    }

    public Integer updateSaleOrder(Integer orderId,SaleOrderVo saleOrderVo){
        Assert.notNull(orderId,"订单id不能为空");
        Assert.notNull(saleOrderVo,"SaleOrderVo为空");
        saleOrderVo.setId(orderId);
        Integer flag = this.saleOrderDao.updateSaleOrder(saleOrderVo);
        if(flag<=0){
            throw new ApolloBizException(ResultCode.UPDATE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("更新订单失败，orderId = %s", orderId));
        }
        return flag;
    }
    
    public Integer agreeSaleOrder(Integer orderId) {
    	SaleOrderVo saleOrderVo = new SaleOrderVo();
    	saleOrderVo.setId(orderId);
    	saleOrderVo.setOrderStatus(OrderStatus.AGREE_TIME);
    	return this.updateSaleOrder(orderId, saleOrderVo);
    }
    
    public Integer shipmentSaleOrder(Integer orderId){
    	SaleOrderVo saleOrderVo = new SaleOrderVo();
    	saleOrderVo.setId(orderId);
    	saleOrderVo.setOrderStatus(OrderStatus.IN_SHIPMENTS);
    	saleOrderVo.setShipmentTime(DateUtil.currentUTCTime());
    	return this.updateSaleOrder(orderId, saleOrderVo);
    }
    
    public Integer finishSaleOrder(Integer orderId){
    	SaleOrderVo saleOrderVo = new SaleOrderVo();
    	saleOrderVo.setId(orderId);
    	saleOrderVo.setOrderStatus(OrderStatus.FINISHED_ORDER);
    	saleOrderVo.setConfirmTime(DateUtil.currentUTCTime());
    	return this.updateSaleOrder(orderId, saleOrderVo);
    }

    public Integer closeSaleOrder(Integer orderId){
        SaleOrderVo saleOrderVo = new SaleOrderVo();
        saleOrderVo.setId(orderId);
        saleOrderVo.setOrderStatus(OrderStatus.CLOSED_ORDER);
        return this.updateSaleOrder(orderId, saleOrderVo);
    }

    @Transactional
	public void suspendSaleOrder(Integer orderId){
        SaleOrderVo saleOrderVo = new SaleOrderVo();
        saleOrderVo.setId(orderId);
        saleOrderVo.setRefundStatus(RefundStatus.CREATE_REFUND);
        saleOrderVo.setCsStatus(CsStatus.IN_CUSTOMER);
        this.updateSaleOrder(orderId, saleOrderVo);
    }
    
    @Transactional
    public void cancelSuspendSaleOrder(Integer orderId) {
        SaleOrderVo saleOrderVo = new SaleOrderVo();
        saleOrderVo.setId(orderId);
        saleOrderVo.setRefundStatus(RefundStatus.CLOSED_REFUND);
        saleOrderVo.setCsStatus(CsStatus.CLOSE_CUSTOMER);
        this.updateSaleOrder(orderId, saleOrderVo);
        UserRightsVo userRightsVo = new UserRightsVo();
        userRightsVo.setOrderId(orderId);
        userRightsVo.setCsStatus(CsStatus.CLOSE_CUSTOMER);
        this.userRightsDao.updateUserRights(userRightsVo);
    }

    @Transactional
    public void endSaleOrder(Integer orderId) {
        SaleOrderVo saleOrderVo = new SaleOrderVo();
        saleOrderVo.setId(orderId);
        saleOrderVo.setOrderStatus(OrderStatus.CLOSED_ORDER);
        saleOrderVo.setRefundStatus(RefundStatus.FINISH_REFUND);
        saleOrderVo.setCsStatus(CsStatus.FINISH_CUSTOMER);
        this.updateSaleOrder(orderId, saleOrderVo);
        UserRightsVo userRightsVo = new UserRightsVo();
        userRightsVo.setOrderId(orderId);
        userRightsVo.setCsStatus(CsStatus.FINISH_CUSTOMER);
        this.userRightsDao.updateUserRights(userRightsVo);
    }

    
	public List<SaleOrder> getSaleOrderList(Integer userId,
			Integer purchaserId, Integer rewardStatus, Integer orderStatus,
			Integer refundStatus, Integer csStatus, Integer needAppraise,
			Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
		SaleOrderVo saleOrderVo = new SaleOrderVo();
		saleOrderVo.setUserId(userId);
		saleOrderVo.setPurchaserId(purchaserId);
		saleOrderVo.setRewardStatus(rewardStatus);
		saleOrderVo.setOrderStatus(orderStatus);
		saleOrderVo.setRefundStatus(refundStatus);
		saleOrderVo.setCsStatus(csStatus);
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy("id");
        List<SaleOrder> saleOrderList = null;
        if(RewardStatus.CREATE_REWARD.equals(rewardStatus)) {
        	//创建悬赏，但是没有创建订单，造假的SaleOrderList
        	List<PostReward> postrewardList = this.postRewardService.getPostRewardList(pageOffset, pageSize, rewardStatus, userId);
			if (!CollectionUtils.isEmpty(postrewardList)) {
				saleOrderList = new ArrayList<>();
				for(PostReward postReward : postrewardList) {
					SaleOrder saleOrder = new SaleOrder();
					saleOrder.setBrandId(postReward.getBrandId());
					saleOrder.setCategoryId(postReward.getCategoryId());
					saleOrder.setUserId(postReward.getUserId());
					saleOrder.setPicAddr1(postReward.getPicAddr1());
					saleOrder.setPicAddr2(postReward.getPicAddr2());
					saleOrder.setPicAddr3(postReward.getPicAddr3());
					saleOrder.setPicAddr4(postReward.getPicAddr4());
					saleOrder.setPicAddr5(postReward.getPicAddr5());
					saleOrder.setPicAddr6(postReward.getPicAddr6());
					saleOrder.setPicAddr7(postReward.getPicAddr7());
					saleOrder.setPicAddr8(postReward.getPicAddr8());
					saleOrder.setPicAddr9(postReward.getPicAddr9());
					saleOrder.setRewardPrice(postReward.getRewardPrice());
					saleOrder.setRewardStatus(postReward.getRewardStatus());
					saleOrder.setProductNum(postReward.getProductNum());
					saleOrder.setPostrewardId(postReward.getId());
					saleOrderList.add(saleOrder);
				}
			}
        }else{
        	saleOrderList = this.saleOrderDao.getSaleOrderList(saleOrderVo, page);
        }
        if(CollectionUtils.isEmpty(saleOrderList)) {
        	return saleOrderList;
        }
        List<SaleOrder> tempSaleOrderList = new ArrayList<SaleOrder>();
        for(SaleOrder saleOrder : saleOrderList) {
        	saleOrder.setBrandName(categoryService.getBrandById(saleOrder.getBrandId()).getBrandName());
        	saleOrder.setCategoryName(categoryService.getCategoryById(saleOrder.getCategoryId()).getCategoryName());
			User user = this.userService.getUserById(saleOrder.getUserId());
			saleOrder.setUserNickName(user.getNickName());
			saleOrder.setUserHeaderUrl(user.getHeaderUrl());
			saleOrder.setUserSignature(user.getSignature());
			tempSaleOrderList.add(saleOrder);
        }
        return tempSaleOrderList;
    }
	
	public List<SaleOrder> getSaleOrderListForCs(Integer orderId,
			Integer rewardStatus, Integer orderStatus, Integer refundStatus,
			Integer csStatus, Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
		SaleOrderVo saleOrderVo = new SaleOrderVo();
		saleOrderVo.setId(orderId);
		saleOrderVo.setRewardStatus(rewardStatus);
		saleOrderVo.setOrderStatus(orderStatus);
		saleOrderVo.setRefundStatus(refundStatus);
		saleOrderVo.setCsStatus(csStatus);
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy("id");
        return this.saleOrderDao.getSaleOrderList(saleOrderVo, page);
	}
	
	public List<OrderCount> getMyOrderCount(Integer userId, Integer purchaserId) {
		//获取101待接单，102待确认，103待发货，104待收货，105已完成
		List<OrderCount> orderCountList = this.saleOrderDao.getMyOrderCount(userId, purchaserId);
		//获取500待评价
		Integer appraiseCount = this.saleOrderDao.getMyAppraiseOrderCount(userId, purchaserId);
		//获取301退款售后
		Integer refundCount = this.saleOrderDao.getMyRefundOrderCount(userId, purchaserId);
		orderCountList.add(new OrderCount(500, appraiseCount));
		orderCountList.add(new OrderCount(301, refundCount));
		return orderCountList;
	}

    public List<SaleOrder> getAppraiseTimeoutSaleOrderList(Long timestamp, Integer pageOffset, Integer pageSize) {
    	Assert.notNull(timestamp,"时间戳不能为空");
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy("id");
		return this.saleOrderDao.getAppraiseTimeoutSaleOrderList(DateUtil.currentUTCTime(), timestamp, page);
    }
	
    public Integer countAppraiseTimeoutSaleOrderList(Long timestamp) {
    	return this.saleOrderDao.countAppraiseTimeoutSaleOrderList(DateUtil.currentUTCTime(), timestamp);
    }

}
