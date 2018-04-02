/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月14日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.login.message;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.annotation.FromUser;
import com.haitao.apollo.proccess.MessageProcess;
import com.haitao.apollo.web.BaseAction;

/** 
* @ClassName: ImMessageAction 
* @Description: 即时消息action
* @author zengbin
* @date 2015年12月176日 下午19:57:37 
*/
public class ImMessageAction extends BaseAction {
    
	private static final long serialVersionUID = -811274702917865673L;
	@Autowired
    private MessageProcess messageProcess;

	/**
	 * 发送im消息
	 * @param fromUserId
	 * @param toUserId
	 * @param fromUserRole
	 * @param toUserRole
	 * @param content
	 * @return
	 */
	@FromUser
	@FromPurchaser
	public String sendMsg(){
    	Integer fromUserId = this.getIntParameter(request, "fromUserId", null);
    	Integer toUserId = this.getIntParameter(request, "toUserId", null);
    	Integer fromUserRole = this.getIntParameter(request, "fromUserRole", null);
    	Integer toUserRole = this.getIntParameter(request, "toUserRole", null);
    	String content = this.getFilteredParameter(request, "content", 0, null);
    	returnFastJSON(this.messageProcess.sendMessage(fromUserId, toUserId, fromUserRole, toUserRole, content));
    	return null;
	}
	
	/**
	 * 根据来源方和接收方查询消息列表
	 * @param fromUserId
	 * @param toUserId
	 * @param fromUserRole
	 * @param toUserRole
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	@FromUser
	@FromPurchaser
	public String msgList(){
    	Integer fromUserId = this.getIntParameter(request, "fromUserId", null);
    	Integer toUserId = this.getIntParameter(request, "toUserId", null);
    	Integer fromUserRole = this.getIntParameter(request, "fromUserRole", null);
    	Integer toUserRole = this.getIntParameter(request, "toUserRole", null);
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
    	returnFastJSON(this.messageProcess.getImMessageListByFromTo(fromUserId, toUserId, fromUserRole, toUserRole, pageOffset, pageSize));
    	return null;
	}
}
