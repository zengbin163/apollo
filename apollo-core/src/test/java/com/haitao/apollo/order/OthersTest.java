package com.haitao.apollo.order;

import com.alibaba.fastjson.JSONObject;
import com.haitao.apollo.enums.MsgTemplateEnum;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.vo.message.ImMessageVo;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;

public class OthersTest {

	public static void main(String[] args) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException, ChannelException {
//		Charge charge = Charge.retrieve("xxx");
//		charge.setId("123");
//		charge.setDescription("大牌币全额支付");
//		charge.setAmountRefunded(123);
//		System.out.println(JSON.toJSONString(charge,
//				SerializerFeature.WriteMapNullValue,
//				SerializerFeature.WriteNullListAsEmpty,
//				SerializerFeature.WriteNullNumberAsZero,
//				SerializerFeature.WriteNullBooleanAsFalse,
//				SerializerFeature.WriteNullStringAsEmpty).toString());
		
		ImMessageVo imMessageVo = new ImMessageVo(1, 2, 0, 1, "你好，罗田佳", DateUtil.currentUTCTime());
        JSONObject json = new JSONObject();
        json.put("imMsg", imMessageVo);
        json.put("msgType", MsgTemplateEnum.IM_MESSAGE.getCode());

		System.out.println(json.toJSONString());
	}

}

class Person {
	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
