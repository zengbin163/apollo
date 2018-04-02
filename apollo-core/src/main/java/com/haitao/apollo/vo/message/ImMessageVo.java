package com.haitao.apollo.vo.message;

import java.io.Serializable;

public class ImMessageVo implements Serializable {
	private static final long serialVersionUID = -432004617295644316L;

	private Integer id;
	private Integer fromUserId;
	private Integer toUserId;
	private Integer fromUserRole;
	private Integer toUserRole;
	private String content;
	private Long createTime;

	public ImMessageVo() {

	}

	public ImMessageVo(Integer fromUserId, Integer toUserId,
			Integer fromUserRole, Integer toUserRole, String content,
			Long createTime) {
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.fromUserRole = fromUserRole;
		this.toUserRole = toUserRole;
		this.content = content;
		this.createTime = createTime;
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
