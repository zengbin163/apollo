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
import com.haitao.apollo.pojo.order.RefundPreview;
import com.haitao.apollo.vo.order.RefundPreviewVo;

/** 
* @ClassName: RefundPreviewDao 
* @Description: 发起退款记录DAO
* @author zengbin
* @date 2016年04月07日 下午15:12:29 
*/
public interface RefundPreviewDao {
    Integer insertRefundPreview(RefundPreviewVo refundPreviewVo);
    RefundPreview getRefundPreviewByPayOrderId(Integer payOrderId);
    List<RefundPreview> getRefundPreviewList(@Param(value = "page") Page<?> page);
}