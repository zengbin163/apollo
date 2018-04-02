/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl.release;

import com.haitao.apollo.plugin.session.SessionContainer;

/** 
* @ClassName: AbstractPurchaserReleaseStrategy 
* @Description: 买手释放悬赏到公共池的策略
* @author zengbin
* @date 2016年01月27日 15:59:21 
*/
public abstract class AbstractPurchaserReleaseStrategy {
    
    public Integer getUserId() {
        return SessionContainer.getSession().getOperatorId();
    }
    
    public abstract void execute(Integer postrewardId);
}
