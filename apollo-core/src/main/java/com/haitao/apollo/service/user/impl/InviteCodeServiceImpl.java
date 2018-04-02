/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月10日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.user.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.dao.user.InviteCodeDao;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.user.InviteCode;
import com.haitao.apollo.service.user.InviteCodeService;
import com.haitao.apollo.util.CodeUtil;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.util.LoggerUtil;
import com.haitao.apollo.vo.user.InviteCodeVo;

/** 
* @ClassName: InviteCodeServiceImpl 
* @author zengbin
* @date 2015年11月10日 下午7:50:48 
*/
@Service
public class InviteCodeServiceImpl implements InviteCodeService {
    
    @Resource(name = "inviteCodeDao")
    private InviteCodeDao inviteCodeDao;
    private static final Logger logger = LoggerFactory.getLogger(InviteCodeServiceImpl.class);
    
    @Override
    public String saveInviteCode(Integer inviteRole) {
        Assert.notNull(inviteRole, "邀请者角色不能为空");
        String inviteCode = null;
        if(OperatorRoleEnum.ROLE_PURCHASER.getCode().equals(inviteRole)){
        	inviteCode = CodeUtil.produceInviteCode(CodeUtil.PURCHASER_PREFIX);
        }else{
        	inviteCode = CodeUtil.produceInviteCode(CodeUtil.USER_PREFIX);
        }
        Integer inviteId = SessionContainer.getSession().getOperatorId();
        InviteCodeVo inviteCodeVo = new InviteCodeVo();
        try{
            inviteCodeVo.setInviteCode(inviteCode);
            inviteCodeVo.setInviteId(inviteId);
            inviteCodeVo.setInviteRole(inviteRole);
            inviteCodeVo.setCreateTime(DateUtil.currentUTCTime());
            this.inviteCodeDao.insertInviteCode(inviteCodeVo);
        }catch(Exception e){
            LoggerUtil.ERROR(logger,String.format("插入邀请码异常，邀请码：%s && 邀请人id：%s && 异常信息：%s", inviteCode, inviteId, e.getMessage()));
            //出现邀请码异常，递归抵用
            this.saveInviteCode(inviteRole);
        }
        return inviteCode;
    }
    
    @Override
    public InviteCode getInviteCode(String inviteCode) {
        Assert.notNull(inviteCode, "邀请码不能为空");
        return this.inviteCodeDao.getInviteCode(inviteCode);
    }
    
}

