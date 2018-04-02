package com.haitao.apollo.vo.user;

import java.io.Serializable;

public class UserAppraiseVo implements Serializable {

	private static final long serialVersionUID = -8509412977313115252L;
	private Integer id;
	private Integer userId;
	private Integer purchaserId;
	private Integer isAnonym;
	private Integer orderId;
	private Integer score;
	private String content;
	private Long createTime;
	private Long modifyTime;

	public UserAppraiseVo() {

	}

	public UserAppraiseVo(Integer userId, Integer purchaserId,
			Integer isAnonym, Integer orderId, Integer score, String content,
			Long createTime, Long modifyTime) {
		this.userId = userId;
		this.purchaserId = purchaserId;
		this.orderId = orderId;
		this.isAnonym = isAnonym;
		this.score = score;
		this.content = content;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
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

	public Integer getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(Integer purchaserId) {
		this.purchaserId = purchaserId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public Integer getIsAnonym() {
		return isAnonym;
	}

	public void setIsAnonym(Integer isAnonym) {
		this.isAnonym = isAnonym;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
