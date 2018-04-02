package com.haitao.apollo.service.purchaser.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.purchaser.PurchaserFrozenCashDao;
import com.haitao.apollo.enums.FrozenCashCauseEnum;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.global.GlobalConstants;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.purchaser.PurchaserFrozenCash;
import com.haitao.apollo.service.purchaser.PurchaserFrozenCashService;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.purchaser.PurchaserFrozenCashVo;

@Service
public class PurchaserFrozenCashServiceImpl implements PurchaserFrozenCashService {

	@Resource(name="purchaserFrozenCashDao")
	private PurchaserFrozenCashDao purchaserFrozenCashDao;
	
	@Override
	public Integer createPurchaserFrozenCash(Integer purchaserId,
			Integer postrewardId, BigDecimal frozenAmount, Integer cause,
			Integer payStatus) {
		Long currentTime = DateUtil.currentUTCTime();
		PurchaserFrozenCashVo purchaserFrozenCashVo = new PurchaserFrozenCashVo(purchaserId, postrewardId, frozenAmount, cause, payStatus, currentTime, currentTime);
		this.purchaserFrozenCashDao.insertPurchaserFrozenCash(purchaserFrozenCashVo);
		if(null == purchaserFrozenCashVo.getId()) {
			throw new ApolloBizException(ResultCode.SAVE_FAIL, purchaserId, String.format("冻结买手账户资金失败[purchaserId=%s]", purchaserId));
		}
		return purchaserFrozenCashVo.getId();
	}

	@Override
	public List<PurchaserFrozenCash> getPurchaserFrozenCash(Integer purchaserId, Integer postrewardId) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Assert.notNull(postrewardId, "悬赏id不能为空");
		return this.purchaserFrozenCashDao.getPurchaserFrozenCash(purchaserId, postrewardId);
	}
	
	@Override
	public List<PurchaserFrozenCash> getUnPayPurchaserFrozenCash(Integer purchaserId, Integer postrewardId) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Assert.notNull(postrewardId, "悬赏id不能为空");
		return this.purchaserFrozenCashDao.getUnPayPurchaserFrozenCash(purchaserId, postrewardId);
	}
	
	@Override
	public List<PurchaserFrozenCash> getMyFrozenGuaranteeListByPurchaserId(Integer purchaserId, Integer pageOffset, Integer pageSize) {
		Assert.notNull(purchaserId, "买手id不能为空");
    	Assert.notNull(pageOffset,"分页起始页码不能为空");
    	Assert.notNull(pageSize,"分页每页的总数不能为空");
    	Page<?> page = new Page<>();
    	page.setPageNo(pageOffset);
    	page.setPageSize(pageSize);
    	page.setOrder(Page.DESC);
    	page.setOrderBy(Page.ORDER_BY_MODIFY_TIME);
		List<PurchaserFrozenCash> purchaserFrozenCashList = this.purchaserFrozenCashDao.getMyFrozenGuaranteeListByPurchaserId(purchaserId, page);
		if(null==purchaserFrozenCashList || 0==purchaserFrozenCashList.size()) {
			return purchaserFrozenCashList;
		}
		List<PurchaserFrozenCash> tempPurchaserFrozenCashList = new ArrayList<PurchaserFrozenCash>();
		for(PurchaserFrozenCash frozenCash : purchaserFrozenCashList) {
			FrozenCashCauseEnum frozenCashCause = FrozenCashCauseEnum.getEnum(frozenCash.getCause());
			Integer payStatus = frozenCash.getPayStatus();
			StringBuffer sb = new StringBuffer();
			switch (frozenCashCause) {
			  case SHIPMENT_DELAY:
				  if(IsDefaultEnum.DEFAULT_NO.getCode().equals(payStatus)) {
					  sb.append("您有一笔因为延期发货导致的保证金被冻结，冻结金额为").append(frozenCash.getFrozenAmount().multiply(new BigDecimal(GlobalConstants.DIVIDE_PERCENT)).setScale(0, BigDecimal.ROUND_HALF_UP)).append("元");
				  } else {
					  sb.append("您有一笔因为延期发货导致的保证金被赔付，赔付金额为").append(frozenCash.getFrozenAmount().multiply(new BigDecimal(GlobalConstants.DIVIDE_PERCENT)).setScale(0, BigDecimal.ROUND_HALF_UP)).append("元");
				  }
				break;
			  case CANCEL_ORDER:
				  sb.append("您主动取消订单被处罚，处罚的金额赔付给消费者，赔付金额为").append(frozenCash.getFrozenAmount().multiply(new BigDecimal(GlobalConstants.DIVIDE_PERCENT)).setScale(0, BigDecimal.ROUND_HALF_UP)).append("元");
				break;
			  default:
				break;
			}
			frozenCash.setDesc(sb.toString());
			tempPurchaserFrozenCashList.add(frozenCash);
		}
		return tempPurchaserFrozenCashList;
	}
	
	@Override
	public Integer finishPurchaserFrozenCash(Integer purchaserId, Integer postrewardId) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Assert.notNull(postrewardId, "悬赏id不能为空");
		Integer flag = this.purchaserFrozenCashDao.finishPurchaserFrozenCash(purchaserId, postrewardId);
		if(flag <=0) {
			throw new ApolloBizException(ResultCode.UPDATE_FAIL, purchaserId, String.format("赔付买手冻结账户资金失败[purchaserId=%s]", purchaserId));
		}
		return flag;
	}

}
