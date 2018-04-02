/*
 *@Project: GZJK
 *@Author: zengbin
 *@Date: 2015年11月18日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pay.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: PayVo
 * @Description: 支付输入
 * @author zengbin
 * @date 2015年11月18日 下午3:29:50
 */
public class PayVo implements Serializable {

	private static final long serialVersionUID = -500084518193796930L;
	private String apiKey; // API KEY
	private String appId; // appId
	private BigDecimal amount;// 支付金额
	private BigDecimal bigMoney;// 大牌币
	private String currency;// 币种 例如：cny
	private String subject;
	private String body;
	private String orderNo;
	private String channel;
	private String clientIp;
	private Integer fundType;
	
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Integer getFundType() {
		return fundType;
	}

	public void setFundType(Integer fundType) {
		this.fundType = fundType;
	}

	public BigDecimal getBigMoney() {
		return bigMoney;
	}

	public void setBigMoney(BigDecimal bigMoney) {
		this.bigMoney = bigMoney;
	}
}
