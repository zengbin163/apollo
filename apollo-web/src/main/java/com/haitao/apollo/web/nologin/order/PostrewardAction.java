/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月14日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.web.nologin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.apollo.pojo.order.PostReward;
import com.haitao.apollo.proccess.PageIndexProcess;
import com.haitao.apollo.proccess.PageListProcess;
import com.haitao.apollo.web.BaseAction;

/** 
* @ClassName: PostRewardAction 
* @Description: 悬赏action
* @author zengbin
* @date 2015年11月16日 下午19:57:37 
*/
public class PostrewardAction extends BaseAction {
    
    private static final long serialVersionUID = -7637011317919479555L;
    @Autowired
    private PageIndexProcess pageIndexProcess;
    @Autowired
    private PageListProcess pageListProcess;

    /**
     * 
    * @Description 悬赏详情
    * @param postrewardId 悬赏id
    * @return
     */
    public String postrewardDetail(){
        Integer postrewardId = this.getIntParameter(request, "postrewardId", null);
        PostReward postReward = this.pageIndexProcess.postrewardDetail(postrewardId);
        returnFastJSON(postReward);
        return null;
    }
    
    /**
     * 查询消费者端悬赏池
     * @param categoryId 类目id
     * @param pageOffset
     * @param pageSize
     * @return
     */
    public String postRewardPoolListByUser(){
    	Integer brandId = this.getIntParameter(request, "brandId", null);
        Integer categoryId = this.getIntParameter(request, "categoryId", null);
        Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
        Integer pageSize = this.getIntParameter(request, "pageSize", null);
        List<PostReward> postrewardList = this.pageListProcess.getPostRewardPoolListByUser(brandId, categoryId, pageOffset, pageSize);
        returnFastJSON(postrewardList);
        return null;
    }
}
