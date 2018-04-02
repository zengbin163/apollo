package com.haitao.apollo.pojo.message;

import java.io.Serializable;

public class MessageBox implements Serializable {
	private static final long serialVersionUID = -432004617295644316L;

	private Integer id;
	private Integer boxType;
	private Integer operatorRole; // @OperatorRoleEnum
	private Integer operatorId;
	private String boxContent;
	private Long createTime;
	private Long modifyTime;

	public MessageBox() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBoxType() {
		return boxType;
	}

	public void setBoxType(Integer boxType) {
		this.boxType = boxType;
	}

	public Integer getOperatorRole() {
		return operatorRole;
	}

	public void setOperatorRole(Integer operatorRole) {
		this.operatorRole = operatorRole;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getBoxContent() {
		return boxContent;
	}

	public void setBoxContent(String boxContent) {
		this.boxContent = boxContent;
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

}
