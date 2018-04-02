package com.haitao.apollo.pojo.order;

import java.io.Serializable;

public class OrderCount implements Serializable {

	private static final long serialVersionUID = -3455189233687541976L;

	private Integer allStatus;// 各种状态值的组合   101待接单，102待确认，103待发货，104待收货，105已完成，500待评价，301退款售后
	private Integer number;// 数量

	public OrderCount() {

	}

	public OrderCount(Integer allStatus, Integer number) {
		this.allStatus = allStatus;
		this.number = number;
	}

	public Integer getAllStatus() {
		return allStatus;
	}

	public void setAllStatus(Integer allStatus) {
		this.allStatus = allStatus;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
