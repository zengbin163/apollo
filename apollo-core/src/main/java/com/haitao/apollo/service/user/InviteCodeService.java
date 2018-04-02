/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.user;

import com.haitao.apollo.pojo.user.InviteCode;

/** 
* @ClassName: InviteCodeService 
* @Description: 邀请码相关接口
* @author zengbin
* @date 2015年11月10日 下午2:19:41 
*/
public interface InviteCodeService {
    
    /**
     * <pre>
     *    发送邀请码并插入数据库
     * </pre>
     * @param inviteRole
     * @return 返回邀请码
     */
    String saveInviteCode(Integer inviteRole);
    
    InviteCode getInviteCode(String inviteCode);
    
}
