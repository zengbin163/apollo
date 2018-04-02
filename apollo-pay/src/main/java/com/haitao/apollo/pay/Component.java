package com.haitao.apollo.pay;

import java.util.UUID;

public interface Component {
	public static final String APP_ID = "id";
	public static final String CHARGE = "charge";
	public static final String APP_MAP = "app";
	public static final String AMOUNT = "amount";
	public static final String CURRENCY = "currency";
	public static final String SUBJECT = "subject";
	public static final String BODY = "body";
	public static final String ORDER_NO = "order_no";
	public static final String PAY_SERIAL_NO = "id";
	public static final String CHANNEL = "channel";
	public static final String CLIENT_IP = "client_ip";
	public static final String DESCRIPTION = "description";
	public static final String FUND_TYPE = "fundType";
	public static final String BIG_MONEY = "bigMoney";
	public static final String METADATA_MAP = "metadata";
	
	public static final String API_KEY_VALUE = "sk_live_rfr1S8OyPivTOm5anTuLiLS8"; // API KEY

	public static final String NULL_PAY_SERIAL_NO = UUID.randomUUID().toString();//支付序列号如果为空
}
