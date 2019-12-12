package com.cmp.common.util.utils;

import com.cmp.common.util.exception.GeneralException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import com.cmos.cache.service.ICacheService;

import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @CLassName KeysUtil
 * @Description: TODO
 * @Author: shenhao
 * @date: 2019/12/12 9:50
 * @Version 1.0
 */
public class KeysUtil {
	private static final Logger logger = LoggerFactory.getLogger(KeysUtil.class);
	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	/**
	 * @return
	 * @Description 主键生成策略
	 * @Param tableName 需要获取主键的表名
	 **/
//	public static String getSequence(ICacheService cacheService, String tableName) throws ValidationException {
//		if (StringUtil.isEmpty(tableName)) {
//			throw new ValidationException("获取主键时前缀不能为空！建议传入表名");
//		}
//		String redisKey = "REDIS_TBL_" + tableName;
//		String id = null;
//		try {
//			logger.info("开始获取主键" + redisKey);
//			id = DateUtil.date2String(new Date(), DateUtil.DATE_PATTERN.YYYYMMDDHHMMSS) + ""
//					+ getSequenceOnlyIncr(redisKey, cacheService, 5);
//			logger.info("获取主键成功id" + id);
//		} catch (Exception e) {
//			id = UUID.randomUUID().toString();
//			logger.error("NOT ERROR! 主键获取成功，key=" + redisKey, e);
//		}
//		return id;
//	}
//
//	/**
//	 * @return 序列唯一标识
//	 * @Description 根据redis键值获取序列唯一标识
//	 * @Param key 缓存键值
//	 * @Param length 给定位数长度
//	 * <P><span Style="color:red;">警告：自定义长度必须大于6位！</span></P>
//	 **/
//	public static String getSequenceOnlyIncr(String key, ICacheService cacheService, int length) throws GeneralException {
//		int baseLength = 4;
//		StringBuilder baseNums = new StringBuilder();
//		String uniqueNums;
//		if (length <= baseLength) {
//			throw new GeneralException("8888", "序列长度不满足唯一标识生成需求！");
//		}
//		if (StringUtils.isEmpty(key)) {
//			throw new GeneralException("8888", "redis键值不能为空");
//		}
//		String id;
//		for (int i = 0; i < length; i++) {
//			baseNums.insert(0, 9);
//		}
//		try {
//			logger.info("开始获取主键" + "key=" + key);
//			Long redisValue = cacheService.incr(key);
//			//如果缓存Incr的键值大于基数数值则进行截取
//			if (redisValue - Long.valueOf(baseNums.toString()) > 0) {
//				logger.info("缓存Incr的键值大于基准数值" + "redisValue=" + redisValue);
//				String transValue = splits(redisValue.toString(), length);
//				id = transValue;
//			} else {
//				uniqueNums = addPosition(redisValue.toString(), length);
//				id = uniqueNums;
//			}
//		} catch (Exception e) {
//			logger.info("使用redis获取主键失败，开始使用：随机字符" + "key=" + key, e);
//			id = getRandomStringByLength(length);
//		}
//		return id;
//	}
	private static String splits(String temp,int length){
		String value=temp.substring(temp.length()-length);
		return value;
	}
	private static String addPosition(String temp,int length){
		StringBuilder value=new StringBuilder();
		for(int i=0;i<length-temp.length();i++){
			value.insert(0,"0");
		}
		value.append(temp);
		return value.toString();
	}
	/**
	 * @Description 获取一定长度的随机字符串
	 * @Param  length 指定字符串长度
	 * @return  一定长度的字符串
	 **/
	private static String getRandomStringByLength(int length){
		String base="0123456789";
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<length;i++){
			int number=random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
