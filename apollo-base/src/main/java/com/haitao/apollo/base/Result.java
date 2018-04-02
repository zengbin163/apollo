/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月20日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.base;

import java.io.Serializable;

/** 
* @ClassName: Result 
* @Description: 接口层返回值
* @author zengbin
* @date 2015年10月20日 上午9:56:58 
*/
public class Result implements Serializable {
    
    private static final long serialVersionUID = -7387700935792128042L;
    
    private Integer resultCode; // 如果请求正常，返回正常的ResultCode.SUCCESS
    private String resultMessage; // 如果请求正常，返回正常的Result信息
    private String exceptionMessage; // 如果请求异常，返回异常日志信息
    private Object result; // 返回结果，支持各种序列化的数据类型
    
    public Result(){
    	
    }
    
    public static Result CREATE(Integer resultCode, String resultMessage, Object resultObj){
    	Result result = new Result();
    	result.setResultCode(resultCode);
    	result.setResultMessage(resultMessage);
    	result.setResult(resultObj);
    	return result;
    }

    public static Result ERROR(Integer resultCode, String exceptionMessage){
    	Result result = new Result();
    	result.setResultCode(resultCode);
    	result.setExceptionMessage(exceptionMessage);
    	return result;
    }
    
    public Integer getResultCode() {
        return resultCode;
    }
    
    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }
    
    public String getResultMessage() {
        return resultMessage;
    }
    
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    
    public String getExceptionMessage() {
        return exceptionMessage;
    }
    
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
    
    public Object getResult() {
        return result;
    }
    
    public void setResult(Object result) {
        this.result = result;
    }
    
}
