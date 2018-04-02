package com.haitao.apollo.pojo.purchaser;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.haitao.apollo.pojo.product.Category;

/**
 * 买手
 * @author zengbin
 *
 */
public class Purchaser implements Serializable {

	private static final long serialVersionUID = -1812693643730468722L;

	private Integer id;
	private String purchaserName;
	private String mobile;
	private String password;
	private Long registerTime;
	private Long lastLoginTime;
	private String signature;
	private String headerUrl;
	private Integer isActive;
	private Long modifyTime;
	private BigDecimal guarantee;
	private BigDecimal quota;
	private BigDecimal account;
	private Integer vip;
	private Integer quantity;
	private Integer version;
    private String deviceId;
    private String token;
    
    List<Category> beSubscribeCateList;//买手订阅的类目

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

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public List<Category> getBeSubscribeCateList() {
		return beSubscribeCateList;
	}

	public void setBeSubscribeCateList(List<Category> beSubscribeCateList) {
		this.beSubscribeCateList = beSubscribeCateList;
	}
}
