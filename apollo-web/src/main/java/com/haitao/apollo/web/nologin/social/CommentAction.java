package com.haitao.apollo.web.nologin.social;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.pojo.comment.PostrewardComment;
import com.haitao.apollo.pojo.comment.ShowOrderComment;
import com.haitao.apollo.proccess.PageListProcess;
import com.haitao.apollo.web.BaseAction;

public class CommentAction extends BaseAction {

	private static final long serialVersionUID = 6528792848452819125L;
	@Autowired
	private PageListProcess pageListProcess;
	
	/**
	 * 
	* @Description  查询悬赏评论列表
	* @param postrewardId 悬赏id
	* @param pageOffset 分页开始数
	* @param pageSize 多少条
	* @return
	 */
	public String postrewardCommentList(){
        Integer postrewardId = this.getIntParameter(request, "postrewardId", null);
        Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
        Integer pageSize = this.getIntParameter(request, "pageSize", null);
        List<PostrewardComment> postrewardCommentList = this.pageListProcess.postrewardCommentList(postrewardId, pageOffset, pageSize);
        returnFastJSON(postrewardCommentList);
        return null;
	}
	
	/**
	 * 
	* @Description
	* @param showOrderId 悬赏id
	* @param pageOffset 分页开始数
	* @param pageSize 多少条
	* @return
	 */
	public String showOrderCommentList(){
        Integer showOrderId = this.getIntParameter(request, "showOrderId", null);
        Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
        Integer pageSize = this.getIntParameter(request, "pageSize", null);
        List<ShowOrderComment> showOrderCommentList = this.pageListProcess.showOrderCommentList(showOrderId, pageOffset, pageSize);
        returnFastJSON(showOrderCommentList);
        return null;
	}
}
