package com.haitao.apollo.web.login.social;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.proccess.SocialProcess;
import com.haitao.apollo.web.BaseAction;

public class CommentAction extends BaseAction {

	private static final long serialVersionUID = 4164814915694074284L;
	@Autowired
	private SocialProcess socialProcess;
	
	/**
	 * 悬赏评论
	 * @param postrewardId  悬赏id
	 * @param commenterId  评论者id
	 * @param beCommentedId  被评论者id
	 * @param comment  评论内容
	 * @return
	 */
	public String postrewardComment(){
        Integer postrewardId = this.getIntParameter(request, "postrewardId", 0);
        Integer commenterId = this.getIntParameter(request, "commenterId", 0);
        Integer beCommentedId = this.getIntParameter(request, "beCommentedId", 0);
        String comment = this.getFilteredParameter(request, "comment", 0, null);
        Integer id = this.socialProcess.postrewardComment(postrewardId, commenterId, beCommentedId, comment);
        returnFastJSON(toMap("commentId", id));
        return null;
	}
	
	/**
	 * 晒单评论
	 * @param showOrderId  晒单id
	 * @param commenterId  评论者id
	 * @param beCommentedId  被评论者id
	 * @param comment  评论内容
	 * @return
	 */
	public String showOrderComment(){
        Integer showOrderId = this.getIntParameter(request, "showOrderId", 0);
        Integer commenterId = this.getIntParameter(request, "commenterId", 0);
        Integer beCommentedId = this.getIntParameter(request, "beCommentedId", 0);
        String comment = this.getFilteredParameter(request, "comment", 0, null);
        Integer id = this.socialProcess.showOrderComment(showOrderId, commenterId, beCommentedId, comment);
        returnFastJSON(toMap("commentId", id));
        return null;
	}
}
