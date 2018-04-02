package com.haitao.apollo.vo.purchaser;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 买手被冻结的保证金
 * 
 * @author zengbin
 *
 */
public class PurchaserFrozenCashVo implements Serializable {

	private static final long serialVersionUID = 4527890703308375744L;
	private Integer id;
	private Integer purchaserId;
	private Integer postrewardId;
	private BigDecimal frozenAmount;
	private Integer cause; // 冻结原因 0:延期发货
	private Integer payStatus;// 赔偿状态 0:未赔付 1:已赔付
	private Long createTime;
	private Long modifyTime;

	public PurchaserFrozenCashVo() {

	}

	public PurchaserFrozenCashVo(Integer purchaserId, Integer postrewardId,
			BigDecimal frozenAmount, Integer cause, Integer payStatus,
			Long createTime, Long modifyTime) {
		this.purchaserId = purchaserId;
		this.postrewardId = postrewardId;
		this.frozenAmount = frozenAmount;
		this.cause = cause;
		this.payStatus = payStatus;
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

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public Integer getCause() {
		return cause;
	}

	public void setCause(Integer cause) {
		this.cause = cause;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
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
