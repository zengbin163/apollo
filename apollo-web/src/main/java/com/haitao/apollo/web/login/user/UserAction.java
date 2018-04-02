/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.login.user;

import java.math.BigDecimal;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.annotation.FromUser;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.service.order.PayOrderService;
import com.haitao.apollo.service.user.UserAppraiseService;
import com.haitao.apollo.service.user.UserRightsService;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.web.BaseAction;

public class UserAction extends BaseAction {
    
    private static final long serialVersionUID = 7672546430485123824L;

    @Autowired
    private UserService userService;
    @Autowired
    protected UserAppraiseService userAppraiseService;
	@Autowired
	private UserRightsService userRightsService;
	@Autowired
	private PayOrderService payOrderService;

	/**
	 * 评价买手出售的订单
	 * @param purchaserId 买手id
	 * @param isAnonym 是否匿名评价，0:否   1:是
	 * @param orderId 订单id
	 * @param score  评分 1-5分之间
	 * @param content 评价内容
	 * @return
	 */
    @FromUser
    public String appraise(){
    	Integer purchaserId = this.getIntParameter(request, "purchaserId", null);
    	Integer isAnonym = this.getIntParameter(request, "isAnonym", null);
    	Integer orderId = this.getIntParameter(request, "orderId", null);
    	Integer score = this.getIntParameter(request, "score", null);
    	String content = this.getFilteredParameter(request, "content", 0, null);
        /** 消费者的操作 **/
        if(!isUser()) {
        	throw new ApolloBizException(ResultCode.NOT_USER_REQUEST, getOperatorId(), String.format("非消费者端请求[userId=%s]", getOperatorId()));
        }
        Integer userAppraiseId = this.userAppraiseService.appraise(SessionContainer.getSession().getOperatorId(), purchaserId, isAnonym, orderId, score, content);
    	returnFastJSON(toMap("userAppraiseId", userAppraiseId));
    	return null;
    }
    
    
    /**
     * 修改用户基本信息
     * @param nickName 昵称
     * @param headerUrl 头像
     * @param address 当前位置（128个字符）
     * @param signature 签名 （256个字符）
     * @return
     */
    @FromUser
    public String modify(){
        String nickName = this.getFilteredParameter(request, "nickName", 0, null);
        String headerUrl = this.getFilteredParameter(request, "headerUrl", 0, null);
        String address = this.getFilteredParameter(request, "address", 0, null);
        String signature = this.getFilteredParameter(request, "signature", 0, null);
        /** 消费者的操作 **/
        if(!isUser()) {
        	throw new ApolloBizException(ResultCode.NOT_USER_REQUEST, getOperatorId(), String.format("非消费者端请求[userId=%s]", getOperatorId()));
        }
		User user = this.userService.updateUser(getOperatorId(), nickName,
				null, null, headerUrl, signature, address, new BigDecimal(0),
				null, null, null, null, null);
    	this.returnFastJsonExcludeProperties(user, User.class, new String[]{"password","version"});
        return null;
    }

	
	/**
	 * 消费者申请售后
	 * @param orderId
	 * @param reason
	 * @param picAddr1
	 * @param picAddr2
	 * @param picAddr3
	 * @return
	 */
    @FromUser
    @FromPurchaser
	public String applyRights() {
    	Integer orderId = this.getIntParameter(request, "orderId", null);
    	String reason = this.getFilteredParameter(request, "reason", 0, null);
 	    String picAddr1 = this.getFilteredParameter(request, "picAddr1", 0, null);
    	String picAddr2 = this.getFilteredParameter(request, "picAddr2", 0, null);
    	String picAddr3 = this.getFilteredParameter(request, "picAddr3", 0, null);
        /** 消费者的操作     @TODO是买手还是消费者发起的没有做区分 **/
//        if(!isUser()) {
//        	throw new ApolloBizException(ResultCode.NOT_USER_REQUEST, getOperatorId(), String.format("非消费者端请求[userId=%s]", getOperatorId()));
//        }
    	Integer rightsId = this.userRightsService.applyUserRights(orderId, reason, picAddr1, picAddr2, picAddr3);
    	returnFastJSON(toMap("rightsId", rightsId));
    	return null;
	}
	
	/**
	 * 我的账单（消费者端）
	 * @return
	 */
    @FromUser
	public String myBill() {
        /** 消费者的操作 **/
        if(!isUser()) {
        	throw new ApolloBizException(ResultCode.NOT_USER_REQUEST, getOperatorId(), String.format("非消费者端请求[userId=%s]", getOperatorId()));
        }
    	Integer month = this.getIntParameter(request, "month", null);
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
		if (null == month || 0 == month) {
			Calendar cal = Calendar.getInstance();
			month = cal.get(Calendar.MONTH) + 1;
		}
        returnFastJSON(this.payOrderService.myBillList(SessionContainer.getSession().getOperatorId(), month, pageOffset, pageSize));
        return null;
	}
    
	/**
	 * 查看消费者个人信息
	 * @return
	 */
    @FromUser
	public String userInfo(){
        if(!isUser()) {
        	throw new ApolloBizException(ResultCode.NOT_USER_REQUEST, getOperatorId(), String.format("非消费者端请求[userId=%s]", getOperatorId()));
        }
    	User user = this.userService.getUserById(getOperatorId());
    	returnFastJSON(user);
		return null;
	}
}
