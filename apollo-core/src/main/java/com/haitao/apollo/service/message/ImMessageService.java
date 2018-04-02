package com.haitao.apollo.service.message;

import java.util.List;

import com.haitao.apollo.pojo.message.ImMessage;

public interface ImMessageService {
	Integer sendImMessage(Integer fromUserId, Integer toUserId, Integer fromUserRole, Integer toUserRole, String content);
	List<ImMessage> getImMessageListByFromTo(Integer fromUserId, Integer toUserId, Integer fromUserRole, Integer toUserRole, Integer pageOffset, Integer pageSize);
	List<ImMessage> getImMessageNameList(Integer fromUserId, Integer fromUserRole);
}
