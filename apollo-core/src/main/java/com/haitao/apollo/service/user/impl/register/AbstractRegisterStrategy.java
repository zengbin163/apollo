/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月10日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.user.impl.register;

/** 
* @ClassName: AbstractRegisterStrategy 
* @Description: 注册抽象验证策略
* @author zengbin
* @date 2015年11月10日 下午8:43:21 
*/
public abstract class AbstractRegisterStrategy {
    public abstract void execute(String mobile, String inviteCode, String sms);
}

