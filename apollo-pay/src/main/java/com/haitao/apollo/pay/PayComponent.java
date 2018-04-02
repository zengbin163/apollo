/*
 *@Project: GZJK
 *@Author: zengbin
 *@Date: 2015年11月18日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pay;

import com.haitao.apollo.base.Result;
import com.haitao.apollo.pay.vo.PayVo;

/**
 * @ClassName: PayComponent
 * @Description: 支付组件
 * @author zengbin
 * @date 2015年12月07日 上午9:57:03
 */
public abstract class PayComponent implements Component{

	/**
	 * 创建预支付的charge
	 * @param aliPayVo
	 * @return
	 */
	public abstract Result pay(PayVo aliPayVo);
	
	/**
	 * 根据prepayId查询预支付信息
	 * @param prepayId
	 * @return
	 */
	public abstract Result query(String prepayId);

}
