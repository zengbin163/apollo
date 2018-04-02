/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月15日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.proccess;

import com.haitao.apollo.plugin.session.Session;
import com.haitao.apollo.plugin.session.SessionContainer;

/** 
* @ClassName: Process 
* @Description: 流程引擎
* @author zengbin
* @date 2015年11月15日 下午3:30:40 
*/
public abstract class Process {
    
    private static final Integer NO_USER_ID  = -10000;
    public static final String  SPLIT_COMMA = ",";
    public static final String  REFUND_PREFIX = "REFUND_";
    
    /***
     * 
     * <pre>
     *   返回用户id，如果用户未登录，用户id统一等于-10000
     * </pre>
     * @return
     */
    public Integer getOperatorId(){
        Session session = SessionContainer.getSession();
        if(null==session){
            return NO_USER_ID;
        }
        return session.getOperatorId();
    }
}

