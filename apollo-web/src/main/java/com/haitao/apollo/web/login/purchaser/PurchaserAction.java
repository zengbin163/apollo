package com.haitao.apollo.web.login.purchaser;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.service.purchaser.PurchaserDayAccountService;
import com.haitao.apollo.service.purchaser.PurchaserFrozenCashService;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.user.InviteCodeService;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.vo.purchaser.PurchaserVo;
import com.haitao.apollo.web.BaseAction;

public class PurchaserAction extends BaseAction {

	private static final long serialVersionUID = -5410095405909011135L;
	@Autowired
	private PurchaserService purchaserService;
	@Autowired
	private UserService userService;
	@Autowired
	private PurchaserFrozenCashService purchaserFrozenCashService;
	@Autowired
	private PurchaserDayAccountService purchaserDayAccountService;
	@Autowired
	private InviteCodeService inviteCodeService;
	
	/**
	 *  role 0:买手  1:消费者
	 * @return
	 */
	@FromPurchaser
	public String inviteCode() {
        Integer role = this.getIntParameter(request, "role", null);
        this.returnFastJSON(this.inviteCodeService.saveInviteCode(role));
        return null;
	}
	
    /**
     * 修改买手基本信息
     * @param purchaserName 买手昵称
     * @param headerUrl 头像
     * @param signature 签名 （256个字符）
     * @return
     */
    @FromPurchaser
    public String modify(){
        String purchaserName = this.getFilteredParameter(request, "purchaserName", 0, null);
        String headerUrl = this.getFilteredParameter(request, "headerUrl", 0, null);
        String signature = this.getFilteredParameter(request, "signature", 0, null);
        if(!isPurchaser()) {
        	throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), String.format("非买手端请求[purchaserId=%s]", getOperatorId()));
        }
        PurchaserVo purchaserVo = new PurchaserVo();
        purchaserVo.setId(SessionContainer.getSession().getOperatorId());
        purchaserVo.setPurchaserName(purchaserName);
        purchaserVo.setHeaderUrl(headerUrl);
        purchaserVo.setSignature(signature);
        Purchaser purchaser = this.purchaserService.updatePurchaserById(purchaserVo);
    	this.returnFastJsonExcludeProperties(purchaser, Purchaser.class, new String[]{"password","version"});
        return null;
    }
    
	/**
	 * 我的账单（买手端）
	 * @return
	 */
    @FromPurchaser
	public String myBill() {
        /** 买手端的操作 **/
        if(!isPurchaser()) {
        	throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), String.format("非买手端请求[purchaserId=%s]", getOperatorId()));
        }
    	Integer month = this.getIntParameter(request, "month", null);
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
		if (null == month || 0 == month) {
			Calendar cal = Calendar.getInstance();
			month = cal.get(Calendar.MONTH) + 1;
		}
        this.returnFastJSON(this.purchaserDayAccountService.myBillList(SessionContainer.getSession().getOperatorId(), month, pageOffset, pageSize));
        return null;
	}

	/**
	 * 我的冻结保证金列表（买手端）
	 * @return
	 */
    @FromPurchaser
    public String myFrozenGuarantee() {
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
        /** 买手端的操作 **/
        if(!isPurchaser()) {
        	throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), String.format("非买手端请求[purchaserId=%s]", getOperatorId()));
        }
        this.returnFastJSON(this.purchaserFrozenCashService.getMyFrozenGuaranteeListByPurchaserId(getOperatorId(), pageOffset, pageSize));
        return null;
    }
    
	/**
	 * 查看买手个人个人信息
	 * @return
	 */
    @FromPurchaser
	public String purchaserInfo(){
        if(!isPurchaser()) {
        	throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), String.format("非买手端请求[purchaserId=%s]", getOperatorId()));
        }
	    Purchaser purchaser = this.purchaserService.getPurchaserById(getOperatorId());
    	returnFastJSON(purchaser);
		return null;
	}
    
    /**
     * 查询买手的客户列表
     * @param pageOffset
     * @param pageOffset
     * @return
     */
    @FromPurchaser
	public String myUsers(){
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
        if(!isPurchaser()) {
        	throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), String.format("非买手端请求[purchaserId=%s]", getOperatorId()));
        }
    	returnFastJSON(this.userService.getUserListByPurchaserId(getOperatorId(), pageOffset, pageSize));
		return null;
	}
}
