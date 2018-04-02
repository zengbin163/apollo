package com.haitao.apollo.dao.message;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.message.ImMessage;
import com.haitao.apollo.vo.message.ImMessageVo;

public interface ImMessageDao {
	Integer insertImMessage(ImMessageVo imMessageVo);

	List<ImMessage> getImMessageListByFromTo(
			@Param(value = "fromUserId") Integer fromUserId,
			@Param(value = "toUserId") Integer toUserId,
			@Param(value = "fromUserRole") Integer fromUserRole,
			@Param(value = "toUserRole") Integer toUserRole,
			@Param(value = "page") Page<?> page);
	
	List<ImMessage> getImMessageNameList(@Param(value = "fromUserId") Integer fromUserId, @Param(value = "fromUserRole") Integer fromUserRole);

	ImMessage getImMessageByFromTo(
			@Param(value = "fromUserId") Integer fromUserId,
			@Param(value = "fromUserRole") Integer fromUserRole,
			@Param(value = "toUserId") Integer toUserId,
			@Param(value = "toUserRole") Integer toUserRole);
}
