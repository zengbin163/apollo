/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pojo.user;

import java.io.Serializable;
import java.math.BigDecimal;

/** 
* @ClassName: User 
* @Description: 用户对象
* @author zengbin
* @date 2015年10月28日 下午4:05:06 
*/
public class User implements Serializable {
    
    private static final long serialVersionUID = 5170093482512533151L;
    
    private Integer id;
    private String nickName;
    private String mobile;
    private String password;
    private Long registerTime;
    private Long lastLoginTime;
    private String headerUrl;
    private Long modifyTime;
    private String inviteCode;
    private String signature;
    private String address;
    private BigDecimal bigMoney;
    private Integer isActive; //是否被激活   0:未激活    1:已激活
    private Integer isForbidPost;//是否禁止悬赏 0:未禁止   1:已禁止
    private Integer isForbidLogin;//是否禁止登陆  0:未禁止  1:已禁止
    private Integer isForbidShow;//是否禁止晒单  0:未禁止   1:已禁止
    private Integer version;
    private String deviceId;
    private String token;
    private Receiver defaultReceiver;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNickName() {
        return nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Long getRegisterTime() {
        return registerTime;
    }
    
    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }
    
    public Long getLastLoginTime() {
        return lastLoginTime;
    }
    
    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    public String getHeaderUrl() {
        return headerUrl;
    }
    
    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }
    
    public Long getModifyTime() {
        return modifyTime;
    }
    
    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public String getInviteCode() {
        return inviteCode;
    }
    
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
    
    public BigDecimal getBigMoney() {
		return bigMoney;
	}

	public void setBigMoney(BigDecimal bigMoney) {
		this.bigMoney = bigMoney;
	}

	public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Receiver getDefaultReceiver() {
		return defaultReceiver;
	}

	public void setDefaultReceiver(Receiver defaultReceiver) {
		this.defaultReceiver = defaultReceiver;
	}

	public Integer getIsForbidPost() {
		return isForbidPost;
	}

	public void setIsForbidPost(Integer isForbidPost) {
		this.isForbidPost = isForbidPost;
	}

	public Integer getIsForbidLogin() {
		return isForbidLogin;
	}

	public void setIsForbidLogin(Integer isForbidLogin) {
		this.isForbidLogin = isForbidLogin;
	}

	public Integer getIsForbidShow() {
		return isForbidShow;
	}

	public void setIsForbidShow(Integer isForbidShow) {
		this.isForbidShow = isForbidShow;
	}
}
