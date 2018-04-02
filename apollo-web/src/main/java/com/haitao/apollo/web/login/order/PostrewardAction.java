/*

 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月14日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.login.order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.annotation.FromUser;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.global.GlobalConstants;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.proccess.OrderProcess;
import com.haitao.apollo.proccess.PageListProcess;
import com.haitao.apollo.proccess.ThirdPayProcess;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.web.BaseAction;

/** 
* @ClassName: PostRewardAction 
* @Description: 悬赏action
* @author zengbin
* @date 2015年11月16日 下午19:57:37 
*/
public class PostrewardAction extends BaseAction {
    
    private static final long serialVersionUID = -7637011317919479555L;
    @Autowired
    private OrderProcess orderProcess;
    @Autowired
    private ThirdPayProcess thirdPayProcess;
    @Autowired
    private PageListProcess pageListProcess;

   /**
    * @Description 预支付，悬赏状态为预支付
    * @param rewardPrice      悬赏单价（悬赏价钱）     单位为分
    * @param productNum       产品数量
    * @param content          悬赏内容
    * @param receiverId       收货人id
    * @param requireDay       消费者要求发货时间  1天    3天    7天
    * @param picAddr1  
    * @param picAddr2
    * @param picAddr3
    * @param picAddr4
    * @param picAddr5
    * @param picAddr6
    * @param picAddr7
    * @param picAddr8
    * @param picAddr9
    * @param brandId         品牌id
    * @param categoryId      类目id
    * @param source          0.晒单页发起  1.自主发起  2.悬赏页跟单
    * @param sourceId        晒单发起悬赏，填入晒单id；悬赏跟单，填入被跟悬赏id；自主发起传入用户id
    * @return
    */
    @FromUser
    public String prePostReward(){
        BigDecimal rewardPrice =this.getDecimalParameter(request, "rewardPrice", null);
        Integer productNum = this.getIntParameter(request, "productNum", 1);
        String content = this.getFilteredParameter(request, "content", 0, null);
        Integer receiverId = this.getIntParameter(request, "receiverId", null);
        String picAddr1 = this.getFilteredParameter(request, "picAddr1", 0, null);
        String picAddr2 = this.getFilteredParameter(request, "picAddr2", 0, null);
        String picAddr3 = this.getFilteredParameter(request, "picAddr3", 0, null);
        String picAddr4 = this.getFilteredParameter(request, "picAddr4", 0, null);
        String picAddr5 = this.getFilteredParameter(request, "picAddr5", 0, null);
        String picAddr6 = this.getFilteredParameter(request, "picAddr6", 0, null);
        String picAddr7 = this.getFilteredParameter(request, "picAddr7", 0, null);
        String picAddr8 = this.getFilteredParameter(request, "picAddr8", 0, null);
        String picAddr9 = this.getFilteredParameter(request, "picAddr9", 0, null);
        Integer brandId = this.getIntParameter(request, "brandId", null);
        Integer categoryId = this.getIntParameter(request, "categoryId", null);
        Integer source = this.getIntParameter(request, "source", null);
        Integer sourceId = this.getIntParameter(request, "sourceId", null);
        Integer requireDay = this.getIntParameter(request, "requireDay", null);
		Integer postrewardId = this.orderProcess.prePostReward(rewardPrice,
				productNum, content, receiverId, picAddr1, picAddr2, picAddr3,
				picAddr4, picAddr5, picAddr6, picAddr7, picAddr8, picAddr9,
				brandId, categoryId, source, sourceId, requireDay);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rewardId", postrewardId);
		map.put("deposit", rewardPrice.multiply(new BigDecimal(GlobalConstants.DEPOSIT_PERCENT)).setScale(0, BigDecimal.ROUND_HALF_UP));
        returnFastJSON(map);
        return null;
    }
    
    /**
     * 买手接单
     * @param purchaserId   买手id
     * @param postrewardId  悬赏id
     * @param purchaserDay  采购天数，1天、3天、7天
     * @return
     */
    @FromPurchaser
    public String accept(){
        Integer purchaserId = this.getIntParameter(request, "purchaserId", null);
        Integer postrewardId = this.getIntParameter(request, "postrewardId", null);
        Integer purchaserDay = this.getIntParameter(request, "purchaserDay", null);
        if(!isPurchaser()){
        	throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), ResultCode.NOT_PURCHASER_REQUEST.getString());
        }
        Long acceptTime = DateUtil.currentUTCTime();
        Integer orderId = this.orderProcess.accept(purchaserId, postrewardId, acceptTime, purchaserDay);
        returnFastJSON(toMap("orderId", orderId));
        return null;
    }
    
    /**
     * 释放悬赏到公共池
     * @param postrewardId 悬赏id
     * @param source  
     *     0:买手在接单处释放到公共池，1:消费者在拒绝买手发货时间释放到公共池，2:消费者24小时未同意发货时间释放到公共池
     * @return
     */
    @FromPurchaser
    @FromUser
    public String release(){
        Integer postrewardId = this.getIntParameter(request, "postrewardId", null);
    	Integer source = this.getIntParameter(request, "source", null);
        Long releaseTime = DateUtil.currentUTCTime();
		Integer rewardId = this.orderProcess.release(postrewardId, source, releaseTime);
        returnFastJSON(toMap("postrewardId", rewardId));
    	return null;
    }
    
    /**
     * 消费者同意发货时间并返回尾款金额和大牌币金额
     * @param postrewardId
     * @return
     */
    @FromUser
    public String agreeAndPrePayFinal(){
        Integer postrewardId = this.getIntParameter(request, "postrewardId", null);
        Map<String,Object> map = this.orderProcess.agreeAndPrePayFinal(postrewardId);
        returnFastJSON(map);
    	return null;
    }
    
    /**
     * 消费者修改出价
     * @param postrewardId
     * @param rewardPrice
     * @return
     */
    @FromUser
    public String adjustPrice(){
        Integer postrewardId = this.getIntParameter(request, "postrewardId", null);
        BigDecimal rewardPrice = this.getDecimalParameter(request, "rewardPrice", null);
        returnFastJSON(this.orderProcess.adjustPrice(postrewardId, rewardPrice));
        return null;
    }
    
    /**
     * 消费者取消待接单悬赏
     * @param postrewardId
     * @return
     */
    @FromUser
    public String cancelPostreward() {
        Integer postrewardId = this.getIntParameter(request, "postrewardId", null);
        if(this.orderProcess.cancelPostreward(postrewardId)) {
        	returnFastJSON(success());
        }else {
        	returnFastJSON(failure());
        }
        return null;
    }
    
    /**
     * 查询买手端首页我的客户悬赏列表
     * @param categoryId   类目id
     * @param rewardStatus 悬赏状态，可以切换
     * @param isPublic  0:买手优先     1:公共池优先
     * @param pageOffset
     * @param pageSize
     * @return
     */
    @FromPurchaser
    public String postRewardListByPurchaser(){
    	Integer brandId = this.getIntParameter(request, "brandId", null);
        Integer categoryId = this.getIntParameter(request, "categoryId", null);
        Integer rewardStatus = this.getIntParameter(request, "rewardStatus", null);
        Integer isPublic = this.getIntParameter(request, "isPublic", 0);
        Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
        Integer pageSize = this.getIntParameter(request, "pageSize", null);
        if(!isPurchaser()){
        	throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), ResultCode.NOT_PURCHASER_REQUEST.getString());
        }
        Assert.notNull(rewardStatus,"悬赏状态不能为空");
        returnFastJSON(this.pageListProcess.getPostRewardListByPurchaser(getOperatorId(), rewardStatus, isPublic, brandId, categoryId, pageOffset, pageSize));
        return null;
    }
    
    /**
     * 查询买手端的悬赏广场
     * @param categoryId 类目id
     * @param pageOffset
     * @param pageSize
     * @return
     */
    @FromPurchaser
    public String postRewardSquareListByPurchaser(){
    	Integer brandId = this.getIntParameter(request, "brandId", null);
    	Integer categoryId = this.getIntParameter(request, "categoryId", null);
    	Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
    	Integer pageSize = this.getIntParameter(request, "pageSize", null);
    	List<PostReward> postrewardList = this.pageListProcess.getPostRewardSquareListByPurchaser(brandId, categoryId, pageOffset, pageSize);
    	returnFastJSON(postrewardList);
    	return null;
    }
    
    
}
