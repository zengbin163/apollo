package com.haitao.apollo.service.message;

import java.util.List;

import com.haitao.apollo.pojo.message.MessageBox;

public interface MessageBoxService {
	/**
	 * 创建消息盒子消息
	 * @param boxType @MessageBoxTypeEnum
	 * @param boxContent
	 * @param operatorRole
	 * @param operatorId
	 * @return
	 */
	Integer createMessageBox(Integer boxType, String boxContent, Integer operatorRole, Integer operatorId);
	
	/**
	 * 查询消息盒子首页每个类型下面的最新一条
	 * @param boxType
	 * @param operatorRole
	 * @param operatorId
	 * @return
	 */
	MessageBox getMessageBox(Integer boxType, Integer operatorRole, Integer operatorId);
	
	/**
	 * 分页查询消息盒子消息
	 * @param operatorRole
	 * @param operatorId
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	List<MessageBox> getMessageBoxList(Integer boxType, Integer operatorRole, Integer operatorId, Integer pageOffset, Integer pageSize);
}
