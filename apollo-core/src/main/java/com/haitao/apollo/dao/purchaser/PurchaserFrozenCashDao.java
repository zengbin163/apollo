package com.haitao.apollo.dao.purchaser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.purchaser.PurchaserFrozenCash;
import com.haitao.apollo.vo.purchaser.PurchaserFrozenCashVo;

/**
 * 买手冻结保证金DAO
 * @author zengbin
 *
 */
public interface PurchaserFrozenCashDao {
	Integer insertPurchaserFrozenCash(PurchaserFrozenCashVo purchaserFrozenCashVo);
	List<PurchaserFrozenCash> getPurchaserFrozenCash(@Param(value = "purchaserId") Integer purchaserId,@Param(value = "postrewardId") Integer postrewardId);
	List<PurchaserFrozenCash> getUnPayPurchaserFrozenCash(@Param(value = "purchaserId") Integer purchaserId,@Param(value = "postrewardId") Integer postrewardId);
	List<PurchaserFrozenCash> getMyFrozenGuaranteeListByPurchaserId(@Param(value = "purchaserId") Integer purchaserId, @Param(value = "page") Page<?> page);
	Integer finishPurchaserFrozenCash(@Param(value = "purchaserId") Integer purchaserId,@Param(value = "postrewardId") Integer postrewardId);
}
