package com.haitao.apollo.pay.pojo;

import java.io.Serializable;

import com.pingplusplus.model.Charge;

/**
 * 第三方支付对象
 * 
 * @author zengbin
 *
 */
public class ThirdPay implements Serializable {

	private static final long serialVersionUID = -2674469348097170930L;

	private Integer postrewardId;// 悬赏id
	private Charge charge;

	public ThirdPay() {

	}

	public ThirdPay(Integer postrewardId, Charge charge) {
		this.postrewardId = postrewardId;
		this.charge = charge;
	}

	public static ThirdPay create(Integer postrewardId, Charge charge) {
		return new ThirdPay(postrewardId, charge);
	}

	public Integer getPostrewardId() {
		return postrewardId;
	}

	public void setPostrewardId(Integer postrewardId) {
		this.postrewardId = postrewardId;
	}

	public Charge getCharge() {
		return charge;
	}

	public void setCharge(Charge charge) {
		this.charge = charge;
	}
}
