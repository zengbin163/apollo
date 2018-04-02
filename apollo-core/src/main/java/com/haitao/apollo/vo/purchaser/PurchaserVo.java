package com.haitao.apollo.vo.purchaser;

import java.io.Serializable;
import java.math.BigDecimal;

public class PurchaserVo implements Serializable {

	private static final long serialVersionUID = 2843081733875017259L;
	private Integer id;
	private String purchaserName;// 买手名称
	private String mobile;// 手机
	private String password;// 密码
	private Long registerTime;// 注册时间
	private Long lastLoginTime;// 最后登录时间
	private String signature;// 签名
	private String headerUrl;// 头像
	private Integer isActive;// 是否激活
	private Long modifyTime;// 修改时间
	private BigDecimal guarantee;// 当前保证金
	private BigDecimal quota;// 剩余接单额度
	private BigDecimal account;// 账户金额
	private Integer vip;// vip等级
	private Integer quantity;// 接单数量
	private Integer version;// 乐观锁版本
	private String deviceId;// 设备id
	private String token;// apns推送token

	public PurchaserVo() {

	}

	public PurchaserVo(Integer id, String purchaserName, String password,
			String deviceId, String token) {
		this.id = id;
		this.purchaserName = purchaserName;
		this.password = password;
		this.deviceId = deviceId;
		this.token = token;
	}

	public PurchaserVo(String purchaserName, String mobile, String password,
			String signature, String headerUrl, BigDecimal guarantee,
			BigDecimal quota, BigDecimal account, Integer vip,
			Integer quantity, Long registerTime, Long lastLoginTime,
			Long modifyTime) {
		this.purchaserName = purchaserName;
		this.mobile = mobile;
		this.password = password;
		this.signature = signature;
		this.headerUrl = headerUrl;
		this.guarantee = guarantee;
		this.quota = quota;
		this.account = account;
		this.vip = vip;
		this.quantity = quantity;
		this.registerTime = registerTime;
		this.lastLoginTime = lastLoginTime;
		this.modifyTime = modifyTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
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

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public BigDecimal getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(BigDecimal guarantee) {
		this.guarantee = guarantee;
	}

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	public BigDecimal getQuota() {
		return quota;
	}

	public void setQuota(BigDecimal quota) {
		this.quota = quota;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}
}
