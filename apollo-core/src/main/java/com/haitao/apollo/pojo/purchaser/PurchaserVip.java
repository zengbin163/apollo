package com.haitao.apollo.pojo.purchaser;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 买手等级配置表
 * @author zengbin
 *
 */
public class PurchaserVip implements Serializable {

	private static final long serialVersionUID = -1491714215666131422L;
	private Integer id;
	private Integer vip; //vip等级
	private BigDecimal guarantee; //保证金
	private BigDecimal quota; //接单额度
	private Integer quantity; //买手接单量

	public PurchaserVip() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	public BigDecimal getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(BigDecimal guarantee) {
		this.guarantee = guarantee;
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
}
