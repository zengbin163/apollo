/*
 * @Project: GZJK
 * @Author: bin
 * @Date: 2015年6月23日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.haitao.apollo.base.ResultCode;

/** 
* @ClassName: ResultUtils 
* @Description: 结论工具类
* @author bin
* @date 2015年6月23日 下午4:58:51 
*/
public class ResultUtil {
    
    public static final String RETURN_KEY = "recode";
    public static final String MSG = "msg";
    public static final String DATA = "data";
    
    public static Map<String, Object> toMap(ResultCode resultCode, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(RETURN_KEY, resultCode.getCode());
        if (StringUtils.isEmpty(message)) {
            map.put(MSG, resultCode.getString());
        } else {
            map.put(MSG, message);
        }
        return map;
    }
    
}
