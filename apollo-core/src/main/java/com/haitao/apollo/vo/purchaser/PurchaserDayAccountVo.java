package com.haitao.apollo.vo.purchaser;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 买手账户流水账
 * 
 * @author zengbin
 *
 */
public class PurchaserDayAccountVo implements Serializable {

	private static final long serialVersionUID = -6982532558744785173L;
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
	
	public PurchaserDayAccountVo() {
		
	}
	
	public PurchaserDayAccountVo(Integer purchaserId, Integer postrewardId,
			Integer payType, Integer fundType, BigDecimal payAmount,
			String paySerialNo, Long createTime, Long modifyTime) {
		this.purchaserId = purchaserId;
		this.postrewardId = postrewardId;
		this.payType = payType;
		this.fundType = fundType;
		this.payAmount = payAmount;
		this.paySerialNo = paySerialNo;
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
}
