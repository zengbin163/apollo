package com.haitao.apollo.web.login.social;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.annotation.FromUser;
import com.haitao.apollo.proccess.SocialProcess;
import com.haitao.apollo.web.BaseAction;

public class PraiseAction extends BaseAction {
	private static final long serialVersionUID = -6352030918400502860L;
	@Autowired
	private SocialProcess socialProcess;
	
	/**
	 * 悬赏点赞
	 * @param postrewardId
	 * @param praiserId
	 * @return
	 */
    @FromPurchaser
    @FromUser
	public String postrewardPraise(){
        Integer postrewardId = this.getIntParameter(request, "postrewardId", 0);
        Integer praiserId = this.getIntParameter(request, "praiserId", 0);
        Integer id = this.socialProcess.postrewardPraise(postrewardId, praiserId);
        returnFastJSON(toMap("praiseId", id));
        return null;
	}
	
	/**
	 * 晒单点赞
	 * @param showOrderId
	 * @param praiserId
	 * @return
	 */
    @FromPurchaser
    @FromUser
    public String showOrderPraise(){
        Integer showOrderId = this.getIntParameter(request, "showOrderId", 0);
        Integer praiserId = this.getIntParameter(request, "praiserId", 0);
        Integer id = this.socialProcess.showOrderPraise(showOrderId, praiserId);
        returnFastJSON(toMap("praiseId", id));
        return null;
	}
}
