/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月18日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pojo.order;

import java.io.Serializable;

/** 
* @ClassName: LogisticsOrder 
* @Description: 物流订单
* @author zengbin
* @date 2015年11月18日 下午9:16:46 
*/
public class LogisticsOrder implements Serializable {
    
    private static final long serialVersionUID = 1085561029147744857L;
    
    private Integer id;
    private Integer orderId;
    private Integer receiverId;
    private String logisticsCompany;
    private String trackingNo;
    private Long createTime;
    private Long modifyTime;
    
    private String province;
    private String city;
    private String address;
    private String postcode;
    private String receiver;
    private String receiverMobile;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public Integer getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

	public String getLogisticsCompany() {
        return logisticsCompany;
    }
    
    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }
    
    public String getTrackingNo() {
        return trackingNo;
    }
    
    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }
    
    public Long getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    
    public Long getModifyTime() {
        return modifyTime;
    }
    
    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
    
}
