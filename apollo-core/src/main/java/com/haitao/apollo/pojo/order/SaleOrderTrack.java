/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月14日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pojo.order;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: SaleOrderTrack 
* @Description: 订单轨迹
* @author zengbin
* @date 2015年11月14日 下午2:17:29 
*/
public class SaleOrderTrack implements Serializable {
    
    private static final long serialVersionUID = -6139412851250118412L;
    private Integer id;
    private Integer orderId;
    private Integer userId;
    private Integer purchaserId;
    private Integer postrewardId;
    private Integer rewardStatus;
    private Integer orderStatus;
    private String bizCode;
    private String track;
    private Date createTime;
    
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
    
    public Integer getPostrewardId() {
        return postrewardId;
    }
    
    public void setPostrewardId(Integer postrewardId) {
        this.postrewardId = postrewardId;
    }
    
    public Integer getRewardStatus() {
        return rewardStatus;
    }
    
    public void setRewardStatus(Integer rewardStatus) {
        this.rewardStatus = rewardStatus;
    }
    
    public Integer getOrderStatus() {
        return orderStatus;
    }
    
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public String getBizCode() {
        return bizCode;
    }
    
    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }
    
    public String getTrack() {
        return track;
    }
    
    public void setTrack(String track) {
        this.track = track;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
