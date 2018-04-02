package com.haitao.apollo.pojo.purchaser;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 买手账户流水账
 * 
 * @author zengbin
 *
 */
public class PurchaserDayAccount implements Serializable {

	private static final long serialVersionUID = 5262975795724351396L;

	private Integer id;
	private Integer purchaserId;
	private Integer postrewardId;
	private Integer payType; // 支付类型 0:alipay 1:weixin
	private Integer fundType;
	private Integer isDeleted;
	private BigDecimal payAmount;
	private String paySerialNo;
	private Long createTime;
	private Long modifyTime;
	private String desc;
	private Integer inOrOut;//-1出账，1入账  @InOrOutEnum

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

	public Integer getPostrewardId() {
		return postrewardId;
	}

	public void setPostrewardId(Integer postrewardId) {
		this.postrewardId = postrewardId;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getFundType() {
		return fundType;
	}

	public void setFundType(Integer fundType) {
		this.fundType = fundType;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getPaySerialNo() {
		return paySerialNo;
	}

	public void setPaySerialNo(String paySerialNo) {
		this.paySerialNo = paySerialNo;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getInOrOut() {
		return inOrOut;
	}

	public void setInOrOut(Integer inOrOut) {
		this.inOrOut = inOrOut;
	}
}
