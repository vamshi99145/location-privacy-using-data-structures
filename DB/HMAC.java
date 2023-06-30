package com;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;
import org.apache.commons.codec.binary.Base64;
public class HMAC{
public static String getHmac(String msg,byte key[])throws Exception{
	SecretKeySpec skey = new SecretKeySpec(key,"HmacSHA1");
	Mac mac = Mac.getInstance("HmacSHA1");
	mac.init(skey);
	byte[] bytes = mac.doFinal(msg.getBytes("UTF-8"));
	return new String(Base64.encodeBase64(bytes));
}
}