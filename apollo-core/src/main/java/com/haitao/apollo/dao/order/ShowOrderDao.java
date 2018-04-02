/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月12日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.order.ShowOrder;
import com.haitao.apollo.vo.order.ShowOrderVo;

/** 
* @ClassName: ShowOrderDao 
* @Description: 晒单DAO
* @author zengbin
* @date 2015年11月20日 下午17:47:29 
*/
public interface ShowOrderDao {
    void insertShowOrder(ShowOrderVo showOrderVo);
    ShowOrder getShowOrderById(Integer id);
    List<ShowOrder> getshowOrderByOrderId(Integer orderId);
    //消费者端晒单池
	List<ShowOrder> getShowOrderPoolListByOperator(@Param(value = "showOrderVo") ShowOrderVo showOrderVo, @Param(value = "page") Page<?> page);
}

