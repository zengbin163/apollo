/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月18日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.pay;

import com.pingplusplus.model.Refund;

/** 
* @ClassName: ThirdRefundService 
* @Description: 第三方支付退款服务层
* @author zengbin
* @date 2015年11月18日 下午4:44:40 
*/
public interface ThirdRefundService {
	/**
	 * 创建退款信息
	 * @param postrewardId
	 * @param payOrderId
	 * @param 预支付chargeId
	 * @param description
	 * @return
	 */
    Refund refund(Integer postrewardId, Integer payOrderId, String chargeId, String description);
    
    /**
     * 查询退款单
     * @param 预支付chargeId
     * @param refundId 退款id
     * @return
     */
    Refund query(String chargeId, String refundId);
}
