package com.cmp.common.util.utils;

import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @CLassName RandomCodeUtil
 * @Description: 随机码生成
 * @Author: shenhao
 * @date: 2019/12/10 9:24
 * @Version 1.0
 */
public class RandomCodeUtil {
	private static final ThreadLocalRandom random=ThreadLocalRandom.current();

	/**
	 * @Description 生成6位随机的数字验证码
	 * @Param []
	 * @return java.lang.String
	 **/
	public static String getRandomCode(){
		//创建一个随机数生成器类
		StringBuilder randomCode=new StringBuilder();
		//设置验证码字数
		int codeCount=6;
		//设置验证码内容
		char[] codeSequence={'0','1','2','3','4','5','6','7','8','9'};
		//随机产生色codeCount数字的验证码
		for(int i=0;i<codeCount;i++){
			//得到随机产生的验证码数字
			String strRand=String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			//将产生的6个随机数字组合在一起
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}

	public static String getRandomCode(int num){
		//创建一个随机数生成器类
		StringBuilder randomCode=new StringBuilder();
		//设置验证数字
		int codeCount=num;
		char[] codeSequence={'0','1','2','3','4','5','6','7','8','9'};
		for(int i=0;i<codeCount;i++){
			//得到随机产生的验证码数字
			String strRand=String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			//将产生的codeCount个随机数字组合在一起
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}

	public static String getCurrentDateYMRHMS(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(System.currentTimeMillis());
	}
public static String getRandomCodeNew(int num){
		//创建一个随机数生成器类
	StringBuilder randomCode=new StringBuilder();
	//设置验证码字数
	int codeCount=num;
	//设置验证码内容
	char[] codeSequence={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e',
			'f','g','h','i','j','k','l','m','n','o','p',
			'q','r','s','t','u','v','w','x','y','z','A',
			'B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
			'Q','R','S','T','U','V','W','X','Y','Z'};
//随机产生codeCount数字的验证码。
	for(int i=0;i<codeCount;i++){
//得到随机产生的验证码
		String strRand=String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
		//将产生的六个随机数/字母组合起来
		randomCode.append(strRand);
	}
	return randomCode.toString();
}

	public static void main(String[] args) {
		System.out.println(getRandomCode());
	}
}
