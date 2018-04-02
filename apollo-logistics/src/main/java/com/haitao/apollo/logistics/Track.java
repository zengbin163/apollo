/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月19日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.logistics;

import java.io.Serializable;

/** 
* @ClassName: Track 
* @Description: 物流路径
* @author zengbin
* @date 2015年11月19日 下午2:30:37 
*/
public class Track implements Serializable {
    
    private static final long serialVersionUID = -6018617868578659287L;
    
    private Integer logisticsOrderId;
    private String time;
    private String track;
    
    public Integer getLogisticsOrderId() {
        return logisticsOrderId;
    }
    
    public void setLogisticsOrderId(Integer logisticsOrderId) {
        this.logisticsOrderId = logisticsOrderId;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    public String getTrack() {
        return track;
    }
    
    public void setTrack(String track) {
        this.track = track;
    }
    
}
