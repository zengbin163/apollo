package com.haitao.apollo.dao.purchaser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.purchaser.PurchaserDayAccount;
import com.haitao.apollo.vo.purchaser.PurchaserDayAccountVo;

/**
 * 买手账户交易记录
 * @author zengbin
 *
 */
public interface PurchaserDayAccountDao {
	Integer insertPurchaserDayAccount(PurchaserDayAccountVo purchaserDayAccountVo);
	List<PurchaserDayAccount> getMyDayAccountByPurchaserId(@Param(value = "purchaserId") Integer purchaserId, @Param(value = "month") Integer month, @Param(value = "page") Page<?> page);
}
