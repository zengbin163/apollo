package com.haitao.apollo.util;

import org.apache.commons.codec.binary.Base64;
import java.io.FileInputStream;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

/**
 * WebHooks签名校验
 * @author zengbin
 *
 */
public class WebHooksVerifyUtil {

	public static byte[] getByteFromFile(String file, boolean base64) throws Exception {
		FileInputStream in = new FileInputStream(file);
		byte[] fileBytes = new byte[in.available()];
		in.read(fileBytes);
		in.close();
		String pubKey = new String(fileBytes, "UTF-8");
		if (base64) {
			fileBytes = Base64.decodeBase64(pubKey);
		}
		return fileBytes;
	}

	public static PublicKey getPubKey(String path) throws Exception {
		// read key bytes
		FileInputStream in = new FileInputStream(path);
		byte[] keyBytes = new byte[in.available()];
		in.read(keyBytes);
		in.close();
		String pubKey = new String(keyBytes, "UTF-8");
		pubKey = pubKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
		keyBytes = Base64.decodeBase64(pubKey);
		// generate public key
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(spec);
		return publicKey;
	}

	public static boolean verifyData(byte[] data, byte[] sigBytes, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initVerify(publicKey);
		signature.update(data);
		return signature.verify(sigBytes);
	}

}
