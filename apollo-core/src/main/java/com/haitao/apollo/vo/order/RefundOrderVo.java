/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月11日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.vo.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: RefundOrderVo
 * @Description: 退款订单Vo
 * @author zengbin
 * @date 2015年12月10日 下午14:41:10
 */
public class RefundOrderVo implements Serializable {

	private static final long serialVersionUID = -3214991339749176333L;
	private Integer id;
	private Integer postrewardId;// 悬赏id
	private Integer userId;
	private Integer payOrderId;// 支付订单id
	private BigDecimal refundAmount;// 退款金额
	private String paySerialNo;// 第三方支付返回的支付流水号
	
	public RefundOrderVo() {

	}

	public RefundOrderVo(Integer postrewardId, Integer userId,
			Integer payOrderId, BigDecimal refundAmount, String paySerialNo) {
		this.postrewardId = postrewardId;
		this.userId = userId;
		this.payOrderId = payOrderId;
		this.refundAmount = refundAmount;
		this.paySerialNo = paySerialNo;
	}

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(Integer payOrderId) {
		this.payOrderId = payOrderId;
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
}
