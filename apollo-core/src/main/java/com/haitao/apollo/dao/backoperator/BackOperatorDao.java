package com.haitao.apollo.dao.backoperator;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.pojo.backoperator.BackOperator;

public interface BackOperatorDao {

	BackOperator getBackOperatorByMobileAndPassword(@Param(value = "mobile")String mobile, @Param(value = "password")String password);
}
