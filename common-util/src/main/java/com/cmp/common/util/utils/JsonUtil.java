package com.cmp.common.util.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @CLassName JSonUtil
 * @Description: json转换工具类
 * @Author: shenhao
 * @date: 2019/12/9 17:26
 * @Version 1.0
 */
public final class JsonUtil {
	private static ObjectMapper objectMapper;
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static Map convertorMap = new HashMap();

	static {
		objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	private JsonUtil() {
	}

	/**
	 * 将Object对象转换为json
	 * @param object
	 * @return
	 */
	public static String convertObject2Json(Object object) {
		String method = "convertObject2Json";
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error(method,"convert json error",object.toString(),e);
		}
		return null;
	}
	public static Object convertJson2Object(String json,Class cls){
		String method="convertJson2Object";
		try{
			return objectMapper.readValue(json,cls);
		}catch(Exception e){
			logger.error(method,"convert json error",json,e);
		}return null;
	}
}
