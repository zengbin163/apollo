package com.haitao.apollo.web.login.order;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.annotation.FromUser;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.CurrencyEnum;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.pay.pojo.ThirdPay;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.LoginOperator;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.proccess.PageIndexProcess;
import com.haitao.apollo.proccess.ThirdPayProcess;
import com.haitao.apollo.service.purchaser.PurchaserCashService;
import com.haitao.apollo.status.RewardStatus;
import com.haitao.apollo.web.BaseAction;

public class PayAction extends BaseAction {

	private static final long serialVersionUID = 1822971706149732592L;
    @Autowired
    private ThirdPayProcess thirdPayProcess;
    @Autowired
    private PageIndexProcess pageIndexProcess;
    @Autowired
    private PurchaserCashService purchaserCashService;
    
    /**
     * 消费者支付
     * @param postrewardId 悬赏id
     * @param payType 支付类型  0:alipay 1:wx
     * @param fundType 款项类型  0:定金  1:尾款  2:全款  3:买手充值保证金
     * @param payAmount 支付金额    单位为分
     * @param bigMoney 大牌币，如果是定金传入0
     * @param clientIp  发起支付请求终端的 IP 地址，格式为 IPV4，如: 127.0.0.1
     * @return
     */
    @FromUser
	public String pay(){
    	Integer postrewardId = this.getIntParameter(request, "postrewardId", null);
    	Integer payType = this.getIntParameter(request, "payType", null);
    	Integer fundType = this.getIntParameter(request, "fundType", null);
    	BigDecimal payAmount = this.getDecimalParameter(request, "payAmount", null);//支付金额
    	BigDecimal bigMoney = this.getDecimalParameter(request, "bigMoney", BigDecimal.ZERO);//大牌币，如果是定金传入0，如果尾款大牌币没有也传入0
        String clientIp = this.getFilteredParameter(request, "clientIp", 0, null);//发起支付请求终端的 IP 地址，格式为 IPV4，如: 127.0.0.1
        /** 消费者的操作 **/
        if(!isUser()) {
        	throw new ApolloBizException(ResultCode.NOT_USER_REQUEST, getOperatorId(), String.format("非消费者端请求[userId=%s]", getOperatorId()));
        }
        /** 用户支付   **/
        PostReward postreward = pageIndexProcess.postrewardDetail(postrewardId);
        if(null==postreward){
        	throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, getOperatorId(), String.format("悬赏不存在，[postrewardId=%s]", postrewardId));
        }
		if (!RewardStatus.PREPAY_REWARD.equals(postreward.getRewardStatus())
				&& !RewardStatus.PURCHASER_ACCEPT.equals(postreward.getRewardStatus())) {
        	throw new ApolloBizException(ResultCode.POSTREWARD_STATUS_ERROR, getOperatorId(), String.format("悬赏状态不正确，只有悬赏预支付状态或者买手已经接单才能调用支付接口，[postrewardId=%s]", postrewardId));
		}
        FundTypeEnum fundTypeEnum = FundTypeEnum.getEnum(fundType);
		String subject = "悬赏-" + postrewardId + "-" + fundTypeEnum.getName();
		ThirdPay thirdPay = thirdPayProcess.pay(payAmount, bigMoney,
				CurrencyEnum.RMB.getName(), subject, subject, postrewardId,
				payType, clientIp, fundType, USER_APP_ID);
        this.returnFastJSON(thirdPay);
        return null;
    }
	
    /**
     * 买手充值保证金
     * @param payType 支付类型  0:alipay 1:wx
     * @param fundType 款项类型  0:定金  1:尾款  2:全款  3:买手充值保证金
     * @param payAmount 支付金额    单位为分
     * @param clientIp  发起支付请求终端的 IP 地址，格式为 IPV4，如: 127.0.0.1
     * @return
     */
    @FromPurchaser
    public String recharge() {
    	Integer payType = this.getIntParameter(request, "payType", null);
    	Integer fundType = this.getIntParameter(request, "fundType", null);
    	BigDecimal payAmount = this.getDecimalParameter(request, "payAmount", null);//支付金额
        String clientIp = this.getFilteredParameter(request, "clientIp", 0, null);//发起支付请求终端的 IP 地址，格式为 IPV4，如: 127.0.0.1
        if(!isPurchaser()) {
        	throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), String.format("非买手端请求[purchaserId=%s]", getOperatorId()));
        }
        LoginOperator loginOperator = SessionContainer.getSession().getLoginOperator();
        /**坑爹的一起，买手只能充值一次
        Purchaser purchaser = this.purchaserService.getPurchaserById(loginOperator.getId());
        if(!(BigDecimal.ZERO.compareTo(purchaser.getGuarantee())==0)) {
        	throw new ApolloBizException(ResultCode.PURCHASER_GUARANTEE_RECHARGE, loginOperator.getId(), String.format("赏购一期买手保证金只能充值一次[purchaserId=%s]", loginOperator.getId()));
        }
        **/
        FundTypeEnum fundTypeEnum = FundTypeEnum.getEnum(fundType);
		String subject = "买手-" + loginOperator.getName() + "-" + fundTypeEnum.getName();
		ThirdPay thirdPay = thirdPayProcess.pay(payAmount, BigDecimal.ZERO,
				CurrencyEnum.RMB.getName(), subject, subject, getOperatorId(),
				payType, clientIp, fundType, PURCHASER_APP_ID);
        returnFastJSON(thirdPay);
        return null;
	}
	
	/**
	 * 买手申请提现
	 * @param cash 提现现金，单位分
	 * @return
	 */
    @FromPurchaser
	public String applyCash(){
        BigDecimal cash = this.getDecimalParameter(request, "cash", null);
        if(!isPurchaser()) {
        	throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), String.format("非买手端请求[purchaserId=%s]", getOperatorId()));
        }
        Integer applyId = this.purchaserCashService.applyCash(cash);
        returnFastJSON(toMap("applyId", applyId));
		return null;
	}
}
