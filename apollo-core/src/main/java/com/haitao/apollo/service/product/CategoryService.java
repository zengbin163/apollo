/*
 *@Project: GZJK
 *@Author: zengbin
 *@Date: 2015年11月19日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.product;

import java.util.List;

import com.haitao.apollo.pojo.order.PostrewardCount;
import com.haitao.apollo.pojo.product.Brand;
import com.haitao.apollo.pojo.product.BrandCategory;
import com.haitao.apollo.pojo.product.Category;

/**
 * @ClassName: CategoryService
 * @Description: 类目service
 * @author zengbin
 * @date 2015年11月19日 下午4:51:19
 */
public interface CategoryService {
	
	/**
	 * 根据类目id查询类目详情
	 * @param id
	 * @return
	 */
	Category getCategoryById(Integer id);
	
	/**
	 * 查询所有类目列表
	 * @return
	 */
	List<Category> getCategoryList();

	/**
	 * 买手订阅类目
	 * @param purchaserId
	 * @param categoryList
	 */
	List<Category> subscribeCategory(Integer purchaserId, List<Integer> categoryList);
	
	/**
	 * 根据买手id查询类目信息
	 * @return
	 */
	List<Category> getCategoryListByPurchaserId(Integer purchaserId);

	/**
	 * 查询所有品牌列表
	 * @return
	 */
	List<Brand> getBrandList();

	/**
	 * 根据品牌id查询品牌信息
	 * @return
	 */
	Brand getBrandById(Integer brandId);

	/**
	 * 根据品牌id查询类目列表
	 * @return
	 */
	List<BrandCategory> getCategoryListByBrandId(Integer brandId);
	
	/**
	 *  查询所有类目下面待接单数
  	 * @return
	 */
	List<PostrewardCount> getPostrewardCount();
	
	/**
	 * 查询买手所订阅类目下面待接单数
	 * @param purchaserId
	 * @return
	 */
	List<PostrewardCount> getPostrewardCountByPurchaser(Integer purchaserId);
}
