package com.haitao.apollo.vo.purchaser;

import java.io.Serializable;

public class PurchaserInfoVo implements Serializable {

	private static final long serialVersionUID = 8870446065122498272L;
	private Integer id;
	private Integer purchaserId;
	private String alipayAccount;// 支付宝账号
	private String bankName;// 银行名称
	private String bankAccount;// 银行账号
	private String email;// email
	private Integer sex;// 性别，0:女性 1:男性
	private String birth;// 出生年月 yyyy-MM-dd
	private String address;// 地址
	private String idCardFrontUrl;// 身份证正面
	private String idCardBackUrl;// 身份证反面
	private String studentIdCardUrl;// 学生证
	private String vipCardUrl;// VIP会员卡
	private String creditCardAccUrl;// 信用卡对账
	private String liveForeverUrl;// 长久居住证，比如：意大利
	private String utilityBillUrl;// 三个月内的水电费账单
	private String mobileBillUrl;// 手机话费账单
	private String webServiceUrl;// 网络服务
	private String drivingLicenceUrl;// 所在城市驾驶证
	private String overseasProveUrl;// 海外工作证明文件
	private Long createTime;
	private Long modifyTime;

	public PurchaserInfoVo() {

	}

	public PurchaserInfoVo(Integer purchaserId, String alipayAccount,
			String bankName, String bankAccount, String email, Integer sex,
			String birth, String address, String idCardFrontUrl,
			String idCardBackUrl, String studentIdCardUrl, String vipCardUrl,
			String creditCardAccUrl, String liveForeverUrl,
			String utilityBillUrl, String mobileBillUrl, String webServiceUrl,
			String drivingLicenceUrl, String overseasProveUrl, Long createTime,
			Long modifyTime) {
		this.purchaserId = purchaserId;
		this.alipayAccount = alipayAccount;
		this.bankName = bankName;
		this.bankAccount = bankAccount;
		this.email = email;
		this.sex = sex;
		this.birth = birth;
		this.address = address;
		this.idCardFrontUrl = idCardFrontUrl;
		this.idCardBackUrl = idCardBackUrl;
		this.studentIdCardUrl = studentIdCardUrl;
		this.vipCardUrl = vipCardUrl;
		this.creditCardAccUrl = creditCardAccUrl;
		this.liveForeverUrl = liveForeverUrl;
		this.utilityBillUrl = utilityBillUrl;
		this.mobileBillUrl = mobileBillUrl;
		this.webServiceUrl = webServiceUrl;
		this.drivingLicenceUrl = drivingLicenceUrl;
		this.overseasProveUrl = overseasProveUrl;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(Integer purchaserId) {
		this.purchaserId = purchaserId;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdCardFrontUrl() {
		return idCardFrontUrl;
	}

	public void setIdCardFrontUrl(String idCardFrontUrl) {
		this.idCardFrontUrl = idCardFrontUrl;
	}

	public String getIdCardBackUrl() {
		return idCardBackUrl;
	}

	public void setIdCardBackUrl(String idCardBackUrl) {
		this.idCardBackUrl = idCardBackUrl;
	}

	public String getStudentIdCardUrl() {
		return studentIdCardUrl;
	}

	public void setStudentIdCardUrl(String studentIdCardUrl) {
		this.studentIdCardUrl = studentIdCardUrl;
	}

	public String getVipCardUrl() {
		return vipCardUrl;
	}

	public void setVipCardUrl(String vipCardUrl) {
		this.vipCardUrl = vipCardUrl;
	}

	public String getCreditCardAccUrl() {
		return creditCardAccUrl;
	}

	public void setCreditCardAccUrl(String creditCardAccUrl) {
		this.creditCardAccUrl = creditCardAccUrl;
	}

	public String getLiveForeverUrl() {
		return liveForeverUrl;
	}

	public void setLiveForeverUrl(String liveForeverUrl) {
		this.liveForeverUrl = liveForeverUrl;
	}

	public String getUtilityBillUrl() {
		return utilityBillUrl;
	}

	public void setUtilityBillUrl(String utilityBillUrl) {
		this.utilityBillUrl = utilityBillUrl;
	}

	public String getMobileBillUrl() {
		return mobileBillUrl;
	}

	public void setMobileBillUrl(String mobileBillUrl) {
		this.mobileBillUrl = mobileBillUrl;
	}

	public String getWebServiceUrl() {
		return webServiceUrl;
	}

	public void setWebServiceUrl(String webServiceUrl) {
		this.webServiceUrl = webServiceUrl;
	}

	public String getDrivingLicenceUrl() {
		return drivingLicenceUrl;
	}

	public void setDrivingLicenceUrl(String drivingLicenceUrl) {
		this.drivingLicenceUrl = drivingLicenceUrl;
	}

	public String getOverseasProveUrl() {
		return overseasProveUrl;
	}

	public void setOverseasProveUrl(String overseasProveUrl) {
		this.overseasProveUrl = overseasProveUrl;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

}
