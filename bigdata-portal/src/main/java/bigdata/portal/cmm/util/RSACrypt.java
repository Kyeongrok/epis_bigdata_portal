package bigdata.portal.cmm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSACrypt {
	private static final Logger LOGGER = LoggerFactory.getLogger(RSACrypt.class);

	private PublicKey publicKey = null;
	private PrivateKey privateKey = null;
	private Cipher cipher = null;

	public RSACrypt() throws RSACryptException {
		try {
			cipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			throw new RSACryptException(e);
		} catch (NoSuchPaddingException e) {
			throw new RSACryptException(e);
		}
	}

	/**
	 * 키페어 생성
	 */
	private void createKeyPair() throws RSACryptException {
		LOGGER.debug("Create Keypair");

		// 서버측 키 파일 생성 하기
		PublicKey publicKey = null;
		PrivateKey privateKey = null;

		SecureRandom secureRandom = new SecureRandom();
		KeyPairGenerator keyPairGenerator;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512, secureRandom);

			KeyPair keyPair = keyPairGenerator.genKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();

			KeyFactory keyFactory1 = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec rsaPublicKeySpec = keyFactory1.getKeySpec(publicKey, RSAPublicKeySpec.class);
			RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory1.getKeySpec(privateKey, RSAPrivateKeySpec.class);
			LOGGER.debug("Public  key modulus : {}", rsaPublicKeySpec.getModulus());
			LOGGER.debug("Public  key exponent: {}", rsaPublicKeySpec.getPublicExponent());
			LOGGER.debug("Private key modulus : {}", rsaPrivateKeySpec.getModulus());
			LOGGER.debug("Private key exponent: {}", rsaPrivateKeySpec.getPrivateExponent());
		} catch (NoSuchAlgorithmException e) {
			throw new RSACryptException(e);
		} catch (InvalidKeySpecException e) {
			throw new RSACryptException(e);
		}

		byte[] bPublicKey = publicKey.getEncoded();
		String sPublicKey = Base64.encodeBase64String(bPublicKey);

		byte[] bPrivateKey = privateKey.getEncoded();
		String sPrivateKey = Base64.encodeBase64String(bPrivateKey);

		LOGGER.debug("Keyfile path : {}", (new File(".")).getAbsolutePath());
		try {
			// 공개키 생성
			BufferedWriter bw1 = new BufferedWriter(new FileWriter("PublicKey.txt"));
			bw1.write(sPublicKey);
			bw1.newLine();
			bw1.close();

			// 개인키 생성
			BufferedWriter bw2 = new BufferedWriter(new FileWriter("PrivateKey.txt"));
			bw2.write(sPrivateKey);
			bw2.newLine();
			bw2.close();
		} catch (IOException e) {
			throw new RSACryptException(e);
		}
	}

	/**
	 * 공개키 가져오기
	 * 
	 * @param publilcKeyPath
	 */
	public void initPublicKey(String publilcKeyPath) throws RSACryptException {
		BufferedReader brPublicKey = null;
		String sPublicKey = null;

		try {
			brPublicKey = new BufferedReader(new FileReader(publilcKeyPath));
			sPublicKey = brPublicKey.readLine(); // First Line Read

			byte[] bPublicKey = Base64.decodeBase64(sPublicKey.getBytes());
			try {
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");

				X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bPublicKey);
				publicKey = keyFactory.generatePublic(publicKeySpec);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				throw new RSACryptException(e);
			}
		} catch (IOException e) {
			throw new RSACryptException(e);
		} finally {
			try {
				if (brPublicKey != null)
					brPublicKey.close();
			} catch (IOException e) {
				LOGGER.debug(e.getMessage());
			}
		}
	}

	/**
	 * 개인키 가져오기
	 * 
	 * @param privateKeyPath
	 */
	public void initPrivateKey(String privateKeyPath) throws RSACryptException {
		BufferedReader brPrivateKey = null;
		String sPrivateKey = null;

		try {
			brPrivateKey = new BufferedReader(new FileReader(privateKeyPath));
			sPrivateKey = brPrivateKey.readLine(); // First Line Read

			byte[] bPrivateKey = Base64.decodeBase64(sPrivateKey.getBytes());
			try {
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");

				PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bPrivateKey);
				privateKey = keyFactory.generatePrivate(privateKeySpec);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				throw new RSACryptException(e);
			}
		} catch (IOException e) {
			throw new RSACryptException(e);
		} finally {
			try {
				if (brPrivateKey != null)
					brPrivateKey.close();
			} catch (IOException e) {
				LOGGER.debug(e.getMessage());
			}
		}
	}

	/**
	 * 암호화
	 * 
	 * @param sPlain
	 * @return
	 */
	public String encrypt(String sPlain) throws RSACryptException {
		if (publicKey == null) {
			throw new RSACryptException("먼저 공개키를 설정하세요. initPublicKey()");
		}

		try {
			// 공개키 이용 암호화
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] bCipher = cipher.doFinal(sPlain.getBytes());
			String sCipherBase64 = Base64.encodeBase64String(bCipher);

			return sCipherBase64;
		} catch (InvalidKeyException e) {
			throw new RSACryptException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RSACryptException(e);
		} catch (BadPaddingException e) {
			throw new RSACryptException(e);
		}
	}

	/**
	 * 복호화
	 * 
	 * @param str
	 * @return
	 */
	public String decrypt(String sCipherBase64) throws RSACryptException {
		if (privateKey == null) {
			throw new RSACryptException("먼저 개인키를 설정하세요. initPrivateKey()");
		}

		try {
			// 개인키 이용 복호화
			byte[] bCipher = Base64.decodeBase64(sCipherBase64.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] bPlain = cipher.doFinal(bCipher);
			String sPlain = new String(bPlain);

			return sPlain;
		} catch (InvalidKeyException e) {
			throw new RSACryptException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RSACryptException(e);
		} catch (BadPaddingException e) {
			throw new RSACryptException(e);
		}
	}

	/**
	 * 암/복호화 테스트
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RSACrypt rsaCrypt = new RSACrypt();
			rsaCrypt.createKeyPair();

			String sPlain1 = "Welcome to RSA";
			LOGGER.debug("Plain : {}", sPlain1); // 원본

			rsaCrypt.initPublicKey("PublicKey.txt");
			String sCipherBase64 = rsaCrypt.encrypt(sPlain1);

			LOGGER.debug("Encrypt : {}", sCipherBase64); // 암호화

			rsaCrypt.initPrivateKey("PrivateKey.txt");
			String sPlain2 = rsaCrypt.decrypt(sCipherBase64);
			LOGGER.debug("Decrypt : {}", sPlain2); // 복호화된 평문
		} catch (RSACryptException e) {
			LOGGER.debug(e.getMessage());
		}
	}
}
