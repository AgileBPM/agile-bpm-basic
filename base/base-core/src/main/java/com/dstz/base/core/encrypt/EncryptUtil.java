package com.dstz.base.core.encrypt;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.dstz.base.api.exception.BusinessError;

/**
 * 加密算法。 <br/>
 * 2.SHA-256 <br/>
 */
public class EncryptUtil {
	private static final String CODE = "UTF-8";
 

	/**
	 * 输出明文按sha-256加密后的密文
	 *
	 * @param inputStr
	 *            明文
	 * @return
	 */
	public static synchronized String encryptSha256(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte digest[] = md.digest(inputStr.getBytes(CODE));
			return new String(Base64.encodeBase64(digest));
		} catch (Exception e) {
			throw new BusinessError(e);
		}
	}

	/**
	 * 密钥
	 */
	private static final String KEY = "@#$%^6a7";

	/**
	 * 对称解密算法
	 *
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String message) {
		return aesDecrypt(message, KEY);
	}

	/**
	 * 对称加密算法
	 *
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String message) {
		return aesEncrypt(message, KEY);
	}

	  /**
     * aes解密-128位
     */
    public static String aesDecrypt(String encryptContent, String password) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(hex2Bytes(encryptContent)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * aes加密-128位
     */
    public static String aesEncrypt(String content, String password) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return byte2Hex(cipher.doFinal(content.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将byte[] 转换成字符串
     */
    public static String byte2Hex(byte[] srcBytes) {
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : srcBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSB.toString();
    }

    /**
     * 将16进制字符串转为转换成字符串
     */
    public static byte[] hex2Bytes(String source) {
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

}
