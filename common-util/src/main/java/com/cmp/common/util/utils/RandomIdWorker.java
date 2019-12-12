package com.cmp.common.util.utils;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @CLassName RandomIdWorker
 * @Description: TODO
 * @Author: shenhao
 * @date: 2019/12/10 10:18
 * @Version 1.0
 */
public class RandomIdWorker {
	private final static ThreadLocalRandom rd = ThreadLocalRandom.current();
	private static final String INT = "0123456789";
	private static final String STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String ALL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	/**
	 * 私有构造器
	 **/
	private RandomIdWorker() {
	}

	/**
	 * @return java.lang.String
	 * @Description 生成一个A-Z,a-z之间的随机字符串，如oUdeFd
	 * @Param [length]
	 **/
	public static String randomStr(int length) {
		return random(length, RndType.STRING);
	}

	/**
	 * @return java.lang.String
	 * @Description 生成一个0-9之间的随机字符串，如1233252
	 * @Param [length]
	 **/
	public static String randomInt(int length) {
		return random(length, RndType.INT);
	}

	/**
	 * @return java.lang.String
	 * @Description 生成一个0-9，A-Z,a-z之间的随机字符串，如9scWUeSfRs
	 * @Param [length]
	 **/
	public static String randomAll(int length) {
		return random(length, RndType.ALL);
	}
    /**
     * @Description 返回指定随机数的流水号，格式为前缀+时间戳+指定位数的随机数
     * @Param
     * @return
     **/
    public static String getTransNum(String prefix,int length){
    	StringBuilder stb=new StringBuilder(prefix==null?"":prefix);
    	String date2String=DateUtil.date2String(new Date(),DateUtil.DATE_PATTERN.YYYYMMDDHHMMSS);
    	stb.append(date2String).append(randomInt(length));
    	return stb.toString();
	}

	/**
	 * @Description 返回指定随机数的流水号，格式为时间戳+指定位数的随机数
	 * @Param
	 * @return
	 **/
	public static String getTransNum(int length){
		String date2String=DateUtil.date2String(new Date(),DateUtil.DATE_PATTERN.YYYYMMDDHHMMSS);
		StringBuilder stb=new StringBuilder(date2String);
		stb.append(randomInt(length));
		return stb.toString();
	}
	private static String random(int length,RndType rndType){
		StringBuilder stb=new StringBuilder();
		char num;
		for(int i=0;i<length;i++) {
			if(rndType.equals(RndType.INT)) {
				num = INT.charAt(rd.nextInt(INT.length()));//产生数字0-9的随机数
			}
			else if(rndType.equals(RndType.STRING)){
				num=STR.charAt(rd.nextInt(STR.length()));//产生字母a-z,A-Z的随机数
			}else{
				num=ALL.charAt(rd.nextInt(ALL.length()));
			}
			stb.append(num);
		}
		return stb.toString();
	}

	public static enum RndType{
		INT,STRING,ALL
	}

	public static void main(String[] args) {
		System.out.println(RandomIdWorker.randomInt(10));
//		System.out.println(RandomIdWorker.randomStr(8));
//		System.out.println(RandomIdWorker.randomAll(6));
//		System.out.println(getTransNum(6));

	}
}
