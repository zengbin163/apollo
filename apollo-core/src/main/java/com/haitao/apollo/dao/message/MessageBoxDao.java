package com.haitao.apollo.dao.message;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.message.MessageBox;
import com.haitao.apollo.vo.message.MessageBoxVo;

public interface MessageBoxDao {
	Integer insertMessageBox(MessageBoxVo messageBoxVo);
	MessageBox getMessageBox(@Param(value = "boxType") Integer boxType, @Param(value = "operatorRole") Integer operatorRole, @Param(value = "operatorId") Integer operatorId);
	List<MessageBox> getMessageBoxList(@Param(value = "boxType") Integer boxType, @Param(value = "operatorRole") Integer operatorRole, @Param(value = "operatorId") Integer operatorId, @Param(value = "page") Page<?> page);
}