package com.haitao.apollo.service.backoperator;

import com.haitao.apollo.pojo.backoperator.BackOperator;

/**
 * 后台用户操作相关service
 * 
 * @author zengbin
 *
 */
public interface BackOperatorService {

	/**
	 * 后台系统登录
	 * @param mobile
	 * @param password
	 * @return
	 */
	BackOperator login(String mobile, String password);

	/**
	 * 验证是否处于登录状态
	 * @param mobile
	 * @param password
	 * @return
	 */
	boolean isLogin(String mobile, String password);

}
