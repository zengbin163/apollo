package com.haitao.apollo.web.nologin.purchaser;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.enums.ShowSourceEnum;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.proccess.PageListProcess;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.purchaser.PurchaserVipService;
import com.haitao.apollo.web.BaseAction;

public class PurchaserAction extends BaseAction {

	private static final long serialVersionUID = 3193370783933945858L;

	@Autowired
    private PurchaserService purchaserService;
	@Autowired
	private PurchaserVipService purchaserVipService;
	@Autowired
	private PageListProcess pageListProcess;

	/**
	 * 查看买手个人主页
	 * @return
	 */
	public String purchaserHomePage(){
    	Integer purchaserId = this.getIntParameter(request, "purchaserId", null);
    	Purchaser purchaser = this.purchaserService.getPurchaserById(purchaserId);
    	this.returnFastJsonExcludeProperties(purchaser, Purchaser.class, new String[]{"password","version"});
		return null;
	}
	
	/**
	 * 买手个人主页晒单列表
	 * @param purchaserId
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public String purchaserShowList(){
    	Integer purchaserId = this.getIntParameter(request, "purchaserId", null);
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
    	returnFastJSON(this.pageListProcess.getShowOrderPoolListByOperator(ShowSourceEnum.PURCHASER.getCode(), purchaserId, null, null, pageOffset, pageSize));
    	return null;
	}
	
	/**
	 * 买手等级规则
	 * @return
	 */
	public String purchaserVipList() {
		this.returnFastJSON(this.purchaserVipService.getAllPurchaserVip());
		return null;
	}
}
