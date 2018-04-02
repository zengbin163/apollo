/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月13日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl.accept;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.PostRewardDao;
import com.haitao.apollo.enums.IsPublicEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.status.RewardStatus;

/** 
* @ClassName: PostRewardStrategy 
* @Description: 悬赏相关的策略校验
* @author zengbin
* @date 2015年11月13日 下午3:12:27 
*/
@Component
public class PostRewardStrategy extends AbstractPurchaserAcceptStrategy {
    @Resource(name = "postRewardDao")
    private PostRewardDao postRewardDao;
    
	@Override
	public void execute(Integer purchaserId, Integer postrewardId) {
        Assert.notNull(postrewardId,"悬赏id不能为空");
        PostReward postreward = this.postRewardDao.getPostRewardById(postrewardId);
        if(null==postreward){
            throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, getUserId() , String.format("买手接单失败，悬赏单不存在 , purchaserId=%s , postrewardId=%s" , purchaserId , postrewardId));
        }
        //悬赏中
        if(!RewardStatus.CREATE_REWARD.equals(postreward.getRewardStatus())){
            throw new ApolloBizException(ResultCode.POSTREWARD_STATUS_ERROR, getUserId() , String.format("买手接单失败，悬赏状态必须为悬赏中 , postrewardId=%s , rewardStatus=%s" , postrewardId , postreward.getRewardStatus()));
        }
        //买手优选
        if(IsPublicEnum.POOL_PURCHASER.getCode().equals(postreward.getIsPublic())){
        	if(!purchaserId.equals(postreward.getPriorPurchaserId())){
                throw new ApolloBizException(ResultCode.PURCHASER_ILLEGAL, getUserId() , String.format("买手非法，接单买手不是优先买手 , postrewardId=%s , purchaserId=%s , priorPurchaserId=%s" , postrewardId , purchaserId , postreward.getPriorPurchaserId()));
        	}
        }
	}
}
