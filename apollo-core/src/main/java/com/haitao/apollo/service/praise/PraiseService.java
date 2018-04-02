package com.haitao.apollo.service.praise;

import java.util.List;

import com.haitao.apollo.pojo.praise.PostrewardPraise;
import com.haitao.apollo.pojo.praise.ShowOrderPraise;

public interface PraiseService {
	Integer postrewardPraise(Integer postrewardId,Integer praiserId,Integer isRead);
	Integer showOrderPraise(Integer showOrderId,Integer praiserId,Integer isRead);
	List<PostrewardPraise> getPostrewardPraiseListByPostrewardId(Integer postrewardId, Integer pageOffset, Integer pageSize);
	List<ShowOrderPraise> getShowOrderPraiseListByShowOrderId(Integer showOrderId, Integer pageOffset, Integer pageSize);
	/**
	 * 查询晒单的点赞数
	 * @param showOrderId
	 * @return
	 */
	Integer countShowOrderPraise(Integer showOrderId);
}
