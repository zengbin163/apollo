package com.haitao.apollo.order;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.FundTypeEnum;

public class JsonTestCase {
	
	protected static Map<String,Object> toJsonString(String key, Object obj) {
		Map<String,Object> json = new HashMap<String,Object>();
		json.put(key, obj);
		return json;
	}
    
    protected static String returnFastJSON(Object obj) {
    	return ("{\"recode\":\"" + ResultCode.SUCCESS.getCode() + "\"," + "\"data\":"
                + JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue).toString()
                + ",\"msg\":\"" + ResultCode.SUCCESS.getString() + "\"}") ; 
    }
	
	public static final void main(String []args){
		System.out.println(FundTypeEnum.getEnum(0).toString() + 98);
		System.out.println(FundTypeEnum.getEnum(1).toString() + 98);
		System.out.println(FundTypeEnum.getEnum(5).toString() + 98);
		System.out.println((FundTypeEnum.getEnum(0).toString() + 98).contains("DEPOSIT"));
		System.out.println((FundTypeEnum.getEnum(0).toString() + 98).startsWith("DEPOSIT"));
		System.out.println((FundTypeEnum.getEnum(0).toString() + 98).substring("DEPOSIT".length()));
		System.out.println((FundTypeEnum.getEnum(1).toString() + 98).substring("FINAL".length()));
		System.out.println((FundTypeEnum.getEnum(5).toString() + 98).substring("GUARANTEE".length()));
	}

}
