/**
 * 
 */
package kr.co.ucsit.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * AES 암호화/복호화
 * @author cs1492
 * @date   2018. 2. 28.
 *
 */
public class CsCrypto {

	/**
	 * 비밀키
	 * key size : 16,24,32 bytes만 가능
	 */
	private final static String SECRET_KEY  = "0123456789012345";
	/**
	 * 초기 벡터
	 */
	private final static String INIT_VECTOR = "0123456789012345";
	
	
	public static void main(String[] args) {
		System.out.println(CsCrypto.sha512("admin@123"));	//a1102957491b9ce5441e111f7725f2fd0201bc32465e2536e5182d1c5e3f6b0965355c09f2c8b9111ab6d18a73b75f0f3a06e788bd2a6dff4ddc7c4da6ada603
		System.out.println(CsCrypto.sha512("rda@123"));	//5ece07bbc2bf53a6d3fff11df6ad5af602f16baa8d4d9e3053ab054374c4c82d7922885defdc40de35d321b62adfc0308ca43876390c579ed21400a89cc2e457
		System.out.println(CsCrypto.sha512("cj_instr@123"));
		
		System.out.println("admin@123 : a1102957491b9ce5441e111f7725f2fd0201bc32465e2536e5182d1c5e3f6b0965355c09f2c8b9111ab6d18a73b75f0f3a06e788bd2a6dff4ddc7c4da6ada603");
		System.out.println("rda@123 : 5ece07bbc2bf53a6d3fff11df6ad5af602f16baa8d4d9e3053ab054374c4c82d7922885defdc40de35d321b62adfc0308ca43876390c579ed21400a89cc2e457");
//		String plainText = "kihyun4477@nate.com";
//		String cipherText = CsCrypto.encrypt(plainText);
//		String plainText2 = CsCrypto.decrypt(cipherText, SECRET_KEY);
//		
//		System.out.println(String.format("%s\n%s\n%s", plainText, cipherText,  plainText2));
		
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("cipherText", "ZYM79uiEvGQs7jY/ns/T/c36g5V8t9jJRdMJ74TTgt8=");
//		System.out.println( CsCrypto.decrypt(map, "cipherText") );
	}

	/**
	 * hash function(sha256) 암호화 처리
	 * @param plainText 평문
	 * @return
	 */
	public static String sha256(String plainText){
		if(CsUtil.isEmpty(plainText)){
			return plainText;
		}
		
		return DigestUtils.sha256Hex(plainText);
	}
	
	/**
	 * hash function(sha512) 암호화 처리
	 * @param plainText 평문
	 * @return
	 */
	public static String sha512(String plainText){
		if(CsUtil.isEmpty(plainText)){
			return plainText;
		}
		
		return DigestUtils.sha512Hex(plainText);
	}

	
	/**
	 * @see	kr.co.ucsit.fwk.utl.FwkCrypto.encrypt(String, String, String)
	 */
	public static String encrypt(String plainText) {
		return encrypt(plainText, SECRET_KEY, INIT_VECTOR);
	}

	/**
	 * @see	kr.co.ucsit.fwk.utl.FwkCrypto.encrypt(String, String, String)
	 */
	public static String encrypt(String plainText, String secretKey) {
		return encrypt(plainText, secretKey, INIT_VECTOR);
	}

	/**
	 * AES 암호화
	 * @param plainText	평문
	 * @param secretKey	키
	 * @param initVector init vector
	 * @return
	 */
	public static String encrypt(String plainText, String secretKey, String initVector) {
		if(CsUtil.isEmpty(plainText)) {
			return "";
		}
		
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(plainText.getBytes());
			//System.out.println("encrypted string: "	+ Base64Utils.encodeToString(encrypted));

			return new String((new Base64()).encode(encrypted));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	
	/**
	 * @see	kr.co.ucsit.fwk.utl.FwkCrypto.decrypt(String, String, String)	
	 */
	public static String decrypt(String cipherText) {
		return decrypt(cipherText, SECRET_KEY, INIT_VECTOR);
	}
	
	/**
	 * @see	kr.co.ucsit.fwk.utl.FwkCrypto.decrypt(String, String, String)	
	 */
	public static String decrypt(String cipherText, String secretKey) {
		return decrypt(cipherText, secretKey, INIT_VECTOR);
	}
	

	/**
	 * AES 복호화
	 * @param cipherText	암호화된 텍스트
	 * @param secretKey	키
	 * @param initVector	init vector
	 * @return
	 */
	public static String decrypt(String cipherText, String secretKey, String initVector) {
		if(CsUtil.isEmpty(cipherText)) {
			return "";
		}
		
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal( (new Base64()).decode(cipherText.getBytes()));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	
	/**
	 * AES 암호화
	 * @param paramMap CsMap
	 * @param keys CsMap의 키 목록
	 * @return
	 */
	public static CsMap encrypt(CsMap paramMap, String...keys	) {
		if(null == paramMap || null == keys) {
			return null;
		}
		
		for(String k : keys) {
			paramMap.put(k + "Enc", CsCrypto.encrypt(paramMap.getString(k)));
		}
		
		return paramMap;
	}

	/**
	 * AES 복호화
	 * @param datas 데이터
	 * @param keys 맵의 키 목록
	 * @return
	 */
	public static List<Map<String,Object>> decrypt(List<Map<String, Object>> datas, String...keys) {
		if(null == datas || null == keys) {
			return null;
		}
		
		for(Map<String,Object> data : datas) {
			decrypt(data, keys);
		}
		
		//
		return datas;
	}
	
	/**
	 * AES 복호화 
	 * @param data 데이터
	 * @param keys 맵의 키 목록
	 */
	public static Map<String,Object> decrypt(Map<String, Object> data, String...keys) {
		if(null == data || null == keys) {
			return null;
		}
		
		for(String k : keys) {
			if(data.containsKey(k)) {
				data.put(k + "_DEC", CsCrypto.decrypt(""+data.get(k)));
				
			}else {
				data.put(k+"_DEC", "");
			}
		}
		
		return data;
	}
}
