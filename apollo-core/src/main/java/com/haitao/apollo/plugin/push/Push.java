/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月16日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.plugin.push;

import java.util.Map;

/** 
* @ClassName: Push 
* @Description: 推送相关
* @author zengbin
* @date 2015年11月16日 下午4:31:43 
*/
public abstract class Push {
    
    public static final String KEY_TOKEN = "token";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_URL = "redirectUrl";
    public static final String KEY_ROLE = "role";
    
    /**
     * <pre>
     *    key 
     *       token   		推送的唯一token Id
     *       content    	推送的内容
     *       redirectUrl    透传消息跳转URL
     *       role           操作者角色 @OperatorRoleEnum
     * </pre>
     * @param pushMap
     */
    abstract void push(Map<String,Object> pushMap);
    
}
