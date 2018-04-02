package com.haitao.apollo.service.backoperator.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.backoperator.BackOperatorDao;
import com.haitao.apollo.enums.CachePrefixEnum;
import com.haitao.apollo.plugin.cache.RedisService;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.backoperator.BackOperator;
import com.haitao.apollo.service.backoperator.BackOperatorService;
import com.haitao.apollo.util.MD5Util;

@Service
public class BackOperatorServiceImpl implements BackOperatorService {
    
	@Resource(name = "backOperatorDao")
    private BackOperatorDao backOperatorDao;
    @Autowired
    private RedisService redisService;

	@Override
	public BackOperator login(String mobile, String password) {
		Assert.notNull(mobile, "手机号码不能为空");
		Assert.notNull(password, "密码不能为空");
		BackOperator backOperator = this.backOperatorDao.getBackOperatorByMobileAndPassword(mobile, MD5Util.md5(password));
        if(null==backOperator){
            throw new ApolloBizException(ResultCode.LOGIN_ERROR , mobile , ResultCode.LOGIN_ERROR.getString());
        }
		String key = CachePrefixEnum.PREFIX_BACK_OPERATOR_SESSION_.toString() + mobile + MD5Util.md5(password);
        this.redisService.setObj(key, backOperator);
		return backOperator;
	}
	
	@Override
	public boolean isLogin(String mobile, String password) {
		Assert.notNull(mobile, "手机号码不能为空，这是一个简单的登录拦截");
		Assert.notNull(password, "密码不能为空，这是一个简单的登录拦截");
		String key = CachePrefixEnum.PREFIX_BACK_OPERATOR_SESSION_.toString() + mobile + MD5Util.md5(password);
		Object obj = this.redisService.getObj(key);
		if(null == obj) {
			this.login(mobile, password);
		}
		return true;
	}
}
