/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.schedule.impl.acept;


/** 
* @ClassName: AbstractAcceptTimeoutStrategy 
* @Description: 抽象接单超时策略
* @author zengbin
* @date 2015年12月14日 上午8:43:21 
*/
public abstract class AbstractAcceptTimeoutStrategy {

    public Integer getUserId() {
        return -10000;
    }
    
    public abstract void execute(Long acceptPurchaserTimeout, Long acceptPublicTimeout);
}
