package com.haitao.apollo.plugin.database.page;

import org.apache.ibatis.session.RowBounds;

/**
 * 
* @ClassName: NewRowBounds 
* @Description: 扩展原RowBounds，加入排序实现
* @author zengbin
* @date 2015年10月28日 下午3:05:55
 */
public class NewRowBounds extends RowBounds {
    
    private String orderBy;
    private String order;
    public final static NewRowBounds DEFAULT = new NewRowBounds();
    
    public NewRowBounds() {
        super();
    }
    
    @SuppressWarnings("rawtypes")
    public NewRowBounds(Page page) {
        super(page.getFirst(), page.getPageSize());
        this.orderBy = page.getOrderBy();
        this.order = page.getOrder();
    }
    
    public NewRowBounds(int offset, int limit, String orderBy, String order) {
        super(offset, limit);
        this.orderBy = orderBy;
        this.order = order;
    }
    
    public String getOrderBy() {
        return orderBy;
    }
    
    public String getOrder() {
        return order;
    }
    
}
