package com.haitao.apollo.dao.praise;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.praise.PostrewardPraise;
import com.haitao.apollo.vo.praise.PostrewardPraiseVo;

public interface PostrewardPraiseDao {
	Integer insertPostrewardPraise(PostrewardPraiseVo postrewardPraiseVo);
	List<PostrewardPraise> getPostrewardPraiseListByPostrewardId(@Param(value = "postrewardId")Integer postrewardId, @Param(value = "page") Page<?> page);
}
