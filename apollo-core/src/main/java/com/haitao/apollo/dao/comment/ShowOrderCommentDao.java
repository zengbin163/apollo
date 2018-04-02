package com.haitao.apollo.dao.comment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.comment.ShowOrderComment;
import com.haitao.apollo.vo.comment.ShowOrderCommentVo;

public interface ShowOrderCommentDao {
	Integer insertShowOrderComment(ShowOrderCommentVo showOrderCommentVo);
	List<ShowOrderComment> getShowOrderCommentListByShowOrderId(@Param(value = "showOrderId")Integer showOrderId, @Param(value = "page") Page<?> page);
}
