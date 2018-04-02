package com.haitao.apollo.pojo.purchaser;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 买手账户提现记录
 * 
 * @author zengbin
 *
 */
public class PurchaserCash implements Serializable {

	private static final long serialVersionUID = 6735910838666713643L;

	private Integer id;
	private Integer purchaserId;
	private Integer fundType;
	private BigDecimal cash;//提现金额，单位分
	private Integer applyStatus; // 申请状态 0申请中 1完结
	private Long createTime;
	private Long finishTime;
	private Long modifyTime;

	private String purchaserName;//买手名称
	private String alipayAccount;//支付宝账号
	private String bankAccount;//银行账号
	private BigDecimal cashYuan;//提现金额，单位元

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

	public Integer getFundType() {
		return fundType;
	}

	public void setFundType(Integer fundType) {
		this.fundType = fundType;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
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

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public Long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public BigDecimal getCashYuan() {
		return cashYuan;
	}

	public void setCashYuan(BigDecimal cashYuan) {
		this.cashYuan = cashYuan;
	}
}
