/*
 *@Project: GZJK
 *@Author: zengbin
 *@Date: 2015年11月14日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.order;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.haitao.apollo.util.DateUtil;

/**
 * @ClassName: OrderTestCase
 * @author zengbin
 * @date 2015年11月14日 下午3:32:53
 */
public class DateTestCase {

	public static void main(String[] args) {
		System.out.println(DateUtil.currentUTCTime() - 3 * 24 * 60 * 60 * 1000  - 80 * 60 * 60 * 1000);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(1460883543530L)));
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.get(Calendar.MONTH)+1);
		
		BigDecimal b1 = new BigDecimal("0");
		BigDecimal b2 = new BigDecimal("0.00");
		
		System.out.println(BigDecimal.ZERO.equals(b1));
		System.out.println(BigDecimal.ZERO.compareTo(b2) == 0);
	}
}
