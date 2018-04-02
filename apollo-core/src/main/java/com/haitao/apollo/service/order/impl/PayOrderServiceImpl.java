/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月12日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.PayOrderDao;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.enums.InOrOutEnum;
import com.haitao.apollo.enums.IsDefaultEnum;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.PayOrder;
import com.haitao.apollo.service.order.PayOrderService;
import com.haitao.apollo.service.order.impl.pay.AbstractPayCallBackStrategy;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.order.PayOrderVo;

/** 
* @ClassName: PayOrderServiceImpl 
* @Description: 支付订单相关service
* @author zengbin
* @date 2015年11月17日 下午15:50:23 
*/
@Service
public class PayOrderServiceImpl implements PayOrderService {
    
    @Resource(name = "payOrderDao")
    private PayOrderDao payOrderDao;
    @Resource(name = "payCallBackStrategyMap")
    private Map<String,AbstractPayCallBackStrategy> payCallBackStrategyMap;

    @Override
    @Transactional
	public Integer createPayOrder(Integer postrewardId, Integer userId,
			Integer payType, Integer fundType, BigDecimal payAmount,
			BigDecimal bigMoney, Integer payByBig, String paySerialNo) {
        Assert.notNull(payType,"支付类型不能为空");
        Assert.notNull(fundType,"款项类型不能为空");
        Assert.notNull(payAmount,"支付金额不能为空");
        Assert.notNull(userId,"用户id不能为空");
        Long currentTime = DateUtil.currentUTCTime();
		PayOrderVo payOrderVo = new PayOrderVo(postrewardId, userId, payType, fundType, payAmount, bigMoney, payByBig, paySerialNo, currentTime, currentTime, IsDefaultEnum.DEFAULT_NO.getCode());
        this.payOrderDao.insertPayOrder(payOrderVo);
        if(null==payOrderVo.getId()){
            throw new ApolloBizException(ResultCode.SAVE_FAIL, userId, String.format("创建支付订单失败，postrewardId=%s", postrewardId));
        }
        return payOrderVo.getId();
    }
    
    public void payCallBack(Integer orderNo, String paySerialNo, Integer payType, Integer fundType, BigDecimal payAmount, BigDecimal bigMoney, String subject){
    	Assert.notNull(fundType, "款项类型fundType不能为空");
        //校验各种策略
        for(Map.Entry<String, AbstractPayCallBackStrategy> payCallBackMap : payCallBackStrategyMap.entrySet()){
        	AbstractPayCallBackStrategy payCallBackStrategy = payCallBackMap.getValue();
        	payCallBackStrategy.execute(orderNo, paySerialNo, payType, fundType, payAmount, bigMoney, subject);
        }
    }
    
    public PayOrder getPayOrderBySerialNo(String paySerialNo){
    	Assert.notNull(paySerialNo, "paySerialNo支付流水号不能为空");
    	return this.payOrderDao.getPayOrderBySerialNo(paySerialNo);
    }
    
    public List<PayOrder> getPayOrderByPostrewardId(Integer postrewardId){
    	Assert.notNull(postrewardId, "postrewardId悬赏id不能为空");
    	return this.payOrderDao.getPayOrderByPostrewardId(postrewardId);
    }
    
    public Integer discardPayOrderById(Integer id){
    	Integer flag = this.payOrderDao.discardPayOrderById(id);
    	if(flag<=0){
            throw new ApolloBizException(ResultCode.UPDATE_FAIL, SessionContainer.getSession().getOperatorId() , String.format("作废支付订单失败，payOrderId=%s" , id));
    	}
    	return flag;
    }
    
    public List<PayOrder> myBillList(Integer userId, Integer month, Integer pageOffset, Integer pageSize) {
    	Assert.notNull(userId,"用户id不能为空");
    	Assert.notNull(month,"账单月份不能为空");
		Assert.notNull(pageOffset,"分页起始页码不能为空");
		Assert.notNull(pageSize,"分页每页的总数不能为空");
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.DESC);
        page.setOrderBy(Page.ORDER_BY_CREATE_TIME);
        List<PayOrder> payOrderList = this.payOrderDao.getMyPayOrderListByUserId(userId, month, page);
        if(CollectionUtils.isEmpty(payOrderList)) {
        	return payOrderList;
        }
        List<PayOrder> tempPayOrderList = new ArrayList<PayOrder>();
        for(PayOrder payOrder : payOrderList) {
        	FundTypeEnum fundTypeEnum = FundTypeEnum.getEnum(payOrder.getFundType());
        	StringBuffer desc = new StringBuffer();
        	InOrOutEnum inOrOutEnum = InOrOutEnum.IN_ACCOUNT;
            switch (fundTypeEnum) {
	            case DEPOSIT:
	            	desc.append("您支付了一笔悬赏定金");
	            	inOrOutEnum = InOrOutEnum.OUT_ACCOUNT;
	                break;
	            case FINAL:
		            desc.append("您支付了一笔悬赏尾款");
	            	inOrOutEnum = InOrOutEnum.OUT_ACCOUNT;
	                break;
	            case REFUND_DEPOSIT:
	            	desc.append("您收到了一笔悬赏定金退款");
	            	inOrOutEnum = InOrOutEnum.IN_ACCOUNT;
	                break;
	            case REFUND_FINAL:
	            	desc.append("您收到了一笔悬赏尾款退款");
	            	inOrOutEnum = InOrOutEnum.IN_ACCOUNT;
	            	break;
	            case COMPENSATION:
	            	desc.append("您收到了一笔买手赔偿违约金");
	            	inOrOutEnum = InOrOutEnum.IN_ACCOUNT;
	            	break;
	            default:
	                break;
            }
            payOrder.setDesc(desc.toString());
            payOrder.setInOrOut(inOrOutEnum.getCode());
            tempPayOrderList.add(payOrder);
        }
        return tempPayOrderList;
    }
}