package com.haitao.apollo.dao.purchaser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.purchaser.PurchaserCash;
import com.haitao.apollo.vo.purchaser.PurchaserCashVo;

/**
 * 买手提现DAO
 * @author zengbin
 *
 */
public interface PurchaserCashDao {
	Integer insertPurchaserCash(PurchaserCashVo purchaserCashVo);
	List<PurchaserCash> getPurchaserCashListByApplyStatus(@Param(value = "applyStatus") Integer applyStatus, @Param(value = "page") Page<?> page);
	Integer finishPurchaserCash(Integer id);
}
