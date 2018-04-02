/*
*@Project: GZJK
*@Author: zengbin
*@Date: 2015年11月12日
*@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.status;

import java.io.Serializable;

/** 
* @ClassName: RewardStatus 
* @Description: 悬赏状态
* @author zengbin
* @date 2015年11月15日 下午14:05:31 
*/
public class RewardStatus implements Serializable{
    private static final long serialVersionUID = -7346205391725001166L;
    public static final Integer PREPAY_REWARD=          100;//悬赏预支付状态
    public static final Integer CREATE_REWARD=          101;//悬赏中，用户支付40%定金
    public static final Integer PURCHASER_ACCEPT=     	102;//买手已接单
    public static final Integer AGREE_TIME=             103;//消费者同意发货时间并支付尾款
    public static final Integer IN_SHIPMENTS=           104;//买手已发货
    public static final Integer FINISH_REWARD=          105;//悬赏完成
    public static final Integer CLOSE_REWARD=           106;//悬赏关闭
}