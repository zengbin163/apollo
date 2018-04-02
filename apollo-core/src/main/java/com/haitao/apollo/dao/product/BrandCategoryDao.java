/*
 *@Project: GZJK
 *@Author: zengbin
 *@Date: 2015年11月12日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.product;

import java.util.List;

import com.haitao.apollo.pojo.product.BrandCategory;

/**
 * @ClassName: BrandCategoryDao
 * @Description: 品牌类目DAO
 * @author zengbin
 * @date 2016年01月29日 下午15:20:29
 */
public interface BrandCategoryDao {
	List<BrandCategory> getCategoryListByBrandId(Integer brandId);
}