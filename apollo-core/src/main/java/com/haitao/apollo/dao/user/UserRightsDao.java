package com.haitao.apollo.dao.user;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.vo.user.UserRightsVo;

public interface UserRightsDao {
	Integer insertUserRights(UserRightsVo userRightsVo);

	Integer updateUserRights(@Param(value = "userRightsVo") UserRightsVo userRightsVo);
	
	Integer getUserRightsCountByOrderId(@Param(value = "orderId") Integer orderId);
}
