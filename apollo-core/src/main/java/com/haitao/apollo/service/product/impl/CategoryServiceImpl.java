/*
 *@Project: GZJK
 *@Author: zengbin
 *@Date: 2015年11月19日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.product.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.haitao.apollo.dao.product.BrandCategoryDao;
import com.haitao.apollo.dao.product.BrandDao;
import com.haitao.apollo.dao.product.CategoryDao;
import com.haitao.apollo.dao.product.PurchaserCategoryDao;
import com.haitao.apollo.enums.CachePrefixEnum;
import com.haitao.apollo.plugin.cache.RedisService;
import com.haitao.apollo.pojo.order.PostrewardCount;
import com.haitao.apollo.pojo.product.Brand;
import com.haitao.apollo.pojo.product.BrandCategory;
import com.haitao.apollo.pojo.product.Category;
import com.haitao.apollo.service.product.CategoryService;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.product.PurchaserCategoryVo;

/**
 * @ClassName: CategoryServiceImpl
 * @Description: 类目service
 * @author zengbin
 * @date 2015年11月19日 下午4:52:23
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Resource(name = "brandDao")
	private BrandDao brandDao;
	@Resource(name = "brandCategoryDao")
	private BrandCategoryDao brandCategoryDao;
	@Resource(name = "categoryDao")
	private CategoryDao categoryDao;
	@Resource(name = "purchaserCategoryDao")
	private PurchaserCategoryDao purchaserCategoryDao;
    @Autowired
    private RedisService redisService;

    public Category getCategoryById(Integer id){
		Assert.notNull(id, "类目id不能为空");
		String key = CachePrefixEnum.PREFIX_CATEGORY_.toString() + id;
		Category category = (Category) this.redisService.getObj(key);
		if (null == category) {
			category = this.categoryDao.getCategoryById(id);
			this.redisService.setObj(key, category);
		}
		return category;
    }
    
	/**
	 * 买手订阅类目
	 * @param purchaserId
	 * @param categoryList
	 */
	public List<Category> subscribeCategory(Integer purchaserId, List<Integer> categoryList){
		Assert.notNull(purchaserId, "买手id不能为空");
		Assert.notEmpty(categoryList, "类目集合不能为空");
		List<Category> alreadySubscribeCateList = this.getCategoryListByPurchaserId(purchaserId);
		if(!CollectionUtils.isEmpty(alreadySubscribeCateList)){
			for(Category category : alreadySubscribeCateList){
				if(!categoryList.contains(category.getId())){
					this.purchaserCategoryDao.deletePurchaserCategory(category.getId());
				}
			}
		}
		List<PurchaserCategoryVo> tempList = new ArrayList<PurchaserCategoryVo>();
		for(Integer categoryId:categoryList){
			PurchaserCategoryVo purchaserCategoryVo = new PurchaserCategoryVo(
					purchaserId, categoryId, 0, DateUtil.currentUTCTime(),
					DateUtil.currentUTCTime());
			tempList.add(purchaserCategoryVo);
		}
		this.purchaserCategoryDao.insertPurchaserCategoryBatch(tempList);
		List<Category> list = this.purchaserCategoryDao.getCategoryListByPurchaserId(purchaserId);
		this.redisService.setObj(CachePrefixEnum.PREFIX_PURCHASER_CATEGORY_.toString() + purchaserId, list);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Category> getCategoryListByPurchaserId(Integer purchaserId) {
		Assert.notNull(purchaserId, "买手id不能为空");
		String key = CachePrefixEnum.PREFIX_PURCHASER_CATEGORY_.toString() + purchaserId;
		List<Category> list = (List<Category>) this.redisService.getObj(key);
		if(CollectionUtils.isEmpty(list)){
			list = this.purchaserCategoryDao.getCategoryListByPurchaserId(purchaserId);
			this.redisService.setObj(key + purchaserId, list);
		}
		return list;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoryList() {
		String key = CachePrefixEnum.PREFIX_ALL_CATEGORY_300.toString();
		List<Category> categoryList = (List<Category>) this.redisService.getObj(key);
		if (CollectionUtils.isEmpty(categoryList)) {
			categoryList = this.categoryDao.getCategoryList();
			this.redisService.setObj(key, categoryList);
		}
		return categoryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Brand> getBrandList() {
		String key = CachePrefixEnum.PREFIX_ALL_BRAND_301.toString();
		List<Brand> brandList = (List<Brand>) this.redisService.getObj(key);
		if (CollectionUtils.isEmpty(brandList)) {
			brandList = this.brandDao.getBrandList();
			this.redisService.setObj(key, brandList);
		}
		return brandList;
	}

	@Override
	public Brand getBrandById(Integer brandId) {
		String key = CachePrefixEnum.PREFIX_BRAND_.toString() + brandId;
		Brand brand = (Brand) this.redisService.getObj(key);
		if (null == brand) {
			brand = this.brandDao.getBrandById(brandId);
			this.redisService.setObj(key, brand);
		}
		return brand;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BrandCategory> getCategoryListByBrandId(Integer brandId) {
		String key = CachePrefixEnum.PREFIX_BRAND_CATEGORY_.toString() + brandId;
		List<BrandCategory> brandCategoryList = (List<BrandCategory>) this.redisService.getObj(key);
		if (CollectionUtils.isEmpty(brandCategoryList)) {
			brandCategoryList = this.brandCategoryDao.getCategoryListByBrandId(brandId);
			this.redisService.setObj(key, brandCategoryList);
		}
		return brandCategoryList;
	}
	
	public List<PostrewardCount> getPostrewardCount() {
		return this.categoryDao.getPostrewardCount();
	}
	
	public List<PostrewardCount> getPostrewardCountByPurchaser(Integer purchaserId) {
		Assert.notNull(purchaserId, "买手id不能为空");
		return this.categoryDao.getPostrewardCountByPurchaser(purchaserId);
	}

}
