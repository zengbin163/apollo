package com.haitao.apollo.pojo.user;

import java.io.Serializable;

public class UserRights implements Serializable {

	private static final long serialVersionUID = -6551458489247617934L;

	private Integer id;
	private Integer userId;
	private Integer purchaserId;
	private Integer orderId;
	private String reason;
	private String picAddr1;
	private String picAddr2;
	private String picAddr3;
	private Integer csStatus;//客服状态：1客服已介入 2完结 3已取消     @CsStatus
	private Long createTime;
	private Long modifyTime;

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

	public Integer getCsStatus() {
		return csStatus;
	}

	public void setCsStatus(Integer csStatus) {
		this.csStatus = csStatus;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPicAddr1() {
		return picAddr1;
	}

	public void setPicAddr1(String picAddr1) {
		this.picAddr1 = picAddr1;
	}

	public String getPicAddr2() {
		return picAddr2;
	}

	public void setPicAddr2(String picAddr2) {
		this.picAddr2 = picAddr2;
	}

	public String getPicAddr3() {
		return picAddr3;
	}

	public void setPicAddr3(String picAddr3) {
		this.picAddr3 = picAddr3;
	}

}
