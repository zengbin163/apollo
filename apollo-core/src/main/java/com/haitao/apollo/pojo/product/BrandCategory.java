/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月19日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pojo.product;

import java.io.Serializable;

/**
 * @ClassName: BrandCategory
 * @Description: 品牌类目中间表
 * @author zengbin
 * @date 2016年01月29日 下午4:25:05
 */
public class BrandCategory implements Serializable {

	private static final long serialVersionUID = 908229969696263527L;
	private Integer id;
	private Integer brandId;
	private String brandName;
	private Integer categoryId;
	private String categoryName;
	private Long createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
