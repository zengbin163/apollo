/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月11日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pojo.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.haitao.apollo.util.DateUtil;

/**
 * @ClassName: PostReward
 * @Description: 悬赏
 * @author zengbin
 * @date 2015年11月11日 下午8:56:11
 */
public class PostReward implements Serializable {

	private static final long serialVersionUID = 8482425409520298633L;

	private Integer id;
	private Integer userId;
	private Integer priorPurchaserId; // 优先展现的买手id
	private Integer purchaserId; // 接单买手
	private Long acceptTime; // 买手接单时间（UTC时间）
	private Integer requireDay;// 消费者要求发货时间 1天 3天 7天
	private Integer purchaserDay; // 采购时长1天、3天、7天
	private Long releaseTime; // 买手超时接单释放到公共池的时间
	private Long finalTime; // 尾款支付时间
	private Integer isPublic;// 是否公共池
	private BigDecimal rewardPrice;// 悬赏价（支付金额+红包+优惠券+优惠码）
	private Integer productNum;// 商品数量
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
	private String address;
	private Integer brandId;
	private Integer categoryId;
	private Integer source; // 0.晒单页发起 1.自主发起 2.悬赏页跟单
	private Integer sourceId; // 晒单发起的悬赏，填入晒单id ； 悬赏跟单，填入被跟悬赏id
	private Long createTime;
	private Long modifyTime;
	private Long clock;// 倒计时闹钟
	private String userNickName;// 消费者昵称
	private String userHeaderUrl;// 消费者头像
	private String userSignature;// 消费者签名
	private String brandName;
	private String categoryName;
	private Integer followCount;// 跟单数

	public static void main(String[] args) {
		Date now = new Date();
		Long current = now.getTime();
		Long current3 = current + 3 * 24 * 60 * 60 * 1000;
		System.out.println(DateUtil.defaultFormatTime(now));
		System.out.println(DateUtil.defaultFormatTime(new Date(current3)));
		System.out
				.println(DateUtil.defaultFormatTime(new Date(1453860196000L)));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPriorPurchaserId() {
		return priorPurchaserId;
	}

	public void setPriorPurchaserId(Integer priorPurchaserId) {
		this.priorPurchaserId = priorPurchaserId;
	}

	public Integer getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(Integer purchaserId) {
		this.purchaserId = purchaserId;
	}

	public Long getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Long acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public BigDecimal getRewardPrice() {
		return rewardPrice;
	}

	public void setRewardPrice(BigDecimal rewardPrice) {
		this.rewardPrice = rewardPrice;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public Integer getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

	public Long getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Long releaseTime) {
		this.releaseTime = releaseTime;
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

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getPurchaserDay() {
		return purchaserDay;
	}

	public void setPurchaserDay(Integer purchaserDay) {
		this.purchaserDay = purchaserDay;
	}

	public Long getClock() {
		return clock;
	}

	public void setClock(Long clock) {
		this.clock = clock;
	}

	public Long getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(Long finalTime) {
		this.finalTime = finalTime;
	}

	public Integer getRequireDay() {
		return requireDay;
	}

	public void setRequireDay(Integer requireDay) {
		this.requireDay = requireDay;
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

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}
}
