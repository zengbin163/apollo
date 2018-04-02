/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.user.impl.register;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.plugin.cache.RedisService;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.util.SpringContextUtil;

/** 
* @ClassName: SmsRegisterStrategy 
* @Description: 校验手机验证码策略
* @author zengbin
* @date 2015年11月10日 下午8:46:58 
*/
@Component
public class SmsRegisterStrategy extends AbstractRegisterStrategy {
    @Override
    public void execute(String mobile, String inviteCode, String sms) {
        RedisService redisService = SpringContextUtil.getBean("redisService");
        String validateCode = redisService.get(MsgTemplateEnum.SMS_REGISTER_USER+mobile);
        if(StringUtils.isEmpty(validateCode) || !validateCode.equals(sms)){
            throw new ApolloBizException(ResultCode.REG_CODE_ERROR  , mobile , ResultCode.REG_CODE_ERROR.getString());
        }
    }
}
