/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月11日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.vo.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: SaleOrderVo
 * @Description: 销售订单Vo
 * @author zengbin
 * @date 2015年11月11日 下午9:34:10
 */
public class SaleOrderVo implements Serializable {

	private static final long serialVersionUID = 2236317846292951810L;

	private Integer id;
	private Integer postrewardId;// 悬赏id
	private Integer userId;
	private Integer purchaserId;
	private Integer orderStatus;//@OrderStatus
	private Integer refundStatus;//@RefundStatus
	private Integer csStatus;//@CsStatus
	private BigDecimal rewardPrice;// 悬赏单价
	private Integer productNum;// 商品数量
	private Long shipmentTime;// 发货时间（UTC时间）
	private Long confirmTime;// 用户确认收货时间（UTC时间）
	private List<Integer> orderStatusList;//订单状态集合
	private Long createTime;// 创建时间
	private Long modifyTime;// 更新时间时间

    private Integer rewardStatus;//悬赏状态
    private Integer needAppraise;//待评价  0表示未评价，1表示已评价
	
	public SaleOrderVo() {

	}

	public SaleOrderVo(Integer postrewardId, Integer userId,
			Integer purchaserId, Integer orderStatus, BigDecimal rewardPrice,
			Integer productNum, Long createTime, Long modifyTime) {
		this.postrewardId = postrewardId;
		this.userId = userId;
		this.purchaserId = purchaserId;
		this.orderStatus = orderStatus;
		this.rewardPrice = rewardPrice;
		this.productNum = productNum;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}
	
	public SaleOrderVo(Integer postrewardId, Integer userId,
			Integer purchaserId, Integer orderStatus, Integer refundStatus,
			Integer csStatus, BigDecimal rewardPrice, Integer productNum,
			Long createTime, Long modifyTime) {
		this.postrewardId = postrewardId;
		this.userId = userId;
		this.purchaserId = purchaserId;
		this.orderStatus = orderStatus;
		this.refundStatus = refundStatus;
		this.csStatus = csStatus;
		this.rewardPrice = rewardPrice;
		this.productNum = productNum;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPostrewardId() {
		return postrewardId;
	}

	public void setPostrewardId(Integer postrewardId) {
		this.postrewardId = postrewardId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(Integer purchaserId) {
		this.purchaserId = purchaserId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getRewardPrice() {
		return rewardPrice;
	}

	public void setRewardPrice(BigDecimal rewardPrice) {
		this.rewardPrice = rewardPrice;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public Long getShipmentTime() {
		return shipmentTime;
	}

	public void setShipmentTime(Long shipmentTime) {
		this.shipmentTime = shipmentTime;
	}

	public Long getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Long confirmTime) {
		this.confirmTime = confirmTime;
	}

	public List<Integer> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<Integer> orderStatusList) {
		this.orderStatusList = orderStatusList;
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

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Integer getCsStatus() {
		return csStatus;
	}

	public void setCsStatus(Integer csStatus) {
		this.csStatus = csStatus;
	}

	public Integer getRewardStatus() {
		return rewardStatus;
	}

	public void setRewardStatus(Integer rewardStatus) {
		this.rewardStatus = rewardStatus;
	}

	public Integer getNeedAppraise() {
		return needAppraise;
	}

	public void setNeedAppraise(Integer needAppraise) {
		this.needAppraise = needAppraise;
	}
}
