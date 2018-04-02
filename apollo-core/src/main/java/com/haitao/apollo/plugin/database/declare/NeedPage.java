/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月8日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.plugin.database.declare;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* @ClassName: NeedPage 
* @Description: 标记哪些查询方法需要分页
* @author zengbin
* @date 2015年11月8日 上午10:18:11 
*/
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedPage {
}
