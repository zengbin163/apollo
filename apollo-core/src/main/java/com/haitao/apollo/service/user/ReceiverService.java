package com.haitao.apollo.service.user;

import java.util.List;

import com.haitao.apollo.pojo.user.Receiver;

public interface ReceiverService {
	Integer createReceiver(String receiver, String receiverMobile,
			String province, String city, String address, String postcode,
			Integer isDefault);
	List<Receiver> receiverList();
	Receiver defaultReceiver();
	Integer setDefaultReceiver(Integer receiverId);
	Receiver getReceiverById(Integer id);
	Integer updateReceiverById(Integer id, String receiver,
			String receiverMobile, String province, String city,
			String address, String postcode, Integer isDefault);
}
