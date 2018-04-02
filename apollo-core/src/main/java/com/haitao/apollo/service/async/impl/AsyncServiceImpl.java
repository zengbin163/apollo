/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月13日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.async.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.SaleOrderTrackDao;
import com.haitao.apollo.enums.MessageBoxTypeEnum;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.enums.OrderTrackEnum;
import com.haitao.apollo.plugin.push.IOSPush;
import com.haitao.apollo.plugin.push.Push;
import com.haitao.apollo.plugin.sms.GJSmsMessageClient;
import com.haitao.apollo.plugin.sms.SmsMessageClient;
import com.haitao.apollo.service.async.AsyncService;
import com.haitao.apollo.service.message.MessageBoxService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.util.LoggerUtil;
import com.haitao.apollo.util.WebUtil;
import com.haitao.apollo.vo.order.SaleOrderTrackVo;

/** 
* @ClassName: AsyncServiceImpl 
* @Description: 异步接口
* @author zengbin
* @date 2015年11月13日 上午11:31:16 
*/
@Service
public class AsyncServiceImpl implements AsyncService {
    
    @Resource(name = "saleOrderTrackDao")
    private SaleOrderTrackDao saleOrderTrackDao;
    @Resource(name = "iosPush")
    private IOSPush iosPush;
	@Autowired	
	private MessageBoxService messageBoxService;

    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
	private static final SmsMessageClient client = new SmsMessageClient(SN, PWD);
	private static final GJSmsMessageClient gjClient = new GJSmsMessageClient(SN, PWD);

    @Async
    public void orderTrack(Integer userId, Integer orderId, Integer purchaserId,
                           Integer postrewardId, Integer rewardStatus, Integer orderStatus,
                           String bizCode, String track) {
        JSONObject json = new JSONObject();
        json.put("createTime", DateUtil.defaultFormatTime(new Date()));
        json.put("track", OrderTrackEnum.getEnum(bizCode).getDesc());
        json.put("extends", track);
        SaleOrderTrackVo saleOrderTrackVo = new SaleOrderTrackVo(orderId, userId, purchaserId, postrewardId, rewardStatus, orderStatus, bizCode, json.toJSONString());
        Integer trackId = this.saleOrderTrackDao.insertSaleOrderTrack(saleOrderTrackVo);
        if (null == trackId) {
            LoggerUtil.ERROR(logger, ResultCode.SAVE_FAIL, "订单轨迹保存失败，trackId为null");
        }
    }
    
    @Async
    public void push(String token, String content, String redirectUrl, Integer role) {
    	Map<String,Object> pushMap = new HashMap<String,Object>();
    	pushMap.put(Push.KEY_TOKEN, token);
    	pushMap.put(Push.KEY_CONTENT, content);
    	pushMap.put(Push.KEY_URL, redirectUrl);
    	pushMap.put(Push.KEY_ROLE, role);
    	this.iosPush.push(pushMap);
    }
    
    @Async
    public void sms(String mobile, String content) {
    	String ret = null;
    	if(WebUtil.isChinaSms(mobile)) {
			ret = client.sendSMS(mobile, content, "", "", "", "");
    	} else {
			ret = gjClient.sendSMS("00" + mobile, content, "", "", "");
    	}
        if(logger.isDebugEnabled()) {
        	LoggerUtil.DEBUG(logger, ret);
        }
    }
    
    @Async
    public void messageBox(MsgTemplateEnum msgTemplate, MessageBoxTypeEnum messageBoxType, Integer userId, Integer purchaserId, String extension) {
		Assert.notNull(msgTemplate, "消息模板不能为空");
		Assert.notNull(messageBoxType, "消息盒子类型不能为空");
		if(null==userId && null==purchaserId) {
			return;
		}
		Integer operatorId = null;
		if(OperatorRoleEnum.ROLE_PURCHASER.equals(msgTemplate.getOperatorEnum())) {
			operatorId = purchaserId;
		} else {
			operatorId = userId;
		}
    	this.messageBoxService.createMessageBox(messageBoxType.getCode(), String.format(msgTemplate.getDesc(), extension), msgTemplate.getOperatorEnum().getCode(), operatorId);
    }
    
    public static final void main(String []args) {
    	AsyncService service = new AsyncServiceImpl();
    	service.sms("393274581111", "亲爱的陈总，你在意大利好吗" + MsgTemplate.SIGN);
    }
}
