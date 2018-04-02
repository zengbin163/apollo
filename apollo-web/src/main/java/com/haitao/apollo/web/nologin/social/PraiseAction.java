package com.haitao.apollo.web.nologin.social;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.pojo.praise.PostrewardPraise;
import com.haitao.apollo.pojo.praise.ShowOrderPraise;
import com.haitao.apollo.proccess.PageListProcess;
import com.haitao.apollo.web.BaseAction;

public class PraiseAction extends BaseAction {

	private static final long serialVersionUID = -5231533084109810904L;
	@Autowired
	private PageListProcess pageListProcess;
	
	public String postrewardPraiseList(){
        Integer postrewardId = this.getIntParameter(request, "postrewardId", 0);
        Integer pageOffset = this.getIntParameter(request, "pageOffset", 0);
        Integer pageSize = this.getIntParameter(request, "pageSize", 0);
        List<PostrewardPraise> postrewardPraiseList = this.pageListProcess.postrewardPraiseList(postrewardId, pageOffset, pageSize);
        returnFastJSON(postrewardPraiseList);
        return null;
	}
	
	public String showOrderPraiseList(){
        Integer showOrderId = this.getIntParameter(request, "showOrderId", 0);
        Integer pageOffset = this.getIntParameter(request, "pageOffset", 0);
        Integer pageSize = this.getIntParameter(request, "pageSize", 0);
        List<ShowOrderPraise> showOrderPraiseList = this.pageListProcess.showOrderPraiseList(showOrderId, pageOffset, pageSize);
        returnFastJSON(showOrderPraiseList);
        return null;
	}
}
