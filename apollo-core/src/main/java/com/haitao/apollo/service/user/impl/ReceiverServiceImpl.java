package com.haitao.apollo.service.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.user.ReceiverDao;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.user.Receiver;
import com.haitao.apollo.service.user.ReceiverService;
import com.haitao.apollo.vo.user.ReceiverVo;

@Service
public class ReceiverServiceImpl implements ReceiverService {
	
    @Resource(name = "receiverDao")
    private ReceiverDao receiverDao;

	@Override
	public Integer createReceiver(String receiver, String receiverMobile,
			String province, String city, String address, String postcode,
			Integer isDefault) {
		Assert.notNull(receiver,"收件人不能为空");
		Assert.notNull(receiverMobile,"收件人手机不能为空");
		Assert.notNull(province,"收件人省份不能为空");
		Assert.notNull(city,"收件人城市不能为空");
		Assert.notNull(address,"收件人地址不能为空");
		if(IsDefaultEnum.DEFAULT_YES.getCode().equals(isDefault)){
			Receiver defaultReceiver = this.defaultReceiver();
			if(null != defaultReceiver){
				ReceiverVo receiverVo = new ReceiverVo();
				receiverVo.setId(defaultReceiver.getId());
				receiverVo.setIsDefault(IsDefaultEnum.DEFAULT_NO.getCode());
				this.receiverDao.updateReceiver(receiverVo);
			}
		}
		ReceiverVo receiverVo = new ReceiverVo(SessionContainer.getSession().getOperatorId(), receiver, receiverMobile, province, city, address, postcode, isDefault);
		this.receiverDao.insertReceiver(receiverVo);
		if(null == receiverVo.getId()) {
			throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("新增消费者收货地址失败[userId=%s]", SessionContainer.getSession().getOperatorId()));
		}
		return receiverVo.getId();
	}

	@Override
	public List<Receiver> receiverList() {
		return this.receiverDao.getReceiverList(SessionContainer.getSession().getOperatorId());
	}

	@Override
	public Integer setDefaultReceiver(Integer receiverId) {
		Receiver defaultReceiver = this.defaultReceiver();
		if(null!=defaultReceiver){
			ReceiverVo receiverVo = new ReceiverVo();
			receiverVo.setId(defaultReceiver.getId());
			receiverVo.setIsDefault(IsDefaultEnum.DEFAULT_NO.getCode());
			this.receiverDao.updateReceiver(receiverVo);
		}
		ReceiverVo receiverVo = new ReceiverVo();
		receiverVo.setId(receiverId);
		receiverVo.setIsDefault(IsDefaultEnum.DEFAULT_YES.getCode());
		Integer flag = this.receiverDao.updateReceiver(receiverVo);
		if(flag <= 0) {
			throw new ApolloBizException(ResultCode.UPDATE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("更新消费者收货地址失败[userId=%s]", SessionContainer.getSession().getOperatorId()));
		}
		return flag;
	}

	@Override
	public Receiver defaultReceiver() {
		return this.receiverDao.getDefaultReceiver(SessionContainer.getSession().getOperatorId());
	}

	public Receiver getReceiverById(Integer id) {
		Assert.notNull(id, "收货人id不能为空");
		return this.receiverDao.getReceiverById(id);
	}
	
	public Integer updateReceiverById(Integer id, String receiver,
			String receiverMobile, String province, String city,
			String address, String postcode, Integer isDefault) {
		ReceiverVo receiverVo = new ReceiverVo(SessionContainer.getSession().getOperatorId(), receiver, receiverMobile, province, city, address, postcode, isDefault);
		receiverVo.setId(id);
		Integer flag = this.receiverDao.updateReceiver(receiverVo);
		if(flag<=0) {
			throw new ApolloBizException(ResultCode.UPDATE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("更新收货人失败，[receiverId=%s]", id));
		}
		return flag;
	}

}
