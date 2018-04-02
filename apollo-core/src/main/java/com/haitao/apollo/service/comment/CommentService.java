package com.haitao.apollo.service.comment;

import java.util.List;

import com.haitao.apollo.pojo.comment.PostrewardComment;
import com.haitao.apollo.pojo.comment.ShowOrderComment;

public interface CommentService {
	Integer publishPostrewardComment(Integer postrewardId,Integer commenterId,Integer beCommentedId,String comment,Integer isRead);
	Integer publishShowOrderComment(Integer showOrderId,Integer commenterId,Integer beCommentedId,String comment,Integer isRead);

	List<PostrewardComment> getPostrewardCommentListByPostrewardId(Integer postrewardId, Integer pageOffset, Integer pageSize);
	List<ShowOrderComment> getShowOrderCommentListByShowOrderId(Integer showOrderId, Integer pageOffset, Integer pageSize);
}
