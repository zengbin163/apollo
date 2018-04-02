/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月10日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.nologin;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.web.BaseAction;

/** 
* @ClassName: LoginAction 
* @Description: 登录相关的action
* @author zengbin
* @date 2015年11月10日 下午4:28:23 
*/
public class LoginAction extends BaseAction {

    private static final long serialVersionUID = -1342413595183502212L;
    
    @Autowired
    private UserService userService;
    @Autowired
    private PurchaserService purchaserService;
    
    /**
     * 
    * @Description  消费者登录接口
    * @param role   0:买手      1:消费者
    * @param deviceId 设备id
    * @param mobile 手机号码
    * @param password 密码
    * @return
     */
    public String login(){
        Integer role = this.getIntParameter(request, "role", null);
    	String deviceId = this.getFilteredParameter(request, "deviceId", 0, null);
    	String token = this.getFilteredParameter(request, "token", 0, null);
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String password = this.getFilteredParameter(request, "password", 0, null);
        if(OperatorRoleEnum.ROLE_USER.getCode().equals(role)){
        	this.returnFastJsonExcludeProperties(this.userService.login(deviceId, mobile, password, role, token), User.class, new String[]{"password","version"});
        }else{
        	this.returnFastJsonExcludeProperties(this.purchaserService.login(deviceId, mobile, password, role, token), Purchaser.class, new String[]{"password","version"});
        }
        return null;
    }
    
    /**
     * 注销（买手和消费者同一个）
     * @param deviceId 设备id
     * @return
     */
    public String logout() {
    	String deviceId = this.getFilteredParameter(request, "deviceId", 0, null);
    	this.userService.logout(deviceId);
    	this.returnFastJSON(this.success());
    	return null;
    }
}

