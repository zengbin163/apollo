/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl.show;

import com.haitao.apollo.plugin.session.SessionContainer;

/** 
* @ClassName: AbstractShowStrategy 
* @Description: 发布晒单校验策略
* @author zengbin
* @date 2015年11月22日 下午8:43:21 
*/
public abstract class AbstractShowStrategy {
    
    public Integer getUserId() {
    	if(null==SessionContainer.getSession()){
    		return -10000;
    	}
        return SessionContainer.getSession().getOperatorId();
    }
    public abstract void execute(Integer orderId, Integer source);
}
