/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.dao.purchaser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haitao.apollo.plugin.database.page.Page;
import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.vo.purchaser.PurchaserInfoVo;
import com.haitao.apollo.vo.purchaser.PurchaserVo;

/** 
* @ClassName: PurchaserDao 
* @Description: 买手信息DAO
* @author zengbin
* @date 2015年11月30日 下午3:23:23 
*/
public interface PurchaserDao {
    Integer insertPurchaser(PurchaserVo purchaserVo);
	Purchaser getPurchaserByMobile(@Param(value = "mobile") String mobile);
	Purchaser getPurchaserByMobileAndPassword(@Param(value = "mobile") String mobile,@Param(value = "password") String password);
    Purchaser getPurchaserById(@Param(value = "id") Integer id);
    Integer updatePurchaserById(@Param(value = "purchaserVo") PurchaserVo purchaserVo);
    List<Purchaser> getPurchaserList(@Param(value = "page") Page<?> page);
    Integer insertPurchaserInfo(PurchaserInfoVo purchaserInfoVo);
}
