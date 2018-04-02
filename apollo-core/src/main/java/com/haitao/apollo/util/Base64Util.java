package com.haitao.apollo.util;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.util.StringUtils;

/**
 * Base64编码和解码
 * @author zengbin
 *
 */
public class Base64Util {

	public static String decode(String deviceId) {
		if (StringUtils.isEmpty(deviceId)) {
			return null;
		}
		try {
			// 1、解密
			Decoder decoder = Base64.getDecoder();
			byte[] b = decoder.decode(deviceId);
			return new String(b, "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	public static String encode(String deviceId) {
		if (StringUtils.isEmpty(deviceId)) {
			return null;
		}
		try {
			// 1、加密
			Encoder encoder = Base64.getEncoder();
			byte[] b = encoder.encode(deviceId.getBytes());
			return new String(b, "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

}
