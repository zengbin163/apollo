/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月18日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pay;

import com.haitao.apollo.base.Result;
import com.haitao.apollo.pay.vo.RefundVo;


/** 
* @ClassName: RefundComponent 
* @Description: 退款组件
* @author zengbin
* @date 2015年12月07日 上午9:57:03 
*/
public abstract class RefundComponent  implements Component{
	
	/**
	 * 根据预支付信息发起退款
	 * @param refundVo
	 * @return
	 */
	public abstract Result refund(RefundVo refundVo);
	
	/**
	 * 根据预支付prepayId和退款refundId查询退款信息
	 * @param prepayId
	 * @param refundId
	 * @return
	 */
	public abstract Result query(String prepayId, String refundId);
}

