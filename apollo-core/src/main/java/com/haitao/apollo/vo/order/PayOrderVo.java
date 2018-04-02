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
 * @ClassName: PayOrderVo
 * @Description: 交易订单Vo
 * @author zengbin
 * @date 2015年11月17日 下午15:15:10
 */
public class PayOrderVo implements Serializable {

	private static final long serialVersionUID = -5679124414297541358L;
	private Integer id;
	private Integer postrewardId;// 悬赏id
	private Integer userId;
	private Integer payType;// 支付类型 0:alipay 1:weixin
	private Integer fundType;// 款项类型     0:消费者支付定金、1:消费者支付尾款、2:消费者支付全款、3:买手充值保证金、4:买手申请提现、5:消费者退定金、6:消费者退尾款
	private BigDecimal payAmount;// 支付金额
	private String paySerialNo;// 支付序列号
	private BigDecimal bigMoney;// 大牌币
	private Integer payByBig;// 是否由大牌币全额支付 0：否 1：是
	private Integer isDeleted;
	private Long createTime;// 创建时间
	private Long modifyTime;// 修改时间

	public PayOrderVo() {

	}

	public PayOrderVo(Integer postrewardId, Integer userId, Integer payType,
			Integer fundType, BigDecimal payAmount, BigDecimal bigMoney,
			Integer payByBig, String paySerialNo, Long createTime,
			Long modifyTime, Integer isDeleted) {
		this.postrewardId = postrewardId;
		this.userId = userId;
		this.payType = payType;
		this.fundType = fundType;
		this.payAmount = payAmount;
		this.paySerialNo = paySerialNo;
		this.bigMoney = bigMoney;
		this.payByBig = payByBig;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.isDeleted = isDeleted;
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

	public BigDecimal getBigMoney() {
		return bigMoney;
	}

	public void setBigMoney(BigDecimal bigMoney) {
		this.bigMoney = bigMoney;
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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getPayByBig() {
		return payByBig;
	}

	public void setPayByBig(Integer payByBig) {
		this.payByBig = payByBig;
	}
}
