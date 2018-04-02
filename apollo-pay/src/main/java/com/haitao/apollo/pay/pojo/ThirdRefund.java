package com.haitao.apollo.pay.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ThirdRefund implements Serializable {

	private static final long serialVersionUID = 3452350649456837702L;

	private Integer postrewardId;// 悬赏id
	private BigDecimal refundAmount; // 退款金额
	private String paySerialNo; // 第三方支付流水号，charge对象的id
	private BigDecimal bigMoney;//大牌币
	
	public ThirdRefund() {
		
	}
	
	public ThirdRefund(Integer postrewardId, BigDecimal refundAmount, String paySerialNo, BigDecimal bigMoney) {
		this.postrewardId = postrewardId;
		this.refundAmount = refundAmount;
		this.paySerialNo = paySerialNo;
		this.bigMoney = bigMoney;
	}
	
	public static ThirdRefund create(Integer postrewardId, BigDecimal refundAmount, String paySerialNo, BigDecimal bigMoney) {
		return new ThirdRefund(postrewardId, refundAmount, paySerialNo, bigMoney);
	}
	
	public Integer getPostrewardId() {
		return postrewardId;
	}

	public void setPostrewardId(Integer postrewardId) {
		this.postrewardId = postrewardId;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getPaySerialNo() {
		return paySerialNo;
	}

	public void setPaySerialNo(String paySerialNo) {
		this.paySerialNo = paySerialNo;
	}

	public BigDecimal getBigMoney() {
		return bigMoney;
	}

	public void setBigMoney(BigDecimal bigMoney) {
		this.bigMoney = bigMoney;
	}
}
