/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月11日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pojo.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: SaleOrder
 * @Description: 销售订单
 * @author zengbin
 * @date 2015年11月11日 下午9:34:10
 */
public class SaleOrder implements Serializable {

	private static final long serialVersionUID = 7219663410830433931L;

	private Integer id;
	private Integer postrewardId;// 悬赏id
	private Integer userId;
	private Integer purchaserId;
	private Integer orderStatus;// @OrderStatus
	private Integer refundStatus;// @RefundStatus
	private Integer csStatus;// @CsStatus
	private BigDecimal rewardPrice;// 悬赏单价
	private Integer productNum;// 商品数量
	private Long shipmentTime;// 发货时间（UTC时间）
	private Long confirmTime;// 用户确认收货时间（UTC时间）
	private Long createTime;// 创建时间
	private Long modifyTime;// 更新时间时间

	private Long acceptTime; // 买手接单时间（UTC时间）
	private Integer requireDay;// 消费者要求发货时间 1天 3天 7天
	private Integer purchaserDay; // 采购时长1天、3天、7天
	private Long releaseTime; // 买手超时接单释放到公共池的时间
	private Long finalTime; // 尾款支付时间
	private Integer isPublic;// 是否公共池
	private String content;
	private Integer receiverId;
	private Integer rewardStatus;
	private String picAddr1;
	private String picAddr2;
	private String picAddr3;
	private String picAddr4;
	private String picAddr5;
	private String picAddr6;
	private String picAddr7;
	private String picAddr8;
	private String picAddr9;
	private Integer brandId;
	private Integer categoryId;
	private Integer source; // 0.晒单页发起 1.自主发起 2.悬赏页跟单
	private Integer sourceId; // 晒单发起的悬赏，填入晒单id ； 悬赏跟单，填入被跟悬赏id
	private String userNickName;// 消费者昵称
	private String userHeaderUrl;// 消费者头像
	private String userSignature;// 消费者签名
	private String brandName;
	private String categoryName;
	private Long clock;// 倒计时闹钟

	private String logisticsCompany;
	private String trackingNo;
	private String province;
	private String city;
	private String address;
	private String postcode;
	private String receiver;
	private String receiverMobile;

	private String rightsReason;// 售后申请理由
	private String rightsPicAddr1;// 售后凭证1
	private String rightsPicAddr2;// 售后凭证2
	private String rightsPicAddr3;// 售后凭证3

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

	public Long getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Long acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Integer getRequireDay() {
		return requireDay;
	}

	public void setRequireDay(Integer requireDay) {
		this.requireDay = requireDay;
	}

	public Integer getPurchaserDay() {
		return purchaserDay;
	}

	public void setPurchaserDay(Integer purchaserDay) {
		this.purchaserDay = purchaserDay;
	}

	public Long getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Long releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Long getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(Long finalTime) {
		this.finalTime = finalTime;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

	public Integer getRewardStatus() {
		return rewardStatus;
	}

	public void setRewardStatus(Integer rewardStatus) {
		this.rewardStatus = rewardStatus;
	}

	public String getPicAddr1() {
		return picAddr1;
	}

	public void setPicAddr1(String picAddr1) {
		this.picAddr1 = picAddr1;
	}

	public String getPicAddr2() {
		return picAddr2;
	}

	public void setPicAddr2(String picAddr2) {
		this.picAddr2 = picAddr2;
	}

	public String getPicAddr3() {
		return picAddr3;
	}

	public void setPicAddr3(String picAddr3) {
		this.picAddr3 = picAddr3;
	}

	public String getPicAddr4() {
		return picAddr4;
	}

	public void setPicAddr4(String picAddr4) {
		this.picAddr4 = picAddr4;
	}

	public String getPicAddr5() {
		return picAddr5;
	}

	public void setPicAddr5(String picAddr5) {
		this.picAddr5 = picAddr5;
	}

	public String getPicAddr6() {
		return picAddr6;
	}

	public void setPicAddr6(String picAddr6) {
		this.picAddr6 = picAddr6;
	}

	public String getPicAddr7() {
		return picAddr7;
	}

	public void setPicAddr7(String picAddr7) {
		this.picAddr7 = picAddr7;
	}

	public String getPicAddr8() {
		return picAddr8;
	}

	public void setPicAddr8(String picAddr8) {
		this.picAddr8 = picAddr8;
	}

	public String getPicAddr9() {
		return picAddr9;
	}

	public void setPicAddr9(String picAddr9) {
		this.picAddr9 = picAddr9;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserHeaderUrl() {
		return userHeaderUrl;
	}

	public void setUserHeaderUrl(String userHeaderUrl) {
		this.userHeaderUrl = userHeaderUrl;
	}

	public String getUserSignature() {
		return userSignature;
	}

	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
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

	public Long getClock() {
		return clock;
	}

	public void setClock(Long clock) {
		this.clock = clock;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
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

	public String getRightsReason() {
		return rightsReason;
	}

	public void setRightsReason(String rightsReason) {
		this.rightsReason = rightsReason;
	}

	public String getRightsPicAddr1() {
		return rightsPicAddr1;
	}

	public void setRightsPicAddr1(String rightsPicAddr1) {
		this.rightsPicAddr1 = rightsPicAddr1;
	}

	public String getRightsPicAddr2() {
		return rightsPicAddr2;
	}

	public void setRightsPicAddr2(String rightsPicAddr2) {
		this.rightsPicAddr2 = rightsPicAddr2;
	}

	public String getRightsPicAddr3() {
		return rightsPicAddr3;
	}

	public void setRightsPicAddr3(String rightsPicAddr3) {
		this.rightsPicAddr3 = rightsPicAddr3;
	}
}
