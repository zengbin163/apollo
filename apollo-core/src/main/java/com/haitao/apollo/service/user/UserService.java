/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.user;

import java.math.BigDecimal;
import java.util.List;

import com.haitao.apollo.pojo.user.User;

/** 
* @ClassName: UserService 
* @Description: 用户相关service
* @author zengbin
* @date 2015年10月28日 下午4:55:15 
*/
public interface UserService {
	User getUserById(Integer id);
    List<User> getUserListByPage(String nickName, Integer pageOffset, Integer pageSize);
    List<User> getUserListByPurchaserId(Integer purchaserId, Integer pageOffset, Integer pageSize);
    List<User> getForbiddenUserList(Integer pageOffset, Integer pageSize);
    void registerStrategy(String mobile, String inviteCode, String sms);
    User login(String deviceId, String mobile,String password,Integer role,String token); 
    User register(String deviceId, String mobile, String password, String inviteCode, String sms, Integer role,String token);
    User findPassword(Integer role, String mobile, String sms, String password);
	User updateUser(Integer id, String nickName, String password,
			Long lastLoginTime, String headerUrl, String signature,
			String address, BigDecimal bigMoney, String deviceId, String token,
			Integer isForbidPost, Integer isForbidLogin, Integer isForbidShow);
	/**
	 * 增加用户的大牌币
	 * @param userId 用户id
	 * @param bigMoney 大牌币数
	 * @return
	 */
	Integer increaseUserBigMoney(Integer userId, BigDecimal bigMoney);

	/**
	 * 减少用户的大牌币
	 * @param userId 用户id
	 * @param bigMoney 大牌币数
	 * @return
	 */
	Integer decreaseUserBigMoney(Integer userId, BigDecimal bigMoney);
	/**
	 * 禁止悬赏
	 * @param userId
	 * @return
	 */
	void forForbidPost(Integer userId);
	/**
	 * 禁止登录
	 * @param userId
	 * @return
	 */
	void forForbidLogin(Integer userId);
	/**
	 * 禁止晒单
	 * @param userId
	 * @return
	 */
	void forForbidShow(Integer userId);
	/**
	 * 解禁消费者的所有禁令
	 * @param userId
	 * @return
	 */
	void unForbidden(Integer userId);
	
	/**
	 * 注销（消费者和买手公用一个）
	 * @param deviceId
	 */
	void logout(String deviceId);
}
