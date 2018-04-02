package com.haitao.apollo.pojo.order;

import java.io.Serializable;

public class PostrewardCount implements Serializable {

	private static final long serialVersionUID = -3844617995513091162L;

	private Integer categoryId;// 类目id
	private Integer number;// 数量

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
