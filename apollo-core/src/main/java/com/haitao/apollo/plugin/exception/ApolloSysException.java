package com.haitao.apollo.plugin.exception;

import com.haitao.apollo.base.ResultCode;

/** 
* @ClassName: ApolloSysException 
* @Description: 系统异常
* @author zengbin
* @date 2015年11月10日 下午7:24:25 
*/
public class ApolloSysException extends RuntimeException {
    
    private static final long serialVersionUID = 604122701395795861L;
    
    private ResultCode resultCode;
    
    public ApolloSysException() {
        super();
    }
    
    public ApolloSysException(String message) {
        super(String.format("====[errorMessage ：  %s]", message));
    }
    
    public ApolloSysException(ResultCode resultCode , Integer userId , String message) {
        super(String.format("===[errorCode ： %s ]===[userId ： %s]===[errorMessage ：  %s]===", resultCode.getCode(), userId , message));
        this.resultCode = resultCode;
    }
    
    public ApolloSysException(ResultCode resultCode , String deviceId , String message) {
        super(String.format("===[errorCode ： %s ]===[deviceId ： %s]===[errorMessage ：  %s]===", resultCode.getCode(), deviceId , message));
        this.resultCode = resultCode;
    }

    public ApolloSysException(ResultCode resultCode , Integer userId , String message, Throwable cause) {
        super(String.format("===[errorCode ： %s ]===[userId ： %s]===[errorMessage ：  %s]===", resultCode.getCode() , userId , message), cause);
        this.resultCode = resultCode;
    }
    
    public ApolloSysException(ResultCode resultCode , String deviceId , String message, Throwable cause) {
        super(String.format("===[errorCode ： %s ]===[deviceId ： %s]===[errorMessage ：  %s]===", resultCode.getCode() , deviceId , message), cause);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
    
}
