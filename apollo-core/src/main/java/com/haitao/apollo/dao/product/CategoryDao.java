/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月12日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.product;

import java.util.List;

import com.haitao.apollo.pojo.order.PostrewardCount;
import com.haitao.apollo.pojo.product.Category;


/** 
* @ClassName: CategoryDao 
* @Description: 类目DAO
* @author zengbin
* @date 2015年11月19日 下午15:20:29 
*/
public interface CategoryDao {
	public Category getCategoryById(Integer id);
    public List<Category> getCategoryList();
	List<PostrewardCount> getPostrewardCount();//查询所有类目下面待接单数
	List<PostrewardCount> getPostrewardCountByPurchaser(Integer purchaserId);//查询买手所订阅类目下面待接单数
}