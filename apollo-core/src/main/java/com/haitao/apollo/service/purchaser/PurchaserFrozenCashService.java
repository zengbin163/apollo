package com.haitao.apollo.service.purchaser;

import java.math.BigDecimal;
import java.util.List;

import com.haitao.apollo.pojo.purchaser.PurchaserFrozenCash;

/**
 * 买手冻结保证金账户
 * @author zengbin
 *
 */
public interface PurchaserFrozenCashService {
	/**
	 * 买手被处罚时，冻结买手保证金账户
	 * @param purchaserId
	 * @param postrewardId
	 * @param frozenAmount  单位分
	 * @param cause
	 * @param payStatus
	 * @return
	 */
	Integer createPurchaserFrozenCash(Integer purchaserId, Integer postrewardId, BigDecimal frozenAmount, Integer cause, Integer payStatus);
	
	/**
	 * 查询买手某个悬赏下面<P>所有<P>被处罚时冻结的保证金数
	 * @param purchaserId
	 * @param postrewardId
	 * @return
	 */
	List<PurchaserFrozenCash> getPurchaserFrozenCash(Integer purchaserId, Integer postrewardId);

	/**
	 * 查询买手某个悬赏下面<P>未赔付<P>被处罚时冻结的保证金数
	 * @param purchaserId
	 * @param postrewardId
	 * @return
	 */
	List<PurchaserFrozenCash> getUnPayPurchaserFrozenCash(Integer purchaserId, Integer postrewardId);
	
	/**
	 * 查询买手的保证金冻结情况
	 * @param purchaserId
	 * @return
	 */
	List<PurchaserFrozenCash> getMyFrozenGuaranteeListByPurchaserId(Integer purchaserId, Integer pageOffset, Integer pageSize);
	
	/**
	 * 修改当前买手下某悬赏下面的冻结保证金为已赔付
	 * @param purchaserId
	 * @param postrewardId
	 * @return
	 */
	Integer finishPurchaserFrozenCash(Integer purchaserId, Integer postrewardId);
}
