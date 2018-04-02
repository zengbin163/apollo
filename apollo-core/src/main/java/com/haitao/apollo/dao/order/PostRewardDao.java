/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月12日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.vo.order.PostRewardVo;

/** 
* @ClassName: PostRewardDao 
* @Description: 悬赏DAO
* @author zengbin
* @date 2015年11月12日 上午9:42:29 
*/
public interface PostRewardDao {
	void insertPostReward(PostRewardVo postRewardVo);

	Integer updatePostRewardById(
			@Param(value = "postRewardVo") PostRewardVo postRewardVo);

	PostReward getPostRewardById(Integer id);
	
	/**查询悬赏列表**/
	List<PostReward> getPostRewardList(
			@Param(value = "rewardStatus") Integer rewardStatus,
			@Param(value = "userId") Integer userId,
			@Param(value = "page") Page<?> page);

	/**消费者端悬赏池**/
	List<PostReward> getPostRewardPoolListByUser(
			@Param(value = "postRewardVo") PostRewardVo postRewardVo,
			@Param(value = "page") Page<?> page);
	
	/**买手端悬赏广场**/
	List<PostReward> getPostRewardSquareListByPurchaser(
			@Param(value = "postRewardVo") PostRewardVo postRewardVo,
			@Param(value = "page") Page<?> page);

	/**买手端我的客户悬赏**/
	List<PostReward> getPostRewardListByPurchaser(
			@Param(value = "postRewardVo") PostRewardVo postRewardVo,
			@Param(value = "page") Page<?> page);

	Integer countPostRewardList(
			@Param(value = "postRewardVo") PostRewardVo postRewardVo);

	List<PostReward> getAcceptTimeoutPostRewardList(
			@Param(value = "isPurchaser") Integer isPurchaser,
			@Param(value = "currentTime") Long currentTime,
			@Param(value = "timestamp") Long timestamp,
			@Param(value = "page") Page<?> page);

	Integer countAcceptTimeoutPostRewardList(
			@Param(value = "isPurchaser") Integer isPurchaser,
			@Param(value = "currentTime") Long currentTime,
			@Param(value = "timestamp") Long timestamp);

	List<PostReward> getAgreeShipmentTimeoutPostRewardList(
			@Param(value = "currentTime") Long currentTime,
			@Param(value = "timestamp") Long timestamp,
			@Param(value = "page") Page<?> page);
	
	Integer countAgreeShipmentTimeoutPostRewardList(
			@Param(value = "currentTime") Long currentTime,
			@Param(value = "timestamp") Long timestamp);

	List<PostReward> getShipmentTimeoutPostRewardList(
			@Param(value = "currentTime") Long currentTime,
			@Param(value = "page") Page<?> page);
	
	Integer countShipmentTimeoutPostRewardList(
			@Param(value = "currentTime") Long currentTime);
	
	Integer countFollowPostrewardBySourceId(
			@Param(value = "source") Integer source,
			@Param(value = "sourceId") Integer sourceId);// 查询某个晒单/悬赏下的悬赏跟单数
}

