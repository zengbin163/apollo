package com.haitao.apollo.service.schedule.impl.agree;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.haitao.apollo.enums.ReleaseSourceEnum;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.proccess.OrderProcess;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.util.DateUtil;

/**
 * UserAgreeShipmentTimeoutStrategy
 * 用户同意收货时间超时策略，释放到公共池
 * @author zengbin
 */
@Component
public class UserAgreeShipmentTimeoutStrategy extends AbstractAgreeShipmentTimeoutStrategy {
    @Autowired
    private PostRewardService postRewardService;
    @Autowired
    private OrderProcess orderProcess;

    @Transactional
	@Override
	public void execute(Long timestamp) {
		Integer sum = this.postRewardService.countAgreeShipmentTimeoutPostRewardList(timestamp);
		if(Page.DEFAULT_SUM.equals(sum)){
			return;
		}
		int totalPages = Page.getTotalPages(sum);
		for(int i=0;i<totalPages;i++){
			List<PostReward> postRewardList = this.postRewardService.getAgreeShipmentTimeoutPostRewardList(timestamp, i * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			for(PostReward postReward : postRewardList){
				this.orderProcess.release(postReward.getId(), ReleaseSourceEnum.USER_AGREE_TIMEOUT.getCode(), DateUtil.currentUTCTime());
			}
		}
    }

}
