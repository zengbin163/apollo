package com.haitao.apollo.vo.praise;

import java.io.Serializable;

public class ShowOrderPraiseVo implements Serializable {

	private static final long serialVersionUID = -8980463308684225603L;
	private Integer id;
	private Integer showOrderId;
	private Integer praiserId;
	private Integer isRead;
	private Long createTime;

	public ShowOrderPraiseVo() {

	}

	public ShowOrderPraiseVo(Integer showOrderId, Integer praiserId, Integer isRead) {
		this.showOrderId = showOrderId;
		this.praiserId = praiserId;
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

	public Integer getPraiserId() {
		return praiserId;
	}

	public void setPraiserId(Integer praiserId) {
		this.praiserId = praiserId;
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
