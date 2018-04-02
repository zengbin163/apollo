/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.nologin.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.enums.ShowSourceEnum;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.proccess.PageListProcess;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.web.BaseAction;

public class UserAction extends BaseAction {
    
	private static final long serialVersionUID = -4937622145003046142L;
	@Autowired
    private UserService userService;
	@Autowired
	private PageListProcess pageListProcess;
	
	/**
	 * 查看消费者个人主页
	 * @param userId
	 * @return
	 */
	public String userHomePage(){
    	Integer userId = this.getIntParameter(request, "userId", null);
    	User user = this.userService.getUserById(userId);
    	this.returnFastJsonExcludeProperties(user, User.class, new String[]{"password","version"});
		return null;
	}
	
	/**
	 * 消费者个人主页晒单列表
	 * @param userId
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public String userShowList(){
    	Integer userId = this.getIntParameter(request, "userId", null);
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
    	returnFastJSON(this.pageListProcess.getShowOrderPoolListByOperator(ShowSourceEnum.USER_APPRAISE.getCode(), userId, null, null, pageOffset, pageSize));
    	return null;
	}
}
