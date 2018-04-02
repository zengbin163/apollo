/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月12日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.status;

import java.io.Serializable;

/** 
* @ClassName: RefundStatus 
* @Description: 退款状态
* @author zengbin
* @date 2015年12月10日 下午9:19:31 
*/
public class RefundStatus implements Serializable{
	private static final long serialVersionUID = 3503780033351240291L;
    public static final Integer CREATE_REFUND=              301;// 退款中
    public static final Integer FINISH_REFUND=              302;// 退款完成 
    public static final Integer CLOSED_REFUND=              303;// 退款关闭
}

