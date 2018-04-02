/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月8日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.vo.user;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: UserVo
 * @Description: web和控制层传入的用户相关的VO参数
 * @author zengbin
 * @date 2015年11月8日 上午11:30:55
 */
public class UserVo implements Serializable {

	private static final long serialVersionUID = -69628026263026065L;
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
	private Integer version;
    private Integer isActive; //是否被激活   0:未激活    1:已激活
    private Integer isForbidPost;//是否禁止悬赏 0:未禁止   1:已禁止
    private Integer isForbidLogin;//是否禁止登陆  0:未禁止  1:已禁止
	private Integer isForbidShow;//是否禁止晒单  0:未禁止   1:已禁止
    private String deviceId;
    private String token;

	public UserVo() {

	}

	public UserVo(Integer id, String nickName, String password,
			String headerUrl, Long lastLoginTime, String signature,
			String address, BigDecimal bigMoney, String deviceId, String token,
			Integer isForbidPost, Integer isForbidLogin, Integer isForbidShow) {
		this.id = id;
		this.nickName = nickName;
		this.password = password;
		this.headerUrl = headerUrl;
		this.lastLoginTime = lastLoginTime;
		this.signature = signature;
		this.address = address;
		this.bigMoney = bigMoney;
		this.deviceId = deviceId;
		this.token = token;
		this.isForbidPost = isForbidPost;
		this.isForbidLogin = isForbidLogin;
		this.isForbidShow = isForbidShow;
	}

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

	public String getHeaderUrl() {
		return headerUrl;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
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

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
