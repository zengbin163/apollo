/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月20日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.vo.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: ShowOrderVo
 * @Description: 晒单Vo
 * @author zengbin
 * @date 2015年11月20日 下午4:59:35
 */
public class ShowOrderVo implements Serializable {
	private static final long serialVersionUID = -4352316551840378020L;
	private Integer id;
	private Integer orderId;
	private Integer role; // 0买手主动发布晒单    1消费者好评默认晒单
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
	private Long createTime;
	private Long modifyTime;

	public ShowOrderVo() {

	}

	public ShowOrderVo(Integer orderId, Integer role, Integer operatorId,
			BigDecimal showPrice, String content, String picAddr1,
			String picAddr2, String picAddr3, String picAddr4, String picAddr5,
			String picAddr6, String picAddr7, String picAddr8, String picAddr9,
			Integer brandId, Integer categoryId, Long createTime,
			Long modifyTime) {
		this.orderId = orderId;
		this.role = role;
		this.operatorId = operatorId;
		this.showPrice = showPrice;
		this.content = content;
		this.picAddr1 = picAddr1;
		this.picAddr2 = picAddr2;
		this.picAddr3 = picAddr3;
		this.picAddr4 = picAddr4;
		this.picAddr5 = picAddr5;
		this.picAddr6 = picAddr6;
		this.picAddr7 = picAddr7;
		this.picAddr8 = picAddr8;
		this.picAddr9 = picAddr9;
		this.brandId = brandId;
		this.categoryId = categoryId;
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
}
