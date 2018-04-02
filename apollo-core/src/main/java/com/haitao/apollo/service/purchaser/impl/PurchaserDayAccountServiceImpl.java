package com.haitao.apollo.service.purchaser.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.purchaser.PurchaserDayAccountDao;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.enums.InOrOutEnum;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.purchaser.PurchaserDayAccount;
import com.haitao.apollo.service.purchaser.PurchaserDayAccountService;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.purchaser.PurchaserDayAccountVo;

@Service
public class PurchaserDayAccountServiceImpl implements PurchaserDayAccountService {
	
	@Resource(name = "purchaserDayAccountDao")
	private PurchaserDayAccountDao purchaserDayAccountDao;

	@Override
	public Integer createDayAccount(Integer purchaserId, Integer userId,
			Integer postrewardId, Integer payType, Integer fundType,
			BigDecimal payAmount, String paySerialNo) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Assert.notNull(payType, "支付类型不能为空");
		Assert.notNull(fundType, "款项类型不能为空");
		Assert.notNull(payAmount, "支付金额不能为空");
		Assert.notNull(paySerialNo, "第三方支付流水号不能为空");
		Long currentTime = DateUtil.currentUTCTime();
		PurchaserDayAccountVo purchaserDayAccountVo = new PurchaserDayAccountVo(purchaserId, postrewardId, payType, fundType, payAmount, paySerialNo, currentTime, currentTime);
		this.purchaserDayAccountDao.insertPurchaserDayAccount(purchaserDayAccountVo);
		if(null == purchaserDayAccountVo.getId()) {
			throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("新增买手账户流水失败[operatorId=%s]", SessionContainer.getSession().getOperatorId()));
		}
		return purchaserDayAccountVo.getId();
	}

	@Override
	public List<PurchaserDayAccount> myBillList(Integer purchaserId, Integer month, Integer pageOffset, Integer pageSize) {
    	Assert.notNull(purchaserId,"买手id不能为空");
    	Assert.notNull(month,"账单月份不能为空");
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy(Page.ORDER_BY_CREATE_TIME);
        List<PurchaserDayAccount> purchaserDayAccountList = this.purchaserDayAccountDao.getMyDayAccountByPurchaserId(purchaserId, month, page);
        if(CollectionUtils.isEmpty(purchaserDayAccountList)) {
        	return purchaserDayAccountList;
        }
        List<PurchaserDayAccount> tempPurchaserDayAccountList = new ArrayList<PurchaserDayAccount>();
        for(PurchaserDayAccount purchaserDayAccount : purchaserDayAccountList) {
        	FundTypeEnum fundTypeEnum = FundTypeEnum.getEnum(purchaserDayAccount.getFundType());
        	StringBuffer desc = new StringBuffer();
        	InOrOutEnum inOrOutEnum = InOrOutEnum.IN_ACCOUNT;
        	switch (fundTypeEnum) {
	            case GUARANTEE:
	            	desc.append("您往平台充值了一笔保证金");
	            	inOrOutEnum = InOrOutEnum.OUT_ACCOUNT;
	                break;
	            case CASH:
		            desc.append("您从平台提取了一笔现金");
	            	inOrOutEnum = InOrOutEnum.IN_ACCOUNT;
		            break;
	            case ACC_DEPOSIT:
	            	desc.append("您收到平台转账的一笔定金");
	            	inOrOutEnum = InOrOutEnum.IN_ACCOUNT;
	            	break;
	            case ACC_FINAL:
	            	desc.append("您收到平台转账的一笔尾款");
	            	inOrOutEnum = InOrOutEnum.IN_ACCOUNT;
	            	break;
	            default:
	                break;
            }
            purchaserDayAccount.setDesc(desc.toString());
            purchaserDayAccount.setInOrOut(inOrOutEnum.getCode());
            tempPurchaserDayAccountList.add(purchaserDayAccount);
        }
        return tempPurchaserDayAccountList;

	}
}
