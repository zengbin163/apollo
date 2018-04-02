package com.haitao.apollo.dao.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.pojo.product.Category;
import com.haitao.apollo.vo.product.PurchaserCategoryVo;

/**
 * 买手类目DAO
 * @author zengbin
 *
 */
public interface PurchaserCategoryDao {
	public Integer insertPurchaserCategoryBatch(@Param(value="purchaserCategoryList")List<PurchaserCategoryVo> purchaserCategoryList);
	public Integer deletePurchaserCategory(Integer id);
    public List<Category> getCategoryListByPurchaserId(@Param(value = "purchaserId") Integer purchaserId);
}
