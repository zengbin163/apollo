/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月18日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.proccess;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.enums.RewardSourceEnum;
import com.haitao.apollo.logistics.Track;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.comment.PostrewardComment;
import com.haitao.apollo.pojo.comment.ShowOrderComment;
import com.haitao.apollo.pojo.order.OrderCount;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.order.ShowOrder;
import com.haitao.apollo.pojo.praise.PostrewardPraise;
import com.haitao.apollo.pojo.praise.ShowOrderPraise;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.service.comment.CommentService;
import com.haitao.apollo.service.logistics.LogisticsOrderService;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.order.ShowOrderService;
import com.haitao.apollo.service.praise.PraiseService;
import com.haitao.apollo.service.product.CategoryService;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.user.UserService;

/** 
* @ClassName: PageListProcess 
* @Description: 列表页查询流程
* @author zengbin
* @date 2015年11月18日 下午6:54:45 
*/
@Component
public class PageListProcess extends Process {

    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    private LogisticsOrderService logisticsOrderService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PraiseService praiseService;
    @Autowired
    private UserService userService;
    @Autowired
    private PurchaserService purchaserService;
    @Autowired
    private PostRewardService postRewardService;
    @Autowired
    private ShowOrderService showOrderService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 物流跟踪记录
     * @param trackingNo
     * @return
     */
    public List<Track> trackList(String trackingNo){
        return this.logisticsOrderService.getLogisticsTrackList(trackingNo);
    }
    
    /**
     * 悬赏评论列表
     * @param postrewardId
     * @return
     */
	public List<PostrewardComment> postrewardCommentList(Integer postrewardId, Integer pageOffset, Integer pageSize){
		List<PostrewardComment> commentList = this.commentService.getPostrewardCommentListByPostrewardId(postrewardId, pageOffset, pageSize);
		if(CollectionUtils.isEmpty(commentList)){
			return commentList;
		}
		/**加入用户信息**/
		List<PostrewardComment> tempCommentList = new ArrayList<PostrewardComment>();
		for(PostrewardComment postrewardComment : commentList){
			User commenter = this.userService.getUserById(postrewardComment.getCommenterId());
			User beCommented = this.userService.getUserById(postrewardComment.getBeCommentedId());
			postrewardComment.setCommenterNickName(commenter.getNickName());
			postrewardComment.setCommenterHeaderUrl(commenter.getHeaderUrl());
			postrewardComment.setBeCommentedNickName(beCommented.getNickName());
			postrewardComment.setBeCommentedHeaderUrl(beCommented.getHeaderUrl());
			tempCommentList.add(postrewardComment);
		}
		return tempCommentList;
	}

	/**
	 * 晒单评论列表
	 * @param showOrderId
	 * @return
	 */
	public List<ShowOrderComment> showOrderCommentList(Integer showOrderId, Integer pageOffset, Integer pageSize){
		List<ShowOrderComment> commentList = this.commentService.getShowOrderCommentListByShowOrderId(showOrderId, pageOffset, pageSize);
		if(CollectionUtils.isEmpty(commentList)){
			return commentList;
		}
		/**加入用户信息**/
		List<ShowOrderComment> tempCommentList = new ArrayList<ShowOrderComment>();
		for(ShowOrderComment showOrderComment : commentList){
			User commenter = this.userService.getUserById(showOrderComment.getCommenterId());
			User beCommented = this.userService.getUserById(showOrderComment.getBeCommentedId());
			showOrderComment.setCommenterNickName(commenter.getNickName());
			showOrderComment.setCommenterHeaderUrl(commenter.getHeaderUrl());
			showOrderComment.setBeCommentedNickName(beCommented.getNickName());
			showOrderComment.setBeCommentedHeaderUrl(beCommented.getHeaderUrl());
			tempCommentList.add(showOrderComment);
		}
		return tempCommentList;
	}

	/**
	 * 悬赏点赞列表
	 * @param postrewardId
	 * @return
	 */
	public List<PostrewardPraise> postrewardPraiseList(Integer postrewardId, Integer pageOffset, Integer pageSize){
		List<PostrewardPraise> praiseList = this.praiseService.getPostrewardPraiseListByPostrewardId(postrewardId, pageOffset, pageSize);
		if(CollectionUtils.isEmpty(praiseList)){
			return praiseList;
		}
		/**加入用户信息**/
		List<PostrewardPraise> tempPraiseList = new ArrayList<PostrewardPraise>();
		for(PostrewardPraise postwardPraise : praiseList){
			User user = this.userService.getUserById(postwardPraise.getPraiserId());
			postwardPraise.setNickName(user.getNickName());
			postwardPraise.setHeaderUrl(user.getHeaderUrl());
			tempPraiseList.add(postwardPraise);
		}
		return tempPraiseList;
	}

	/**
	 * 晒单点赞列表
	 * @param showOrderId
	 * @return
	 */
	public List<ShowOrderPraise> showOrderPraiseList(Integer showOrderId, Integer pageOffset, Integer pageSize){
		List<ShowOrderPraise> praiseList = this.praiseService.getShowOrderPraiseListByShowOrderId(showOrderId, pageOffset, pageSize);
		if(CollectionUtils.isEmpty(praiseList)){
			return praiseList;
		}
		/**加入用户信息**/
		List<ShowOrderPraise> tempPraiseList = new ArrayList<ShowOrderPraise>();
		for(ShowOrderPraise showOrderPraise : praiseList){
			User user = this.userService.getUserById(showOrderPraise.getPraiserId());
			showOrderPraise.setNickName(user.getNickName());
			showOrderPraise.setHeaderUrl(user.getHeaderUrl());
			tempPraiseList.add(showOrderPraise);
		}
		return tempPraiseList;
	}
	
	/**
	 * 查询消费者端悬赏池
	 * @param categoryId
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public List<PostReward> getPostRewardPoolListByUser(Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize){
		List<PostReward> postrewardList = this.postRewardService.getPostRewardPoolListByUser(brandId, categoryId, pageOffset, pageSize);
    	if(CollectionUtils.isEmpty(postrewardList)){
    		return postrewardList;
    	}
    	List<PostReward> tempPostrewardList = new ArrayList<PostReward>();
    	for(PostReward postReward : postrewardList){
    		postReward.setBrandName(categoryService.getBrandById(postReward.getBrandId()).getBrandName());
    		postReward.setCategoryName(categoryService.getCategoryById(postReward.getCategoryId()).getCategoryName());
    		postReward.setFollowCount(this.postRewardService.countFollowPostrewardBySourceId(RewardSourceEnum.FROM_REWARD_FOLLOW.getCode(), postReward.getId()));//当前晒单的悬赏跟单数
			User user = this.userService.getUserById(postReward.getUserId());
			postReward.setUserNickName(user.getNickName());
			postReward.setUserHeaderUrl(user.getHeaderUrl());
			postReward.setUserSignature(user.getSignature());
			tempPostrewardList.add(postReward);
    	}
    	return tempPostrewardList;
	}

	/**
	 * 查询买手端的悬赏广场
	 * @param categoryId
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public List<PostReward> getPostRewardSquareListByPurchaser(Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize){
		List<PostReward> postrewardList =  this.postRewardService.getPostRewardSquareListByPurchaser(brandId, categoryId, pageOffset, pageSize);
    	if(CollectionUtils.isEmpty(postrewardList)){
    		return postrewardList;
    	}
    	List<PostReward> tempPostrewardList = new ArrayList<PostReward>();
    	for(PostReward postReward : postrewardList){
    		postReward.setBrandName(categoryService.getBrandById(postReward.getBrandId()).getBrandName());
    		postReward.setCategoryName(categoryService.getCategoryById(postReward.getCategoryId()).getCategoryName());
			User user = this.userService.getUserById(postReward.getUserId());
			postReward.setUserNickName(user.getNickName());
			postReward.setUserHeaderUrl(user.getHeaderUrl());
			postReward.setUserSignature(user.getSignature());
			tempPostrewardList.add(postReward);
    	}
    	return tempPostrewardList;
	} 
	
    /**
     * 查询买手端首页我的客户悬赏列表
     * @param priorPurchaserId  优先展现的买手
     * @param rewardStatus 悬赏状态
     * @param categoryId 类目id
     * @param pageOffset
     * @param pageSize
     * @return
     */
    public List<PostReward> getPostRewardListByPurchaser(Integer priorPurchaserId, Integer rewardStatus, Integer isPublic, Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize){
    	List<PostReward> postrewardList = this.postRewardService.getPostRewardListByPurchaser(priorPurchaserId, rewardStatus, isPublic, brandId, categoryId, pageOffset, pageSize);
    	if(CollectionUtils.isEmpty(postrewardList)){
    		return postrewardList;
    	}
    	List<PostReward> tempPostrewardList = new ArrayList<PostReward>();
    	for(PostReward postReward : postrewardList){
    		postReward.setBrandName(categoryService.getBrandById(postReward.getBrandId()).getBrandName());
    		postReward.setCategoryName(categoryService.getCategoryById(postReward.getCategoryId()).getCategoryName());
			User user = this.userService.getUserById(postReward.getUserId());
			postReward.setUserNickName(user.getNickName());
			postReward.setUserHeaderUrl(user.getHeaderUrl());
			postReward.setUserSignature(user.getSignature());
			tempPostrewardList.add(postReward);
    	}
    	return tempPostrewardList;
    }

    /**
     * 查询消费者端（或者买手端）首页晒单池列表
	 * @param role  0:买手主动发布晒单，1:消费者好评默认晒单
     * @param operatorId 用户id
     * @param categoryId 类目id
     * @param pageOffset
     * @param pageSize
     * @return
     */
    public List<ShowOrder> getShowOrderPoolListByOperator(Integer role, Integer operatorId, Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize){
    	List<ShowOrder> showOrderList = this.showOrderService.getShowOrderPoolListByOperator(role, operatorId, brandId, categoryId, pageOffset, pageSize);
    	if(CollectionUtils.isEmpty(showOrderList)){
    		return showOrderList;
    	}
    	List<ShowOrder> tempShowOrderList = new ArrayList<>();
    	for(ShowOrder showOrder : showOrderList){
    		showOrder.setBrandName(categoryService.getBrandById(showOrder.getBrandId()).getBrandName());
    		showOrder.setCategoryName(categoryService.getCategoryById(showOrder.getCategoryId()).getCategoryName());
    		showOrder.setPraiseCount(this.praiseService.countShowOrderPraise(showOrder.getId()));//点赞数
    		showOrder.setFollowCount(this.postRewardService.countFollowPostrewardBySourceId(RewardSourceEnum.FROM_SHOW_ORDER.getCode(), showOrder.getId()));//当前晒单的悬赏跟单数
    		if(OperatorRoleEnum.ROLE_USER.getCode().equals(showOrder.getRole())){
    			SaleOrder saleOrder = this.saleOrderService.getSaleOrderByOrderId(showOrder.getOrderId());
    			User user = this.userService.getUserById(saleOrder.getUserId());
    			Purchaser purchaser = this.purchaserService.getPurchaserById(saleOrder.getPurchaserId());
    			showOrder.setUserId(user.getId());
    			showOrder.setUserNickName(user.getNickName());
    			showOrder.setUserHeaderUrl(user.getHeaderUrl());
    			showOrder.setUserSignature(user.getSignature());
    			showOrder.setPurchaserId(purchaser.getId());
    			showOrder.setPurchaserNickName(purchaser.getPurchaserName());
    			showOrder.setPurchaserHeaderUrl(purchaser.getHeaderUrl());
    			showOrder.setPurchaserSignature(purchaser.getSignature());
    			tempShowOrderList.add(showOrder);
    		}else{
    			Purchaser purchaser = this.purchaserService.getPurchaserById(showOrder.getOperatorId());
    			showOrder.setPurchaserId(purchaser.getId());
    			showOrder.setPurchaserNickName(purchaser.getPurchaserName());
    			showOrder.setPurchaserHeaderUrl(purchaser.getHeaderUrl());
    			showOrder.setPurchaserSignature(purchaser.getSignature());
    			tempShowOrderList.add(showOrder);
    		}
    		
    	}
    	return tempShowOrderList;
    }
	
	/**
	 * 根据订单状态或者退款状态查询订单列表
	 * @param role 0：买手   1：消费者  @OperatorRoleEnum
	 * @param rewardStatus 悬赏状态  @RewardStatus
	 * @param orderStatus 订单状态  @OrderStatus
	 * @param refundStatus 退款状态  @RefundStatus
	 * @param csStatus 客服状态  @CsStatus
	 * @param needAppraise 0表示待评价，1表示已评价  @IsDefaultEnum
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public List<SaleOrder> getSaleOrderList(Integer role, Integer rewardStatus,
			Integer orderStatus, Integer refundStatus, Integer csStatus,
			Integer needAppraise, Integer pageOffset, Integer pageSize) {
		Assert.notNull(role, "操作者角色不能为空");
		if(OperatorRoleEnum.ROLE_USER.getCode().equals(role)) {
			return this.saleOrderService.getSaleOrderList(SessionContainer
					.getSession().getOperatorId(), null, rewardStatus,
					orderStatus, refundStatus, csStatus, needAppraise,
					pageOffset, pageSize);
		}else if(OperatorRoleEnum.ROLE_PURCHASER.getCode().equals(role)){
			return this.saleOrderService.getSaleOrderList(null,
					SessionContainer.getSession().getOperatorId(),
					rewardStatus, orderStatus, refundStatus, csStatus,
					needAppraise, pageOffset, pageSize);
		}else {
			return null;
		}
	}
	
	/**
	 * 根据订单状态或者退款状态查询列表每个订单数
	 * @param role 0：买手   1：消费者  @OperatorRoleEnum
	 * @return
	 */
	public List<OrderCount> getMyOrderCount(Integer role) {
		Assert.notNull(role, "操作者角色不能为空");
		if(OperatorRoleEnum.ROLE_USER.getCode().equals(role)) {
			return this.saleOrderService.getMyOrderCount(SessionContainer.getSession().getOperatorId(), null);
		}else if(OperatorRoleEnum.ROLE_PURCHASER.getCode().equals(role)){
			return this.saleOrderService.getMyOrderCount(null, SessionContainer.getSession().getOperatorId());
		}else {
			return null;
		}

	}
}

