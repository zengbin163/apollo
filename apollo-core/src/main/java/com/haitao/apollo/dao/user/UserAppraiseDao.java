/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.user;

import com.haitao.apollo.pojo.user.UserAppraise;
import com.haitao.apollo.vo.user.UserAppraiseVo;

/**
 * @ClassName: UserAppraiseDao
 * @Description: 消费者评价DAO
 * @author zengbin
 * @date 2015年12月1日 上午11:05:23
 */
public interface UserAppraiseDao {
	Integer insertUserAppraise(UserAppraiseVo userAppraiseVo);
	UserAppraise getUserAppraiseByOrderId(Integer orderId);
}
