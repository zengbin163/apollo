/*
 *@Project: GZJK
 *@Author: zengbin
 *@Date: 2015年11月19日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.login.product;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.annotation.FromUser;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.proccess.ProductProcess;
import com.haitao.apollo.web.BaseAction;

/**
 * @ClassName: CategoryAction
 * @Description: 类目action
 * @author zengbin
 * @date 2016年01月21日 上午09:41:55
 */
public class CategoryAction extends BaseAction {

	private static final long serialVersionUID = -6079484265562448671L;

	@Autowired
	private ProductProcess productProcess;
	
	/**
	 * 买手订阅各自负责的类目
	 * @param categoryIds被订阅类目id集合，通过","分割
	 * @return
	 */
    @FromPurchaser
	public String subscribeCategory(){
		String categoryIds = this.getFilteredParameter(request, "categoryIds", 0, null);
		if(!isPurchaser()){
			throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), ResultCode.NOT_PURCHASER_REQUEST.getString());
		}
		returnFastJSON(this.productProcess.subscribeCategory(categoryIds));
		return null;
	}

	/**
	 * 买手端，加载首页买手所订阅的类目
	 * @return
	 */
    @FromPurchaser
	public String purCategoryList() {
		if(!isPurchaser()){
			throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), ResultCode.NOT_PURCHASER_REQUEST.getString());
		}
		returnFastJSON(this.productProcess.categoryListByPurchaserId(getOperatorId()));
		return null;
	}
	
	/**
	 * 查询买手悬赏广场每个类目下面的待接单数
	 * @return
	 */
    @FromPurchaser
    @FromUser
	public String getPostrewardCount() {
		if(!isPurchaser()){
			throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), ResultCode.NOT_PURCHASER_REQUEST.getString());
		}
		returnFastJSON(this.productProcess.getPostrewardCount());
		return null;
	}
	
	/**
	 * 查询某个买手对应的每个类目下面的待接单数
	 * @return
	 */
    @FromPurchaser
	public String getPostrewardCountByPurchaser() {
		if(!isPurchaser()){
			throw new ApolloBizException(ResultCode.NOT_PURCHASER_REQUEST, getOperatorId(), ResultCode.NOT_PURCHASER_REQUEST.getString());
		}
		returnFastJSON(this.productProcess.getPostrewardCountByPurchaser(getOperatorId()));
		return null;
	}
}
