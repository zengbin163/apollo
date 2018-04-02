package com.haitao.apollo.service.purchaser.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.purchaser.PurchaserCashDao;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.purchaser.PurchaserCash;
import com.haitao.apollo.service.purchaser.PurchaserCashService;
import com.haitao.apollo.status.ApplyStatus;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.purchaser.PurchaserCashVo;

@Service
public class PurchaserCashServiceImpl implements PurchaserCashService {
	
	@Resource(name = "purchaserCashDao")
	private PurchaserCashDao purchaserCashDao;

	@Override
	@Transactional
	public Integer applyCash(BigDecimal cash) {
		Assert.notNull(cash, "买手申请的现金不能为空");
		
		if(BigDecimal.ZERO.compareTo(cash)==0) {
			throw new IllegalArgumentException("买手申请的现金不能为0");
		}
		Integer purchaserId = SessionContainer.getSession().getOperatorId();
		Long currentTime = DateUtil.currentUTCTime();
		PurchaserCashVo purchaserCashVo = new PurchaserCashVo(purchaserId, cash, FundTypeEnum.CASH.getCode(), ApplyStatus.IN_APPLY, currentTime, currentTime);
		this.purchaserCashDao.insertPurchaserCash(purchaserCashVo);
		if(null == purchaserCashVo.getId()) {
			throw new ApolloBizException(ResultCode.SAVE_FAIL, purchaserId, String.format("买手申请提现或者转账失败[purchaserId=%s]", purchaserId));
		}
		return purchaserCashVo.getId();
	}

	public List<PurchaserCash> getPurchaserCashListByApplyStatus(Integer applyStatus, Integer pageOffset, Integer pageSize) {
		Assert.notNull(applyStatus, "申请状态不能为空");
		Assert.notNull(pageOffset, "页码不能为空");
		Assert.notNull(pageSize, "分页数量不能为空");
		if (!ApplyStatus.IN_APPLY.equals(applyStatus)
				&& !ApplyStatus.APPLY_FINISH.equals(applyStatus)) {
			throw new ApolloBizException(ResultCode.ILLEGAL_ARGUMENT, -10000, String.format("申请状态只能为申请中或者申请已完成[applyStatus=%s]", applyStatus));
		}
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy(Page.ORDER_BY_CREATE_TIME);
		return this.purchaserCashDao.getPurchaserCashListByApplyStatus(applyStatus, page);
	}
	
	public Integer finishPurchaserCash(Integer id) {
		Assert.notNull(id, "买手提现记录id不能为空");
		Integer flag = this.purchaserCashDao.finishPurchaserCash(id);
		if(flag<=0) {
			throw new ApolloBizException(ResultCode.UPDATE_FAIL, -10000, String.format("完成提现记录单失败[id=%s]", id));
		}
		return flag;
	}
}
