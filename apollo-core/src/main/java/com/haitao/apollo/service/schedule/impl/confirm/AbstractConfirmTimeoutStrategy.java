/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.schedule.impl.confirm;


/** 
* @ClassName: AbstractConfirmTimeoutStrategy 
* @Description: 抽象用户确认收货超时策略
* @author zengbin
* @date 2015年12月16日 上午9:43:21 
*/
public abstract class AbstractConfirmTimeoutStrategy {

    public Integer getUserId() {
        return -10000;
    }
    
    public abstract void execute(Long confirmTimeout);
}
