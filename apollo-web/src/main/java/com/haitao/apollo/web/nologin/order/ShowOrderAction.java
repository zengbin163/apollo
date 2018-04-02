/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月14日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.nologin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.pojo.order.ShowOrder;
import com.haitao.apollo.proccess.PageIndexProcess;
import com.haitao.apollo.proccess.PageListProcess;
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
    private PageIndexProcess pageIndexProcess;
    @Autowired
    private PageListProcess pageListProcess;

    /**
     * 查询消费者端晒单池
     * @param categoryId 类目id
     * @param pageOffset
     * @param pageSize
     * @return
     */
    public String showOrderPoolListByUser(){
    	Integer brandId = this.getIntParameter(request, "brandId", null);
        Integer categoryId = this.getIntParameter(request, "categoryId", null);
        Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
        Integer pageSize = this.getIntParameter(request, "pageSize", null);
        List<ShowOrder> showOrderList = this.pageListProcess.getShowOrderPoolListByOperator(null, null, brandId, categoryId, pageOffset, pageSize);
        returnFastJSON(showOrderList);
        return null;
    }
    
    /**
    * @Description 晒单详情
    * @param showOrderId 晒单id
    * @return
     */
    public String showOrderDetail(){
        Integer showOrderId = this.getIntParameter(request, "showOrderId", null);
        ShowOrder showOrder = this.pageIndexProcess.showOrderDetail(showOrderId);
        returnFastJSON(showOrder);
        return null;
    }
}
