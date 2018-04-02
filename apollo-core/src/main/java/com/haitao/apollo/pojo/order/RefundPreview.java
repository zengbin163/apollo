/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月11日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pojo.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: RefundPreview
 * @Description: 发起退款记录
 * @author zengbin
 * @date 2016年04月07日 下午14:41:10
 */
public class RefundPreview implements Serializable {

	private static final long serialVersionUID = -5882208290388313380L;
	private Integer id;
	private Integer postrewardId;// 悬赏id
	private Integer payOrderId;// 支付订单id
	private String paySerialNo;// 第三方支付返回的支付流水号
	private BigDecimal payAmount;// 支付金额
	private BigDecimal bigMoney;// 大牌币
	private Long createTime;
	private Long modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPostrewardId() {
		return postrewardId;
	}

	public void setPostrewardId(Integer postrewardId) {
		this.postrewardId = postrewardId;
	}

	public Integer getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(Integer payOrderId) {
		this.payOrderId = payOrderId;
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

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getBigMoney() {
		return bigMoney;
	}

	public void setBigMoney(BigDecimal bigMoney) {
		this.bigMoney = bigMoney;
	}
}
