package com.haitao.apollo.dao.comment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.comment.PostrewardComment;
import com.haitao.apollo.vo.comment.PostrewardCommentVo;

public interface PostrewardCommentDao {
	Integer insertPostrewardComment(PostrewardCommentVo postrewardCommentVo);
	List<PostrewardComment> getPostrewardCommentListByPostrewardId(@Param(value = "postrewardId")Integer postrewardId, @Param(value = "page") Page<?> page);
}
