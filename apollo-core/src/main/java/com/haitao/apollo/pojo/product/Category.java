/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月19日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.pojo.product;

import java.io.Serializable;

/** 
* @ClassName: Category 
* @Description: 类目
* @author zengbin
* @date 2015年11月19日 下午3:25:05 
*/
public class Category implements Serializable {
    
    private static final long serialVersionUID = -1875016863657429864L;
    
    private Integer id;
    private String categoryName;
    private Long createTime;
    private Integer sum;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Long getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}
}
