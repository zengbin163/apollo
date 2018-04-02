package com.haitao.apollo.service.order.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.ShowOrderDao;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.enums.ShowSourceEnum;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.order.ShowOrder;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.order.ShowOrderService;
import com.haitao.apollo.service.order.impl.show.AbstractShowStrategy;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.order.ShowOrderVo;

@Service
public class ShowOrderServiceImpl implements ShowOrderService {
	
    @Resource(name = "showOrderDao")
    private ShowOrderDao showOrderDao;
    @Resource(name = "showOrderStrategyMap")
    private Map<String,AbstractShowStrategy> showOrderStrategyMap;
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    private UserService userService;
    
	@Override
	public Integer createShowOrder(Integer orderId, Integer role,
			BigDecimal showPrice, String content, String picAddr1,
			String picAddr2, String picAddr3, String picAddr4, String picAddr5,
			String picAddr6, String picAddr7, String picAddr8, String picAddr9,
			Integer brandId, Integer categoryId) {
		Assert.notNull(orderId,"订单id不能为空");
		Assert.notNull(role,"操作人角色不能为空");
		Assert.notNull(showPrice,"晒单价不能为空");
		Assert.notNull(content,"晒单内容不能为空");
        Assert.notNull(picAddr1,"第一张图必须存在");
        Assert.notNull(picAddr2,"第二张图必须存在");
        Assert.notNull(brandId,"品牌id不能为空");
        Assert.notNull(categoryId,"类目id不能为空");
    	//买手晒单，消费者主动好评晒单，定时任务好评晒单
        Integer operatorId = null;
        if(ShowSourceEnum.USER_APPRAISE.getCode().equals(role)) {
        	SaleOrder saleOrder = this.saleOrderService.getSaleOrderByOrderId(orderId);
        	if(null==saleOrder){
        		throw new ApolloBizException(ResultCode.SALEORDER_NOT_EXIST, -10000, String.format("销售订单不存在,orderId=%s", orderId));
        	}
        	operatorId = saleOrder.getUserId();
        	User user = this.userService.getUserById(operatorId);
            if(IsDefaultEnum.DEFAULT_YES.getCode().equals(user.getIsForbidShow())) {
                throw new ApolloBizException(ResultCode.FORBID_SHOW , operatorId , ResultCode.FORBID_SHOW.getString());
            }
        } else {
        	operatorId = SessionContainer.getSession().getOperatorId();
        }
        //校验各种策略
        for(Map.Entry<String, AbstractShowStrategy> showOrderMap : showOrderStrategyMap.entrySet()){
        	AbstractShowStrategy showOrderStrategy = showOrderMap.getValue();
        	showOrderStrategy.execute(orderId, role);
        }
        Long currentTime = DateUtil.currentUTCTime();
		ShowOrderVo showOrderVo = new ShowOrderVo(orderId, role,
				operatorId, showPrice, content, picAddr1, picAddr2, picAddr3,
				picAddr4, picAddr5, picAddr6, picAddr7, picAddr8, picAddr9,
				brandId, categoryId, currentTime, currentTime);
		this.showOrderDao.insertShowOrder(showOrderVo);
		if(null==showOrderVo.getId()){
			throw new ApolloBizException(ResultCode.SAVE_FAIL, operatorId, String.format("新增晒单失败，orderId=%s", orderId));
		}
		return showOrderVo.getId();
	}

	@Override
	public ShowOrder getShowOrderByShowOrderId(Integer id) {
		Assert.notNull(id);
		return this.showOrderDao.getShowOrderById(id);
	}

	@Override
	public List<ShowOrder> getshowOrderByOrderId(Integer orderId) {
		Assert.notNull(orderId);
		return this.showOrderDao.getshowOrderByOrderId(orderId);
	}

	public List<ShowOrder> getShowOrderPoolListByOperator(Integer role, Integer operatorId, Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize) {
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
		ShowOrderVo showOrderVo = new ShowOrderVo();
		showOrderVo.setRole(role);
		showOrderVo.setOperatorId(operatorId);
		showOrderVo.setCategoryId(categoryId);
		showOrderVo.setBrandId(brandId);
    	Page<?> page = new Page<>();
    	page.setPageNo(pageOffset);
    	page.setPageSize(pageSize);
    	page.setOrder(Page.DESC);
    	page.setOrderBy(Page.ORDER_BY_MODIFY_TIME);
		return this.showOrderDao.getShowOrderPoolListByOperator(showOrderVo, page);
	}
}
