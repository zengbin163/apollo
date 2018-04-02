package com.haitao.apollo.dao.user;

import java.util.List;

import com.haitao.apollo.pojo.user.Receiver;
import com.haitao.apollo.vo.user.ReceiverVo;

public interface ReceiverDao {
	Integer insertReceiver(ReceiverVo receiverVo);
	Integer updateReceiver(ReceiverVo receiverVo);
	List<Receiver> getReceiverList(Integer userId);
	Receiver getDefaultReceiver(Integer userId);
	Receiver getReceiverById(Integer id);
}
