/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月12日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.order;

import com.haitao.apollo.vo.order.SaleOrderTrackVo;

/** 
* @ClassName: SaleOrderTrackDao 
* @Description: 销售订单规矩DAO
* @author zengbin
* @date 2015年11月12日 上午9:42:29 
*/
public interface SaleOrderTrackDao {
    Integer insertSaleOrderTrack(SaleOrderTrackVo saleOrderTrackVo);
}