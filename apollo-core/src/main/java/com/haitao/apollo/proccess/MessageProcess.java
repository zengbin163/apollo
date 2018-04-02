package com.haitao.apollo.proccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.haitao.apollo.enums.MessageBoxTypeEnum;
import com.haitao.apollo.plugin.session.Session;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.message.ImMessage;
import com.haitao.apollo.pojo.message.MessageBox;
import com.haitao.apollo.service.message.ImMessageService;
import com.haitao.apollo.service.message.MessageBoxService;

@Component
public class MessageProcess extends Process {
	@Autowired	
	private ImMessageService imMessageService;
	@Autowired	
	private MessageBoxService messageBoxService;
	
	/**
	 * 发送im消息
	 * @param fromUserId
	 * @param toUserId
	 * @param content
	 * @return
	 */
	public Integer sendMessage(Integer fromUserId, Integer toUserId, Integer fromUserRole, Integer toUserRole, String content){
		return this.imMessageService.sendImMessage(fromUserId, toUserId, fromUserRole, toUserRole, content);
	}
	
	/**
	 * 根据来源方和接收方查询消息列表
	 * @param fromUserId
	 * @param toUserId
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public List<ImMessage> getImMessageListByFromTo(Integer fromUserId, Integer toUserId, Integer fromUserRole, Integer toUserRole, Integer pageOffset, Integer pageSize){
		List<ImMessage> tempMessageList = this.imMessageService.getImMessageListByFromTo(fromUserId, toUserId, fromUserRole, toUserRole, pageOffset, pageSize);
		if(CollectionUtils.isEmpty(tempMessageList)){
			return tempMessageList;
		}
		return tempMessageList;
	}
	
	/**
	 * 消息盒子首页每个类型下面的消息信息
	 * @return
	 */
	public Map<Object,Object> msgBoxIndex() {
		Session session = SessionContainer.getSession();
		Map<Object,Object> map = new HashMap<>();
		//不能给客户端返回空，需要一个结构体
		MessageBox mb = this.messageBoxService.getMessageBox(MessageBoxTypeEnum.MSG_ORDER.getCode(), session.getRole(), session.getOperatorId());
		if(null==mb){
			mb = new MessageBox();
			mb.setBoxType(MessageBoxTypeEnum.MSG_ORDER.getCode());
		}
		map.put(MessageBoxTypeEnum.MSG_ORDER.toString(), mb);
		mb = this.messageBoxService.getMessageBox(MessageBoxTypeEnum.MSG_PAY.getCode(), session.getRole(), session.getOperatorId());
		if(null==mb){
			mb = new MessageBox();
			mb.setBoxType(MessageBoxTypeEnum.MSG_PAY.getCode());
		}
		map.put(MessageBoxTypeEnum.MSG_PAY.toString(), mb);
		mb = this.messageBoxService.getMessageBox(MessageBoxTypeEnum.MSG_SOCIAL.getCode(), session.getRole(), session.getOperatorId());
		if(null==mb){
			mb = new MessageBox();
			mb.setBoxType(MessageBoxTypeEnum.MSG_SOCIAL.getCode());
		}
		map.put(MessageBoxTypeEnum.MSG_SOCIAL.toString(), mb);
		map.put(MessageBoxTypeEnum.MSG_IM.toString(), this.imMessageService.getImMessageNameList(session.getOperatorId(), session.getRole()));
		return map;
	}
	
	/**
	 * 分页查询消息盒子消息
	 * @param boxType @MessageBoxTypeEnum 0:订单通知  1:结算通知  2:社交通知
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public List<MessageBox> getMessageBoxList(Integer boxType, Integer pageOffset,Integer pageSize) {
		Session session = SessionContainer.getSession();
		return this.messageBoxService.getMessageBoxList(boxType, session.getRole(), session.getOperatorId(), pageOffset, pageSize);
	}
}
