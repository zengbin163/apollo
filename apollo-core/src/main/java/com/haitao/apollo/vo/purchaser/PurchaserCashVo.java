package com.haitao.apollo.vo.purchaser;

import java.io.Serializable;
import java.math.BigDecimal;

public class PurchaserCashVo implements Serializable {

	private static final long serialVersionUID = 4017083798672306529L;
	private Integer id;
	private Integer purchaserId;
	private Integer fundType;
	private BigDecimal cash;
	private Integer applyStatus; //申请状态   0申请中    1完结
	private Long createTime;
	private Long finishTime;
	private Long modifyTime;

	public PurchaserCashVo() {

	}

	public PurchaserCashVo(Integer purchaserId, BigDecimal cash,
			Integer fundType, Integer applyStatus, Long createTime,
			Long modifyTime) {
		this.purchaserId = purchaserId;
		this.cash = cash;
		this.fundType = fundType;
		this.applyStatus = applyStatus;
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
}
