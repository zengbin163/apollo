package com.haitao.apollo.service.schedule.impl.confirm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.LogisticsOrder;
import com.haitao.apollo.proccess.OrderProcess;
import com.haitao.apollo.service.logistics.LogisticsOrderService;

/**
 * 用户确认收货超时策略，订单完成
 * @author zengbin
 */
@Component
public class UserConfirmTimeoutStrategy extends AbstractConfirmTimeoutStrategy {
    @Autowired
    private OrderProcess orderProcess;
    @Autowired
    private LogisticsOrderService logisticsOrderService;

    @Transactional
	@Override
	public void execute(Long confirmTimeout) {
		Integer sum = this.logisticsOrderService.countConfirmTimeoutLogisticsOrderList(confirmTimeout);
		if(Page.DEFAULT_SUM.equals(sum)){
			return;
		}
		int totalPages = Page.getTotalPages(sum);
		for(int i=0;i<totalPages;i++){
			List<LogisticsOrder> logisticsOrderList = this.logisticsOrderService.getConfirmTimeoutLogisticsOrderList(confirmTimeout, i * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			for(LogisticsOrder logisticsOrder : logisticsOrderList){
				this.orderProcess.confirm(logisticsOrder.getOrderId(), "用户确认收货超时");
			}
		}
    }
}
