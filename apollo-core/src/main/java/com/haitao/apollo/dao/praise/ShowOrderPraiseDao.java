package com.haitao.apollo.dao.praise;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.praise.ShowOrderPraise;
import com.haitao.apollo.vo.praise.ShowOrderPraiseVo;

public interface ShowOrderPraiseDao {
	Integer insertShowOrderPraise(ShowOrderPraiseVo showOrderPraiseVo);
	List<ShowOrderPraise> getShowOrderPraiseListByShowOrderId(@Param(value = "showOrderId")Integer showOrderId, @Param(value = "page") Page<?> page);
	Integer countShowOrderPraise(Integer showOrderId);
}
