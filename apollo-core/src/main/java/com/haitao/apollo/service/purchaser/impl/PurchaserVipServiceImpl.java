package com.haitao.apollo.service.purchaser.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.haitao.apollo.dao.purchaser.PurchaserVipDao;
import com.haitao.apollo.enums.CachePrefixEnum;
import com.haitao.apollo.plugin.cache.RedisService;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.pojo.purchaser.PurchaserVip;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.purchaser.PurchaserVipService;
import com.haitao.apollo.vo.purchaser.PurchaserVo;

@Service
public class PurchaserVipServiceImpl implements PurchaserVipService {
	
	@Resource(name = "purchaserVipDao")
	private PurchaserVipDao purchaserVipDao;
    @Autowired
	private RedisService redisService;
    @Autowired
    private PurchaserService purchaserService;
    
	public PurchaserVip getPurchaserVip(Integer vip) {
		Assert.notNull(vip, "vip等级不能为空");
		List<PurchaserVip> purchaserVipList = this.getAllPurchaserVip();
		for(PurchaserVip purchaserVip : purchaserVipList) {
			if(vip.equals(purchaserVip.getVip())){
				return purchaserVip;
			}
		}
		return null;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaserVip> getAllPurchaserVip() {
		String key = CachePrefixEnum.PREFIX_PURCHASER_VIP_ALL.toString();
		Object obj = redisService.getObj(key);
		List<PurchaserVip> purchaserVipList = null;
		if(null==obj || CollectionUtils.isEmpty((List<PurchaserVip>)obj)){
			purchaserVipList = purchaserVipDao.getAllPurchaserVip();
			redisService.setObj(key, purchaserVipList);
		}else{
			purchaserVipList = (List<PurchaserVip>)obj;
		}
		return purchaserVipList;
	}

	
	public boolean upgradePurchaserVip(Integer purchaserId, BigDecimal rechargeGuarantee) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Purchaser purchaser = this.purchaserService.getPurchaserById(purchaserId);
		//查询买手下个等级需要接单量和保证金是否满足
		int nextVip = purchaser.getVip() + 1;
		PurchaserVip currentPurchaserVip = this.getPurchaserVip(purchaser.getVip());
		PurchaserVip nextPurchaserVip = this.getPurchaserVip(nextVip);
		if(purchaser.getQuantity() < nextPurchaserVip.getQuantity()) {
			return false;
		}
		//买手保证金就是当前买手保证金+买手充值的保证金
		BigDecimal allGuarantee = purchaser.getGuarantee().add(rechargeGuarantee);
		if(allGuarantee.compareTo(nextPurchaserVip.getGuarantee()) == -1) {
			return false;
		}
		PurchaserVo purchaserVo = new PurchaserVo();
		purchaserVo.setId(purchaserId);
		purchaserVo.setVip(nextVip);
		purchaserVo.setGuarantee(allGuarantee);
		BigDecimal nextQuota = (nextPurchaserVip.getQuota().subtract(currentPurchaserVip.getQuota())).add(purchaser.getQuota());
		purchaserVo.setQuota(nextQuota);
		this.purchaserService.updatePurchaserById(purchaserVo);
		return true;
	}
}
