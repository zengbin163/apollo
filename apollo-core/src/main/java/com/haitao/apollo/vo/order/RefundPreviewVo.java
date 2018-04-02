/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月11日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.vo.order;

import java.io.Serializable;

/**
 * @ClassName: RefundPreviewVo
 * @Description: 发起退款记录Vo
 * @author zengbin
 * @date 2016年04月07日 下午14:41:10
 */
public class RefundPreviewVo implements Serializable {

	private static final long serialVersionUID = -3214991339749176333L;
	private Integer id;
	private Integer postrewardId;// 悬赏id
	private Integer payOrderId;// 支付订单id
	private String paySerialNo;// 第三方支付返回的支付流水号
	private Long createTime;
	private Long modifyTime;

	public RefundPreviewVo() {

	}

	public RefundPreviewVo(Integer postrewardId, Integer payOrderId,
			String paySerialNo, Long createTime, Long modifyTime) {
		this.postrewardId = postrewardId;
		this.payOrderId = payOrderId;
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
}
