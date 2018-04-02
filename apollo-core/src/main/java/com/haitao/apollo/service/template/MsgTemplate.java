package com.haitao.apollo.service.template;

import com.alibaba.fastjson.JSONObject;
import com.haitao.apollo.enums.MsgTemplateEnum;

public interface MsgTemplate {
	public static final String SIGN = "【赏购私语】";// 签名

	/**
	 * 推送
	 * @param msgTemplateEnum
	 * @param userId
	 * @param purchaserId
	 * @param extension 扩展内容，类似于一些动态变量
	 */
	void push(MsgTemplateEnum msgTemplateEnum, Integer userId, Integer purchaserId, String extension);

	/**
	 * IM推送
	 * @param msgTemplateEnum
	 * @param json
	 * @param toUserId
	 * @param toUserRole
	 */
	void push4IM(MsgTemplateEnum msgTemplateEnum, JSONObject json, Integer toUserId, Integer toUserRole);

	/**
	 * 短信
	 * @param msgTemplateEnum
	 * @param mobile
	 * @param extension
	 */
	void sms(MsgTemplateEnum msgTemplateEnum, String mobile, String extension);

	/**
	 * 登录注册发送短信息
	 * @param msgTemplateEnum
	 * @param mobile
	 */
	void sendSMS(MsgTemplateEnum msgTemplateEnum, String mobile);
}
