/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月18日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.vo.order;

import java.io.Serializable;

/**
 * @ClassName: LogisticsOrderVo
 * @Description: 物流订单Vo
 * @author zengbin
 * @date 2015年11月18日 下午9:16:46
 */
public class LogisticsOrderVo implements Serializable {

	private static final long serialVersionUID = 2248444293188173314L;
	private Integer id;
	private Integer orderId;
	private Integer receiverId;
	private String logisticsCompany;
	private String trackingNo;
	private Long createTime;
	private Long modifyTime;

	public LogisticsOrderVo() {

	}

	public LogisticsOrderVo(Integer orderId, Integer receiverId,
			String logisticsCompany, String trackingNo, Long createTime,
			Long modifyTime) {
		this.orderId = orderId;
		this.receiverId = receiverId;
		this.logisticsCompany = logisticsCompany;
		this.trackingNo = trackingNo;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}

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

	public Integer getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

}
