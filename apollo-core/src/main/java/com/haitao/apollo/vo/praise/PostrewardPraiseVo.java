package com.haitao.apollo.vo.praise;

import java.io.Serializable;

public class PostrewardPraiseVo implements Serializable {
	private static final long serialVersionUID = -2230113519898069865L;

	private Integer id;
	private Integer postrewardId;
	private Integer praiserId;
	private Integer isRead;
	private Long createTime;
	
	public PostrewardPraiseVo(){
		
	}

	public PostrewardPraiseVo(Integer postrewardId, Integer praiserId, Integer isRead) {
		this.postrewardId = postrewardId;
		this.praiserId = praiserId;
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
