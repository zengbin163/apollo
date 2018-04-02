package com.haitao.apollo.service.schedule.impl.acept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.haitao.apollo.enums.IsPublicEnum;
import com.haitao.apollo.enums.OrderTrackEnum;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.proccess.OrderProcess;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.status.OrderStatus;
import com.haitao.apollo.status.RewardStatus;

/**
 * 公共池接单超时处理策略
 * 公共池48小时无人接单，悬赏关闭，退款
 * @author zengbin
 *
 */
@Component
public class PublicAcceptTimeoutStrategy extends AbstractAcceptTimeoutStrategy {
    @Autowired
    private PostRewardService postRewardService;
    @Autowired
    private OrderProcess orderProcess;
    @Autowired
    private AsyncService asyncService;

    @Transactional
	@Override
	public void execute(Long acceptPurchaserTimeout, Long acceptPublicTimeout) {
		Integer sum = this.postRewardService.countAcceptTimeoutPostRewardList(IsPublicEnum.POOL_PUBLIC.getCode(), acceptPublicTimeout);
		if(Page.DEFAULT_SUM.equals(sum)){
			return;
		}
		int totalPages = Page.getTotalPages(sum);
		for(int i=0;i<totalPages;i++){
			List<PostReward> rewardList = this.postRewardService.getAcceptTimeoutPostRewardList(IsPublicEnum.POOL_PUBLIC.getCode(), acceptPublicTimeout, i * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			for(PostReward postreward : rewardList){
				this.orderProcess.cancelPostreward(postreward.getId());
				this.asyncService.orderTrack(postreward.getUserId(), null, postreward.getPurchaserId(), postreward.getId(), RewardStatus.CREATE_REWARD, OrderStatus.NOT_CREATE_ORDER, OrderTrackEnum.POOL_ACCEPT_TIMEOUT.getCode(), null);
			}
		}
	}

}
