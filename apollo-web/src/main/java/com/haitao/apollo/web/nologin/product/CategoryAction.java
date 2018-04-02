/*
 *@Project: GZJK
 *@Author: zengbin
 *@Date: 2015年11月19日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.nologin.product;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.proccess.ProductProcess;
import com.haitao.apollo.web.BaseAction;

/**
 * @ClassName: CategoryAction
 * @Description: 类目action
 * @author zengbin
 * @date 2015年11月19日 下午4:55:55
 */
public class CategoryAction extends BaseAction {

	private static final long serialVersionUID = -2167779818210558275L;

	@Autowired
	private ProductProcess productProcess;

	/**
	 * 加载所有类目列表
	 * @return
	 */
	public String categoryList() {
		returnFastJSON(this.productProcess.categoryList());
		return null;
	}
	
	/**
	 * 加载所有品牌列表
	 * @return
	 */
	public String brandList(){
		returnFastJSON(this.productProcess.getBrandList());
		return null;
	}
	
	/**
	 * 根据品牌id查询品牌详情
	 * @param brandId 
	 * @return
	 */
	public String brand(){
		Integer brandId = this.getIntParameter(request, "brandId", null);
		returnFastJSON(this.productProcess.getBrandById(brandId));
		return null;
	}
	
	/**
	 * 根据品牌id查询所有类目列表
	 * @param brandId 
	 * @return
	 */
	public String brandCategoryList(){
		Integer brandId = this.getIntParameter(request, "brandId", null);
		returnFastJSON(this.productProcess.getCategoryListByBrandId(brandId));
		return null;
	}
}
