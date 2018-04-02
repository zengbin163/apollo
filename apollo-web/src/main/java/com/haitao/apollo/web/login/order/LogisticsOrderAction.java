/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月19日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.login.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.annotation.FromUser;
import com.haitao.apollo.logistics.Track;
import com.haitao.apollo.proccess.LogisticsProcess;
import com.haitao.apollo.proccess.PageListProcess;
import com.haitao.apollo.web.BaseAction;

/** 
* @ClassName: LogisticsOrderAction 
* @Description: 物流订单action
* @author zengbin
* @date 2015年11月19日 下午3:06:59 
*/
public class LogisticsOrderAction extends BaseAction {

    private static final long serialVersionUID = -6854646700137677495L;
    
    @Autowired
    private LogisticsProcess logisticsProcess;
    @Autowired
    private PageListProcess pageListProcess;
    
    /**
     * 买手发货
     * @param orderId  订单id
     * @param receiverId  收货人Id
     * @param logisticsCompany   物流公司
     * @param trackingNo    物流单号
     * @return
     */
    @FromPurchaser
    public String shipment(){
        Integer orderId = this.getIntParameter(request, "orderId", 0);
        Integer receiverId = this.getIntParameter(request, "receiverId", 0);
        String logisticsCompany = this.getFilteredParameter(request, "logisticsCompany", 0, null);
        String trackingNo = this.getFilteredParameter(request, "trackingNo", 0, null);
        this.logisticsProcess.shipment(orderId, receiverId, logisticsCompany, trackingNo);
        returnFastJSON(this.success());
        return null;
    }
    
    /**
     * <pre>
     *    物流订单跟踪记录
     * </pre>
     * @return
     */
    @FromUser
    public String trackList(){
        String trackingNo = this.getFilteredParameter(request, "trackingNo", 0, null);
        List<Track> trackList = this.pageListProcess.trackList(trackingNo);
        returnFastJSON(trackList);
        return null;
    }
    
    /**
     * 物流详情
     * @return
     */
    @FromUser
    @FromPurchaser
    public String address() {
        Integer orderId = this.getIntParameter(request, "orderId", 0);
    	returnFastJSON(this.logisticsProcess.getLogisticsAddressByOrderId(orderId));
    	return null;
    }
    
    /** 创建收货人
     * @param receiver 收货人
     * @param receiverMobile 收货人手机号
     * @param province 省
     * @param city 市
     * @param address 详细地址
     * @param postcode 邮编
     * @param isDefault 是否为默认收货地址  0不是  1是
     * @return
     */
    @FromUser
    public String createReceiver() {
        String receiver = this.getFilteredParameter(request, "receiver", 0, null);
        String receiverMobile = this.getFilteredParameter(request, "receiverMobile", 0, null);
        String province = this.getFilteredParameter(request, "province", 0, null);
        String city = this.getFilteredParameter(request, "city", 0, null);
        String address = this.getFilteredParameter(request, "address", 0, null);
        String postcode = this.getFilteredParameter(request, "postcode", 0, null);
        Integer isDefault = this.getIntParameter(request, "isDefault", 0);
        returnFastJSON(this.logisticsProcess.createReceiver(receiver, receiverMobile, province, city, address, postcode, isDefault));
        return null;
    }
    
    /**
     * 查询收货人地址列表
     * @return
     */
    @FromUser
    public String receiverList() {
    	returnFastJSON(this.logisticsProcess.receiverList());
    	return null;
    }
    
    /**
     * 查询默认收货地址列表
     * @return
     */
    @FromUser
    public String defaultReceiver() {
    	returnFastJSON(this.logisticsProcess.defaultReceiver());
    	return null;
    }
    
    /**
     * 将某个收货人id设置为默认收货地址
     * @param receiverId
     * @return
     */
    @FromUser
    public String setDefaultReceiver() {
        Integer receiverId = this.getIntParameter(request, "receiverId", 0);
        returnFastJSON(this.logisticsProcess.setDefaultReceiver(receiverId));
        return null;
    }
    
    /**
     * 查询收货人详情
     * @param receiverId 收货人id
     * @return
     */
    @FromUser
    public String receiver() {
        Integer receiverId = this.getIntParameter(request, "receiverId", 0);
        returnFastJSON(this.logisticsProcess.getReceiverById(receiverId));
        return null;
    }

    /**
     * 更新收货人信息
     * @param receiverId 收货人id
     * @return
     */
    @FromUser
    public String updateReceiver() {
    	Integer receiverId = this.getIntParameter(request, "receiverId", 0);
        String receiver = this.getFilteredParameter(request, "receiver", 0, null);
        String receiverMobile = this.getFilteredParameter(request, "receiverMobile", 0, null);
        String province = this.getFilteredParameter(request, "province", 0, null);
        String city = this.getFilteredParameter(request, "city", 0, null);
        String address = this.getFilteredParameter(request, "address", 0, null);
        String postcode = this.getFilteredParameter(request, "postcode", 0, null);
        Integer isDefault = this.getIntParameter(request, "isDefault", null);
    	returnFastJSON(this.logisticsProcess.updateReceiverById(receiverId, receiver, receiverMobile, province, city, address, postcode, isDefault));
    	return null;
    }
}