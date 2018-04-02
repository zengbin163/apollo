/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl.order;

import com.haitao.apollo.plugin.session.SessionContainer;

/** 
* @ClassName: AbstractCreateOrderStrategy 
* @Description: 创建销售订单校验策略
* @author zengbin
* @date 2015年11月30日 下午8:43:21 
*/
public abstract class AbstractCreateOrderStrategy {
    
    public Integer getUserId() {
        return SessionContainer.getSession().getOperatorId();
    }
    
    public abstract void execute(Integer postrewardId);
}
