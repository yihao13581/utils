package com.cmp.common.util.utils;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import java.security.MessageDigest;

/**
 * @CLassName Md5Util
 * @Description: TODO
 * @Author: shenhao
 * @date: 2019/12/12 10:39
 * @Version 1.0
 */
public class Md5Util {
	private static final String CHAR_SET = "UTF-8";
	private static Log log = LogFactory.getLog(Md5Util.class);
	private static final String DEFAULT_SALT = "";
	//private static final String DEFAULT_SALT="isc";

	/**
	 * @return
	 * @Description md5加密
	 * @Param content 加密内容
	 **/
	public static String getMd5(String content) {
		return getMd5(content, "");
	}

	/**
	 * @return
	 * @Description 使用指定盐MD5加密
	 * @Param inContent 加密内容
	 * @Param salt 盐
	 **/
	public static String getMd5(String inContent, String salt) {
		StringBuilder hexStrSb = new StringBuilder();
		MessageDigest md;
		String content = inContent;
		//如果为空不加密直接返回
		if (content == null || content.trim().length() == 0) {
			return content;
		}
		if (salt != null && salt.trim().length() > 0) {
			content = content.concat(salt);
		} else {
			content = content.concat(DEFAULT_SALT);
		}
		try {
			md=MessageDigest.getInstance("MD5");
			byte[] hash=md.digest(content.getBytes(CHAR_SET));
			for(int i=0;i<hash.length;i++)
			{
				if((0xff&hash[i])<0x10){
					hexStrSb.append("0"+Integer.toHexString((0xFF&hash[i])));
				}else{
					hexStrSb.append(Integer.toHexString(0xFF&hash[i]));
				}
			}
			return hexStrSb.toString();
		} catch (Exception e) {
			log.error("md5加密异常：",e);
		}
		return null;

	}
}
