package com.haitao.apollo.proccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.haitao.apollo.enums.IsReadEnum;
import com.haitao.apollo.service.comment.CommentService;
import com.haitao.apollo.service.praise.PraiseService;

@Component
public class SocialProcess extends Process {
	
    @Autowired
    private CommentService commentService;
    @Autowired
    private PraiseService praiseService;
	
    /**
     * 悬赏评论
     * @param postrewardId
     * @param commenterId
     * @param beCommentedId
     * @param comment
     * @return
     */
	public Integer postrewardComment(Integer postrewardId, Integer commenterId, Integer beCommentedId, String comment){
		return this.commentService.publishPostrewardComment(postrewardId, commenterId, beCommentedId, comment, IsReadEnum.UN_READED.getCode());
	}

	/**
	 * 晒单评论
	 * @param showOrderId
	 * @param commenterId
	 * @param beCommentedId
	 * @param comment
	 * @return
	 */
	public Integer showOrderComment(Integer showOrderId, Integer commenterId, Integer beCommentedId, String comment){
		return this.commentService.publishShowOrderComment(showOrderId, commenterId, beCommentedId, comment, IsReadEnum.UN_READED.getCode());
	}
	
	/**
	 * 悬赏点赞
	 * @param postrewardId
	 * @param praiserId
	 * @return
	 */
	public Integer postrewardPraise(Integer postrewardId,Integer praiserId){
		return this.praiseService.postrewardPraise(postrewardId, praiserId, IsReadEnum.UN_READED.getCode());
	}
	
	/**
	 * 晒单点赞
	 * @param showOrderId
	 * @param praiserId
	 * @return
	 */
	public Integer showOrderPraise(Integer showOrderId,Integer praiserId){
		return this.praiseService.showOrderPraise(showOrderId, praiserId, IsReadEnum.UN_READED.getCode());
	}
}
