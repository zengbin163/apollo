/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.user.impl.register;

import org.springframework.stereotype.Component;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.user.InviteCodeDao;
import com.haitao.apollo.dao.user.UserDao;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.user.InviteCode;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.util.SpringContextUtil;

/** 
* @ClassName: InviteCodeRegisterStrategy 
* @Description: 邀请码验证策略
* @author zengbin
* @date 2015年11月10日 下午8:46:58 
*/
@Component
public class InviteCodeRegisterStrategy extends AbstractRegisterStrategy {
    @Override
    public void execute(String mobile, String inviteCode, String sms) {
        InviteCodeDao inviteCodeDao = SpringContextUtil.getBean("inviteCodeDao");
        InviteCode inviteCodePojo = inviteCodeDao.getInviteCode(inviteCode);
        if(null==inviteCodePojo){
            throw new ApolloBizException(ResultCode.INVITE_CODE_ERROR  , mobile , ResultCode.INVITE_CODE_ERROR.getString());
        }
        UserDao userDao = SpringContextUtil.getBean("userDao");
        User user = userDao.getUserByInviteCode(inviteCode);
        if(null!=user){
            throw new ApolloBizException(ResultCode.INVITE_CODE_IS_USED  , mobile , ResultCode.INVITE_CODE_IS_USED.getString());
        }
    }
}
