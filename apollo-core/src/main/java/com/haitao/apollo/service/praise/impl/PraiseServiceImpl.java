package com.haitao.apollo.service.praise.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.PostRewardDao;
import com.haitao.apollo.dao.order.ShowOrderDao;
import com.haitao.apollo.dao.praise.PostrewardPraiseDao;
import com.haitao.apollo.dao.praise.ShowOrderPraiseDao;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.order.ShowOrder;
import com.haitao.apollo.pojo.praise.PostrewardPraise;
import com.haitao.apollo.pojo.praise.ShowOrderPraise;
import com.haitao.apollo.service.praise.PraiseService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.vo.praise.PostrewardPraiseVo;
import com.haitao.apollo.vo.praise.ShowOrderPraiseVo;

@Service
public class PraiseServiceImpl implements PraiseService {

    @Resource(name = "postrewardPraiseDao")
    private PostrewardPraiseDao postrewardPraiseDao;
    @Resource(name = "showOrderPraiseDao")
    private ShowOrderPraiseDao showOrderPraiseDao;
    @Resource(name = "postRewardDao")
    private PostRewardDao postRewardDao;
    @Resource(name = "showOrderDao")
    private ShowOrderDao showOrderDao;
    @Autowired
    protected MsgTemplate msgTemplate;

	@Override
	public Integer postrewardPraise(Integer postrewardId, Integer praiserId, Integer isRead) {
		Assert.notNull(postrewardId,"悬赏id不能为空");
		Assert.notNull(praiserId,"点赞人id不能为空");
		PostReward postreward = this.postRewardDao.getPostRewardById(postrewardId);
		if(null==postreward) {
			throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, SessionContainer.getSession().getOperatorId(), String.format("悬赏不存在，postrewardId=%s", postrewardId));
		}
		PostrewardPraiseVo postrewardPraiseVo = new PostrewardPraiseVo(postrewardId, praiserId, isRead);
		this.postrewardPraiseDao.insertPostrewardPraise(postrewardPraiseVo);
		if(null==postrewardPraiseVo.getId()){
			throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("悬赏点赞失败，postrewardId=%s", postrewardId));
		}
		//发推送
		this.msgTemplate.push(MsgTemplateEnum.POSTREWARD_PRAISE_USER, postreward.getUserId(), postreward.getPurchaserId(), null);
		return postrewardPraiseVo.getId();
	}

	@Override
	public Integer showOrderPraise(Integer showOrderId, Integer praiserId,
			Integer isRead) {
		Assert.notNull(showOrderId,"晒单id不能为空");
		Assert.notNull(praiserId,"点赞人id不能为空");
		ShowOrder showOrder = this.showOrderDao.getShowOrderById(showOrderId);
		if(null==showOrder) {
			throw new ApolloBizException(ResultCode.SHOWORDER_NOT_EXIST, SessionContainer.getSession().getOperatorId(), String.format("晒单不存在，showOrderId=%s", showOrderId));
		}		
		ShowOrderPraiseVo showOrderPraiseVo = new ShowOrderPraiseVo(showOrderId, praiserId, isRead);
		this.showOrderPraiseDao.insertShowOrderPraise(showOrderPraiseVo);
		if(null==showOrderPraiseVo.getId()){
			throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("晒单点赞失败，showOrderId=%s", showOrderId));
		}
		//发推送
		if(OperatorRoleEnum.ROLE_PURCHASER.getCode().equals(showOrder.getRole())) {
			this.msgTemplate.push(MsgTemplateEnum.SHOWORDER_PRAISE_PURCHASER, null, showOrder.getOperatorId(), null);
		}else{
			this.msgTemplate.push(MsgTemplateEnum.SHOWORDER_PRAISE_USER, showOrder.getOperatorId(), null, null);
		}
		return showOrderPraiseVo.getId();
	}

	@Override
	public List<PostrewardPraise> getPostrewardPraiseListByPostrewardId(Integer postrewardId, Integer pageOffset, Integer pageSize) {
		Assert.notNull(postrewardId,"悬赏id不能为空");
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "id");
		return this.postrewardPraiseDao.getPostrewardPraiseListByPostrewardId(postrewardId,page);
	}

	@Override
	public List<ShowOrderPraise> getShowOrderPraiseListByShowOrderId(Integer showOrderId, Integer pageOffset, Integer pageSize) {
		Assert.notNull(showOrderId,"晒单id不能为空");
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "id");
		return this.showOrderPraiseDao.getShowOrderPraiseListByShowOrderId(showOrderId,page);
	}

	@Override
	public Integer countShowOrderPraise(Integer showOrderId) {
		Assert.notNull(showOrderId,"晒单id不能为空");
		return this.showOrderPraiseDao.countShowOrderPraise(showOrderId);
	}
}
