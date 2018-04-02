package com.haitao.apollo.service.purchaser;

import java.math.BigDecimal;
import java.util.List;

import com.haitao.apollo.pojo.purchaser.PurchaserVip;

public interface PurchaserVipService {
	
	/**
	 * 根据vip等级查询买手所属哪个等级
	 * @param vip
	 * @return
	 */
	public PurchaserVip getPurchaserVip(Integer vip);
	
	/**
	 * 查询所有买手晋级准则
	 * @return
	 */
	public List<PurchaserVip> getAllPurchaserVip();

	/**
	 * 升级买手VIP等级
	 * @param purchaserId 买手id
	 * @param rechargeGuarantee 买手充值的保证金，单位分
	 * @return 升级成功返回true，升级失败返回false
	 */
	boolean upgradePurchaserVip(Integer purchaserId, BigDecimal rechargeGuarantee); 	
}
