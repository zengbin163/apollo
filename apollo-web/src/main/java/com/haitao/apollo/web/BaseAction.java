/*
 * @Project: GZJK
 * @Author: bin
 * @Date: 2015年6月19日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.plugin.exception.ApolloSysException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.util.WebUtil;
import com.haitao.apollo.web.filter.SimplePropertyExcludeFilter;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/***
 * 
* @ClassName: BaseAction 
* @Description: BaseAction，所有action的父类
* @author zengbin
* @date 2015年10月28日 下午5:02:22
 */
public abstract class BaseAction extends ActionSupport implements ServletRequestAware,
                                                      ServletResponseAware, Preparable {
    
    private static final long serialVersionUID = 6441661476094389876L;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Object result;
    
    protected static final String USER_APP_ID = "app_iLuDy5vnX5GS8q5e";
    protected static final String PURCHASER_APP_ID = "app_WXLe50n5yP8OnH88";
    
    protected static final int DEFAULT_VALUE = 0;
    
    protected boolean isPurchaser(){
    	if(null == SessionContainer.getSession()) {
    		throw new ApolloSysException(ResultCode.NO_LOGIN.getString());
    	}
    	if(OperatorRoleEnum.ROLE_PURCHASER.getCode().equals(SessionContainer.getSession().getRole())){
    		return Boolean.TRUE;
    	}
    	return Boolean.FALSE;
    }

    protected boolean isUser(){
    	if(null == SessionContainer.getSession()) {
    		throw new ApolloSysException(ResultCode.NO_LOGIN.getString());
    	}
    	if(OperatorRoleEnum.ROLE_USER.getCode().equals(SessionContainer.getSession().getRole())){
    		return Boolean.TRUE;
    	}
    	return Boolean.FALSE;
    }
    
    protected Integer getOperatorId(){
    	if(null == SessionContainer.getSession()) {
    		throw new ApolloSysException(ResultCode.NO_LOGIN.getString());
    	}
        return SessionContainer.getSession().getOperatorId();
    }
    
	protected Map<String,Object> toMap(String key, Object obj) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(key, obj);
		return map;
	}
    
    protected void returnFastJSON(Object obj) {
        if (obj != null) {
            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.SUCCESS.getCode() + "\"," + "\"data\":" + 
            		JSON.toJSONString(obj,
									SerializerFeature.WriteMapNullValue, 		//输出空置字段
									SerializerFeature.WriteNullListAsEmpty,		//list字段如果为null，输出为[]，而不是null
									SerializerFeature.WriteNullNumberAsZero,	//数值字段如果为null，输出为0，而不是null
									SerializerFeature.WriteNullBooleanAsFalse,	//Boolean字段如果为null，输出为false，而不是null
									SerializerFeature.WriteNullStringAsEmpty)	//字符类型字段如果为null，输出为""，而不是null
									.toString() + ",\"msg\":\"" + ResultCode.SUCCESS.getString() + "\"}"), "json");
        } else {
            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.NOT_EXISTS.getCode() + "\"," + "\"msg\":\"" + ResultCode.NOT_EXISTS.getString() + "\"}"), "json");
        }
    }
    
	/**
	 * <pre>
	 *     需要哪些属性的对象或者集合转化为JSON（支持对象实体、集合List、Map）
	 * </pre>
	 * @param obj  单个对象/集合/Map
	 * @param clazz  对象的Class，比如HashMap.class
	 * @param properties  哪些属性是需要包括进来的，可以通过逗号分隔或者字符串数组，如："name","age"   或者    String []strs = {"name","age"}
	 */
	protected void  returnFastJsonIncludeProperties(Object obj, Class<?> clazz, String ...properties){
	       if(obj != null){
	           SimplePropertyPreFilter filter = new SimplePropertyPreFilter(clazz, properties);
	            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.SUCCESS.getCode() + "\"," + "\"data\":" + 
	            		JSON.toJSONString(obj, filter, 
								SerializerFeature.WriteMapNullValue,
								SerializerFeature.WriteNullListAsEmpty,
								SerializerFeature.WriteNullNumberAsZero,
								SerializerFeature.WriteNullBooleanAsFalse,
								SerializerFeature.WriteNullStringAsEmpty).toString() + ",\"msg\":\"" + ResultCode.SUCCESS.getString() + "\"}"), "json");
	        }else{
	            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.NOT_EXISTS.getCode() + "\"," + "\"msg\":\"" + ResultCode.NOT_EXISTS.getString() + "\"}"), "json");
	        }
	}

	/**
	 * <pre>
	 *     需要排除哪些属性的对象或者集合转化为JSON（支持对象实体、集合List、Map）
	 * </pre>
	 * @param obj  单个对象/集合/Map
	 * @param clazz  对象的Class，比如HashMap.class
	 * @param properties  哪些属性是需要排除的，可以通过逗号分隔或者字符串数组，如："name","age"   或者    String []strs = {"name","age"}
	 */
	protected void  returnFastJsonExcludeProperties(Object obj, Class<?> clazz, String ...properties){
	    if(obj != null){
	        SimplePropertyExcludeFilter filter = new SimplePropertyExcludeFilter(clazz, properties);
            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.SUCCESS.getCode() + "\"," + "\"data\":" +
            		JSON.toJSONString(obj, filter, 
            				SerializerFeature.WriteMapNullValue,
							SerializerFeature.WriteNullListAsEmpty,
							SerializerFeature.WriteNullNumberAsZero,
							SerializerFeature.WriteNullBooleanAsFalse,
							SerializerFeature.WriteNullStringAsEmpty).toString() + ",\"msg\":\"" + ResultCode.SUCCESS.getString() + "\"}"), "json");
	    }else{
            WebUtil.returnJSON(response,("{\"recode\":\"" + ResultCode.NOT_EXISTS.getCode() + "\"," + "\"msg\":\"" + ResultCode.NOT_EXISTS.getString() + "\"}"), "json");
	    }
	}
    
    protected String getFilteredParameter(HttpServletRequest request, String name, int maxLength, String defaultString) {
        String[] temp = request.getParameterValues(name);
        if ((temp != null) && (temp.length > 0) && (!(temp[0].equals("")))) {
			try {
				String tempString = URLDecoder.decode(temp[0], "utf-8"); //解码
	            String ret = WebUtil.escapeString(tempString);
	            if (maxLength > 0) {
	                ret = StringUtils.substring(ret, 0, maxLength);
	            }
	            return ret;
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return defaultString;
    }
    
    protected Integer getIntParameter(HttpServletRequest request, String name, Integer defaultNum) {
        String[] temp = request.getParameterValues(name);
        if ((temp != null) && (temp.length > 0) && (!(temp[0].equals("")))) {
            Integer num = defaultNum;
            try {
                num = Integer.valueOf(Integer.parseInt(temp[0]));
            } catch (Exception localException) {
            }
            return num;
        }
        return defaultNum;
    }
    
    protected Long getLongParameter(HttpServletRequest request, String name, Long defaultNum) {
    	String[] temp = request.getParameterValues(name);
    	if ((temp != null) && (temp.length > 0) && (!(temp[0].equals("")))) {
    		Long num = defaultNum;
    		try {
    			num = Long.valueOf(Integer.parseInt(temp[0]));
    		} catch (Exception localException) {
    		}
    		return num;
    	}
    	return defaultNum;
    }

    protected BigDecimal getDecimalParameter(HttpServletRequest request, String name, BigDecimal defaultNum) {
         String[] temp = request.getParameterValues(name);
         if ((temp != null) && (temp.length > 0) && (!(temp[0].equals("")))) {
             BigDecimal num = defaultNum;
             try {
                 num = new BigDecimal(temp[0]);
             } catch (Exception localException) {
             }
             return num;
         }
         return defaultNum;
    }
    
    protected Map<String,Object> success() {
		return toMap("resultType", "SUCCESS");
    }

    protected Map<String,Object> failure() {
		return toMap("resultType", "FAILURE");
    }
    
	/**
	 * 获取服务器的路径(tomcat/webapp)
	 * @param request
	 * @return
	 */
	protected String getServerPath() {
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		return basePath;
	}
    
    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    @Override
    public void prepare() throws Exception {
    }
    
    public Object getResult() {
        return result;
    }
    
    public void setResult(Object result) {
        this.result = result;
    }

}
