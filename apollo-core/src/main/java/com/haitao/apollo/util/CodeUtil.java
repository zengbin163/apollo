/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月10日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.util;

import java.util.UUID;

/** 
* @ClassName: CodeUtil 
* @Description: 各种码的工具类
* @author zengbin
* @date 2015年11月10日 上午9:31:31 
*/
public class CodeUtil {
    
    public static final String PURCHASER_PREFIX = "P";//买手邀请码前缀
    public static final String USER_PREFIX = "M";//用户邀请码前缀
    
    private static String []BE_REPLACE_CHARS = {"1","l","0","o","i"};
    private static String []REPLACE_CHARS = {"a","r","v","8","9","q","r","c","z","x","k","h","y","w","6"};
    
    
    
    /**
     * <pre>
     *    生成标准的六位短信验证码
     * </pre>
     * @return
     */
    public static String produceSmsCode() {
        String str = "";
        for (int i = 0; i < 6; i++) {
            str += (int)(Math.random() * 9);
        }
        return str;
    }
    
    /**
     * <pre>
     *     生成邀请码
     * </pre>
     * @param prefix
     * @return
     */
    public static String produceInviteCode(String prefix){
        String UUIDS = UUID.randomUUID().toString();
        String MD5 = MD5Util.md5(UUIDS);//UUID的MD5
        StringBuffer sb = new StringBuffer();
        sb.append(prefix);
        sb.append(MD5.substring(5, 10));
        sb.append(MD5.substring(20, 23));
        String ret = sb.toString();
        for(int i=0;i<BE_REPLACE_CHARS.length;i++){
            int randInt = (int)(Math.random() * 15);
            ret = ret.replaceAll(BE_REPLACE_CHARS[i], REPLACE_CHARS[randInt]);
        }
        return ret.toUpperCase();
    }
}
