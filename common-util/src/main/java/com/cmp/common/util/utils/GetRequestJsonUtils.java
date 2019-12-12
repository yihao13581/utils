package com.cmp.common.util.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @CLassName GetRequestJsonUtils
 * @Description: 通过httpServlet获取Json格式的参数
 * @Author: shenhao
 * @date: 2019/12/10 11:24
 * @Version 1.0
 */
public class GetRequestJsonUtils {
	public static JSONObject getRequestJsonObject(HttpServletRequest request) throws IOException{
		String json=getRequestJsonObjectString(request);
		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 获取request中json 字符串的内容
	 * @Param [request]
	 * @return java.lang.String
	 **/
	public static String getRequestJsonObjectString(HttpServletRequest request)throws IOException{
		String submitMethod=request.getMethod();
		//GET
		if(StringUtils.equals("GET",submitMethod)){
			return new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22","\"");
		}//post
		else{
			return getRequestPostStr(request);
		}
	}

	/**
	 * @Description 获取post请求的byte【】 数组
	 * @Param
	 * @return
	 **/
	public static byte[] getRequestPostBytes(HttpServletRequest request)
		throws IOException{
		int  contentLength=request.getContentLength();
		if(contentLength<0){
			return null;
		}
		byte[] buffer=new  byte[contentLength];
		for(int i=0;i<contentLength;i++){
			int readlen=request.getInputStream().read(buffer,i,contentLength-i);
			if(readlen==-1){
				break;
			}
			i+=readlen;
		}
		return buffer;
	}

	/**
	 * @Description 获取Post请求内容
	 * @Param
	 * @return
	 **/
	public static String getRequestPostStr(HttpServletRequest request) throws IOException{
		byte[] buffer=getRequestPostBytes(request);
		String charEncoding=request.getCharacterEncoding();
		if(charEncoding==null)
		{
			charEncoding="UTF-8";

		}return  new String(buffer,charEncoding);
	}
}
