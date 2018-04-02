/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月12日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order;

import java.math.BigDecimal;
import java.util.List;

import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.vo.order.PostRewardVo;

/** 
* @ClassName: PostRewardService 
* @Description: 悬赏相关的service
* @author zengbin
* @date 2015年11月12日 上午10:43:16 
*/
public interface PostRewardService {
    /**
     * 
     * <pre>
     *      发布悬赏
     * </pre>
     * @param rewardPrice
     * @param productNum
     * @param content
     * @param receiverId    收货人id
     * @param rewardStatus
     * @param picAddr1
     * @param picAddr2
     * @param picAddr3
     * @param picAddr4
     * @param picAddr5
     * @param picAddr6
     * @param picAddr7
     * @param picAddr8
     * @param picAddr9
     * @param brandId
     * @param categoryId
     * @param source
     * @param sourceId
     * @return
     */
	Integer postReward(BigDecimal rewardPrice, Integer productNum,
			String content, Integer receiverId, Integer rewardStatus,
			String picAddr1, String picAddr2, String picAddr3, String picAddr4,
			String picAddr5, String picAddr6, String picAddr7, String picAddr8,
			String picAddr9, Integer brandId, Integer categoryId,
			Integer source, Integer sourceId, Integer requireDay);
	
	/**
	 * 买手接单
	 * @param purchaserId
	 * @param postrewardId
	 * @param purchaseTime
	 * @return
	 */
	Integer accept(Integer purchaserId , Integer postrewardId , Long acceptTime, Integer purchaserDay);

	/**
	 * 释放悬赏单
	 * @param postrewardId
     * @param source  
     *     0:买手在接单处释放到公共池，1:消费者在拒绝买手发货时间释放到公共池，2:消费者24小时未同意发货时间释放到公共池
	 * @param releaseTime
	 * @return
	 */
	void release(Integer postrewardId , Integer source , Long releaseTime);
	
    /**
     * 
     * <pre>
     *  修改悬赏状态
     * </pre>
     * @param id
     * @param rewardStatus
     * @return
     */
    Integer updatePostReward(Integer rewardId, PostRewardVo postRewardVo);
    
    Integer agreePostReward(Integer rewardId);

    Integer shipmentSaleOrder(Integer rewardId);

    Integer finishPostReward(Integer rewardId);
    
    Integer closePostReward(Integer rewardId);
    
    /**
     * 
     * <pre>
     *  查询悬赏详情
     * </pre>
     * @param id
     * @return
     */
    PostReward getPostRewardById(Integer id);

    /**
     * 查询消费者端悬赏池
     * @param categoryId
     * @param pageOffset
     * @param pageSize
     * @return
     */
    List<PostReward> getPostRewardPoolListByUser(Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize);

    /**
     * 查询买手端悬赏广场
     * @param categoryId
     * @param pageOffset
     * @param pageSize
     * @return
     */
    List<PostReward> getPostRewardSquareListByPurchaser(Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize);

    /**
     * 查询买手端首页悬赏列表
     * @param priorPurchaserId  优先展现的买手
     * @param rewardStatus 悬赏状态
     * @param isPublic
     * @param categoryId 类目id
     * @param pageOffset
     * @param pageSize
     * @return
     */
    List<PostReward> getPostRewardListByPurchaser(Integer priorPurchaserId, Integer rewardStatus, Integer isPublic, Integer brandId, Integer categoryId, Integer pageOffset, Integer pageSize);
    
    /**
     * 查询接单超时的悬赏集合
     * @return
     */
    List<PostReward> getAcceptTimeoutPostRewardList(Integer isPurchaser, Long timestamp, Integer pageOffset, Integer pageSize);

    /**
     * 查询接单超时的悬赏总数
     * @return
     */
    Integer countAcceptTimeoutPostRewardList(Integer isPurchaser, Long timestamp);

    /**
     * 查询消费者同意发货时间超时的悬赏集合
     * @return
     */
    List<PostReward> getAgreeShipmentTimeoutPostRewardList(Long timestamp, Integer pageOffset, Integer pageSize);
    
    /**
     * 查询消费者同意发货时间超时的悬赏总数
     * @return
     */
    Integer countAgreeShipmentTimeoutPostRewardList( Long timestamp);
    
    /**
     * 查询买手发货时间超时的悬赏集合
     * @return
     */
    List<PostReward> getShipmentTimeoutPostRewardList(Integer pageOffset, Integer pageSize);
    
    /**
     * 查询买手发货时间超时的悬赏总数
     * @return
     */
    Integer countShipmentTimeoutPostRewardList();
    
    /**
     * 查询某个晒单/悬赏下面的悬赏跟单数
     * @param source
     * @param sourceId
     * @return
     */
    Integer countFollowPostrewardBySourceId(Integer source, Integer sourceId);
    
    /**
     * 查询悬赏列表
     * @param rewardStatus
     * @param userId
     * @return
     */
	List<PostReward> getPostRewardList(Integer pageOffset, Integer pageSize, Integer rewardStatus, Integer userId);

}
