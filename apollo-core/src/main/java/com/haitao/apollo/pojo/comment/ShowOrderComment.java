package com.haitao.apollo.pojo.comment;

import java.io.Serializable;

public class ShowOrderComment implements Serializable {

	private static final long serialVersionUID = 961587094415858952L;
	private Integer id;
	private Integer showOrderId;
	private Integer commenterId;
	private Integer beCommentedId;
	private String comment;
	private Integer isRead;
	private Long createTime;
	private String commenterNickName;
	private String commenterHeaderUrl;
	private String beCommentedNickName;
	private String beCommentedHeaderUrl;

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

	public String getCommenterNickName() {
		return commenterNickName;
	}

	public void setCommenterNickName(String commenterNickName) {
		this.commenterNickName = commenterNickName;
	}

	public String getCommenterHeaderUrl() {
		return commenterHeaderUrl;
	}

	public void setCommenterHeaderUrl(String commenterHeaderUrl) {
		this.commenterHeaderUrl = commenterHeaderUrl;
	}

	public String getBeCommentedNickName() {
		return beCommentedNickName;
	}

	public void setBeCommentedNickName(String beCommentedNickName) {
		this.beCommentedNickName = beCommentedNickName;
	}

	public String getBeCommentedHeaderUrl() {
		return beCommentedHeaderUrl;
	}

	public void setBeCommentedHeaderUrl(String beCommentedHeaderUrl) {
		this.beCommentedHeaderUrl = beCommentedHeaderUrl;
	}
	
}
