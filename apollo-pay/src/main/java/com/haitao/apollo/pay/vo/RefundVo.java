/*
 *@Project: GZJK
 *@Author: zengbin
 *@Date: 2015年11月18日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pay.vo;

import java.io.Serializable;

/**
 * @ClassName: RefundVo
 * @Description: 退款输入
 * @author zengbin
 * @date 2015年12月07日 下午3:29:50
 */
public class RefundVo implements Serializable {

	private static final long serialVersionUID = 3093207553051078352L;
	private String apiKey; // API KEY
	private String appId; // appId
	private String orderNo;
	private Integer payOrderId;
	private String chargeId;// 预支付id
	private String description;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(Integer payOrderId) {
		this.payOrderId = payOrderId;
	}

}
