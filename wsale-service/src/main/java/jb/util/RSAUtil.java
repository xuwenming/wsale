package jb.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA工具类
 */
public class RSAUtil {

	private static Cipher cipher;

	private final static String ENCODING = "UTF-8";
	
	public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDChIjqEL4Lrr55xfGOymQlKeILVe8JvtfvHkazZvFpKHeN+rQMsroXcE5d6AjBYDMQZ9shyUmOzrb0DN+rFGp4N0XcxeN7qkSDl6ZUldFji3BeitXyODeOpn4y/ox+Arc4N4NteXFZHM7pXuq7g5ATcYMPt/veWwleWjXYYG7NRQIDAQAB";
	public static final String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMKEiOoQvguuvnnF8Y7KZCUp4gtV7wm+1+8eRrNm8Wkod436tAyyuhdwTl3oCMFgMxBn2yHJSY7OtvQM36sUang3RdzF43uqRIOXplSV0WOLcF6K1fI4N46mfjL+jH4Ctzg3g215cVkczule6ruDkBNxgw+3+95bCV5aNdhgbs1FAgMBAAECgYBDjlYivKfUVN8wWa/YQLBhO9eWykSXpOFH2cMCQalsNTK4a66mjlgVFdsRwClyR5/Ufc1cQZU7dM2Tmvj/aLrE5Jrx2IkIasWcGthn4zOZYofFr1JnZuX8ceeoQGw4cFzCEELp6e9YseDgsBU5GuMVcQ1Afp9pskaiahXRF71oTQJBAOQfx63EjjHsKdVNYOKjnwsOmOZueKR5hDQvmis40Mpb6mIdRttouP0agIX3K4FcKPUBUAlSA3BoFWLRCU6rFLMCQQDaSXj8x6vpZxvFJ08woDPNq/IUSpsuQOY+Hkyc4nOX98K0Q8QPEPPhSXa6IoG2SAW/kRV9SfIcqmM4g1rEFcInAkA9+DAbETj7cMcQbZuzFXWf4FBC0KGvNF9oecHsmduGDSAHlSJpefI+t1NQkQtdcbaij7+5OHE8uNmOZXCc0TanAkAwfS2xCTG48TyL+QVwScX4I//sXf5GsgfzOIvQNcVztoe7v2nn5t4l50nGYy/pK+qK+VGKuUhc8cSEozkgYaz1AkAPdbKTmOqP2FpHbMdeaQu3TJkhkqa9KsnXuv7m0i/1nlcViMq+eTtr8cR4ST3gvI40jyZIgHaMduSyCyMtvW7Y";

	public static final String PRIVATE_KEY = "privateKey";
	public static final String PUBLIC_KEY = "publicKey";

	static{
		try {
			cipher = Cipher.getInstance("RSA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成秘钥对
	 */
	public static Map<String, String> generateKeyPair() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();
			String publicKeyString = getKeyString(publicKey);
			String privateKeyString = getKeyString(privateKey);
			Map<String, String> map = new HashMap<>();
			map.put(PUBLIC_KEY, publicKeyString);
			map.put(PRIVATE_KEY, privateKeyString);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 得到公钥
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 使用公钥加密
	 */
	public static String encryptByPublic(String text, String publicKey){
		String result = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, RSAUtil.getPublicKey(publicKey));
			byte[] enBytes = cipher.doFinal(text.getBytes(ENCODING));
			result = Base64.encodeBase64URLSafeString(enBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 使用公钥解密
	 */
	public static String decryptByPublic(String text, String publicKey){
		String result = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, RSAUtil.getPublicKey(publicKey));
			byte[] deBytes = cipher.doFinal(Base64.decodeBase64(text));
			result = new String(deBytes, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 使用私钥加密
	 */
	public static String encryptByPrivate(String text, String privateKey){
		String result = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, RSAUtil.getPrivateKey(privateKey));
			byte[] enBytes = cipher.doFinal(text.getBytes(ENCODING));
			result = Base64.encodeBase64URLSafeString(enBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 使用私钥解密
	 */
	public static String decryptByPravite(String text, String privateKey){
		String result = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, RSAUtil.getPrivateKey(privateKey));
			byte[] deBytes = cipher.doFinal(Base64.decodeBase64(text));
			result = new String(deBytes, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 得到密钥字符串(经过base64编码)
	 */
	public static String getKeyString(Key key) throws Exception {
		return Base64.encodeBase64String(key.getEncoded());
	}

}