package com.haitao.apollo.web.nologin.backoperator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.proccess.OrderProcess;
import com.haitao.apollo.service.backoperator.BackOperatorService;
import com.haitao.apollo.service.order.RefundPreviewService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.purchaser.PurchaserCashService;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.web.BaseAction;

/**
 * 后台系统的用户的操作
 * @author zengbin
 *
 */
public class BackOperatorAction extends BaseAction {

	private static final long serialVersionUID = 3055256182856046056L;
	@Autowired
	private BackOperatorService backOperatorService;
	@Autowired
	private PurchaserCashService purchaserCashService;
	@Autowired
	private PurchaserService purchaserService;
	@Autowired
	private UserService userService;
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    private OrderProcess orderProcess;
    @Autowired
    private RefundPreviewService refundPreviewService;
    
	/**
	 * 后台用户登录
	 * @param mobile
	 * @param password
	 * @return
	 */
	public String login() {
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String password = this.getFilteredParameter(request, "password", 0, null);
        this.returnFastJSON(this.backOperatorService.login(mobile, password));
		return null;
	}
	
	/**
	 * 根据申请状态查询买手的提现记录
	 * @param applyStatus @ApplyStatus 申请状态   0申请中   1完结
	 * @param pageOffset
	 * @param pageSize
	 * @param mobile
	 * @param password
	 * @return
	 */
	public String purchaserCashList() {
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String password = this.getFilteredParameter(request, "password", 0, null);
		Integer applyStatus = this.getIntParameter(request, "applyStatus", null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		this.backOperatorService.isLogin(mobile, password);
		this.returnFastJSON(this.purchaserCashService.getPurchaserCashListByApplyStatus(applyStatus, pageOffset, pageSize));
		return null;
	}
	
	/**
	 * 完成买手提现记录
	 * @param id 买手提现记录id
	 * @param mobile
	 * @param password
	 * @return
	 */
	public String finishPurchaserCash() {
		Integer id = this.getIntParameter(request, "id", null);
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String password = this.getFilteredParameter(request, "password", 0, null);
		this.backOperatorService.isLogin(mobile, password);
		this.returnFastJSON(this.purchaserCashService.finishPurchaserCash(id));
		return null;
	}
	
	/**
	 * 买手录入
	 * @param mobile 登录手机号码
	 * @param password 登录密码
	 * @param purchaserName 买手名称
	 * @param purchaserMobile 买手入驻的手机
	 * @param purchaserPassword 买手入驻的密码
	 * @param signature 签名
	 * @param headerUrl 头像URL
	 * @param guarantee 保证金，额度是保证金的2倍
	 * @param quota 剩余接单额度
	 * @param account 账户金额
	 * @param vip vip等级
	 * @param quantity 接单数量
	 * @param alipayAccount 支付宝账号
	 * @param bankName 银行名称
	 * @param bankAccount 银行账号
     * @param email;// email
     * @param sex;// 性别，0:女性 1:男性
     * @param birth;// 出生年月 yyyy-MM-dd
     * @param address;// 地址
     * @param idCardFrontUrl;// 身份证正面
     * @param idCardBackUrl;// 身份证反面
     * @param studentIdCardUrl;// 学生证
     * @param vipCardUrl;// VIP会员卡
     * @param creditCardAccUrl;// 信用卡对账
     * @param liveForeverUrl;// 长久居住证，比如：意大利
     * @param utilityBillUrl;// 三个月内的水电费账单
     * @param mobileBillUrl;// 手机话费账单
     * @param webServiceUrl;// 网络服务
     * @param drivingLicenceUrl;// 所在城市驾驶证
     * @param overseasProveUrl;// 海外工作证明文件
     * @return
	 */
	public String purchaserInput() {
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String password = this.getFilteredParameter(request, "password", 0, null);
		this.backOperatorService.isLogin(mobile, password);
        String purchaserName = this.getFilteredParameter(request, "purchaserName", 0, null); 
        String purchaserMobile = this.getFilteredParameter(request, "purchaserMobile", 0, null); 
        String purchaserPassword = this.getFilteredParameter(request, "purchaserPassword", 0, null); 
        String signature = this.getFilteredParameter(request, "signature", 0, null); 
        String headerUrl = this.getFilteredParameter(request, "headerUrl", 0, null); 
        BigDecimal guarantee = this.getDecimalParameter(request, "guarantee", null);
        BigDecimal quota = this.getDecimalParameter(request, "quota", null);
        BigDecimal account = this.getDecimalParameter(request, "account", null);
        Integer vip = this.getIntParameter(request, "vip", null);
        Integer quantity = this.getIntParameter(request, "quantity", 0);
        String alipayAccount = this.getFilteredParameter(request, "alipayAccount", 0, null); 
        String bankName = this.getFilteredParameter(request, "bankName", 0, null); 
        String bankAccount = this.getFilteredParameter(request, "bankAccount", 0, null); 
        String email = this.getFilteredParameter(request, "email", 0, null); 
        Integer sex = this.getIntParameter(request, "sex", null);
        String birth = this.getFilteredParameter(request, "birth", 0, null); 
        String address = this.getFilteredParameter(request, "address", 0, null); 
        String idCardFrontUrl = this.getFilteredParameter(request, "idCardFrontUrl", 0, null); 
        String idCardBackUrl = this.getFilteredParameter(request, "idCardBackUrl", 0, null); 
        String studentIdCardUrl = this.getFilteredParameter(request, "studentIdCardUrl", 0, null); 
        String vipCardUrl = this.getFilteredParameter(request, "vipCardUrl", 0, null); 
        String creditCardAccUrl = this.getFilteredParameter(request, "creditCardAccUrl", 0, null); 
        String liveForeverUrl = this.getFilteredParameter(request, "liveForeverUrl", 0, null); 
        String utilityBillUrl = this.getFilteredParameter(request, "utilityBillUrl", 0, null); 
        String mobileBillUrl = this.getFilteredParameter(request, "mobileBillUrl", 0, null); 
        String webServiceUrl = this.getFilteredParameter(request, "webServiceUrl", 0, null); 
        String drivingLicenceUrl = this.getFilteredParameter(request, "drivingLicenceUrl", 0, null); 
        String overseasProveUrl = this.getFilteredParameter(request, "overseasProveUrl", 0, null); 
		Purchaser purchaser = this.purchaserService.purchaserInput(
				purchaserName, purchaserMobile, purchaserPassword, signature,
				headerUrl, guarantee, quota, account, vip, quantity,
				alipayAccount, bankName, bankAccount, email, sex, birth,
				address, idCardFrontUrl, idCardBackUrl, studentIdCardUrl,
				vipCardUrl, creditCardAccUrl, liveForeverUrl, utilityBillUrl,
				mobileBillUrl, webServiceUrl, drivingLicenceUrl,
				overseasProveUrl);
		this.returnFastJSON(purchaser);
		return null;
	}
	
	/**
	 * 买手退出
	 * @param mobile 登录手机号码
	 * @param password 登录密码
	 * @param purchaserId
	 * @return
	 */
	public String purchaserOutput() {
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String password = this.getFilteredParameter(request, "password", 0, null);
		this.backOperatorService.isLogin(mobile, password);
		Integer purchaserId = this.getIntParameter(request, "purchaserId", null);
		this.purchaserService.purchaserOutput(purchaserId);
		this.returnFastJSON(success());
		return null;
	}
	
	/**
	 * 查询买手列表
	 * @param mobile 登录手机号码
	 * @param password 登录密码
	 * @param pageOffset 
	 * @param pageSize 
	 * @return
	 */
	public String purchaserList() {
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String password = this.getFilteredParameter(request, "password", 0, null);
		this.backOperatorService.isLogin(mobile, password);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
        Integer pageSize = this.getIntParameter(request, "pageSize", null);
        this.returnFastJSON(this.purchaserService.getPurchaserList(pageOffset, pageSize));
        return null;
	}
	
	/**
	 * 查询订单列表
	 * @param mobile
	 * @param password
	 * @param orderId
	 * @param rewardStatus
	 * @param orderStatus
	 * @param refundStatus
	 * @param csStatus
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public String getSaleOrderList() {
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String password = this.getFilteredParameter(request, "password", 0, null);
		this.backOperatorService.isLogin(mobile, password);
		Integer orderId = this.getIntParameter(request, "orderId", null);
		Integer rewardStatus = this.getIntParameter(request, "rewardStatus", null);
		Integer orderStatus = this.getIntParameter(request, "orderStatus", null);
        Integer refundStatus = this.getIntParameter(request, "refundStatus", null);
        Integer csStatus = this.getIntParameter(request, "csStatus", null);
        Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
        Integer pageSize = this.getIntParameter(request, "pageSize", null);
        this.returnFastJSON(this.saleOrderService.getSaleOrderListForCs(orderId, rewardStatus, orderStatus, refundStatus, csStatus, pageOffset, pageSize));
        return null;
	}
	
	/**
	 * 查询订单详情
	 * @param mobile
	 * @param password
	 * @param orderId
	 * @return
	 */
    public String saleOrderDetail(){
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String password = this.getFilteredParameter(request, "password", 0, null);
		this.backOperatorService.isLogin(mobile, password);
    	Integer orderId = this.getIntParameter(request, "orderId", null);
        SaleOrder saleOrder = this.saleOrderService.getSaleOrderDetailByOrderId(orderId);
        returnFastJSON(saleOrder);
        return null;
    }
    
    /**
     * 挂起订单
	 * @param mobile
	 * @param password
	 * @param orderId
     * @return
     */
    public String suspend() {
        String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
        String password = this.getFilteredParameter(request, "password", 0, null);
		this.backOperatorService.isLogin(mobile, password);
    	Integer orderId = this.getIntParameter(request, "orderId", null);
    	this.saleOrderService.suspendSaleOrder(orderId);
    	this.returnFastJSON(success());
    	return null;
    }
    /**
     * 还原订单
     * @param mobile
     * @param password
     * @param orderId
     * @return
     */
    public String cancelSuspend() {
    	String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
    	String password = this.getFilteredParameter(request, "password", 0, null);
    	this.backOperatorService.isLogin(mobile, password);
    	Integer orderId = this.getIntParameter(request, "orderId", null);
    	this.saleOrderService.cancelSuspendSaleOrder(orderId);
    	this.returnFastJSON(success());
    	return null;
    }
    /**
     * 结束订单
     * @param mobile
     * @param password
     * @param orderId
     * @return
     */
    public String endOrder() {
    	String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
    	String password = this.getFilteredParameter(request, "password", 0, null);
    	this.backOperatorService.isLogin(mobile, password);
    	Integer orderId = this.getIntParameter(request, "orderId", null);
    	this.saleOrderService.endSaleOrder(orderId);
    	this.returnFastJSON(success());
    	return null;
    }
    /**
     * 删除订单
     * @param mobile
     * @param password
     * @param orderId
     * @return
     */
    public String deleteOrder() {
    	String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
    	String password = this.getFilteredParameter(request, "password", 0, null);
    	this.backOperatorService.isLogin(mobile, password);
    	Integer orderId = this.getIntParameter(request, "orderId", null);
    	this.orderProcess.cancelOrder(orderId, OperatorRoleEnum.ROLE_CUSTOMER.getCode());
    	this.returnFastJSON(success());
    	return null;
    }
    
    /**
     * 根据用户id查询用户信息
     * @param mobile
     * @param password
     * @param userId
     * @return
     */
    public String getUserById() {
    	String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
    	String password = this.getFilteredParameter(request, "password", 0, null);
    	this.backOperatorService.isLogin(mobile, password);
    	Integer userId = this.getIntParameter(request, "userId", null);
    	this.returnFastJSON(this.userService.getUserById(userId));
    	return null;
    }

    /**
     * 根据用户昵称查询用户列表
     * @param mobile
     * @param password
     * @param nickName
     * @param pageOffset
     * @param pageSize
     * @return
     */
    public String userListByNickName() {
    	String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
    	String password = this.getFilteredParameter(request, "password", 0, null);
    	this.backOperatorService.isLogin(mobile, password);
    	String nickName = this.getFilteredParameter(request, "nickName", 0, null);
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
    	this.returnFastJSON(this.userService.getUserListByPage(nickName, pageOffset, pageSize));
    	return null;
    }

    /**
     * 查询受禁用户列表
     * @param mobile
     * @param password
     * @return
     */
    public String forbiddenUserList() {
    	String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
    	String password = this.getFilteredParameter(request, "password", 0, null);
    	this.backOperatorService.isLogin(mobile, password);
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
    	this.returnFastJSON(this.userService.getForbiddenUserList(pageOffset, pageSize));
    	return null;
    }
    
    /**
     * 禁止用户发悬赏
     * @param mobile
     * @param password
     * @param userId
     * @return
     */
    public String forForbidPost() {
    	String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
    	String password = this.getFilteredParameter(request, "password", 0, null);
    	this.backOperatorService.isLogin(mobile, password);
    	Integer userId = this.getIntParameter(request, "userId", null);
    	this.userService.forForbidPost(userId);
    	this.returnFastJSON(success());
    	return null;
    }

    /**
     * 禁止用户登录
     * @param mobile
     * @param password
     * @param userId
     * @return
     */
    public String forForbidLogin() {
    	String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
    	String password = this.getFilteredParameter(request, "password", 0, null);
    	this.backOperatorService.isLogin(mobile, password);
    	Integer userId = this.getIntParameter(request, "userId", null);
    	this.userService.forForbidLogin(userId);
    	this.returnFastJSON(success());
    	return null;
    }
    
    /**
     * 禁止用户晒单
     * @param mobile
     * @param password
     * @param userId
     * @return
     */
    public String forForbidShow() {
    	String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
    	String password = this.getFilteredParameter(request, "password", 0, null);
    	this.backOperatorService.isLogin(mobile, password);
    	Integer userId = this.getIntParameter(request, "userId", null);
    	this.userService.forForbidShow(userId);
    	this.returnFastJSON(success());
    	return null;
    }
    /**
     * 解禁消费者的所有禁令
     * @param mobile
     * @param password
     * @param userId
     * @return
     */
    public String unForbidden() {
    	String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
    	String password = this.getFilteredParameter(request, "password", 0, null);
    	this.backOperatorService.isLogin(mobile, password);
    	Integer userId = this.getIntParameter(request, "userId", null);
    	this.userService.unForbidden(userId);
    	this.returnFastJSON(success());
    	return null;
    }
    
    /**
     * @param mobile
     * @param password
     * @param pageOffset
     * @param pageSize
     * 查询退款处理记录
     * @return
     */
    public String refundPreviewList() {
    	String mobile = this.getFilteredParameter(request, "mobile", 0, null); 
    	String password = this.getFilteredParameter(request, "password", 0, null);
    	this.backOperatorService.isLogin(mobile, password);
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
    	this.returnFastJSON(this.refundPreviewService.getRefundPreviewList(pageOffset, pageSize));
    	return null;
    }
}
