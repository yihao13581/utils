package com.cmp.common.util.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @CLassName AESUtil
 * @Description: AES加密工具类
 * @Author: shenhao
 * @date: 2019/12/4 15:23
 * @Version 1.0
 */

public class AESUtil {
	private static final String KEY_ALGORITHM = "ASE";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

	/**
	 * @return java.lang.String 返回Base64转码后的加密数据
	 * @Description AES加密操作
	 * @Param content, 代加密内容
	 * @Param password  加密密码
	 **/
	public static String encrypt(String content, String password) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);//创建密码器

			byte[] byteContent = content.getBytes("utf-8");

			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));//初始化为加密模式的密码器

			byte[] result = cipher.doFinal(byteContent);//加密
			return Base64.encode(result);//通过Base64转码返回
		} catch (Exception e) {
			Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return
	 * @Description AES解密操作
	 * @Param
	 **/
	public static String decrypt(String content, String password) {
		try {
			//实例化
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			//使用密钥初始化，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
			//执行操作
			byte[] result=cipher.doFinal(Base64.decode(content));
			return new String(result,"utf-8");

		} catch (Exception e) {
			Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE,null,e);
		}
		return null;
	}

	private static SecretKey getSecretKey(final String password) {
		//返回生成的指定算法密钥生成器的KeyGenerator对象
		KeyGenerator kg=null;
		try {
			kg=KeyGenerator.getInstance(KEY_ALGORITHM);

			SecureRandom secureRandom =SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());

			//AES要求密钥长度为128
			kg.init(128,secureRandom);

			//生成一个密钥
			SecretKey secretKey=kg.generateKey();

			return new SecretKeySpec(secretKey.getEncoded(),KEY_ALGORITHM);//转为AES专用密钥



		} catch (NoSuchAlgorithmException e) {
			Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE,null,e);
		}
		return null;
	}
}
