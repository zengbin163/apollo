package com.haitao.apollo.vo.comment;

import java.io.Serializable;

public class PostrewardCommentVo implements Serializable {

	private static final long serialVersionUID = 7299123973508326851L;
	private Integer id;
	private Integer postrewardId;
	private Integer commenterId;
	private Integer beCommentedId;
	private String comment;
	private Integer isRead;

	public PostrewardCommentVo() {

	}

	public PostrewardCommentVo(Integer postrewardId, Integer commenterId, Integer beCommentedId, String comment,
			Integer isRead) {
		this.postrewardId = postrewardId;
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

	public Integer getPostrewardId() {
		return postrewardId;
	}

	public void setPostrewardId(Integer postrewardId) {
		this.postrewardId = postrewardId;
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

}
