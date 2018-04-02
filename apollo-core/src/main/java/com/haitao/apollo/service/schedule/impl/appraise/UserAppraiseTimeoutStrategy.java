package com.haitao.apollo.service.schedule.impl.appraise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.user.UserAppraiseService;

/**
 * 用户48小时未评价全部默认好评 + 晒单
 * @author zengbin
 */
@Component
public class UserAppraiseTimeoutStrategy extends AbstractAppraiseTimeoutStrategy {
    @Autowired
    protected SaleOrderService saleOrderService;
    @Autowired
    protected UserAppraiseService userAppraiseService;

    @Transactional
	@Override
	public void execute(Long appraiseTimeout) {
		Integer sum = this.saleOrderService.countAppraiseTimeoutSaleOrderList(appraiseTimeout);
		if(Page.DEFAULT_SUM.equals(sum)){
			return;
		}
		int totalPages = Page.getTotalPages(sum);
		for(int i=0;i<totalPages;i++){
			List<SaleOrder> saleOrderList = this.saleOrderService.getAppraiseTimeoutSaleOrderList(appraiseTimeout, i * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			for(SaleOrder saleOrder : saleOrderList){
				this.userAppraiseService.appraise(saleOrder.getUserId(), saleOrder.getPurchaserId(), IsDefaultEnum.DEFAULT_NO.getCode(), saleOrder.getId(), UserAppraiseService.SCORE_5, "系统默认好评");
			}
		}
    }
}
