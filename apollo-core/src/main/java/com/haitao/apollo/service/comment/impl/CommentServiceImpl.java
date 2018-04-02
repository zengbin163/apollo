package com.haitao.apollo.service.comment.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.dao.comment.PostrewardCommentDao;
import com.haitao.apollo.dao.comment.ShowOrderCommentDao;
import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.pojo.comment.PostrewardComment;
import com.haitao.apollo.pojo.comment.ShowOrderComment;
import com.haitao.apollo.service.comment.CommentService;
import com.haitao.apollo.vo.comment.PostrewardCommentVo;
import com.haitao.apollo.vo.comment.ShowOrderCommentVo;

@Service
public class CommentServiceImpl implements CommentService{
	
    @Resource(name = "postrewardCommentDao")
    private PostrewardCommentDao postrewardCommentDao;
    @Resource(name = "showOrderCommentDao")
    private ShowOrderCommentDao showOrderCommentDao;

	@Override
	public Integer publishPostrewardComment(Integer postrewardId, Integer commenterId, Integer beCommentedId,
			String comment, Integer isRead) {
		Assert.notNull(postrewardId,"悬赏id不能为空");
		Assert.notNull(commenterId,"发表评论者id不能为空");
		Assert.notNull(beCommentedId,"被评论者id不能为空");
		PostrewardCommentVo postrewardCommentVo = new PostrewardCommentVo(postrewardId, commenterId, beCommentedId, comment, isRead);
		postrewardCommentDao.insertPostrewardComment(postrewardCommentVo);
		if(null==postrewardCommentVo.getId()){
			throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("悬赏评论发表失败，postrewardId=%s", postrewardId));
		}
		return postrewardCommentVo.getId();
	}

	@Override
	public Integer publishShowOrderComment(Integer showOrderId, Integer commenterId, Integer beCommentedId,
			String comment, Integer isRead) {
		Assert.notNull(showOrderId,"晒单id不能为空");
		Assert.notNull(commenterId,"发表评论者id不能为空");
		Assert.notNull(beCommentedId,"被评论者id不能为空");
		ShowOrderCommentVo showOrderCommentVo = new ShowOrderCommentVo(showOrderId, commenterId, beCommentedId, comment, isRead);
		showOrderCommentDao.insertShowOrderComment(showOrderCommentVo);
		if(null==showOrderCommentVo.getId()){
			throw new ApolloBizException(ResultCode.SAVE_FAIL, SessionContainer.getSession().getOperatorId(), String.format("晒单评论发表失败，showOrderId=%s", showOrderId));
		}
		return showOrderCommentVo.getId();
	}

	@Override
	public List<PostrewardComment> getPostrewardCommentListByPostrewardId(Integer postrewardId, Integer pageOffset, Integer pageSize) {
		Assert.notNull(postrewardId,"悬赏id不能为空");
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "id");
		return this.postrewardCommentDao.getPostrewardCommentListByPostrewardId(postrewardId, page);
	}

	@Override
	public List<ShowOrderComment> getShowOrderCommentListByShowOrderId(Integer showOrderId, Integer pageOffset, Integer pageSize) {
		Assert.notNull(showOrderId,"晒单id不能为空");
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "id");
		return this.showOrderCommentDao.getShowOrderCommentListByShowOrderId(showOrderId, page);
	}

}
