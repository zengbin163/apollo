package com.haitao.apollo.vo.product;

import java.io.Serializable;

public class PurchaserCategoryVo implements Serializable {

	private static final long serialVersionUID = -2066045040276821900L;

	private Integer id;
	private Integer purchaserId;
	private Integer categoryId;
	private Integer isDeleted;
	private Long createTime;
	private Long modifyTime;
	
	public PurchaserCategoryVo(){
		
	}

	public PurchaserCategoryVo(Integer purchaserId, Integer categoryId,
			Integer isDeleted, Long createTime, Long modifyTime) {
		this.purchaserId = purchaserId;
		this.categoryId = categoryId;
		this.isDeleted = isDeleted;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(Integer purchaserId) {
		this.purchaserId = purchaserId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}
}
