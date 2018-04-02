package com.haitao.apollo.service.message.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.dao.message.MessageBoxDao;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.message.MessageBox;
import com.haitao.apollo.service.message.MessageBoxService;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.message.MessageBoxVo;

@Service
public class MessageBoxServiceImpl implements MessageBoxService {
	
    @Resource(name = "messageBoxDao")
    private MessageBoxDao messageBoxDao;

	@Override
	public Integer createMessageBox(Integer boxType, String boxContent, Integer operatorRole, Integer operatorId) {
		Long currentTime = DateUtil.currentUTCTime();
		MessageBoxVo messageBoxVo = new MessageBoxVo(boxType, boxContent, operatorRole, operatorId, currentTime, currentTime);
		this.messageBoxDao.insertMessageBox(messageBoxVo);
		return messageBoxVo.getId();
	}

	@Override
	public MessageBox getMessageBox(Integer boxType, Integer operatorRole, Integer operatorId) {
		Assert.notNull(boxType, "消息盒子类型不能为空");
		Assert.notNull(operatorRole, "操作者角色不能为空");
		Assert.notNull(operatorId, "操作者id不能为空");
		return this.messageBoxDao.getMessageBox(boxType, operatorRole, operatorId);
	}
	
	@Override
	public List<MessageBox> getMessageBoxList(Integer boxType, Integer operatorRole, Integer operatorId, Integer pageOffset, Integer pageSize) {
		Assert.notNull(boxType, "消息盒子类型不能为空");
		Assert.notNull(operatorRole, "操作者角色不能为空");
		Assert.notNull(operatorId, "操作者id不能为空");
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy(Page.ORDER_BY_MODIFY_TIME);
		return this.messageBoxDao.getMessageBoxList(boxType, operatorRole, operatorId, page);
	}

}
