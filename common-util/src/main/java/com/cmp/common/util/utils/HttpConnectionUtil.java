package com.cmp.common.util.utils;

import com.cmp.common.util.exception.GeneralException;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @CLassName HttpConnectionUtil
 * @Description: TODO
 * @Author: shenhao
 * @date: 2019/12/11 10:28
 * @Version 1.0
 */
public class HttpConnectionUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpConnectionUtil.class);

	public static String sendHttpPostEntity(String sendUrl, String content, Map<String, Object> reqParam) throws GeneralException {
		log.info("****sendHttpPostEntity sendurl=" + sendUrl);
		return doHttpPostInter(sendUrl, null, content, false, Integer.parseInt((String) reqParam.get("timout")));
	}

	public static String sendRequest(String url, Map<String, Object> params, Map<String, Object> reqParam) throws GeneralException {
		String responseXml;
		int timeOut = Integer.parseInt((String) reqParam.get("timeOut"));
		responseXml = sendHttpNameValuePair(url, params, timeOut);
		return responseXml;
	}

	private static String sendHttpNameValuePair(String sendUrl, Map<String, Object> params, int timeOut) throws GeneralException {
		List<NameValuePair> list = new ArrayList<>();
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair data = new NameValuePair(entry.getKey(), (String) entry.getValue());
				list.add(data);
			}
		}
		NameValuePair[] datas = list.toArray(new NameValuePair[]{});
		//需要大于inter工程的超时时间，以免出现断开管道
		return doHttpPostInter(sendUrl, datas, null, true, timeOut);
	}

	private static String doHttpPostInter(String sendUrl, NameValuePair[] data, String sContent, boolean isNameValuePair, int timeOut) throws GeneralException {
		String str = null;
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
			httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
			HostConfiguration hostConfiguration = new HostConfiguration();
			if (timeOut > 0) {
				HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
				//设置连接超时时间(单位毫秒)
				managerParams.setConnectionTimeout(timeOut * 1000);
				managerParams.setSoTimeout(timeOut * 1000);
			}
			hostConfiguration.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_0);
			PostMethod httpPost = new PostMethod(sendUrl);
			if (isNameValuePair) {
				httpPost.setRequestBody(data);
			} else {
				httpPost.setRequestHeader("Content_Type", "application/json");
				httpPost.setRequestEntity(new InputStreamRequestEntity(new ByteArrayInputStream(sContent.getBytes())));
			}
			try {
				httpClient.executeMethod(httpPost);
				if(httpPost.getStatusCode()== HttpStatus.SC_OK){
					str=httpPost.getResponseBodyAsString();
				}else{
					log.error("请求返回状态"+httpPost.getStatusCode()+"返回结果"+httpPost.getResponseBodyAsString());
					throw new GeneralException("Unexpected failure:"+httpPost.getStatusLine().toString());
				}
			} catch (Exception e) {
				String sMsg="Http协议Post方法发送字符流是出现异常"+e.getMessage();
				log.error(sMsg,e);
				throw new GeneralException("Unexpected failure"+httpPost.getStatusLine().toString());
			}finally{
				httpPost.releaseConnection();
			}

		} catch (Exception e) {
			log.error("****接口调用失败：",e);
//			if(e instanceof java.net.SocketTimeOutException){
//				str="30002";
//			}
			throw new GeneralException("SEND2001",e);

		}finally{
			log.info("end post request content to"+sendUrl);
		}
		return str;
	}
}
