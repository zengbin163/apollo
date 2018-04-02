package com.haitao.apollo.vo.user;

import java.io.Serializable;

public class ReceiverVo implements Serializable {

	private static final long serialVersionUID = -1243505311335753161L;
	private Integer id;
	private Integer userId;
	private String receiver;
	private String receiverMobile;
	private String province;
	private String city;
	private String address;
	private String postcode;
	private Integer isDefault;

	public ReceiverVo(){
		
	}
	
	public ReceiverVo(Integer userId, String receiver, String receiverMobile,
			String province, String city, String address, String postcode,
			Integer isDefault) {
		this.userId = userId;
		this.receiver = receiver;
		this.receiverMobile = receiverMobile;
		this.province = province;
		this.city = city;
		this.address = address;
		this.postcode = postcode;
		this.isDefault = isDefault;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
}
