/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月11日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.enums;

/** 
* @ClassName: RewardSourceEnum 
* @Description: 悬赏发起来源
* @author zengbin
* @date 2015年11月11日 下午9:02:45 
*/
public enum RewardSourceEnum {
    
    FROM_SHOW_ORDER(0, "晒单页发起悬赏"), 
    FROM_USER_SELF(1, "用户自主发起"),
    FROM_REWARD_FOLLOW(2, "悬赏页跟单");
    
    private RewardSourceEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    
    private Integer code;
    
    private String name;
    
    public Integer getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public static RewardSourceEnum getEnum(Integer code) {
        for (RewardSourceEnum rewardSourceEnum : RewardSourceEnum.values()) {
            if (rewardSourceEnum.getCode().equals(code)) {
                return rewardSourceEnum;
            }
        }
        return null;
    }
    
}
