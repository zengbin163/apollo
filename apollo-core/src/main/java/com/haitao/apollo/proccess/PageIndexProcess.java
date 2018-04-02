/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月18日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.proccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.pojo.order.SaleOrder;
import com.haitao.apollo.pojo.order.ShowOrder;
import com.haitao.apollo.service.order.PostRewardService;
import com.haitao.apollo.service.order.SaleOrderService;
import com.haitao.apollo.service.order.ShowOrderService;
import com.haitao.apollo.service.product.CategoryService;

/** 
* @ClassName: PageIndexProcess 
* @Description: 详情页查询流程
* @author zengbin
* @date 2015年11月18日 下午6:54:45 
*/
@Component
public class PageIndexProcess extends Process {

    @Autowired
    private PostRewardService postRewardService;
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    private ShowOrderService showOrderService;
    @Autowired
    private CategoryService categoryService;

    /**
     * <pre>
     *   订单详情
     * </pre>
     * @param orderId
     */
    public SaleOrder orderDetail(Integer orderId) {
        Assert.notNull(orderId, "订单id不能为空");
        SaleOrder saleOrder = this.saleOrderService.getSaleOrderDetailByOrderId(orderId);
        if(null==saleOrder){
            throw new ApolloBizException(ResultCode.SALEORDER_NOT_EXIST, getOperatorId(), String.format("销售订单不存在，orderId=%s", orderId));
        }
        return saleOrder;
    }
    
    /**
     * <pre>
     *    悬赏详情
     * </pre>
     * @param postrewardId
     * @return
     */
    public PostReward postrewardDetail(Integer postrewardId){
        Assert.notNull(postrewardId, "悬赏id不能为空");
        PostReward postReward = this.postRewardService.getPostRewardById(postrewardId);
        if(null==postReward){
            throw new ApolloBizException(ResultCode.POSTREWARD_NOT_EXIST, getOperatorId(), String.format("悬赏不存在，postrewardId=%s", postrewardId));
        }else{
        	postReward.setBrandName(categoryService.getBrandById(postReward.getBrandId()).getBrandName());
        	postReward.setCategoryName(categoryService.getCategoryById(postReward.getCategoryId()).getCategoryName());
        }
        return postReward;
    }
    
    /**
     * 晒单
     * @param showOrderId
     * @return
     */
    public ShowOrder showOrderDetail(Integer showOrderId){
        Assert.notNull(showOrderId, "晒单id不能为空");
        ShowOrder showOrder = this.showOrderService.getShowOrderByShowOrderId(showOrderId);
        if(null==showOrder){
            throw new ApolloBizException(ResultCode.SHOWORDER_NOT_EXIST, getOperatorId(), String.format("晒单不存在，showOrderId=%s", showOrderId));
        }
        return showOrder;
    }
}

