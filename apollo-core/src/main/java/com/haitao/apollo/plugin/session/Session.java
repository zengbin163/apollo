package com.haitao.apollo.plugin.session;

import java.io.Serializable;

public class Session implements Serializable {
    
    private static final long serialVersionUID = 7680449964098679585L;
    
    private String deviceId;

    private Integer iosPublish; //IOS证书类型  0 : appstore ,  1 : 企业版
    
    private String token;
    
    private Integer operatorId;//买手id或者消费者id
    
    private Integer role;//登陆者角色，0:买手  1:消费者
    
    private LoginOperator loginOperator;
    
    public Session(){
        
    }

    public Session(String deviceId, Integer iosPublish, String token, Integer operatorId, Integer role, LoginOperator loginOperator){
        this.deviceId = deviceId;
        this.iosPublish = iosPublish;
        this.token = token;
        this.operatorId = operatorId;
        this.role = role;
        this.loginOperator = loginOperator;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public LoginOperator getLoginOperator() {
		return loginOperator;
	}

	public void setLoginOperator(LoginOperator loginOperator) {
		this.loginOperator = loginOperator;
	}

	public Integer getIosPublish() {
		return iosPublish;
	}

	public void setIosPublish(Integer iosPublish) {
		this.iosPublish = iosPublish;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
