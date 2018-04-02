/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.logistics.impl.ship;

import com.haitao.apollo.plugin.session.SessionContainer;


/** 
* @ClassName: AbstractLogisticsShipStrategy 
* @Description: 买手发货校验策略
* @author zengbin
* @date 2016年04月18日 上午8:43:21 
*/
public abstract class AbstractLogisticsShipStrategy {
    
    public Integer getUserId() {
        return SessionContainer.getSession().getOperatorId();
    }
    
    public abstract void execute(Integer orderId, Integer receiverId, String logisticsCompany, String trackingNo);
}
