/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月13日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.async;

import com.haitao.apollo.enums.MessageBoxTypeEnum;
import com.haitao.apollo.enums.MsgTemplateEnum;

/** 
* @ClassName: AsyncService 
* @Description: 异步服务接口
* @author zengbin
* @date 2015年11月13日 上午11:30:19 
*/
public interface AsyncService {

	public static final String SN = "SDK-BBX-010-24325";// 序列号
	public static final String PWD = "e-c==c-4";// 密码
	
	/**
	 * 订单跟踪轨迹
	 * @param userId
	 * @param orderId
	 * @param purchaserId
	 * @param postrewardId
	 * @param rewardStatus
	 * @param orderStatus
	 * @param bizCode
	 * @param track
	 */
    void orderTrack(Integer userId, Integer orderId, Integer purchaserId, Integer postrewardId,
                    Integer rewardStatus, Integer orderStatus, String bizCode, String track);
    
    /**
     * 推送接口
     * @param token   	     推送的唯一token Id
     * @param content     推送的内容
     * @param redirectUrl 透传消息跳转URL
     * @param role        操作者角色 @OperatorRoleEnum

     */
    void push(String token, String content, String redirectUrl, Integer role);
    
    /**
     * 发短信
     * @param mobile
     * @param content
     */
    void sms(String mobile, String content);
    
    /**
     * 异步插入消息盒子
     * @param messageBoxType
     * @param boxContent
     * @param operatorRole
     * @param operatorId
     */
    void messageBox(MsgTemplateEnum msgTemplate, MessageBoxTypeEnum messageBoxType, Integer userId, Integer purchaserId, String extension);
}
