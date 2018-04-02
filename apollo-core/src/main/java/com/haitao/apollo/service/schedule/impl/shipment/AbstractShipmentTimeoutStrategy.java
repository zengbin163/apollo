/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.schedule.impl.shipment;


/** 
* @ClassName: AbstractShipmentTimeoutStrategy 
* @Description: 抽象发货超时策略
* @author zengbin
* @date 2015年12月15日 上午8:43:21 
*/
public abstract class AbstractShipmentTimeoutStrategy {

    public Integer getUserId() {
        return -10000;
    }
    
    public abstract void execute();
}
