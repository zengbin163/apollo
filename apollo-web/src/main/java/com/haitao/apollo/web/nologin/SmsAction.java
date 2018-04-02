/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月9日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.nologin;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.web.BaseAction;

/** 
* @ClassName: SmsAction 
* @Description: 发短信接口
* @author zengbin
* @date 2015年11月9日 下午8:57:03 
*/
public class SmsAction extends BaseAction{

    private static final long serialVersionUID = -8009520368776800764L;
   
    @Autowired
    protected MsgTemplate msgTemplate;
    
    /**
     * 
    * @Description 发短信接口
    * @param codeType   17：消费者注册短信验证码，19：消费者找回密码短信验证码，18：买手注册短信，20：买手找回密码短信验证码
    * @param mobile   手机号码（需要携带国家码，比如86）
    * @return
     */
    public String send(){
        Integer codeType = this.getIntParameter(request, "codeType", null);
        String mobile = this.getFilteredParameter(request, "mobile", 0, null);
        this.msgTemplate.sendSMS(MsgTemplateEnum.getEnum(codeType), mobile);
        this.returnFastJSON(success());
        return null;
    }
    
}

