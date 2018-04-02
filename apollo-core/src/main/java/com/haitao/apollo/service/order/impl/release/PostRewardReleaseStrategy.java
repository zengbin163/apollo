/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月13日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl.release;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.status.RewardStatus;

/** 
* @ClassName: PostRewardReleaseStrategy 
* @Description: 释放悬赏到公共池的策略校验
* @author zengbin
* @date 2016年01月27日 16:01:27 
*/
@Component
public class PostRewardReleaseStrategy extends AbstractPurchaserReleaseStrategy {
	@Autowired
	private PostRewardService postRewardService;
    
	@Override
	public void execute(Integer postrewardId) {
        Assert.notNull(postrewardId,"悬赏id不能为空");
        PostReward postreward = this.postRewardService.getPostRewardById(postrewardId);
        if(null==postreward){
            throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, getUserId() , String.format("释放悬赏到公共池失败，悬赏单不存在 , postrewardId=%s" , postrewardId));
        }
		if (RewardStatus.IN_SHIPMENTS.equals(postreward.getRewardStatus())  || 
			RewardStatus.FINISH_REWARD.equals(postreward.getRewardStatus()) || 
			RewardStatus.CLOSE_REWARD.equals(postreward.getRewardStatus())) {
            throw new ApolloBizException(ResultCode.POSTREWARD_STATUS_ERROR, getUserId() , String.format("悬赏状态为买手已发货、悬赏已完成、悬赏已关闭不能释放到公共池 , postrewardId=%s , rewardStatus=%s" , postrewardId , postreward.getRewardStatus()));
        }
	}
}
