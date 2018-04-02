package com.haitao.apollo.proccess;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haitao.apollo.pojo.order.PostrewardCount;
import com.haitao.apollo.pojo.product.Brand;
import com.haitao.apollo.pojo.product.BrandCategory;
import com.haitao.apollo.pojo.product.Category;
import com.haitao.apollo.service.product.CategoryService;

/**
 * 产品相关的过程引擎
 * @author zengbin
 * @date 2016年02月01日 上午11:23:45 
 */
@Component
public class ProductProcess extends Process {
	@Autowired
	private CategoryService categoryService;
	
    /**
     * 所有类目列表
     * @return
     */
    public List<Category> categoryList(){
        return this.categoryService.getCategoryList();
    }

    /**
     * 查询买手端的类目列表
     * @return
     */
    public List<Category> categoryListByPurchaserId(Integer purchaserId){
    	return this.categoryService.getCategoryListByPurchaserId(purchaserId);
    }
    
	/**
	 * 买手订阅类目
	 * @param categoryIds
	 * @return
	 */
	public List<Category> subscribeCategory(String categoryIds){
		Assert.notNull(categoryIds, "传入的买手订阅的类目集合不能为空");
		String [] cateIds = categoryIds.split(SPLIT_COMMA);
		Assert.notEmpty(cateIds,"买手订阅的类目集合不能为空");
		List<Integer> categoryList = new ArrayList<Integer>();
		for(String tempId : cateIds){
			categoryList.add(Integer.parseInt(tempId));
		}
		return this.categoryService.subscribeCategory(getOperatorId(), categoryList);
	}
	
	/**
	 * 加载所有品牌雷彪
	 * @return
	 */
	public List<Brand> getBrandList(){
		return this.categoryService.getBrandList();
	}
	
	/**
	 * 根据品牌id查询品牌信息
	 * @param brandId
	 * @return
	 */
	public Brand getBrandById(Integer brandId){
		Assert.notNull(brandId, "品牌id不能为空");
		return this.categoryService.getBrandById(brandId);
	}
	
	/**
	 * 根据品牌id查询所有类目
	 * @param brandId
	 * @return
	 */
	public List<BrandCategory> getCategoryListByBrandId(Integer brandId){
		Assert.notNull(brandId, "品牌id不能为空");
		return this.categoryService.getCategoryListByBrandId(brandId);
	}
	
	/**
	 * 查询所有类目下面待接单数
	 * @return
	 */
	public List<PostrewardCount> getPostrewardCount() {
		return this.categoryService.getPostrewardCount();
	}
	
	/**
	 * 查询买手所订阅类目下面待接单数
	 * @param purchaserId
	 * @return
	 */
	public List<PostrewardCount> getPostrewardCountByPurchaser(Integer purchaserId) {
		Assert.notNull(purchaserId, "买手id不能为空");
		return this.categoryService.getPostrewardCountByPurchaser(purchaserId);
	}
}
