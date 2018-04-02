/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月14日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.login.order;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.annotation.FromUser;
import com.haitao.apollo.enums.ShowSourceEnum;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.proccess.OrderProcess;
import com.haitao.apollo.proccess.PageIndexProcess;
import com.haitao.apollo.web.BaseAction;

/** 
* @ClassName: ShowOrderAction 
* @Description: 晒单action
* @author zengbin
* @date 2015年11月22日 下午3:30:37 
*/
public class ShowOrderAction extends BaseAction {
    
	private static final long serialVersionUID = -3374902763212942072L;

	@Autowired
    private OrderProcess orderProcess;
	@Autowired
	private PageIndexProcess pageIndexProcess;
    
    /**
     * 用户或者买手晒单
     * @param showPrice  晒单价          单位为分
     * @param content    晒单内容
     * @param picAddr1
     * @param picAddr2
     * @param picAddr3
     * @param picAddr4
     * @param picAddr5
     * @param picAddr6
     * @param picAddr7
     * @param picAddr8
     * @param picAddr9
     * @param brandId      品牌id
     * @param categoryId   分类id
     * @return
     */
    @FromPurchaser
    public String purchaserShowOrder() {
        Integer orderId = this.getIntParameter(request, "orderId", -1);//买手晒单不需要订单id
        BigDecimal showPrice =this.getDecimalParameter(request, "showPrice", null);
        String content = this.getFilteredParameter(request, "content", 0, null);
        String picAddr1 = this.getFilteredParameter(request, "picAddr1", 0, null);
        String picAddr2 = this.getFilteredParameter(request, "picAddr2", 0, null);
        String picAddr3 = this.getFilteredParameter(request, "picAddr3", 0, null);
        String picAddr4 = this.getFilteredParameter(request, "picAddr4", 0, null);
        String picAddr5 = this.getFilteredParameter(request, "picAddr5", 0, null);
        String picAddr6 = this.getFilteredParameter(request, "picAddr6", 0, null);
        String picAddr7 = this.getFilteredParameter(request, "picAddr7", 0, null);
        String picAddr8 = this.getFilteredParameter(request, "picAddr8", 0, null);
        String picAddr9 = this.getFilteredParameter(request, "picAddr9", 0, null);
        Integer brandId = this.getIntParameter(request, "brandId", 0);
        Integer categoryId = this.getIntParameter(request, "categoryId", 0);
		Integer showOrderId = this.orderProcess.showOrder(orderId,
				ShowSourceEnum.PURCHASER.getCode(), showPrice, content,
				picAddr1, picAddr2, picAddr3, picAddr4, picAddr5, picAddr6,
				picAddr7, picAddr8, picAddr9, brandId, categoryId);
        returnFastJSON(toMap("showOrderId", showOrderId));
        return null;
    }
    
    /**
     * 消费者一键晒单
     * @param orderId 订单id
     * @return
     */
    @FromUser
    public String oneKeyShowOrder() {
        Integer orderId = this.getIntParameter(request, "orderId", null);
        SaleOrder saleOrder = this.pageIndexProcess.orderDetail(orderId);
		Integer showOrderId = this.orderProcess.showOrder(orderId,
				ShowSourceEnum.USER_APPRAISE.getCode(),
				saleOrder.getRewardPrice(), saleOrder.getContent(),
				saleOrder.getPicAddr1(), saleOrder.getPicAddr2(),
				saleOrder.getPicAddr3(), saleOrder.getPicAddr4(),
				saleOrder.getPicAddr5(), saleOrder.getPicAddr6(),
				saleOrder.getPicAddr7(), saleOrder.getPicAddr8(),
				saleOrder.getPicAddr9(), saleOrder.getBrandId(),
				saleOrder.getCategoryId());
        returnFastJSON(toMap("showOrderId", showOrderId));
        return null;
    }
}
