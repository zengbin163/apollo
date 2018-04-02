package com.haitao.apollo.vo.comment;

import java.io.Serializable;

public class ShowOrderCommentVo implements Serializable {

	private static final long serialVersionUID = 961587094415858952L;
	private Integer id;
	private Integer showOrderId;
	private Integer commenterId;
	private Integer beCommentedId;
	private String comment;
	private Integer isRead;
	private Long createTime;

	public ShowOrderCommentVo() {

	}

	public ShowOrderCommentVo(Integer showOrderId, Integer commenterId, Integer beCommentedId, String comment,
			Integer isRead) {
		this.showOrderId = showOrderId;
		this.commenterId = commenterId;
		this.beCommentedId = beCommentedId;
		this.comment = comment;
		this.isRead = isRead;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShowOrderId() {
		return showOrderId;
	}

	public void setShowOrderId(Integer showOrderId) {
		this.showOrderId = showOrderId;
	}

	public Integer getCommenterId() {
		return commenterId;
	}

	public void setCommenterId(Integer commenterId) {
		this.commenterId = commenterId;
	}

	public Integer getBeCommentedId() {
		return beCommentedId;
	}

	public void setBeCommentedId(Integer beCommentedId) {
		this.beCommentedId = beCommentedId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

}
