/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月12日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.PostRewardDao;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.enums.IsPublicEnum;
import com.haitao.apollo.enums.MessageBoxTypeEnum;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.ReleaseSourceEnum;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.LoginOperator;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.user.InviteCode;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.order.impl.accept.AbstractPurchaserAcceptStrategy;
import com.haitao.apollo.service.order.impl.release.AbstractPurchaserReleaseStrategy;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.service.user.InviteCodeService;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.status.RewardStatus;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.order.PostRewardVo;
import com.haitao.apollo.vo.order.SaleOrderVo;

/** 
* @ClassName: PostRewardServiceImpl 
* @Description: 悬赏相关service
* @author zengbin
* @date 2015年11月12日 上午11:31:31 
*/
@Service
public class PostRewardServiceImpl implements PostRewardService {
    
    @Autowired
    private InviteCodeService inviteCodeService;
    @Resource(name = "postRewardDao")
    private PostRewardDao postRewardDao;
    @Resource(name = "purchaserAcceptStrategyMap")
    private Map<String,AbstractPurchaserAcceptStrategy> purchaserAcceptStrategyMap;
    @Resource(name = "purchaserReleaseStrategyMap")
    private Map<String,AbstractPurchaserReleaseStrategy> purchaserReleaseStrategyMap;
    @Autowired
    private PurchaserService purchaserService;
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    protected MsgTemplate msgTemplate;
    @Autowired
    private AsyncService asyncService;

    @Override
    public Integer postReward(BigDecimal rewardPrice, Integer productNum,
			String content, Integer receiverId, Integer rewardStatus,
			String picAddr1, String picAddr2, String picAddr3, String picAddr4,
			String picAddr5, String picAddr6, String picAddr7, String picAddr8,
			String picAddr9, Integer brandId, Integer categoryId,
			Integer source, Integer sourceId, Integer requireDay) {
        Assert.notNull(rewardPrice,"悬赏价格不能为空");
        Assert.notNull(productNum,"产品数量不能为空");
        Assert.notNull(content,"悬赏内容不能为空");
        Assert.notNull(receiverId,"收货人id不能为空");
        Assert.notNull(rewardStatus,"悬赏状态不能为空");
        Assert.notNull(picAddr1,"第一张图必须存在");
        Assert.notNull(picAddr2,"第二张图必须存在");
        Assert.notNull(brandId,"品牌id不能为空");
        Assert.notNull(categoryId,"类目id不能为空");
        Assert.notNull(source,"发起悬赏来源不能为空");
        Assert.notNull(sourceId,"来源id不能为空");
        Assert.notNull(requireDay,"消费者要求发货时间不能为空");
        LoginOperator loginOperator = SessionContainer.getSession().getLoginOperator();
        if(IsDefaultEnum.DEFAULT_YES.getCode().equals(loginOperator.getIsForbidPost())) {
            throw new ApolloBizException(ResultCode.FORBID_POST , loginOperator.getId() , ResultCode.FORBID_POST.getString());
        }
        /**
         *  优先展现永远都是推荐用户进来的买手
         */
        InviteCode inviteCode = inviteCodeService.getInviteCode(loginOperator.getInviteCode());
        if (null == inviteCode || null == inviteCode.getInviteId()) {
            throw new ApolloBizException(ResultCode.INVITE_CODE_ERROR, loginOperator.getId(), "发布悬赏的用户邀请码不存在，或者邀请买手id为空");
        }
        Integer priorPurchaserId = inviteCode.getInviteId();
        Long currentTime = DateUtil.currentUTCTime();
		PostRewardVo postRewardVo = new PostRewardVo(loginOperator.getId(),
				priorPurchaserId, IsPublicEnum.POOL_PURCHASER.getCode(),
				rewardPrice, productNum, content, receiverId, rewardStatus,
				picAddr1, picAddr2, picAddr3, picAddr4, picAddr5, picAddr6,
				picAddr7, picAddr8, picAddr9, brandId, categoryId, source,
				sourceId, currentTime, currentTime, requireDay);
         this.postRewardDao.insertPostReward(postRewardVo);
         if(null==postRewardVo.getId()){
             throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId() , String.format("发布悬赏失败，[userId=%s]", loginOperator.getId()));
         }
         return postRewardVo.getId();
    }
    
    public Integer updatePostReward(Integer rewardId, PostRewardVo postRewardVo) {
		Assert.notNull(rewardId, "悬赏id不能为空");
        postRewardVo.setId(rewardId);
        Integer flag =  this.postRewardDao.updatePostRewardById(postRewardVo);
        if(null==flag || flag<=0){
            throw new ApolloBizException(ResultCode.UPDATE_FAIL, SessionContainer.getSession().getOperatorId() , String.format("更新失败，postrewardId=%s" ,rewardId));
        }
        return flag;
    }
    
    public Integer accept(Integer purchaserId , Integer postrewardId , Long acceptTime, Integer purchaserDay){
        Assert.notNull(purchaserDay,"发货时长不能为空");
        Assert.notNull(acceptTime,"接单时间不能为空");
        //校验各种策略
        for(Map.Entry<String, AbstractPurchaserAcceptStrategy> purchaserAcceptMap : purchaserAcceptStrategyMap.entrySet()){
        	AbstractPurchaserAcceptStrategy purchaserAccepStrategy = purchaserAcceptMap.getValue();
        	purchaserAccepStrategy.execute(purchaserId, postrewardId);
        }
        //买手接单
        PostRewardVo postRewardVo = new PostRewardVo();
        postRewardVo.setRewardStatus(RewardStatus.PURCHASER_ACCEPT);
        postRewardVo.setPurchaserId(purchaserId);
        postRewardVo.setAcceptTime(acceptTime);
        postRewardVo.setPurchaserDay(purchaserDay);
        this.updatePostReward(postrewardId, postRewardVo);
        PostReward postreward = this.getPostRewardById(postrewardId);
        //减少买手接单额度
        this.purchaserService.reducePurchaserQuota(purchaserId, postreward.getRewardPrice().multiply(new BigDecimal(postreward.getProductNum())));
        //是否创建订单
        Integer orderId = null;
        SaleOrder saleOrder = this.saleOrderService.getSaleOrderByRewardId(postrewardId);
        if(null == saleOrder) {
    		//创建销售订单
            orderId = this.saleOrderService.createSaleOrder(postrewardId, purchaserId, postreward.getUserId(), OrderStatus.CREATE_ORDER, postreward.getRewardPrice(), postreward.getProductNum());
        }else {
        	//更新之前接单买手id为当前接单买手id
        	SaleOrderVo saleOrderVo = new SaleOrderVo();
        	saleOrderVo.setPurchaserId(purchaserId);
        	this.saleOrderService.updateSaleOrder(saleOrder.getId(), saleOrderVo);
        	orderId = saleOrder.getId();
        }
		//发推送
		this.msgTemplate.push(MsgTemplateEnum.POSTREWARD_ACCEPT, postreward.getUserId(), postreward.getPurchaserId(), null);
		//消息盒子
		this.asyncService.messageBox(MsgTemplateEnum.POSTREWARD_ACCEPT, MessageBoxTypeEnum.MSG_ORDER, postreward.getUserId(), postreward.getPurchaserId(), null); 
		return orderId;
    }
    
    public void release(Integer postrewardId , Integer source , Long releaseTime){
    	Assert.notNull(postrewardId,"悬赏id不能为空");
        Assert.notNull(releaseTime,"释放时间不能为空");
        //校验各种策略
        for(Map.Entry<String, AbstractPurchaserReleaseStrategy> purchaserReleaseMap : purchaserReleaseStrategyMap.entrySet()){
        	AbstractPurchaserReleaseStrategy purchaserReleaseStrategy = purchaserReleaseMap.getValue();
        	purchaserReleaseStrategy.execute(postrewardId);
        }
        PostRewardVo postRewardVo = new PostRewardVo();
        postRewardVo.setIsPublic(IsPublicEnum.POOL_PUBLIC.getCode());
        postRewardVo.setRewardStatus(RewardStatus.CREATE_REWARD);
        postRewardVo.setReleaseTime(releaseTime);
        this.updatePostReward(postrewardId, postRewardVo);
        //如果消费者拒绝买手发货时间并释放到公共池或者消费者24小时未同意发货时间超时，需要释放占用当前抢单买手的额度
        if(ReleaseSourceEnum.USER_REFUSE.getCode().equals(source) || ReleaseSourceEnum.USER_AGREE_TIMEOUT.getCode().equals(source)) {
            PostReward postreward = this.getPostRewardById(postrewardId);
            this.purchaserService.freePurchaserQuota(postreward.getPurchaserId(), postreward.getRewardPrice().multiply(new BigDecimal(postreward.getProductNum())));
        }
    }
    
    public Integer agreePostReward(Integer rewardId) {
        PostRewardVo postRewardVo = new PostRewardVo();
        postRewardVo.setId(rewardId);
        postRewardVo.setRewardStatus(RewardStatus.AGREE_TIME);
        postRewardVo.setFinalTime(DateUtil.currentUTCTime());
		return this.updatePostReward(rewardId, postRewardVo);
    }
    
    public Integer shipmentSaleOrder(Integer rewardId) {
        PostRewardVo postRewardVo = new PostRewardVo();
        postRewardVo.setId(rewardId);
        postRewardVo.setRewardStatus(RewardStatus.IN_SHIPMENTS);
        return this.updatePostReward(rewardId, postRewardVo);
    }
    
    public Integer finishPostReward(Integer rewardId){
    	PostRewardVo postRewardVo = new PostRewardVo();
    	postRewardVo.setId(rewardId);
    	postRewardVo.setRewardStatus(RewardStatus.FINISH_REWARD);
    	return this.updatePostReward(rewardId, postRewardVo);
    }
    
    public Integer closePostReward(Integer rewardId){
        PostRewardVo postRewardVo = new PostRewardVo();
        postRewardVo.setId(rewardId);
        postRewardVo.setRewardStatus(RewardStatus.CLOSE_REWARD);
        postRewardVo.setIsPublic(IsPublicEnum.POOL_GARBAGE.getCode());
        return this.updatePostReward(rewardId, postRewardVo);
    }
    
    public PostReward getPostRewardById(Integer postrewardId){
        Assert.notNull(postrewardId, "悬赏id不能为空");
        return this.postRewardDao.getPostRewardById(postrewardId);
    }
    
    public List<PostReward> getPostRewardPoolListByUser(Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize){
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
    	PostRewardVo postrewardVo = new PostRewardVo();
    	postrewardVo.setBrandId(brandId);
    	postrewardVo.setCategoryId(categoryId);
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy(Page.ORDER_BY_MODIFY_TIME);
		return this.postRewardDao.getPostRewardPoolListByUser(postrewardVo, page);
    }
    
    public List<PostReward> getPostRewardSquareListByPurchaser(Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize){
    	Assert.notNull(pageOffset,"分页起始页码不能为空");
    	Assert.notNull(pageSize,"分页每页的总数不能为空");
    	PostRewardVo postrewardVo = new PostRewardVo();
    	postrewardVo.setBrandId(brandId);
    	postrewardVo.setCategoryId(categoryId);
    	Page<?> page = new Page<>();
    	page.setPageNo(pageOffset);
    	page.setPageSize(pageSize);
    	page.setOrder(Page.DESC);
    	page.setOrderBy(Page.ORDER_BY_MODIFY_TIME);
    	return this.postRewardDao.getPostRewardSquareListByPurchaser(postrewardVo, page);
    }
    
    public List<PostReward> getPostRewardListByPurchaser(Integer priorPurchaserId, Integer rewardStatus, Integer isPublic, Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize){
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
		Assert.notNull(priorPurchaserId,"优先买手id不能为空");
		Assert.notNull(rewardStatus,"悬赏状态不能为空");
    	PostRewardVo postrewardVo = new PostRewardVo();
    	postrewardVo.setPriorPurchaserId(priorPurchaserId);
    	postrewardVo.setRewardStatus(rewardStatus);
    	postrewardVo.setIsPublic(isPublic);
    	postrewardVo.setBrandId(brandId);
    	postrewardVo.setCategoryId(categoryId);
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy(Page.ORDER_BY_MODIFY_TIME);
		return this.postRewardDao.getPostRewardListByPurchaser(postrewardVo, page);
    }
    
    public List<PostReward> getAcceptTimeoutPostRewardList(Integer isPurchaser, Long timestamp, Integer pageOffset, Integer pageSize){
    	Assert.notNull(isPurchaser, "买手优先还是公共池抢");
    	Assert.notNull(timestamp, "买手接单超时时间戳不能为空");
    	Assert.notNull(pageOffset, "pageOffset不能为空");
    	Assert.notNull(pageSize, "pageSize不能为空");
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.ASC);
        page.setOrderBy("id");
    	return this.postRewardDao.getAcceptTimeoutPostRewardList(isPurchaser, DateUtil.currentUTCTime(), timestamp, page);
    }

    public Integer countAcceptTimeoutPostRewardList(Integer isPurchaser, Long timestamp){
    	Assert.notNull(isPurchaser, "买手优先还是公共池抢");
    	Assert.notNull(timestamp, "买手接单超时时间戳不能为空");
    	return this.postRewardDao.countAcceptTimeoutPostRewardList(isPurchaser, DateUtil.currentUTCTime(), timestamp);
    }
    
    public List<PostReward> getAgreeShipmentTimeoutPostRewardList(Long timestamp, Integer pageOffset, Integer pageSize) {
    	Assert.notNull(timestamp, "消费者同意发货时间戳不能为空");
    	Assert.notNull(pageOffset, "pageOffset不能为空");
    	Assert.notNull(pageSize, "pageSize不能为空");
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.ASC);
        page.setOrderBy("id");
    	return this.postRewardDao.getAgreeShipmentTimeoutPostRewardList(DateUtil.currentUTCTime(), timestamp, page);
    }
    
    public Integer countAgreeShipmentTimeoutPostRewardList(Long timestamp) {
    	Assert.notNull(timestamp, "消费者同意发货时间戳不能为空");
    	return this.postRewardDao.countAgreeShipmentTimeoutPostRewardList(DateUtil.currentUTCTime(), timestamp);
    }
    
    public List<PostReward> getShipmentTimeoutPostRewardList(Integer pageOffset, Integer pageSize) {
    	Assert.notNull(pageOffset, "pageOffset不能为空");
    	Assert.notNull(pageSize, "pageSize不能为空");
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.ASC);
        page.setOrderBy("id");
    	return this.postRewardDao.getShipmentTimeoutPostRewardList(DateUtil.currentUTCTime(), page);
    }
    
    public Integer countShipmentTimeoutPostRewardList() {
    	return this.postRewardDao.countShipmentTimeoutPostRewardList(DateUtil.currentUTCTime());
    }
    
	public Integer countFollowPostrewardBySourceId(Integer source, Integer sourceId) {
		Assert.notNull(source,"来源不能为空");
		Assert.notNull(sourceId,"来源id不能为空");
		return this.postRewardDao.countFollowPostrewardBySourceId(source, sourceId);
    }
	
	public List<PostReward> getPostRewardList(Integer pageOffset, Integer pageSize, Integer rewardStatus, Integer userId) {
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy(Page.ORDER_BY_MODIFY_TIME);
		return this.postRewardDao.getPostRewardList(rewardStatus, userId, page);
	}
}