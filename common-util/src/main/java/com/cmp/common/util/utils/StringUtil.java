package com.cmp.common.util.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @CLassName StringUtil
 * @Description: 字符串工具类
 * @Author: shenhao
 * @date: 2019/12/5 13:45
 * @Version 1.0
 */
public final class StringUtil extends org.apache.commons.lang.StringUtils {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	/*
	 *private Constructor
	 **/
	private StringUtil() {
	}

	/**
	 * @return java.lang.String
	 * @Description 生成UUID
	 * @Param []
	 **/
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * @return int
	 * @Description int字符串装int
	 * @Param [instr]
	 **/
	public static int toInt(String instr) {
		try {
			return Integer.parseInt(instr);
		} catch (Exception e) {
			logger.error("String to int error", e);
			return 0;
		}
	}

	/**
	 * @return boolean
	 * @Description 判断字符串是否非null && 非空字符
	 * @Param [param]
	 **/
	public static boolean inNotEmpty(String param) {
		return param != null && !"".equals(param.trim());
	}

	/**
	 * @return
	 * @Description 判断字符串是否为null || 空字符串
	 * @Param
	 **/
	public static boolean isEmpty(String param) {
		return null == param || "".equals(param.trim());
	}

	/**
	 * 判断字符串是否为null ||空字符串 || null字符串
	 */
	public static boolean isBlank(String param) {
		return StringUtils.isBlank(param) || StringUtils.equalsIgnoreCase("null", param);
	}

	/**
	 * @return
	 * @Description 判断字符串是否为非null && 非空字符串 && 非null字符串
	 * @Param
	 **/
	public static boolean isNotBlank(String param) {
		return param != null && !"".equals(param.trim()) && !"null".equalsIgnoreCase(param.trim());
	}

	/**
	 * @return
	 * @Description null转空字符串
	 * @Param
	 **/
	public static String nullToBlank(String param) {
		return null == param ? "" : param;
	}

	/**
	 * @return
	 * @Description 判断是否为数字
	 * @Param
	 **/
	public static boolean inNum(String str) {
		String regex = "^(-?\\d+)(\\.\\d+)?$";
		return matchRegex(str, regex);
	}

	public static String trim2Empty(String str) {
		return isEmpty(str) ? "" : str.trim();
	}

	public static boolean matchRegex(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	/**
	 * @return
	 * @Description 字符串字符转换
	 * @Param
	 **/
	public static String convertCharacter(String str, String encoding, String toEncoding) throws UnsupportedEncodingException {
		return new String(str.getBytes(encoding), toEncoding);
	}

	/**
	 * @return
	 * @Description 判断逗号分隔的字符串是否包含特定数字字符
	 * @Param
	 **/
	public static boolean dealStr(String str, String subStr) {
		boolean flag = false;
		String[] array = str.split(",");
		for (String element : array) {
			if (element.equals(subStr)) {
				flag = true;
			}
		}
		return flag;
	}
	/**
	 * @Description 岗位类型特殊处理
	 * @Param
	 * @return
	 **/
	public static String getPostType(String postType){
		if(postType.length()<1){
			return postType;
		}
		String postTypeStr="";
		String [] postTypeArray=postType.split(",");
		for (String element :postTypeArray){
			if("03".equals(element))
			{
				postTypeStr="03";
				break;
			}
		}
		return postTypeStr;
	}
	/**
	 * @Description 去除字符串数组中的重复项
	 * @Param 
	 * @return 
	 **/
	public static String removerepeatedchar(String value){
		if(StringUtil.isEmpty(value)){
			return value;
		}
		String[] str=value.split(",");
		//将字符串数组转换为字符串list
		List<String> stringList= Arrays.asList(str);
		//将字符串list装化为hashset,利用hashSet无重复元素的特性解决问题
		Set<String> strSet=new HashSet<>(stringList);
		return StringUtils.join(strSet,",");
	}
	
	/**
	 * @Description 手机号验证
	 * @Param 
	 * @return 验证通过返回true
	 **/
	public static boolean isMobile(String str){
		Pattern p;
		Matcher m;
		boolean b=false;
		if(StringUtil.isEmpty(str)){
			return b;
		}
		p=Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");//验证手机号
		m=p.matcher(str);
		b=m.matches();
		return b;
	}
/**
 * 输出身份证前6位后4中间***星号
 */
public static String  dealIdCard(String idCard){
	String first=idCard.substring(0,6);
	String last=idCard.substring(idCard.length()-4,idCard.length());
	StringBuilder sb=new StringBuilder();
	sb.append(first);
	sb.append(last);
	int d=idCard.length()-sb.length();
	sb.delete(sb.length()-4,sb.length());
	for(int i=0;i<d;i++)
	{
		sb.append("*");
	}
	sb.append(last);
	return sb.toString();
}
	/**
	 * 在线渠道编码自增1后前面自动补0
	 */
	public static String frontCompWithZore(String currentNum){
		AtomicInteger num=new AtomicInteger(Integer.parseInt(currentNum));
		return String.format("%07d",num.incrementAndGet());
	}
	public static String getAndCheckNullFromMap(Map<String,String>params,String keyStr,String errMsg)throws Exception{
		if(StringUtils.isEmpty(params.get(keyStr))){
			//throw new Exception("8888",errMsg);
			throw new Exception();
		}
		return params.get(keyStr);
	}
	/**
	 * @Description 字符集
	 * @Param
	 * @return
	 **/
	public interface ENCODING{
		String UTF_8="UTF-8";
		String GBK="GBK";
		String ISO_8858_1="ISO-8859-1";
		String GB2312="GB2312";
	}
	/**
	 * 判断字符串在开头是否以指定字符串开头
	 */
	public static boolean startWith(String str1,String str2){
		if(str1==null||str2==null){
			return false;
		}if(str1.length()<str2.length())
		{
			return false;
		}for(int Index=0;Index<str2.length();Index++){
			if(str1.charAt(Index)!=str2.charAt(Index)){
				return false;
			}
		}
		return true;
	}
	/**
	 * @Description 判断字符串是否是json格式
	 * @Param
	 * @return
	 **/
	public static boolean isJson(String str){
		if(StringUtils.isEmpty(str)){
			return false;
		}else{
			boolean isObj=str.charAt(0)=='{'&& str.charAt(str.length()-1)=='}';
			if(!isObj)
				return str.charAt(0)=='['&& str.charAt(str.length()-1)==']';
			return true;
		}
	}

}
