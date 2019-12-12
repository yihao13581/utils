package com.cmp.common.util.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @CLassName ConvertUtil
 * @Description: Convert(转换)管理工具类
 * @Author: shenhao
 * @date: 2019/12/5 9:54
 * @Version 1.0
 */
public final class ConvertUtil {
	private static Logger logger = LoggerFactory.getLogger(ConvertUtil.class);
	private static final char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
			'd', 'e', 'f'};

	public static List<Map<String, String>> convertSqlMap2JavaMap(List<Map<String, Object>> list) {
		List beans = new ArrayList();
		try {
			for (Map map : list) {
				Map bean = new HashMap();
				Iterator iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					if (val == null) {
						bean.put(key.toString(), "");
					} else if ((val instanceof Date)) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						val = dateFormat.format(val);
						bean.put(key.toString(), val.toString());
					} else if ((val instanceof Clob)) {
						try {
							val = clobToString((Clob) val);
							bean.put(key.toString(), val.toString());
						} catch (Exception e) {
							logger.error("ConvertUtil convertSqlMap2JavaMap" + "clobToString", e);
						}
					} else if ((val instanceof Blob)) {
						try {
							val = blobToString((Blob) val);
							bean.put(key.toString(), val.toString());
						} catch (Exception e) {
							logger.error("ConvertUtil convertSqlMap2JavaMap" + "blobToString", e);
						}
					} else if ((val.getClass() == Byte.class)) {
						try {
							val = new String((byte[]) val, "utf-8");
							bean.put(key.toString(), val.toString());
						} catch (UnsupportedEncodingException e) {
							logger.error("ConvertUtil convertSqlMap2JavaMap" + "byte[]ToString", e);
						}
					} else if ((val instanceof Byte)) {
						val = val.toString();
						bean.put(key.toString(), val.toString());
					} else {
						val = String.valueOf(val);
						bean.put(key.toString(), val.toString());
					}
				}
				beans.add(bean);
			}
		} catch (Exception e) {
			logger.error("ConvertUtil convertSqlMap2JavaMap", e);
		}
		return beans;
	}

	public static String blobToString(Blob blob) throws SQLException {
		byte[] returnValue = blob.getBytes(1L, (int) blob.length());
		return bytes2String(returnValue);
	}

	public static String clobToString(Clob val) {
		if (val == null) {
			return null;
		}
		Reader reader = null;
		StringBuilder buf = new StringBuilder();
		BufferedReader bfReader = null;
		try {
			reader = val.getCharacterStream();
			bfReader = new BufferedReader(reader);
			String s = bfReader.readLine();
			while (s != null) {
				buf.append(s);
				s = bfReader.readLine();
			}
		} catch (Exception e) {
			logger.error("clobToString error", e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (bfReader != null) {
					bfReader.close();
				}
			} catch (IOException e) {
				logger.error("clobToString error", e);
			}
		}
		return buf.toString();
	}

	public static String bytes2String(byte[] bytes) {
		StringBuilder builder = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			builder.append(HEXCHAR[((bytes[i] & 0xF0) >>> 4)]);
			builder.append(HEXCHAR[(bytes[i] & 0xF)]);
		}
		return builder.toString();
	}

	public static byte[] string2Bytes(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

	public static String inputStream2String(InputStream inputStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc;
		while ((rc = inputStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in_b = swapStream.toByteArray();
		return bytes2String(in_b);
	}

//	public static String toIoString(String inputStr) {
//		String picstr = inputStr;
//		try {
//			//base64转 普通字符串
//			Base64 decoder=new Base64();
//			byte[] bt=decoder.decode(picstr);
//			InputStream is=new ByteArrayInputStream(bt);
//			picstr= IOUtils.toString(is,"ISO8859-1");
//		} catch (Exception e) {
//			logger.error("ConvertUtil teIoString");
//		}
//		return picstr;
//	}
	public static String ConvertGenger(String genger){
		if(StringUtil.isBlank(genger)){
			return "";
		}return ("男".equals(genger)||"1".equals(genger))?"1":"0";
	}
}
