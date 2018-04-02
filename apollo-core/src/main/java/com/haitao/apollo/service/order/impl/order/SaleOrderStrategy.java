/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月13日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order.impl.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.SaleOrderDao;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.pojo.order.SaleOrder;

/** 
* @ClassName: SaleOrderStrategy 
* @Description: 销售订单的校验策略
* @author zengbin
* @date 2015年11月30日 下午6:47:27 
*/
@Component
public class SaleOrderStrategy extends AbstractCreateOrderStrategy {
    @Resource(name = "saleOrderDao")
    private SaleOrderDao saleOrderDao;

	@Override
	public void execute(Integer postrewardId) {
		Assert.notNull(postrewardId,"悬赏id不能为空");
		SaleOrder saleOrder = this.saleOrderDao.getSaleOrderByRewardId(postrewardId);
		if(null != saleOrder) {
            throw new ApolloBizException(ResultCode.DATA_IS_ALREADY_EXISTS, getUserId() , String.format("该悬赏的销售订单已经创建 , postrewardId=%s " , postrewardId));
		}
	}
    
}
