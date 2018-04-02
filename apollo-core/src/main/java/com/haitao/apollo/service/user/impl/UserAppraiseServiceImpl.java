package com.haitao.apollo.service.user.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.user.UserAppraiseDao;
import com.haitao.apollo.enums.ShowSourceEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.user.UserAppraise;
import com.haitao.apollo.proccess.OrderProcess;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.user.UserAppraiseService;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.user.UserAppraiseVo;

@Service
public class UserAppraiseServiceImpl implements UserAppraiseService {
	
	@Resource(name = "userAppraiseDao")
	private UserAppraiseDao userAppraiseDao;
    @Autowired
    private PostRewardService postRewardService;
    @Autowired
    private SaleOrderService saleOrderService;
	@Autowired
    private OrderProcess orderProcess;

	@Override
	@Transactional
	public Integer appraise(Integer userId, Integer purchaserId, Integer isAnonym, Integer orderId, Integer score, String content) {
		Assert.notNull(userId,"用户id不能为空");
		Assert.notNull(purchaserId,"买手id不能为空");
		Assert.notNull(isAnonym,"是否匿名不能为空");
		Assert.notNull(orderId,"订单id不能为空");
		Assert.notNull(score,"买手的评分不能为空");
		Assert.notNull(content,"评价内容不能为空");
		if(score < SCORE_1 || score > SCORE_5) {
			throw new IllegalArgumentException(String.format("评分必须介于1-5分之间，userId=%s，purchaserId=%s, orderId=%s", userId, purchaserId, orderId));
		}
		UserAppraise userAppraise = this.userAppraiseDao.getUserAppraiseByOrderId(orderId);
		if(null != userAppraise) {
			throw new ApolloBizException(ResultCode.SALEORDER_BE_APPRAISE, userId, String.format("销售订单已经被评论了，userId=%s，purchaserId=%s, orderId=%s", userId, purchaserId, orderId));
		}
		SaleOrder saleOrder = this.saleOrderService.getSaleOrderByOrderId(orderId);
		if(null == saleOrder || !OrderStatus.FINISHED_ORDER.equals(saleOrder.getOrderStatus())) {
			throw new ApolloBizException(ResultCode.SALEORDER_STATUS_ERROR, userId, String.format("销售订单不存在或者订单状态为未完成，userId=%s，purchaserId=%s, orderId=%s", userId, purchaserId, orderId));
		}
		Long currentTime = DateUtil.currentUTCTime();
		UserAppraiseVo userAppraiseVo = new UserAppraiseVo(userId, purchaserId, isAnonym, orderId, score, content, currentTime, currentTime);
		this.userAppraiseDao.insertUserAppraise(userAppraiseVo);
		if(null == userAppraiseVo.getId()){
			throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("用户评价失败，userId=%s，purchaserId=%s，orderId=%s", userId, purchaserId, orderId));
		}
		//5分好评就默认晒单
		if(SCORE_5.equals(score)) {
			PostReward postreward = this.postRewardService.getPostRewardById(saleOrder.getPostrewardId());
			this.orderProcess.showOrder(orderId, ShowSourceEnum.USER_APPRAISE.getCode(), postreward.getRewardPrice(), postreward.getContent(), postreward.getPicAddr1(), postreward.getPicAddr2(), postreward.getPicAddr3(), postreward.getPicAddr4(), postreward.getPicAddr5(), postreward.getPicAddr6(), postreward.getPicAddr7(), postreward.getPicAddr8(), postreward.getPicAddr9(), postreward.getBrandId(), postreward.getCategoryId());
		}
		return userAppraiseVo.getId();
	}

}
