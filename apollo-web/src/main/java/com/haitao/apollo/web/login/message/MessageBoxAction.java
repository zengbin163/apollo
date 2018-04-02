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
* @ClassName: MessageBoxAction 
* @Description: 消息盒子action
* @author zengbin
* @date 2015年12月176日 下午19:57:37 
*/
public class MessageBoxAction extends BaseAction {
    
	private static final long serialVersionUID = -811274702917865673L;
	@Autowired
    private MessageProcess messageProcess;

	/**
	 * 消息盒子首页每个类型下面的消息信息
	 * @return
	 */
	@FromUser
	@FromPurchaser
	public String msgBoxIndex(){
    	returnFastJSON(this.messageProcess.msgBoxIndex());
    	return null;
	}
	
	/**
	 * 根据消息盒子类型查询消息列表
	 * @param boxType 0:订单通知  1:结算通知  2:社交通知
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	@FromUser
	@FromPurchaser
	public String msgBoxList(){
    	Integer boxType = this.getIntParameter(request, "boxType", null);
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
    	returnFastJSON(this.messageProcess.getMessageBoxList(boxType, pageOffset, pageSize));
    	return null;
	}
}
