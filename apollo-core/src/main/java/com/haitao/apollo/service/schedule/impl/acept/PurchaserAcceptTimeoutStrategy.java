package com.haitao.apollo.service.schedule.impl.acept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.haitao.apollo.enums.IsPublicEnum;
import com.haitao.apollo.enums.OrderTrackEnum;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.status.RewardStatus;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.order.PostRewardVo;

/**
 * 买手接单超时处理策略
 * 买手24小时不接单，订单释放到公共池 
 * @author zengbin
 *
 */
@Component
public class PurchaserAcceptTimeoutStrategy extends AbstractAcceptTimeoutStrategy {
    @Autowired
    private PostRewardService postRewardService;
    @Autowired
    private AsyncService asyncService;

    @Transactional
	@Override
	public void execute(Long acceptPurchaserTimeout, Long acceptPublicTimeout) {
		Integer sum = this.postRewardService.countAcceptTimeoutPostRewardList(IsPublicEnum.POOL_PURCHASER.getCode(), acceptPurchaserTimeout);
		if(Page.DEFAULT_SUM.equals(sum)){
			return;
		}
		int totalPages = Page.getTotalPages(sum);
		for(int i=0;i<totalPages;i++){
			List<PostReward> rewardList = this.postRewardService.getAcceptTimeoutPostRewardList(IsPublicEnum.POOL_PURCHASER.getCode(), acceptPurchaserTimeout, i * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			for(PostReward postreward : rewardList){
				PostRewardVo postRewardVo = new PostRewardVo();
				postRewardVo.setIsPublic(IsPublicEnum.POOL_PUBLIC.getCode());
				postRewardVo.setReleaseTime(DateUtil.currentUTCTime());
				this.postRewardService.updatePostReward(postreward.getId(), postRewardVo);
				this.asyncService.orderTrack(postreward.getUserId(), null, postreward.getPurchaserId(), postreward.getId(), RewardStatus.CREATE_REWARD, OrderStatus.NOT_CREATE_ORDER, OrderTrackEnum.PURCHASER_ACCEPT_TIMEOUT.getCode(), null);
			}
		}
	}

}
