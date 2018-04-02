package com.haitao.apollo.service.order.impl.show;

import org.springframework.stereotype.Component;

import com.haitao.apollo.enums.ShowSourceEnum;

@Component
public class PurchaserShowStrategy extends AbstractShowStrategy {

	@Override
	public void execute(Integer orderId, Integer role) {
		if(!ShowSourceEnum.PURCHASER.getCode().equals(role)){
			return;
		}
		//买手晒单不做限制，后期考虑增加限制
	}

}
