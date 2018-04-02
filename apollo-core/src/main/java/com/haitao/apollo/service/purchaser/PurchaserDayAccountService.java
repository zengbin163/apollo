package com.haitao.apollo.service.purchaser;

import java.math.BigDecimal;
import java.util.List;

import com.haitao.apollo.pojo.purchaser.PurchaserDayAccount;

/**
 * 买手账户交易记录
 * @author zengbin
 *
 */
public interface PurchaserDayAccountService {
	/**
	 * 新增买手账户流水
	 * @param purchaserId
	 * @param userId
	 * @param postrewardId
	 * @param payType
	 * @param fundType
	 * @param payAmount
	 * @param paySerialNo
	 * @return
	 */
	Integer createDayAccount(Integer purchaserId, Integer userId, Integer postrewardId, Integer payType, Integer fundType, BigDecimal payAmount, String paySerialNo);
	
	List<PurchaserDayAccount> myBillList(Integer purchaserId, Integer month, Integer pageOffset, Integer pageSize);
}
