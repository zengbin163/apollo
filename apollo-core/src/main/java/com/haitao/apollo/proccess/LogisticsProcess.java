/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月18日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.proccess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.haitao.apollo.pojo.order.LogisticsOrder;
import com.haitao.apollo.pojo.user.Receiver;
import com.haitao.apollo.service.logistics.LogisticsOrderService;
import com.haitao.apollo.service.user.ReceiverService;

/** 
* @ClassName: LogisticsProcess 
* @Description: 物流流程
* @author zengbin
* @date 2015年11月18日 下午7:28:27 
*/
@Component
public class LogisticsProcess extends Process {
    
    @Autowired
    private LogisticsOrderService logisticsOrderService;
    @Autowired
    private ReceiverService receiverService;

    /**
     * 买手发货
     * @param orderId  订单id
     * @param receiverId  收货人Id
     * @param logisticsCompany   物流公司
     * @param trackingNo    物流单号
     * @return
     */
    @Transactional
	public void shipment(Integer orderId, Integer receiverId, String logisticsCompany, String trackingNo) {
    	this.logisticsOrderService.shipment(orderId, receiverId, logisticsCompany, trackingNo);
    }
    
    /**
     * 
    * @Description  创建用户收货地址
    * @param receiver
    * @param receiverMobile
    * @param province
    * @param city
    * @param address
    * @param postcode
    * @param isDefault
    * @return
     */
	public Integer createReceiver(String receiver, String receiverMobile,
			String province, String city, String address, String postcode,
			Integer isDefault) {
		return this.receiverService.createReceiver(receiver, receiverMobile,
				province, city, address, postcode, isDefault);
	}
	
	/**
	 * 查询当前用户收货的收货地址，默认的排在最前面
	* @Description
	* @return
	 */
	public List<Receiver> receiverList(){
		return this.receiverService.receiverList();
	}
    
	/**
	 * 查询当前用户默认的收货地址
	* @Description
	* @return
	 */
	public Receiver defaultReceiver(){
		return this.receiverService.defaultReceiver();
	}
	
	/**
	 * 设置当前收货地址为默认的收货地址
	* @Description
	* @param receiverId
	* @return
	 */
	public Integer setDefaultReceiver(Integer receiverId){
		return this.receiverService.setDefaultReceiver(receiverId);
	}
	
	/**
	 * 根据订单id查询物流信息
	 * @param orderId
	 * @return
	 */
	public List<LogisticsOrder> getLogisticsAddressByOrderId(Integer orderId) {
		return this.logisticsOrderService.getLogisticsAddressByOrderId(orderId);
	}
	
	public Receiver getReceiverById(Integer id) {
		Assert.notNull(id, "收货人id不能为空");
		return this.receiverService.getReceiverById(id);
	}
	
	public Integer updateReceiverById(Integer id, String receiver,
			String receiverMobile, String province, String city,
			String address, String postcode, Integer isDefault) {
		return this.receiverService.updateReceiverById(id, receiver, receiverMobile, province, city, address, postcode, isDefault);
	}
}
