/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.user.User;
import com.haitao.apollo.vo.user.UserVo;

/** 
* @ClassName: UserDao 
* @Description: 用户信息DAO
* @author zengbin
* @date 2015年10月28日 下午3:23:23 
*/
public interface UserDao {
    List<User> getUserListByPage(@Param(value = "userVo") UserVo userVo, @Param(value = "page") Page<?> page);
    List<User> getForbiddenUserList(@Param(value = "page") Page<?> page);
    User getUserByMobileAndPassword(@Param(value = "mobile") String mobile,@Param(value = "password") String password);
    User getUserByInviteCode(@Param(value = "inviteCode") String inviteCode);
    List<User> getUserListByPurchaserId(@Param(value = "purchaserId") Integer purchaserId, @Param(value = "page") Page<?> page);
    User getUserByMobile(@Param(value = "mobile") String mobile);
    User getUserById(@Param(value = "id") Integer id);
    Integer insertUser(UserVo userVo);
    Integer updateUser(UserVo userVo);
}
