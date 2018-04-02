package com.haitao.apollo.order;

import com.haitao.apollo.util.Base64Util;

public class Base64Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String deviceId = "SU1FST1BRjIzMjg0Ny05MzI5LTRDM0YtOTA1RS0xN0JDQzU5MTYzNzd8VkVSU0lPTj0xLjB8U05BTUU95Lit5Zu956e75YqofERFVklDRT1pUGhvbmU3LDF8T1NWRVJTSU9OPTkuM3xyb2xlPTA=";
		System.out.println(Base64Util.decode(deviceId));
		String deviceId2 = "IMEI=0257AB90-6C26-4EB9-8964-E320D6E59B47|VERSION=1.0|SNAME=(null)|DEVICE=x86_64|OSVERSION=9.2";
		System.out.println(Base64Util.encode(deviceId2));
		System.out.println(deviceId);

		String purchaserDeviceId = Base64Util.encode("IMEI=0257AB90-6C26-4EB9-8964-E320D6E59B47|VERSION=1.0|SNAME=(null)|DEVICE=x86_64|OSVERSION=9.2|role=0");
		String userDeviceId = Base64Util.encode("IMEI=0257AB90-6C26-4EB9-8964-E320D6E59B47|VERSION=1.0|SNAME=(null)|DEVICE=x86_64|OSVERSION=9.2|role=1");
		System.out.println("买手端:" + purchaserDeviceId);
		System.out.println("消费者:" + userDeviceId);
		
		String deviceId3 = "IMEI=0257AB90-6C26-4EB9-8964-E320D6E59B47|VERSION=1.0|SNAME=(null)|DEVICE=x86_64|OSVERSION=9.2|role=0";
		String []devices = deviceId3.split("\\|");
		for(String dev : devices) {
			if("role=0".equals(dev)) {
				System.out.println("deviceId合法");
			}
		}
		
		System.out.println("role=0".substring(5));
		System.out.println("role=1".substring(5));
		
		//买手端:SU1FST0wMjU3QUI5MC02QzI2LTRFQjktODk2NC1FMzIwRDZFNTlCNDd8VkVSU0lPTj0xLjB8U05BTUU9KG51bGwpfERFVklDRT14ODZfNjR8T1NWRVJTSU9OPTkuMnxyb2xlPTA=
		//消费者:SU1FST0wMjU3QUI5MC02QzI2LTRFQjktODk2NC1FMzIwRDZFNTlCNDd8VkVSU0lPTj0xLjB8U05BTUU9KG51bGwpfERFVklDRT14ODZfNjR8T1NWRVJTSU9OPTkuMnxyb2xlPTE=

	}
	
}
