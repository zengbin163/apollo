/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月19日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.logistics;

import java.util.List;

/** 
* @ClassName: LogisticsTrack 
* @Description: 物流记录
* @author zengbin
* @date 2015年11月19日 下午2:11:33 
*/
public abstract class LogisticsTrack {
    public abstract List<Track> getTrackList(String trackingNo);
}

