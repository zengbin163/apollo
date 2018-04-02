/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pojo.user;

import java.io.Serializable;

/** 
* @ClassName: InviteCode 
* @Description: 邀请码
* @author zengbin
* @date 2015年11月10日 上午11:29:06 
*/
public class InviteCode implements Serializable {
    
    private static final long serialVersionUID = -3384539804504410326L;
    
    private Integer id;
    private Integer inviteId;// 邀请人id
    private Integer inviteRole;// 邀请人角色 	@OperatorRoleEnum
    private String inviteCode;// 邀请码
    private Long createTime;// 创建时间
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
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
