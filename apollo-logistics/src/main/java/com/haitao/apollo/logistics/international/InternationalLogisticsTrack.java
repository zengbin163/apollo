/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月19日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.logistics.international;

import java.util.List;

import org.springframework.stereotype.Component;

import com.haitao.apollo.logistics.LogisticsTrack;
import com.haitao.apollo.logistics.Track;

/** 
* @ClassName: InternationalLogisticsTrack 
* @Description: 国际物流路径
* @author zengbin
* @date 2015年11月19日 下午2:39:32 
*/
@Component
public class InternationalLogisticsTrack extends LogisticsTrack {
    
    @Override
    public List<Track> getTrackList(String trackingNo) {
        return null;
    }
    
}

