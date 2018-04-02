/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.user.impl.register;

import org.springframework.stereotype.Component;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.user.UserDao;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.util.SpringContextUtil;

/** 
* @ClassName: MobileRegisterStrategy 
* @Description: 验证手机是否被注册策略
* @author zengbin
* @date 2015年11月10日 下午8:46:58 
*/
@Component
public class MobileRegisterStrategy extends AbstractRegisterStrategy {
    @Override
    public void execute(String mobile, String inviteCode, String sms) {
        UserDao userDao = SpringContextUtil.getBean("userDao");
        User user = userDao.getUserByMobile(mobile);
        if(null!=user){
            throw new ApolloBizException(ResultCode.MOBILE_IS_USED  , mobile , ResultCode.MOBILE_IS_USED.getString());
        }
    }
}
