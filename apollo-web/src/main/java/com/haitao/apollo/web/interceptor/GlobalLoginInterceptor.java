package com.haitao.apollo.web.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.annotation.FromUser;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.CachePrefixEnum;
import com.haitao.apollo.plugin.cache.RedisService;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.exception.ApolloSysException;
import com.haitao.apollo.plugin.session.Session;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.util.Base64Util;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class GlobalLoginInterceptor extends AbstractInterceptor {
    
    private static final long serialVersionUID = -7315374246047220621L;
    private static final String[] interceptMethods = { "index" };
    
    private static final String DEVICE_ID_SPLIT = "\\|";
    private static final String PURCHASER_ROLE = "role=0";
    private static final String USER_ROLE = "role=1";
    
    @Autowired
    private RedisService redisService;
    
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
        String url = request.getRequestURL().toString() ;   //请求的URL
        if(skipUrl(url)){//哪些URL需要被跳过去
            return invocation.invoke();
        }
        String method = invocation.getProxy().getMethod();
        if (!ArrayUtils.contains(interceptMethods, method)) {//这些方法需要登录
            Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
            Object deviceId = parameters.get("deviceId");
            String[] devId = (String[])deviceId;
            Assert.notNull(devId,"deviceId 为空");
            Assert.hasText(devId[0],"deviceId 为空");
            this.validatePlatform(invocation, devId[0]);//验证平台的合理性
            Session session = SessionContainer.getSession(); 
            if(null==session){
                Object obj = redisService.getObj(CachePrefixEnum.PREFIX_SESSION_ + devId[0]);
                if(null == obj){
                    throw new ApolloSysException(ResultCode.NO_LOGIN , devId[0] , ResultCode.NO_LOGIN.getString());
                }
              SessionContainer.setSession((Session)obj);
            }
        }
        return invocation.invoke();
    }
    
    private boolean skipUrl(String url){
        if(url.contains("apollo/login")){
            return false;
        }
        return true;
    }
    
    private void validatePlatform(ActionInvocation invocation, String deviceId) {
    	Method actionMethod = ClassUtils.getMethod(invocation.getAction().getClass(), invocation.getProxy().getMethod());
		if (!actionMethod.isAnnotationPresent(FromUser.class)
				&& !actionMethod.isAnnotationPresent(FromPurchaser.class)) {
			throw new ApolloBizException(ResultCode.PLATFORM_ERROR, deviceId, "请求必须来自买手端或者消费者端");
		}
		//校验deviceId合法性
		String textDeviceId = Base64Util.decode(deviceId);
		if(StringUtils.isEmpty(textDeviceId)) {
			throw new ApolloBizException(ResultCode.DEVICE_ID_ERROR, deviceId, "deviceId非法，deviceId通过base64解密后为空");
		}
		String []devices = textDeviceId.split(DEVICE_ID_SPLIT);
		if (null == devices || 0 == devices.length) {
			throw new ApolloBizException(ResultCode.DEVICE_ID_ERROR, deviceId, "deviceId非法，deviceId通过base64解密后并通过|拆分后为空数组");
		}
		for(String dev : devices) {
			if(PURCHASER_ROLE.equals(dev)) {
				FromPurchaser fromPurchaser = actionMethod.getAnnotation(FromPurchaser.class);
				if(null == fromPurchaser) {
					throw new ApolloBizException(ResultCode.DEVICE_ID_ERROR, deviceId, "action方法没有生命注解FromPurchaser");
				}
				return;
			} else if(USER_ROLE.equals(dev)) {
				FromUser fromUser = actionMethod.getAnnotation(FromUser.class);
				if(null == fromUser) {
					throw new ApolloBizException(ResultCode.DEVICE_ID_ERROR, deviceId, "action方法没有生命注解FromUser");
				}
				return;
			} else {
				;
			}
		}
		throw new ApolloBizException(ResultCode.DEVICE_ID_ERROR, deviceId, "deviceId非法，deviceId明文没有携带role参数，或者role的值不正确");
    }
}
