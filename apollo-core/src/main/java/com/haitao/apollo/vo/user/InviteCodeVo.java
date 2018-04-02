/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.vo.user;

import java.io.Serializable;

/** 
* @ClassName: InviteCodeVo 
* @Description: 邀请码相关Vo
* @author zengbin
* @date 2015年11月10日 上午11:29:06 
*/
public class InviteCodeVo implements Serializable {
    
    private static final long serialVersionUID = -8105822992123154933L;
    private Integer inviteId;// 邀请人id
    private Integer inviteRole;// 邀请人角色
    private String inviteCode;// 邀请码
    private Long createTime;
    
    public InviteCodeVo(){
        
    }

    public InviteCodeVo(Integer inviteId, Integer inviteRole, String inviteCode, Long createTime) {
        this.inviteId = inviteId;
        this.inviteRole = inviteRole;
        this.inviteCode = inviteCode;
        this.createTime = createTime;
    }
    
    public Integer getInviteId() {
        return inviteId;
    }
    
    public void setInviteId(Integer inviteId) {
        this.inviteId = inviteId;
    }
    
    public Integer getInviteRole() {
        return inviteRole;
    }
    
    public void setInviteRole(Integer inviteRole) {
        this.inviteRole = inviteRole;
    }
    
    public String getInviteCode() {
        return inviteCode;
    }
    
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
