/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.schedule.impl.agree;


/** 
* @ClassName: AbstractAgreeShipmentTimeoutStrategy 
* @Description: 消费者同意发货时间超时抽象策略
* @author zengbin
* @date 2015年12月16日 上午9:43:21 
*/
public abstract class AbstractAgreeShipmentTimeoutStrategy {

    public Integer getUserId() {
        return -10000;
    }
    
    public abstract void execute(Long timestamp);
}
