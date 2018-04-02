/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.user.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.user.UserDao;
import com.haitao.apollo.enums.CachePrefixEnum;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.plugin.cache.RedisService;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.LoginOperator;
import com.haitao.apollo.plugin.session.Session;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.service.user.InviteCodeService;
import com.haitao.apollo.service.user.ReceiverService;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.service.user.impl.register.AbstractRegisterStrategy;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.util.LoggerUtil;
import com.haitao.apollo.util.MD5Util;
import com.haitao.apollo.util.SpringContextUtil;
import com.haitao.apollo.vo.user.UserVo;

/** 
* @ClassName: UserServiceImpl 
* @Description: 用户服务层
* @author zengbin
* @date 2015年10月28日 下午4:56:12 
*/
@Service
public class UserServiceImpl implements UserService {
    @Resource(name = "userDao")
    private UserDao userDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private InviteCodeService inviteCodeService;
    @Autowired
    private ReceiverService receiverService;
    @Resource(name = "registerStrategyMap")
    private Map<String,AbstractRegisterStrategy> registerStrategyMap;
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
	public User getUserById(Integer id){
		Assert.notNull(id,"用户id不能为空");
		String key = CachePrefixEnum.PREFIX_USER_INFO_.toString() + id;
		User user = (User)this.redisService.getObj(key);
		if(null==user){
			LoggerUtil.WARN(logger, String.format("用户信息在缓存中不存在，key = %s", key));
			user = this.userDao.getUserById(id);
			if (null == user) {
				throw new ApolloBizException(ResultCode.NOT_EXISTS, id, String.format("用户不存在[userId=%s]", id));
			}
			this.redisService.setObj(key, user);
		}
		return user;
	}
    
    public User login(String deviceId, String mobile,String password,Integer role,String token){
        Assert.notNull(deviceId,"deviceId不能为空");
        Assert.notNull(mobile,"手机号码不能为空");
        Assert.notNull(password,"密码不能为空");
        Assert.notNull(role,"登陆者角色不能为空");
        Assert.notNull(token,"IOS推送token不能为空");
        if(!OperatorRoleEnum.ROLE_USER.getCode().equals(role)){
        	throw new ApolloBizException(ResultCode.NOT_USER_REQUEST, mobile, ResultCode.NOT_USER_REQUEST.getString());
        }
        User user = this.userDao.getUserByMobileAndPassword(mobile, MD5Util.md5(password));
        if(null==user){
            throw new ApolloBizException(ResultCode.LOGIN_ERROR , mobile , ResultCode.LOGIN_ERROR.getString());
        }
        if(IsDefaultEnum.DEFAULT_YES.getCode().equals(user.getIsForbidLogin())) {
            throw new ApolloBizException(ResultCode.FORBID_LOGIN , mobile , ResultCode.FORBID_LOGIN.getString());
        }
        Session session = new Session();
        session.setDeviceId(deviceId);
        session.setRole(role);
        session.setOperatorId(user.getId());
        session.setLoginOperator(LoginOperator.transUser2Operator(user));
        session.setToken(token);
        redisService.setObj(CachePrefixEnum.PREFIX_SESSION_ + deviceId, session);
        SessionContainer.setSession(session);
        //更新IOS推送相关基本信息
		this.updateUser(user.getId(), null, null, null, null, null, null, null, deviceId, token, null, null, null);
		//查询用户默认地址
		user.setDefaultReceiver(this.receiverService.defaultReceiver());
        return user;
    }
    
    public void registerStrategy(String mobile, String inviteCode, String sms){
        Assert.notNull(mobile,"手机号码不能为空");
        Assert.notNull(inviteCode,"邀请码不能为空");
        Assert.notNull(sms,"短信验证码不能为空");
        //校验各种策略
        for(Map.Entry<String, AbstractRegisterStrategy> registerMap : registerStrategyMap.entrySet()){
            AbstractRegisterStrategy registerStrategy = registerMap.getValue();
            registerStrategy.execute(mobile, inviteCode, sms);
        }
    }
    
    public User register(String deviceId, String mobile, String password, String inviteCode, String sms, Integer role,String token){
        Assert.notNull(deviceId,"deviceId不能为空");
        Assert.notNull(mobile,"手机号码不能为空");
        Assert.notNull(password,"密码不能为空");
        Assert.notNull(inviteCode,"邀请码不能为空");
        Assert.notNull(sms,"短信验证码");
        Assert.notNull(role,"注册者角色不能为空");
        Assert.notNull(token,"IOS推送token不能为空");

        //注册校验策略
        this.registerStrategy(mobile, inviteCode.toUpperCase(), sms);
        //注册
        this.userDao.insertUser(toUserVo(deviceId, mobile, password, inviteCode, null, null));
        //登录
        User userPojo = this.login(deviceId, mobile, password, role, token);
        return userPojo;
    }
    
    public User findPassword(Integer role, String mobile, String sms, String password){
        Assert.notNull(role,"角色不能为空");
    	Assert.notNull(mobile,"手机号码不能为空");
        Assert.notNull(password,"密码不能为空");
        Assert.notNull(sms,"短信验证码");
        RedisService redisService = SpringContextUtil.getBean("redisService");
        String validateCode = redisService.get(MsgTemplateEnum.SMS_FINDPASS_USER + mobile);
        if(StringUtils.isEmpty(validateCode) || !validateCode.equals(sms)){
            throw new ApolloBizException(ResultCode.REG_CODE_ERROR  , mobile , ResultCode.REG_CODE_ERROR.getString());
        }
        User user = this.userDao.getUserByMobile(mobile);
        if(null == user) {
            throw new ApolloBizException(ResultCode.NO_REGISTER  , mobile , mobile + "此号码未注册");
        }
        return this.updateUser(user.getId(), null, MD5Util.md5(password), DateUtil.currentUTCTime(), null, null, null, null, null,null, null, null, null);
    }
    
    @Override
    public List<User> getUserListByPage(String nickName, Integer pageOffset, Integer pageSize) {
    	Assert.notNull(pageOffset,"分页起始页码不能为空");
    	Assert.notNull(pageSize,"分页每页的总数不能为空");
        UserVo userVo = new UserVo();
        userVo.setNickName(nickName);
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy("id");
        return this.userDao.getUserListByPage(userVo, page);
    }

    @Override
    public List<User> getUserListByPurchaserId(Integer purchaserId, Integer pageOffset, Integer pageSize) {
    	Assert.notNull(pageOffset,"分页起始页码不能为空");
    	Assert.notNull(pageSize,"分页每页的总数不能为空");
    	Assert.notNull(purchaserId,"买手id不能为空");
    	Page<?> page = new Page<>();
    	page.setPageNo(pageOffset);
    	page.setPageSize(pageSize);
    	page.setOrder(Page.DESC);
    	page.setOrderBy("id");
    	return this.userDao.getUserListByPurchaserId(purchaserId, page);
    }

    @Override
    public List<User> getForbiddenUserList(Integer pageOffset, Integer pageSize) {
    	Assert.notNull(pageOffset,"分页起始页码不能为空");
    	Assert.notNull(pageSize,"分页每页的总数不能为空");
    	Page<?> page = new Page<>();
    	page.setPageNo(pageOffset);
    	page.setPageSize(pageSize);
    	page.setOrder(Page.DESC);
    	page.setOrderBy("id");
    	return this.userDao.getForbiddenUserList(page);
    }

	public User updateUser(Integer id, String nickName, String password,
			Long lastLoginTime, String headerUrl, String signature,
			String address, BigDecimal bigMoney, String deviceId, String token,
			Integer isForbidPost, Integer isForbidLogin, Integer isForbidShow) {
		User user = this.userDao.getUserById(id);
		UserVo userVo = new UserVo(id, nickName, password, headerUrl,
				lastLoginTime, signature, address, bigMoney, deviceId, token,
				isForbidPost, isForbidLogin, isForbidShow);
		userVo.setVersion(user.getVersion() + 1);
		Integer flag = this.userDao.updateUser(userVo);
		if (flag <= 0) {
			throw new ApolloBizException(ResultCode.UPDATE_FAIL, id, String.format("用户信息更新失败，userId=%s", id));
		}
	    user = this.userDao.getUserById(id);
		this.redisService.setObj(CachePrefixEnum.PREFIX_USER_INFO_.toString() + id, user);
		return user;
	}
	
	public Integer increaseUserBigMoney(Integer userId, BigDecimal bigMoney) {
		Assert.notNull(userId, "用户id不能为空");
		Assert.notNull(bigMoney, "大牌币数不能为空");
		if(BigDecimal.ZERO.compareTo(bigMoney)==0) {
			throw new ApolloBizException(ResultCode.ILLEGAL_ARGUMENT, userId, String.format("增加用户大牌币，大牌币为空，[user=%s]", userId));
		}
		User user = this.userDao.getUserById(userId);
		BigDecimal userBig = (user.getBigMoney() == null) ? BigDecimal.ZERO : user.getBigMoney();
		BigDecimal addBig = bigMoney.add(userBig); 
		this.updateUser(userId, null, null, null, null, null, null, addBig, null, null, null, null, null);
		return userId;
	}

	/**
	 * 减少用户的大牌币
	 * @param userId 用户id
	 * @param bigMoney 大牌币数
	 * @return
	 */
	public Integer decreaseUserBigMoney(Integer userId, BigDecimal bigMoney) {
		Assert.notNull(userId, "用户id不能为空");
		Assert.notNull(bigMoney, "大牌币数不能为空");
		if(BigDecimal.ZERO.compareTo(bigMoney)==0) {
			throw new ApolloBizException(ResultCode.ILLEGAL_ARGUMENT, userId, String.format("减少用户大牌币，大牌币为空，[user=%s]", userId));
		}
		User user = this.userDao.getUserById(userId);
		BigDecimal userBig = (user.getBigMoney() == null) ? BigDecimal.ZERO : user.getBigMoney();
		BigDecimal subBig = userBig.subtract(bigMoney);
		this.updateUser(userId, null, null, null, null, null, null, subBig, null, null, null, null, null);
		return userId;
	}

	@Override
	public void forForbidPost(Integer userId) {
		Assert.notNull(userId, "用户id不能为空");
		this.updateUser(userId, null, null, null, null, null, null, null, null,
				null, IsDefaultEnum.DEFAULT_YES.getCode(), null, null);
	}

	@Override
	public void forForbidLogin(Integer userId) {
		Assert.notNull(userId, "用户id不能为空");
		this.updateUser(userId, null, null, null, null, null, null, null, null,
				null, null, IsDefaultEnum.DEFAULT_YES.getCode(), null);
	}

	@Override
	public void forForbidShow(Integer userId) {
		Assert.notNull(userId, "用户id不能为空");
		this.updateUser(userId, null, null, null, null, null, null, null, null,
				null, null, null, IsDefaultEnum.DEFAULT_YES.getCode());
	}
	
	@Override
	public void unForbidden(Integer userId) {
		Assert.notNull(userId, "用户id不能为空");
		this.updateUser(userId, null, null, null, null, null, null, null, null,
				null, IsDefaultEnum.DEFAULT_NO.getCode(),
				IsDefaultEnum.DEFAULT_NO.getCode(),
				IsDefaultEnum.DEFAULT_NO.getCode());
	}
	
	@Override
	public void logout(String deviceId) {
		SessionContainer.clear();
		this.redisService.del(CachePrefixEnum.PREFIX_SESSION_ + deviceId);
	}
	
    private UserVo toUserVo(String deviceId, String mobile, String password, String inviteCode, String nickName, String headerUrl){
        UserVo userVo = new UserVo();
        userVo.setDeviceId(deviceId);
        userVo.setHeaderUrl(headerUrl);
        userVo.setInviteCode(inviteCode);
        userVo.setMobile(mobile);
        userVo.setNickName(nickName);
        userVo.setPassword(MD5Util.md5(password));
        userVo.setBigMoney(new BigDecimal(0));
        return userVo;
    }
}