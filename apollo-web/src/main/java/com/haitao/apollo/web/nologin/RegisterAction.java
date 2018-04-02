/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月9日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.nologin;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.web.BaseAction;

/** 
* @ClassName: RegisterAction 
* @Description: 注册相关的action
* @author zengbin
* @date 2015年11月9日 下午8:51:17 
*/
public class RegisterAction extends BaseAction {

    private static final long serialVersionUID = 7363397630815426797L;

    @Autowired
    private UserService userService;
    @Autowired
    private PurchaserService purchaserService;

    /**
    * @Description  注册接口
    * @param role   0:买手      1:消费者
    * @param  deviceId 设备id
    * @param  mobile 手机号码
    * @param  password 密码
    * @param  inviteCode 邀请码
    * @param  smsCode 短信验证码
     */
    public String register(){
        Integer role = this.getIntParameter(request, "role", null);
        String deviceId = this.getFilteredParameter(request, "deviceId", 0, null);
    	String token = this.getFilteredParameter(request, "token", 0, null);
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String password = this.getFilteredParameter(request, "password", 0, null);
        String inviteCode = this.getFilteredParameter(request, "inviteCode", 0, null);
        String sms = this.getFilteredParameter(request, "smsCode", 0, null);
        if(OperatorRoleEnum.ROLE_USER.getCode().equals(role)) {
            this.returnFastJSON(this.userService.register(deviceId, mobile, password, inviteCode, sms, role, token));
        } else {
        	throw new ApolloBizException(ResultCode.ILLEGAL_ARGUMENT, mobile, String.format("买手只能通过后台系统录入，不存在注册逻辑，[mobile=%s]", mobile));
        }
        return null;
    }
    
    /**
     * 找回密码
    * @param role   0:买手      1:消费者
    * @param mobile 手机号码
    * @param smsCode 手机验证码
    * @param password 手机密码
     * @return
     */
    public String find(){
        Integer role = this.getIntParameter(request, "role", null);
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String sms = this.getFilteredParameter(request, "smsCode", 0, null);
        String password = this.getFilteredParameter(request, "password", 0, null);
        if(OperatorRoleEnum.ROLE_USER.getCode().equals(role)){
            this.returnFastJSON(this.userService.findPassword(role, mobile, sms, password));
        }else {
        	this.returnFastJSON(this.purchaserService.findPassword(role, mobile, sms, password));
        }
        return null;
    }
}

