/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月12日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.service.order;

import java.util.List;

import com.haitao.apollo.pojo.order.RefundPreview;

/**
 * @ClassName: RefundPreviewService
 * @Description: 发起退款记录service
 * @author zengbin
 * @date 2016年04月07日 下午14:51:16
 */
public interface RefundPreviewService {
	void createRefundPreview(Integer postrewardId, Integer payOrderId,
			String paySerialNo);

	RefundPreview getRefundPreviewByPayOrderId(Integer payOrderId);

	List<RefundPreview> getRefundPreviewList(Integer pageOffset,
			Integer pageSize);
}
