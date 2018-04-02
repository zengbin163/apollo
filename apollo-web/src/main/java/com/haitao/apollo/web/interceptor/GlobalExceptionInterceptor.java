package com.haitao.apollo.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.exception.ApolloSysException;
import com.haitao.apollo.util.LoggerUtil;
import com.haitao.apollo.util.ResultUtil;
import com.haitao.apollo.util.WebUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class GlobalExceptionInterceptor extends AbstractInterceptor {
    
    private static final long serialVersionUID = 8198284756139778765L;
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionInterceptor.class);
    
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String resultString = null;
        try {
            resultString = invocation.invoke();
        } catch (Exception e) {
            Map<String, Object> params = invocation.getInvocationContext().getParameters();
            String json = null;
            if (null != params) {
                json = JSONObject.toJSONString(params);
            }
            if (e.getClass().isAssignableFrom(IllegalArgumentException.class)) {
                LoggerUtil.ERROR(logger, String.format("[=========IllegalArgumentException======params : %s ; message : %s] ", json , e.getMessage()));
                write(ResultUtil.toMap(ResultCode.ILLEGAL_ARGUMENT , e.getMessage()));
                return null;
            }else if (e.getClass().isAssignableFrom(NoSuchMethodException.class)) {
                LoggerUtil.ERROR(logger, String.format("[=========NoSuchMethodException======params : %s ; message : %s] ", json , e.getMessage()));
                write(ResultUtil.toMap(ResultCode.NOT_EXISTS , e.getMessage()));
            	return null;
            }else if (e.getClass().isAssignableFrom(ApolloSysException.class)) {
                ApolloSysException ex = (ApolloSysException)e;
                LoggerUtil.ERROR(logger, ex.getResultCode(), String.format("[=========ApolloSysException======params : %s] ", json));
                write(ResultUtil.toMap(ex.getResultCode(), null));
                return null;
            }else if (e.getClass().isAssignableFrom(ApolloBizException.class)) {
                ApolloBizException ex = (ApolloBizException)e;
                LoggerUtil.ERROR(logger, ex.getResultCode(), String.format("[=========ApolloBizException======params : %s] ", json));
                write(ResultUtil.toMap(ex.getResultCode(), null));
                return null;
            }   else {
                LoggerUtil.ERROR(logger, String.format("[=========SystemException======params : %s] ", json), e);
                write(ResultUtil.toMap(ResultCode.SYSTEM_ERROR, null));
                return null;
            }
        }
        return resultString;
    }
    
    private void write(Object obj) {
        HttpServletResponse response = ServletActionContext.getResponse();
        WebUtil.returnJSON(response, JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue)
            .toString(), "json");
    }
}
