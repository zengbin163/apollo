package com.haitao.apollo.service.message.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.message.ImMessageDao;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.message.ImMessage;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.service.message.ImMessageService;
import com.haitao.apollo.service.purchaser.PurchaserService;
import com.haitao.apollo.service.template.MsgTemplate;
import com.haitao.apollo.service.user.UserService;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.message.ImMessageVo;

@Service
public class ImMessageServiceImpl implements ImMessageService {
	
    @Resource(name = "imMessageDao")
    private ImMessageDao imMessageDao;
	@Autowired
    private PurchaserService purchaserService;
	@Autowired
	private UserService userService;
    @Autowired
    protected MsgTemplate msgTemplate;

	@Override
	public Integer sendImMessage(Integer fromUserId, Integer toUserId, Integer fromUserRole, Integer toUserRole, String content) {
		Assert.notNull(fromUserId,"发送人不能为空");
		Assert.notNull(toUserId,"接收人不能为空");
		Assert.notNull(fromUserRole,"发送人角色不能为空");
		Assert.notNull(toUserRole,"接收人角色不能为空");
		Assert.notNull(content,"发送内容不能为空");
		ImMessageVo imMessageVo = new ImMessageVo(fromUserId, toUserId, fromUserRole, toUserRole, content, DateUtil.currentUTCTime());
		this.imMessageDao.insertImMessage(imMessageVo);
        if(null==imMessageVo.getId()){
            throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("发送IM消息失败，[fromUserId=%s,toUserId=%s,content=]", fromUserId, toUserId, content));
        }
        JSONObject json = new JSONObject();
        json.put("imMsg", imMessageVo);
        json.put("msgType", MsgTemplateEnum.IM_MESSAGE.getCode());
        this.msgTemplate.push4IM(MsgTemplateEnum.IM_MESSAGE, json, toUserId, toUserRole);
        return imMessageVo.getId();
	}

	@Override
	public List<ImMessage> getImMessageListByFromTo(Integer fromUserId, Integer toUserId, Integer fromUserRole, Integer toUserRole, Integer pageOffset, Integer pageSize) {
		Assert.notNull(fromUserId,"发送人不能为空");
		Assert.notNull(toUserId,"接收人不能为空");
		Assert.notNull(fromUserRole,"发送人角色不能为空");
		Assert.notNull(toUserRole,"接收人角色不能为空");
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.ASC);
        page.setOrderBy("t1.id");
		return this.imMessageDao.getImMessageListByFromTo(fromUserId, toUserId, fromUserRole, toUserRole, page);
	}

	public List<ImMessage> getImMessageNameList(Integer fromUserId, Integer fromUserRole) {
		Assert.notNull(fromUserId,"发送人不能为空");
		Assert.notNull(fromUserRole,"发送人角色不能为空");
		List<ImMessage> imMessageList = this.imMessageDao.getImMessageNameList(fromUserId, fromUserRole);
		if(CollectionUtils.isEmpty(imMessageList)) {
			return imMessageList;
		}
		List<ImMessage> imMessageTempList = new ArrayList<ImMessage>();
		for(ImMessage message : imMessageList) {
			Integer toUserRole = message.getToUserRole();
			if(OperatorRoleEnum.ROLE_PURCHASER.getCode().equals(toUserRole)) {
				Purchaser purchaser = this.purchaserService.getPurchaserById(message.getToUserId());
				if(null!=purchaser) {
					message.setToNickName(purchaser.getPurchaserName());
					message.setToHeaderUrl(purchaser.getHeaderUrl());
				}
			}else {
				User user = this.userService.getUserById(message.getToUserId());
				if(null!=user) {
					message.setToNickName(user.getNickName());
					message.setToHeaderUrl(user.getHeaderUrl());
				}
			}
			ImMessage im = this.imMessageDao.getImMessageByFromTo(fromUserId, fromUserRole, message.getToUserId(), message.getToUserRole());
			if (null != im) {
				message.setContent(im.getContent());
				message.setCreateTime(im.getCreateTime());
			}
			imMessageTempList.add(message);
		}
		return imMessageTempList;
	}
}
