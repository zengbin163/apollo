/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月14日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* @ClassName: DateUtil 
* @Description: 日期相关工具类
* @author zengbin
* @date 2015年11月14日 下午5:13:10 
*/
public class DateUtil {
    private static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    
    public static Long currentUTCTime(){
    	return new Date().getTime();
    }
    
    public static String defaultFormatTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
        return dateFormat.format(date);
    }
    
    public static String defaultFormatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        return dateFormat.format(date);
    }
    
    public static Date defaultParseTime(String date) {
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Date defaultParseDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
