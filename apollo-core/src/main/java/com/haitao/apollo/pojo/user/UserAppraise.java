package com.haitao.apollo.pojo.user;

import java.io.Serializable;

/**
 * 给买手点赞记录
 * 
 * @author zengbin
 *
 */
public class UserAppraise implements Serializable {

	private static final long serialVersionUID = 7958929002199841626L;
	private Integer id;
	private Integer userId;
	private Integer purchaserId;
	private Integer orderId;
	private Integer isAnonym;
	private Integer score;
	private String content;
	private Long createTime;
	private Long modifyTime;

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
