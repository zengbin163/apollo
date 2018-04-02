/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月16日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.plugin.push;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.IsProductEnum;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.plugin.exception.ApolloSysException;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.EnhancedApnsNotification;

/** 
* @ClassName: IOSPush 
* @Description: IOS推送
* @author zengbin
* @date 2015年11月16日 下午4:32:10 
*/
public class IOSPush extends Push {
    private Integer isProduct;
    private String userAppstorePath;
    private String userAppstorePassword;
    private String purchaserAppstorePath;
    private String purchaserAppstorePassword;
    
    public void push(Map<String,Object> pushMap){
        String token = (String)pushMap.get(KEY_TOKEN);
        String content = (String)pushMap.get(KEY_CONTENT);
        String redirectUrl = (String)pushMap.get(KEY_URL);
        Integer role = (Integer)pushMap.get(KEY_ROLE);
        ApnsService service = getApnsService(role);
        Map<String,Object> map = new HashMap<>();
        map.put("redirectUrl", redirectUrl);
        String payload = APNS.newPayload()
        		.alertBody(content) //推送通知显示的文字
        		.sound("default") //推送附带的声音提示
        		.badge(1) //应用程序图标右上角显示的数字
        		.customFields(map) //自定义参数
        		.build();
        int now =  (int)(new Date().getTime()/1000);
        /** Expire in 24 hour later**/
        EnhancedApnsNotification notification = new EnhancedApnsNotification(EnhancedApnsNotification.INCREMENT_ID(), now + 24 * 60 * 60, token, payload);
        service.push(notification);
    }
    
    private ApnsService getApnsService(Integer role){
        boolean isProduction = IsProductEnum.PRODUCTION.getCode().equals(isProduct) ? true : false;
        ApnsService service = null;
        if(OperatorRoleEnum.ROLE_PURCHASER.getCode().equals(role)){
        	service = APNS.newService().withCert(purchaserAppstorePath, purchaserAppstorePassword).withAppleDestination(isProduction).build();
        } else if(OperatorRoleEnum.ROLE_USER.getCode().equals(role)) {
        	service = APNS.newService().withCert(userAppstorePath, userAppstorePassword).withAppleDestination(isProduction).build();
        } else {
        	throw new ApolloSysException(ResultCode.ILLEGAL_ARGUMENT, -10000, String.format("操作者角色必须是消费者或者买手，[role=%s]", role));
        }
        return service;
    }

    public Integer getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(Integer isProduct) {
        this.isProduct = isProduct;
    }

	public String getUserAppstorePath() {
		return userAppstorePath;
	}

	public void setUserAppstorePath(String userAppstorePath) {
		this.userAppstorePath = userAppstorePath;
	}

	public String getUserAppstorePassword() {
		return userAppstorePassword;
	}

	public void setUserAppstorePassword(String userAppstorePassword) {
		this.userAppstorePassword = userAppstorePassword;
	}

	public String getPurchaserAppstorePath() {
		return purchaserAppstorePath;
	}

	public void setPurchaserAppstorePath(String purchaserAppstorePath) {
		this.purchaserAppstorePath = purchaserAppstorePath;
	}

	public String getPurchaserAppstorePassword() {
		return purchaserAppstorePassword;
	}

	public void setPurchaserAppstorePassword(String purchaserAppstorePassword) {
		this.purchaserAppstorePassword = purchaserAppstorePassword;
	}
}
