/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月19日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pojo.product;

import java.io.Serializable;

/**
 * @ClassName: Brand
 * @Description: 品牌
 * @author zengbin
 * @date 2016年01月29日 下午3:25:05
 */
public class Brand implements Serializable {

	private static final long serialVersionUID = -4439896007230354635L;
	private Integer id;
	private String brandName;
	private Long createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
