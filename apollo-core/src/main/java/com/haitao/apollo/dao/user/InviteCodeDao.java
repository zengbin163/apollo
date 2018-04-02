/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.user;

import com.haitao.apollo.pojo.user.InviteCode;
import com.haitao.apollo.vo.user.InviteCodeVo;

/** 
* @ClassName: InviteCodeDao 
* @Description: 邀请码相关DAO
* @author zengbin
* @date 2015年11月10日 下午11:23:23 
*/
public interface InviteCodeDao {
    
    public void insertInviteCode(InviteCodeVo inviteCodeVo);

    public InviteCode getInviteCode(String inviteCode);
}
