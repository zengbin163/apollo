package com.haitao.apollo.service.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.order.RefundPreviewDao;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.Session;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.order.RefundPreview;
import com.haitao.apollo.service.order.RefundPreviewService;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.order.RefundPreviewVo;

@Service
public class RefundPreviewServiceImpl implements RefundPreviewService {
	
	@Resource(name = "refundPreviewDao")
	private RefundPreviewDao refundPreviewDao;

	@Override
	public void createRefundPreview(Integer postrewardId, Integer payOrderId, String paySerialNo) {
		Assert.notNull(postrewardId, "悬赏id不能为空");
		Assert.notNull(payOrderId, "支付订单id不能为空");
		Assert.notNull(paySerialNo, "第三方支付序列号不能为空");
		Long currentTime = DateUtil.currentUTCTime();
		RefundPreviewVo refundPreviewVo = new RefundPreviewVo(postrewardId, payOrderId, paySerialNo, currentTime, currentTime);
		Integer id = this.refundPreviewDao.insertRefundPreview(refundPreviewVo);
		if (null == id) {
			Session session = SessionContainer.getSession();
			Integer userId = -10000;
			if (null != session) {
				userId = session.getOperatorId();
			}
			throw new ApolloBizException(ResultCode.SAVE_FAIL, userId, String.format("新增发起退款记录失败，postrewardId=%s", postrewardId));
		}
	}

	@Override
	public RefundPreview getRefundPreviewByPayOrderId(Integer payOrderId) {
		Assert.notNull(payOrderId, "支付订单id不能为空");
		return this.refundPreviewDao.getRefundPreviewByPayOrderId(payOrderId);
	}

	@Override
	public List<RefundPreview> getRefundPreviewList(Integer pageOffset, Integer pageSize) {
    	Assert.notNull(pageOffset, "pageOffset不能为空");
    	Assert.notNull(pageSize, "pageSize不能为空");
        Page<?> page = new Page<>();
        page.setPageNo(pageOffset);
        page.setPageSize(pageSize);
        page.setOrder(Page.ASC);
        page.setOrderBy("id");
		return this.refundPreviewDao.getRefundPreviewList(page);
	}

}
