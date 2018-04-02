/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月26日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: WebUtil 
* @Description: web层的工具类
* @author zengbin
* @date 2015年10月26日 下午5:21:00 
*/
public class WebUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
    
    public static void returnJSON(HttpServletResponse response, String jsonData, String dataType) {
        if ("text".equals(dataType)) {
            response.setContentType("text/html;charset=UTF-8");
        } else {
            response.setContentType("application/json;charset=UTF-8");
        }
        response.setHeader("Charset", "UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            LoggerUtil.ERROR(logger, String.format(e.getMessage()));
        }
        out.write(jsonData);
        out.flush();
    }
    
    public static String escapeString(String value) {
        if (value == null) {
            return "";
        }
        value = StringUtils.replace(value, "|", "");
        value = StringUtils.replace(value, "&amp;", "");
        value = StringUtils.replace(value, "$", "");
        value = StringUtils.replace(value, "'", "");
        value = StringUtils.replace(value, "\"", "");
        value = StringUtils.replace(value, "\\'", "");
        value = StringUtils.replace(value, "&lt;", "");
        value = StringUtils.replace(value, "&gt;", "");
        value = StringUtils.replace(value, "<", "");
        value = StringUtils.replace(value, ">", "");
        value = StringUtils.replace(value, "\n", "");
        value = StringUtils.replace(value, "\r", "");
        value = StringUtils.replace(value, "\\", "");
        
        return value;
    }
    
	
	/**
	 * 校验是否为中国手机号码
	 * @param mobile
	 * @return
	 */
	public static boolean isChinaSms(String mobile) {
		if(StringUtils.isEmpty(mobile)) {
			return false;
		}
		if(mobile.startsWith("86")) {
			return true;
		}else{
			return false;
		}
	}
}
