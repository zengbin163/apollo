package com.haitao.apollo.service.purchaser.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.purchaser.PurchaserDao;
import com.haitao.apollo.enums.CachePrefixEnum;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.enums.MessageBoxTypeEnum;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.enums.PayTypeEnum;
import com.haitao.apollo.pay.Component;
import com.haitao.apollo.plugin.cache.RedisService;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.LoginOperator;
import com.haitao.apollo.plugin.session.Session;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.product.Category;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.product.CategoryService;
import com.haitao.apollo.service.purchaser.PurchaserDayAccountService;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.util.LoggerUtil;
import com.haitao.apollo.util.MD5Util;
import com.haitao.apollo.util.SpringContextUtil;
import com.haitao.apollo.vo.purchaser.PurchaserInfoVo;
import com.haitao.apollo.vo.purchaser.PurchaserVo;

@Service
public class PurchaserServiceImpl implements PurchaserService {

	@Resource(name = "purchaserDao")
	private PurchaserDao purchaserDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private PurchaserDayAccountService purchaserDayAccountService;
	@Autowired
	private CategoryService categoryService;
    @Autowired
    protected MsgTemplate msgTemplate;
    @Autowired
    private AsyncService asyncService;

    private static final Logger logger = LoggerFactory.getLogger(PurchaserServiceImpl.class);

    @Transactional
	public Purchaser purchaserInput(String purchaserName, String mobile,
			String password, String signature, String headerUrl,
			BigDecimal guarantee, BigDecimal quota, BigDecimal account,
			Integer vip, Integer quantity, String alipayAccount,
			String bankName, String bankAccount, String email, Integer sex,
			String birth, String address, String idCardFrontUrl,
			String idCardBackUrl, String studentIdCardUrl, String vipCardUrl,
			String creditCardAccUrl, String liveForeverUrl,
			String utilityBillUrl, String mobileBillUrl, String webServiceUrl,
			String drivingLicenceUrl, String overseasProveUrl) {
		Assert.notNull(purchaserName, "买手名称不能为空");
		Assert.notNull(mobile, "手机号码不能为空");
		Assert.notNull(password, "密码不能为空");
		Assert.notNull(guarantee, "保证金不能为空");
		Assert.notNull(alipayAccount, "支付宝账号不能为空");
		Assert.notNull(bankName, "银行名称不能为空");
		Assert.notNull(bankAccount, "银行账号不能为空");
		Assert.notNull(email, "email不能为空");
		Assert.notNull(sex, "性别不能为空");
		Assert.notNull(birth, "出生年月不能为空");
		Assert.notNull(address, "地址不能为空");

		Assert.notNull(idCardFrontUrl, "身份证正面链接不能为空");
		Assert.notNull(idCardBackUrl, "身份证反面链接不能为空");
//		Assert.notNull(quota, "剩余接单额度不能为空");
//		Assert.notNull(account, "账户金额不能为空");
//		Assert.notNull(vip, "VIP等级不能为空");
//		Assert.notNull(quantity, "接单数量不能为空");
//		Assert.notNull(studentIdCardUrl, "学生证链接不能为空");
//		Assert.notNull(vipCardUrl, "VIP会员卡链接不能为空");
//		Assert.notNull(creditCardAccUrl, "信用卡对账链接不能为空");
//		Assert.notNull(liveForeverUrl, "永久居住链接不能为空");
//		Assert.notNull(utilityBillUrl, "三个月水电费账单链接不能为空");
//		Assert.notNull(mobileBillUrl, "手机话费链接不能为空");
//		Assert.notNull(webServiceUrl, "网络服务链接不能为空");
//		Assert.notNull(drivingLicenceUrl, "性别不能为空");
//		Assert.notNull(overseasProveUrl, "出生年月不能为空");
		
		if(BigDecimal.ZERO.compareTo(guarantee)==0) {
        	throw new ApolloBizException(ResultCode.ILLEGAL_ARGUMENT, mobile, String.format("买手录入保证金不能为0，[mobile=%s]", mobile));
		}
		PurchaserVo purchaserVo = new PurchaserVo(purchaserName, mobile,
				MD5Util.md5(password), signature, headerUrl, guarantee,
				guarantee.multiply(new BigDecimal(2)), account, vip, quantity,
				DateUtil.currentUTCTime(), DateUtil.currentUTCTime(),
				DateUtil.currentUTCTime());
		this.purchaserDao.insertPurchaser(purchaserVo);
		if(null == purchaserVo.getId()) {
        	throw new ApolloBizException(ResultCode.SAVE_FAIL, mobile, String.format("买手录入失败[mobile=%s]", mobile));
		}
		//录入买手默认前4个类目
		List<Category> cateList = this.categoryService.getCategoryList();
		if(!CollectionUtils.isEmpty(cateList)) {
			List<Integer> list = new ArrayList<Integer>();
			for(Category category : cateList) {
				list.add(category.getId());
				if(list.size() == 4) {
					break;
				}
			}
			this.categoryService.subscribeCategory(purchaserVo.getId(), list);
		}
		//录入买手基本信息
		PurchaserInfoVo purchaserInfoVo = new PurchaserInfoVo(
				purchaserVo.getId(), alipayAccount, bankName, bankAccount,
				email, sex, birth, address, idCardFrontUrl, idCardBackUrl,
				studentIdCardUrl, vipCardUrl, creditCardAccUrl, liveForeverUrl,
				utilityBillUrl, mobileBillUrl, webServiceUrl,
				drivingLicenceUrl, overseasProveUrl, DateUtil.currentUTCTime(),
				DateUtil.currentUTCTime());
		this.purchaserDao.insertPurchaserInfo(purchaserInfoVo);
		if(null == purchaserInfoVo.getId()) {
        	throw new ApolloBizException(ResultCode.SAVE_FAIL, mobile, String.format("买手信息录入失败[mobile=%s]", mobile));
		}
		Purchaser purchaser = this.getPurchaserById(purchaserVo.getId());
		return purchaser;
	}
	
	public void purchaserOutput(Integer purchaserId) {
		PurchaserVo purchaserVo = new PurchaserVo();
		purchaserVo.setId(purchaserId);
		purchaserVo.setIsActive(IsDefaultEnum.DEFAULT_NO.getCode());
		this.updatePurchaserById(purchaserVo);
	}
    
	public List<Purchaser> getPurchaserList(Integer pageOffset, Integer pageSize) {
    	Assert.notNull(pageOffset, "pageOffset不能为空");
    	Assert.notNull(pageSize, "pageSize不能为空");
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy(Page.ORDER_BY_MODIFY_TIME);
        return this.purchaserDao.getPurchaserList(page);
	}
	
	public Purchaser login(String deviceId, String mobile,String password,Integer role,String token) {
        Assert.notNull(deviceId,"deviceId不能为空");
        Assert.notNull(mobile,"手机号码不能为空");
        Assert.notNull(password,"密码不能为空");
        Assert.notNull(role,"登陆者角色不能为空");
        Assert.notNull(token,"IOS推送token不能为空");
        if(!OperatorRoleEnum.ROLE_PURCHASER.getCode().equals(role)){
        	throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, mobile, ResultCode.NOT_PURCHASER_REQUEST.getString());
        }
        Purchaser purchaser = this.purchaserDao.getPurchaserByMobileAndPassword(mobile, MD5Util.md5(password));
        if(null==purchaser){
            throw new ApolloBizException(ResultCode.LOGIN_ERROR , mobile , ResultCode.LOGIN_ERROR.getString());
        }
        Session session = new Session();
        session.setDeviceId(deviceId);
        session.setRole(role);
        session.setOperatorId(purchaser.getId());
        session.setLoginOperator(LoginOperator.transPurchaser2Operator(purchaser));
        session.setToken(token);
        redisService.setObj(CachePrefixEnum.PREFIX_SESSION_ + deviceId, session);
        SessionContainer.setSession(session);
        //更新ios推送相关参数
        PurchaserVo purchaserVo = new PurchaserVo(purchaser.getId(), null, null, deviceId, token);
        this.updatePurchaserById(purchaserVo);
        //查询买手订阅了哪些类目
        purchaser.setBeSubscribeCateList(this.categoryService.getCategoryListByPurchaserId(purchaser.getId()));
        return purchaser;
	}
	
	public Purchaser getPurchaserById(Integer id) {
		Assert.notNull(id,"买手id不能为空");
		String key = CachePrefixEnum.PREFIX_PURCHASER_INFO_.toString() + id;
		Purchaser purchaser = (Purchaser)this.redisService.getObj(key);
		if(null==purchaser){
			LoggerUtil.WARN(logger, String.format("买手信息在缓存中不存在，key = %s", key));
			purchaser = this.purchaserDao.getPurchaserById(id);
			if(null==purchaser){
				throw new ApolloBizException(ResultCode.NOT_EXISTS, id, String.format("买手不存在[purchaserId=%s]", id));
			}
			this.redisService.setObj(key, purchaser);
		}
		return purchaser;
	}
	
	public Purchaser updatePurchaserById(PurchaserVo purchaserVo) {
		Assert.notNull(purchaserVo, "买手vo对象不能为空");
		Integer id = purchaserVo.getId();
		Purchaser purchaser = this.purchaserDao.getPurchaserById(id);//更新前
		Integer version = purchaser.getVersion() + 1;
		purchaserVo.setVersion(version);
		Integer flag = this.purchaserDao.updatePurchaserById(purchaserVo);
		if(flag <= 0) {
			throw new ApolloBizException(ResultCode.UPDATE_FAIL, id, String.format("更新买手信息失败，[purchaserId = %s]", id));
		}
		String key = CachePrefixEnum.PREFIX_PURCHASER_INFO_.toString() + id;
	    purchaser = this.purchaserDao.getPurchaserById(id);//更新后
		this.redisService.setObj(key, purchaser);
		return purchaser;
	}
	
	public Purchaser findPassword(Integer role, String mobile, String sms, String password) {
        Assert.notNull(role,"角色不能为空");
    	Assert.notNull(mobile,"手机号码不能为空");
        Assert.notNull(password,"密码不能为空");
        Assert.notNull(sms,"短信验证码");
        RedisService redisService = SpringContextUtil.getBean("redisService");
        String validateCode = redisService.get(MsgTemplateEnum.SMS_FINDPASS_PURCHASER + mobile);
        if(StringUtils.isEmpty(validateCode) || !validateCode.equals(sms)){
            throw new ApolloBizException(ResultCode.REG_CODE_ERROR  , mobile , ResultCode.REG_CODE_ERROR.getString());
        }
        Purchaser purchaser = this.purchaserDao.getPurchaserByMobile(mobile);
        PurchaserVo purchaserVo = new PurchaserVo();
        purchaserVo.setId(purchaser.getId());
        purchaserVo.setPassword(purchaser.getPassword());
        return this.updatePurchaserById(purchaserVo);
	}
	
	public Integer increasePurchaserGuarantee(Integer purchaserId, BigDecimal guarantee) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Assert.notNull(guarantee, "买手充值的保证金不能为空");
		if(guarantee.compareTo(BigDecimal.ZERO) == 0) {
			throw new ApolloBizException(ResultCode.PURCHASER_EXCEPTION, purchaserId, String.format("买手充值的保证金不能为0,[purchaserId=%s]", purchaserId));
		}
		Purchaser purchaser = this.purchaserDao.getPurchaserById(purchaserId);
		PurchaserVo purchaserVo = new PurchaserVo();
		purchaserVo.setId(purchaserId);
		purchaserVo.setGuarantee(guarantee.add(purchaser.getGuarantee()));
		this.updatePurchaserById(purchaserVo);
		return purchaserId;
	}
	
	public Integer decreasePurchaserGuarantee(Integer purchaserId, BigDecimal guarantee) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Assert.notNull(guarantee, "买手减少的保证金不能为空");
		if(guarantee.compareTo(BigDecimal.ZERO) == 0) {
			throw new ApolloBizException(ResultCode.PURCHASER_EXCEPTION, purchaserId, String.format("买手充值的保证金不能为0,[purchaserId=%s]", purchaserId));
		}
		Purchaser purchaser = this.purchaserDao.getPurchaserById(purchaserId);
		PurchaserVo purchaserVo = new PurchaserVo();
		purchaserVo.setId(purchaserId);
		purchaserVo.setGuarantee(purchaser.getGuarantee().subtract(guarantee));
		this.updatePurchaserById(purchaserVo);
		return purchaserId;
	}
	
	public Integer reducePurchaserQuota(Integer purchaserId, BigDecimal beReduceQuota) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Assert.notNull(beReduceQuota, "被减少的额度不能为空");
		if(beReduceQuota.compareTo(BigDecimal.ZERO) == 0) {
			throw new ApolloBizException(ResultCode.PURCHASER_EXCEPTION, purchaserId, String.format("买手被减少的额度不能为0,[purchaserId=%s]", purchaserId));
		}
		Purchaser purchaser = this.purchaserDao.getPurchaserById(purchaserId);
		PurchaserVo purchaserVo = new PurchaserVo();
		purchaserVo.setId(purchaserId);
		purchaserVo.setQuota(purchaser.getQuota().subtract(beReduceQuota));
		this.updatePurchaserById(purchaserVo);
		return purchaserId;
	}
	
	public Integer freePurchaserQuota(Integer purchaserId, BigDecimal beFreeQuota) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Assert.notNull(beFreeQuota, "被释放的额度不能为空");
		if(beFreeQuota.compareTo(BigDecimal.ZERO) == 0) {
			throw new ApolloBizException(ResultCode.PURCHASER_EXCEPTION, purchaserId, String.format("买手被释放的额度不能为0,[purchaserId=%s]", purchaserId));
		}
		Purchaser purchaser = this.purchaserDao.getPurchaserById(purchaserId);
		PurchaserVo purchaserVo = new PurchaserVo();
		purchaserVo.setId(purchaserId);
		purchaserVo.setQuota(beFreeQuota.add(purchaser.getQuota()));
		this.updatePurchaserById(purchaserVo);
		return purchaserId;
	}
	
	public Integer increasePurchaserQuantity(Integer purchaserId) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Purchaser purchaser = this.purchaserDao.getPurchaserById(purchaserId);
		PurchaserVo purchaserVo = new PurchaserVo();
		purchaserVo.setId(purchaserId);
		purchaserVo.setQuantity(purchaser.getQuantity() + 1);
		this.updatePurchaserById(purchaserVo);
		return purchaserId;
	}
	
	public Integer inPurchaserAccount(Integer purchaserId, BigDecimal account, Integer fundType) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Assert.notNull(account, "入账账户资金不能为空");
		if(account.compareTo(BigDecimal.ZERO) == 0) {
			throw new ApolloBizException(ResultCode.PURCHASER_EXCEPTION, purchaserId, String.format("入账账户资金不能为0,[purchaserId=%s]", purchaserId));
		}
		Purchaser purchaser = this.purchaserDao.getPurchaserById(purchaserId);
		PurchaserVo purchaserVo = new PurchaserVo();
		purchaserVo.setId(purchaserId);
		purchaserVo.setAccount(account.add(purchaser.getAccount()));
		this.updatePurchaserById(purchaserVo);
		//买手入账
		this.purchaserDayAccountService.createDayAccount(purchaserId, null, null, PayTypeEnum.OFFLINE.getCode(), fundType, account, Component.NULL_PAY_SERIAL_NO);
		//发推送
		this.msgTemplate.push(MsgTemplateEnum.ACCOUNT_TRANSPORT, null, purchaserId, null);
		//发短信
		this.msgTemplate.sms(MsgTemplateEnum.ACCOUNT_TRANSPORT, purchaser.getMobile(), null);
		//消息盒子
		this.asyncService.messageBox(MsgTemplateEnum.ACCOUNT_TRANSPORT, MessageBoxTypeEnum.MSG_PAY, null, purchaserId, null); 
		return purchaserId;
	}

	public Integer outPurchaserAccount(Integer purchaserId, BigDecimal account, Integer fundType) {
		Assert.notNull(purchaserId, "买手id不能为空");
		Assert.notNull(account, "出账账户资金不能为空");
		if(account.compareTo(BigDecimal.ZERO) == 0) {
			throw new ApolloBizException(ResultCode.PURCHASER_EXCEPTION, purchaserId, String.format("出账账户资金不能为0,[purchaserId=%s]", purchaserId));
		}
		Purchaser purchaser = this.purchaserDao.getPurchaserById(purchaserId);
		PurchaserVo purchaserVo = new PurchaserVo();
		purchaserVo.setId(purchaserId);
		purchaserVo.setAccount(purchaser.getAccount().subtract(account));
		this.updatePurchaserById(purchaserVo);
		//买手入账
		this.purchaserDayAccountService.createDayAccount(purchaserId, null, null, PayTypeEnum.OFFLINE.getCode(), fundType, account, Component.NULL_PAY_SERIAL_NO);
		//发推送
		this.msgTemplate.push(MsgTemplateEnum.CASH_TRANSPORT, null, purchaserId, null);
		//发短信
		this.msgTemplate.sms(MsgTemplateEnum.CASH_TRANSPORT, purchaser.getMobile(), null);
		//消息盒子
		this.asyncService.messageBox(MsgTemplateEnum.CASH_TRANSPORT, MessageBoxTypeEnum.MSG_PAY, null, purchaserId, null); 
		return purchaserId;
	}

}
