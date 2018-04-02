/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月14日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.vo.order;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: SaleOrderTrackVo 
* @Description: 订单轨迹
* @author zengbin
* @date 2015年11月14日 下午2:17:29 
*/
public class SaleOrderTrackVo implements Serializable {
    
    private static final long serialVersionUID = -4603358624610776478L;
    
    private Integer orderId;
    private Integer userId;
    private Integer purchaserId;
    private Integer postrewardId;
    private Integer rewardStatus;
    private Integer orderStatus;
    private String bizCode;
    private String track;
    private Date createTime;
    
    public SaleOrderTrackVo() {
    }
    
    public SaleOrderTrackVo(Integer orderId, Integer userId, Integer purchaserId,
                            Integer postrewardId, Integer rewardStatus, Integer orderStatus,
                            String bizCode, String track) {
        this.orderId = orderId;
        this.userId = userId;
        this.purchaserId = purchaserId;
        this.postrewardId = postrewardId;
        this.rewardStatus = rewardStatus;
        this.orderStatus = orderStatus;
        this.bizCode = bizCode;
        this.track = track;
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
