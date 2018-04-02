package com.haitao.apollo.service.purchaser;

import java.math.BigDecimal;
import java.util.List;

import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.vo.purchaser.PurchaserVo;

public interface PurchaserService {
	
	/**
	 * 买手录入
	 * @param purchaserName
	 * @param mobile
	 * @param password
	 * @param signature
	 * @param headerUrl
	 * @param guarantee
	 * @param quota
	 * @param account
	 * @param vip
	 * @param quantity
	 * @param alipayAccount
	 * @param bankName
	 * @param bankAccount
	 * @param email
	 * @param sex
	 * @param birth
	 * @param address
	 * @param idCardFrontUrl
	 * @param idCardBackUrl
	 * @param studentIdCardUrl
	 * @param vipCardUrl
	 * @param creditCardAccUrl
	 * @param liveForeverUrl
	 * @param utilityBillUrl
	 * @param mobileBillUrl
	 * @param webServiceUrl
	 * @param drivingLicenceUrl
	 * @param overseasProveUrl
	 * @return
	 */
	Purchaser purchaserInput(String purchaserName, String mobile,
			String password, String signature, String headerUrl,
			BigDecimal guarantee, BigDecimal quota, BigDecimal account,
			Integer vip, Integer quantity, String alipayAccount,
			String bankName, String bankAccount, String email, Integer sex,
			String birth, String address, String idCardFrontUrl,
			String idCardBackUrl, String studentIdCardUrl, String vipCardUrl,
			String creditCardAccUrl, String liveForeverUrl,
			String utilityBillUrl, String mobileBillUrl, String webServiceUrl,
			String drivingLicenceUrl, String overseasProveUrl);
	/**
	 * 买手退出
	 * @param purchaserId
	 */
	void purchaserOutput(Integer purchaserId);
	/**
	 * 查询买手列表
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	List<Purchaser> getPurchaserList(Integer pageOffset, Integer pageSize);
	Purchaser login(String deviceId, String mobile,String password,Integer role,String token);
	Purchaser getPurchaserById(Integer id);
	Purchaser updatePurchaserById(PurchaserVo purchaserVo);
	Purchaser findPassword(Integer role, String mobile, String sms, String password);

	/**
	 * 买手充值成功增加账户保证金
	 * @param purchaserId 买手id
	 * @param guarantee 买手充值的保证金，单位分
	 * @return
	 */
	Integer increasePurchaserGuarantee(Integer purchaserId, BigDecimal guarantee);

	/**
	 * 买手被处罚减少账户保证金
	 * @param purchaserId 买手id
	 * @param guarantee 买手减少的保证金，单位分
	 * @return
	 */
	Integer decreasePurchaserGuarantee(Integer purchaserId, BigDecimal guarantee);
	
	/**
	 * 减少买手额度
	 * @param purchaserId 买手id
	 * @param beReduceQuota 被减少的额度，单位分
	 * @return
	 */
	Integer reducePurchaserQuota(Integer purchaserId, BigDecimal beReduceQuota);

	/**
	 * 释放买手额度
	 * @param purchaserId 买手id
	 * @param beFreeQuota 被释放的额度，单位分
	 * @return
	 */
	Integer freePurchaserQuota(Integer purchaserId, BigDecimal beFreeQuota);
	
	/**
	 * 累加接单量
	 * @param purchaserId 买手id
	 * @return
	 */
	Integer increasePurchaserQuantity(Integer purchaserId);
	
	/**
	 * 买手入账
	 * @param purchaserId
	 * @param account
	 * @return
	 */
	Integer inPurchaserAccount(Integer purchaserId, BigDecimal account, Integer fundType);

	/**
	 * 买手出账
	 * @param purchaserId
	 * @param account
	 * @return
	 */
	Integer outPurchaserAccount(Integer purchaserId, BigDecimal account, Integer fundType);
}
