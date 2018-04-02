package com.haitao.apollo.pojo.message;

import java.io.Serializable;

public class ImMessage implements Serializable {
	private static final long serialVersionUID = -1260337450090311355L;
	private Integer id;
	private Integer fromUserId;
	private Integer toUserId;
	private Integer fromUserRole;
	private Integer toUserRole;
	private String content;
	private Long createTime;
	private String fromNickName;
	private String toNickName;
	private String fromHeaderUrl;
	private String toHeaderUrl;

	public ImMessage() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getFromNickName() {
		return fromNickName;
	}

	public void setFromNickName(String fromNickName) {
		this.fromNickName = fromNickName;
	}

	public String getToNickName() {
		return toNickName;
	}

	public void setToNickName(String toNickName) {
		this.toNickName = toNickName;
	}

	public String getFromHeaderUrl() {
		return fromHeaderUrl;
	}

	public void setFromHeaderUrl(String fromHeaderUrl) {
		this.fromHeaderUrl = fromHeaderUrl;
	}

	public String getToHeaderUrl() {
		return toHeaderUrl;
	}

	public void setToHeaderUrl(String toHeaderUrl) {
		this.toHeaderUrl = toHeaderUrl;
	}

	public Integer getFromUserRole() {
		return fromUserRole;
	}

	public void setFromUserRole(Integer fromUserRole) {
		this.fromUserRole = fromUserRole;
	}

	public Integer getToUserRole() {
		return toUserRole;
	}

	public void setToUserRole(Integer toUserRole) {
		this.toUserRole = toUserRole;
	}
}
