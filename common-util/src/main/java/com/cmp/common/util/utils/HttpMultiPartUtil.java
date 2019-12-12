package com.cmp.common.util.utils;

import com.cmp.common.util.exception.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * @CLassName HttpMultiPartUtil
 * @Description: TODO
 * @Author: shenhao
 * @date: 2019/12/10 17:28
 * @Version 1.0
 */
public class HttpMultiPartUtil {
	public final static Logger logger = LoggerFactory.getLogger(HttpMultiPartUtil.class);
	//http连接超时的时间
	private static String connection_timeout = "30";
	//从响应中读取数据超时的时间
	private static String so_timeout = "30";
	private static String charset = "UTF-8";
	private static String method = "POST";
	private static boolean ismultipart = false;

	private static ThreadLocal<String> conTimeOut = new ThreadLocal<>();//连接超时时间
	private static ThreadLocal<String> soTimeOut = new ThreadLocal<>();//读取超时时间
	private static ThreadLocal<String> session = new ThreadLocal<>();//保存session

	public static String post(String urlStr, Map<String, String> textMap,
							  Map<String, String> fileMap) throws GeneralException {
		OutputStream out = null;
		InputStream input = null;
		ByteArrayOutputStream baos = null;
		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "WEBKitFromBoundSmrz";//boundary就是request头和上传文件内容的分隔符
		if (fileMap != null && fileMap.size() > 0) {
			ismultipart = true;
		} else {
			ismultipart = false;
		}
		try {
			URL url=new URL(urlStr);
			conn=(HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(Integer.valueOf(getConnection_timeout())*1000);
			conn.setReadTimeout(Integer.valueOf(getSo_timeout())*1000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Connection","Keep-Alive");
			conn.setRequestProperty("Accept_Charset",charset);
			conn.setRequestProperty("User-Agent","Mozilla/5.0(Windows NT 6.1;zh_CH;rv:1.9.2.6)");
			if(ismultipart){
				conn.setRequestProperty("Content_Type","Multipart/from-data;boundary="+BOUNDARY);
			}else{
				conn.setRequestProperty("Content_Type","application/x-www-form-urlencoded;charset="+charset);
			}
			out=new DataOutputStream(conn.getOutputStream());
			//text
			if(textMap!=null)
			{
				StringBuilder strBuf=new StringBuilder();
				for(Map.Entry<String,String> entry:textMap.entrySet()){
					String inputName=entry.getKey();
					String inputValue=entry.getValue();
					if(inputValue==null){
						continue;
					}if(ismultipart){
						strBuf.append("\r\n").append("--").append(BOUNDARY)
								.append("\r\n");
						strBuf.append("Content-Disposition:form-date;name=\""+inputName+"\"\r\n\r\n");
						strBuf.append(inputValue);
					}else{
						strBuf.append(inputName).append("=").append(inputValue).append("&");
					}
				}
				String param=strBuf.toString();
				if(param.endsWith("&"))
				{
					param=param.substring(0,strBuf.length()-1);
				}
				out.write(param.getBytes(charset));
			}
			//file
			if(fileMap!=null){
				for(Map.Entry<String,String>entry:fileMap.entrySet()){
					String inputName=entry.getKey();
					String inputValue=entry.getValue();
					if(inputValue==null){
						continue;
					}
					String filename=inputName;
					String contentType="application/octet-stream";
					StringBuilder strBuf=new StringBuilder();
					strBuf.append("\r\n").append("--").append(BOUNDARY)
							.append("\r\n");
					strBuf.append("Content-Disposition:form-date;name=\""+inputName+"\";filename=\""+filename+"\"\r\n");
					strBuf.append("Content-Type:"+contentType+"\r\n\r\n");
					out.write(strBuf.toString().getBytes(charset));
					out.write(inputValue.getBytes("ISO8859-1"));
					strBuf.append(inputValue);
				}
			}
			if(ismultipart){
				String endData="\r\n--"+BOUNDARY+"--\r\n";
				out.write(endData.getBytes());
				out.flush();
				out.close();
			}
			input=conn.getInputStream();
			baos=new ByteArrayOutputStream();
			byte[] data=new byte[1024];
			int len=0;
			while((len=input.read(data))!=-1){
				baos.write(data,0,len);
			}
			res=new String(baos.toByteArray(),"ISO8859-1");
			//log.info("res:”+res)

		} catch (Exception e) {
			try{
				if(ismultipart&&out!=null){
					String endData="\r\n--"+BOUNDARY+"--\r\n";
					out.write(endData.getBytes());
					out.flush();
					out.close();
				}
			}catch (Exception e2){
				logger.debug(e2.getMessage(),e2);

			}
			logger.error("发送<"+urlStr+">请求出错。"+e);
			throw new GeneralException("",e.getMessage(),e);
		}finally {
			try{
				if(baos!=null){
					baos.close();}
				if(input!=null){
					input.close();
				}if(out!=null){
					out.close();
				}
			}catch(Exception e2){
				logger.error("send post error",e2);
			}
			if(conn!=null){
				conn.disconnect();
				//conn=null;
			}

		}
		return res;
	}

	private static String getSo_timeout() {
		String strSoTimeout=soTimeOut.get();
		if (strSoTimeout == null) {
			strSoTimeout=so_timeout;
		}return strSoTimeout;
	}

	private static String getConnection_timeout() {
		String strConTimeOut=conTimeOut.get();
		if(strConTimeOut==null);
		{
			strConTimeOut=connection_timeout;
		}return strConTimeOut;
	}


}
