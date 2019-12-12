package com.cmp.common.util.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @CLassName Base64
 * @Description: TODO
 * @Author: shenhao
 * @date: 2019/12/4 16:21
 * @Version 1.0
 */
public class Base64 {
	private static final Logger logger = LoggerFactory.getLogger(Base64.class);
	private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRETUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	/**
	 * @return
	 * @Description data[]进行编码
	 * @Param data
	 **/
	public static String encode(byte[] data) {
		int start = 0;
		int len = data.length;
		StringBuilder buf = new StringBuilder(data.length * 3 / 2);

		int end = len - 3;
		int i = start;
		int n = 0;

		while (i <= end) {
			int d = ((((int) data[i]) & 0x0ff) << 16)
					| ((((int) data[i + 1]) & 0x0ff) << 8)
					| ((((int) data[i]) & 0x0ff));
			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append(legalChars[(d >> 6) & 63]);
			buf.append(legalChars[d & 63]);

			i += 3;
			if (n++ >= 14) {
				n = 0;
				buf.append(" ");
			}

		}
		if (i == start + len - 2) {
			int d = ((((int) data[i]) & 0x0ff) << 16)
					| ((((int) data[i + 1]) & 255) << 8);
			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append(legalChars[(d >> 6) & 63]);
			buf.append("=");
		} else if (i == start + len - 1) {
			int d = (((int) data[i]) & 0x0ff) << 16;

			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append("==");
		}
		return buf.toString();
	}


	private static int decode(char c) throws Exception {
		if (c >= 'A' && c <= 'Z')
			return ((int) c) - 65;
		if (c >= 'a' && c <= 'z')
			return ((int) c) - 97 + 26;
		else if (c >= '0' && c <= '9')
			return ((int) c) - 48 + 26 + 26;
		else
			switch (c) {
				case '+':
					return 62;
				case '/':
					return 63;
				case '=':
					return 0;
				default:
					throw new Exception("unexpected code" + c);
			}
	}

	public static byte[] decode(String s) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			decode(s, bos);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		byte[] decodeBytes = bos.toByteArray();
		try {
			bos.close();
		} catch (IOException e) {
			logger.error("Error while decoding BASE64 ：" + e.toString(), e);
		}
		return decodeBytes;
	}

	private static void decode(String s, OutputStream os) throws Exception {
		try {
			int i=0;
			int len=s.length();
			while(true){
				while (i<len&&s.charAt(i)<=' ')
					i++;
				if(i==len)
					break;
				int tri=(decode(s.charAt(i))<<18)
						+(decode(s.charAt(i+1))<<12)
						+(decode(s.charAt(i+2))<<6)
						+(decode(s.charAt(i+3)));
				os.write((tri>>16)&255);
				if(s.charAt(i+2)=='=')
					break;
				os.write((tri>>8)&255);
				if(s.charAt((i+3))=='=')
					break;
				os.write(tri&255);
				i+=4;

			}
		} catch (Exception e) {
			logger.error("",e);
			throw new Exception(e.getMessage());
		}
	}
}
