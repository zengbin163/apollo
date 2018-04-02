package com.haitao.apollo.service.purchaser;

import java.math.BigDecimal;
import java.util.List;

import com.haitao.apollo.pojo.purchaser.PurchaserCash;

/**
 * 买手提现记录
 * @author zengbin
 *
 */
public interface PurchaserCashService {
	
	Integer applyCash(BigDecimal cash);
	List<PurchaserCash> getPurchaserCashListByApplyStatus(Integer applyStatus, Integer pageOffset, Integer pageSize);
	Integer finishPurchaserCash(Integer id);
}
