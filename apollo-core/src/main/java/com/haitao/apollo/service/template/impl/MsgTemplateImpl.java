package com.haitao.apollo.service.template.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.plugin.cache.RedisService;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.util.CodeUtil;
import com.haitao.apollo.util.LoggerUtil;

@Service
public class MsgTemplateImpl implements MsgTemplate {
	
    @Autowired
    private RedisService redisService;
	@Autowired
	private AsyncService asyncService;
	@Autowired
	private UserService userService;
	@Autowired
	private PurchaserService purchaserService;
	private static final int TIME_OUT = 3 * 60 ; //三分钟过去
	
	private static final Logger logger = LoggerFactory.getLogger(MsgTemplateImpl.class);
	
	public void push(MsgTemplateEnum msgTemplateEnum, Integer userId, Integer purchaserId, String extension) {
		Assert.notNull(msgTemplateEnum, "消息模板不能为空");
		if(null==userId && null==purchaserId) {
			LoggerUtil.ERROR(logger, "推送失败，用户id和买手id同时为空");
			return;
		}
		String token = null;
		if(OperatorRoleEnum.ROLE_PURCHASER.equals(msgTemplateEnum.getOperatorEnum())) {
			Purchaser purchaser = this.purchaserService.getPurchaserById(purchaserId);
			token = purchaser.getToken();
		} else {
			User user = this.userService.getUserById(userId);
			token = user.getToken();
		}
		this.asyncService.push(token, String.format(msgTemplateEnum.getDesc(), extension), msgTemplateEnum.getCode().toString(), msgTemplateEnum.getOperatorEnum().getCode());
	}
	
	public void push4IM(MsgTemplateEnum msgTemplateEnum, JSONObject json, Integer toUserId, Integer toUserRole) {
		Assert.notNull(msgTemplateEnum, "消息模板不能为空");
		String toToken = null;
		if(OperatorRoleEnum.ROLE_PURCHASER.equals(toUserRole)) {
			Purchaser purchaser = this.purchaserService.getPurchaserById(toUserId);
			toToken = purchaser.getToken();
		} else {
			User user = this.userService.getUserById(toUserId);
			toToken = user.getToken();
		}
		this.asyncService.push(toToken, msgTemplateEnum.getDesc(), json.toString(), toUserRole);
	}

	public void sms(MsgTemplateEnum msgTemplateEnum, String mobile, String extension) {
		Assert.notNull(msgTemplateEnum, "消息模板不能为空");
		Assert.notNull(mobile, "手机号码不能为空");
        String content = String.format(msgTemplateEnum.getDesc(), extension);
        this.asyncService.sms(mobile, content + SIGN);
	}

	public void sendSMS(MsgTemplateEnum msgTemplateEnum, String mobile) {
		Assert.notNull(msgTemplateEnum, "消息模板不能为空");
		Assert.notNull(mobile, "手机号码不能为空");
		String redisPrefix = msgTemplateEnum + mobile.substring(2);//这里会将国家码86带过来
		String code = CodeUtil.produceSmsCode();
		this.redisService.set(redisPrefix, code ,TIME_OUT);//插入缓存
		String content = String.format(msgTemplateEnum.getDesc(), code);
        this.asyncService.sms(mobile, content + SIGN);
	}
	
}
