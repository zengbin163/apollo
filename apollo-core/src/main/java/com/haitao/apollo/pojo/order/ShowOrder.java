/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月20日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pojo.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: ShowOrder
 * @Description: 晒单
 * @author zengbin
 * @date 2015年11月20日 下午4:59:35
 */
public class ShowOrder implements Serializable {
	private static final long serialVersionUID = -3891260969965222248L;
	private Integer id;
	private Integer orderId;
	private Integer role; // 0买手主动发布晒单 1消费者好评默认晒单
	private Integer operatorId; // 如果是用户晒单填写用户id，如果买手晒单填写买手id
	private BigDecimal showPrice;// 晒单价
	private String content;
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
	private String brandName;
	private String categoryName;
	private Long createTime;
	private Long modifyTime;
	private Integer userId;//消费者id
	private String userNickName;// 消费者昵称
	private String userHeaderUrl;// 消费者头像
	private String userSignature;// 消费者签名
	private Integer purchaserId;//买手id
	private String purchaserNickName;// 买手昵称
	private String purchaserHeaderUrl;// 买手头像
	private String purchaserSignature;// 买手签名
	private Integer praiseCount;//点赞数
	private Integer followCount;//跟单数

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

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public BigDecimal getShowPrice() {
		return showPrice;
	}

	public void setShowPrice(BigDecimal showPrice) {
		this.showPrice = showPrice;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getPurchaserNickName() {
		return purchaserNickName;
	}

	public void setPurchaserNickName(String purchaserNickName) {
		this.purchaserNickName = purchaserNickName;
	}

	public String getPurchaserHeaderUrl() {
		return purchaserHeaderUrl;
	}

	public void setPurchaserHeaderUrl(String purchaserHeaderUrl) {
		this.purchaserHeaderUrl = purchaserHeaderUrl;
	}

	public String getPurchaserSignature() {
		return purchaserSignature;
	}

	public void setPurchaserSignature(String purchaserSignature) {
		this.purchaserSignature = purchaserSignature;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
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
}
