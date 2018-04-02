/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月12日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.order;

import com.haitao.apollo.vo.order.RefundOrderVo;

/** 
* @ClassName: RefundOrderDao 
* @Description: 退款订单DAO
* @author zengbin
* @date 2015年12月10日 下午15:12:29 
*/
public interface RefundOrderDao {
    void insertRefundOrder(RefundOrderVo refundOrderVo);
    Integer updateRefundOrderBySerialNo(RefundOrderVo refundOrderVo);
}