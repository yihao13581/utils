package com.cmp.common.util.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.java2d.pipe.SpanShapeRenderer;
import sun.util.resources.cldr.en.CalendarData_en_Dsrt_US;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @CLassName DateUtil
 * @Description: 日期工具类
 * @Author: shenhao
 * @date: 2019/12/9 14:47
 * @Version 1.0
 */
public final class DateUtil {
	/**
	 * 日期格式
	 **/
	public interface DATE_PATTERN {
		String HHMMSS = "HHmmss";
		String HH_MM_SS = "HH:mm:ss";
		String YYYYMMDD = "yyyyMMdd";
		String YYYY_MM_DD = "yyyy-MM-dd";
		String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
		String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
		String YNR = "yyyy年MM月dd日";
	}

	private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String YYYYMMDD_FORMAT = "yyyyMMdd";
	private static final String YYYYMM_FORMAT = "yyyyMM";
	private static final String YYYYMMDD = "yyyy年MM月dd日 HH分mm秒";
	private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	private static TimeZone tz = null;

	static {
		tz = TimeZone.getTimeZone("GMT+8");
		TimeZone.setDefault(tz);//设置时区
	}

	/**
	 * @return java.util.Date
	 * @Description 将字符串转换成日期
	 * @Param [date]
	 **/
	public static Date formatDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("将字符串转换成日期出错" + date, e);

		}
		return new Date();

	}

	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * @return
	 * @Description 获取当前时间
	 * @Param
	 **/
	public static Timestamp getCurrentTime() {
		Timestamp sqlTimestamp = new Timestamp(new Date().getTime());
		return sqlTimestamp;
	}

	/**
	 * 将字符串转换成日期
	 *
	 * @param date    日期字符串
	 * @param pattern 格式
	 * @return
	 */
	public static Date formatDate(String date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("将字符串转换成日期出错！>>" + date, e);
		}
		return new Date();
	}

	/**
	 * @return java.lang.String
	 * @Description 将日期按照特定格式转换成字符串
	 * @Param [date, pattern]
	 **/
	public static String formatString(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		return dateFormat.format(date);
	}

	/**
	 * @return java.lang.String
	 * @Description 将日期按照特定格式转换成字符串
	 * @Param [date]
	 **/
	public static String formatString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
		return dateFormat.format(date);
	}

	/**
	 * 将日期字符串dateStr从sourcePattern格式转换成pattern格式
	 *
	 * @param dateStr
	 * @param sourcePattern
	 * @param pattern
	 * @return
	 */
	public static String formatString(String dateStr, String sourcePattern, String pattern) {
		Date date = formatDate(dateStr, sourcePattern);
		return formatString(date, pattern);
	}

	/**
	 * @return yyyyMMdd
	 * @Description 获取去年今天
	 * @Param []
	 **/
	public static String getPreviousYear() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(YYYYMMDD_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * @return [{04月=201204}，{03月=201203}]
	 * @Description 获取date之前月份
	 * @Param date 时间
	 * @Param count 月份个数
	 **/
	public static Map<String, String> getPreviousMonths(Date date, int count) {
		Map<String, String> monthMap = new TreeMap<>(new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				if (o1 == null || o2 == null) {
					return 0;
				}
				return String.valueOf(o2).compareTo(String.valueOf(o1));
			}
		});
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < count; i++) {
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - i);
			monthMap.put(formatString(calendar.getTime(), "yyyy") + "年" +
							formatString(calendar.getTime(), "MM") + "月",
					formatString(calendar.getTime(), "yyyyMM"));
		}
		return monthMap;
	}

	/**
	 * @return java.lang.String
	 * @Description 获取date之前N个月的时间
	 * @Param date 时间
	 * @Param count  月份个数
	 **/
	public static String getPreviousDates(Date date, int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - count);
		return formatString(calendar.getTime(), "yyyy-MM-dd");
	}

	/**
	 * @return java.util.Date
	 * @Description 在date基础上加addMonth个月
	 * @Param [date, addMonth]
	 **/
	public static Date addMonthes(Date date, int addMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + addMonth);
		return calendar.getTime();
	}

	/**
	 * @return java.util.Date
	 * @Description 获取date日期对应月的最后一天
	 * @Param [date]
	 **/

	public static Date getLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * @return java.lang.String
	 * @Description 获取下个月的1号
	 * @Param [date]
	 **/
	public static String getNextOne(Date date) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(c.getTime());
		return dateString;
	}

	public static int compareDate(String startDay, String endDay, int stype) {
		int n = 0;
		String[] u = {"天", "月", "年"};
		String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM--dd";
		String newEndDay = endDay;
		newEndDay = endDay == null ? getCurrentDate("yyyy-MM-dd") : newEndDay;

		DateFormat df = new SimpleDateFormat(formatStyle);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(startDay));
			c2.setTime(df.parse(newEndDay));
		} catch (Exception e3) {
			logger.error("wrong occurred", e3);
		}
		while (!c1.after(c2)) {//循环对比，直到相等，n就是所要的结果
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1);//比较月份，月份+1
			} else {
				c1.add(Calendar.DATE, 1);//比较天数，日期+1
			}
		}
		n = n - 1;
		if (stype == 2) {
			n = (int) n / 365;
		}
		logger.info(startDay + "--" + newEndDay + "相差多少" + u[stype] + ":" + n);
		return n;
	}

	public static String getCurrentDate(String format) {
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, 0);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String date = sdf.format(day.getTime());
		return date;
	}

//	//a必须大于b
//	public static int getMonthNum(String a, String b) {
//		Date aa = formatDate(a, "yyyy-MM-dd");
//		Date bb = formatDate(b, "yyyy-MM-dd");
//		Calendar calendar1 = Calendar.getInstance();
//		calendar1.setTime(aa);
//		int month1 = calendar1.get(Calendar.MONTH);
//		int year1 = calendar1.get(Calendar.YEAR);
//		Calendar calendar2 = Calendar.getInstance();
//		calendar1.setTime(bb);
//		int month2 = calendar2.get(Calendar.MONTH);
//		int year2 = calendar2.get(Calendar.YEAR);
//		int reNum = 0;
//		if (year1 == year2) {
//			reNum = Math.abs(month1 - month2) + 1;
//		} else if (year1 > year2) {
//			if (month1 - month2 > 0) {
//				reNum = (year1 - year2) * 12 + (month1 - month2) + 1;
//			} else {
//				reNum = (year1 - year2) * 12 + (month1 - month2) + 1;
//			}
//
//		} else {
//
//		}
//		return reNum;
//	}

	/**
	 * @return java.lang.String
	 * @Description 获取这个月的1号
	 * @Param [date]
	 **/
	public static String getOne(Date date) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(c.getTime());
		return dateString;
	}

	/**
	 * @return java.lang.String
	 * @Description 对输入的日期进行格式化，如果输入的日期是null则返回空窜
	 * @Param [dtDate, strFormatTo]
	 **/
	public static String getFormattedDate(java.sql.Timestamp dtDate, String strFormatTo) {
		if (dtDate == null) {
			return "";
		}
		if (dtDate.equals(new java.sql.Timestamp(0))) {
			return "";
		}
		String newStrFormateTo = strFormatTo;
		newStrFormateTo = newStrFormateTo.replace('/', '-');
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy");
			if (Integer.parseInt(format.format(dtDate)) < 1900) {
				return "";
			} else {
				format = new SimpleDateFormat(newStrFormateTo);
				return format.format(dtDate);
			}
		} catch (Exception e) {
			logger.error("转换日期字符串格式时出错" + e.getMessage(), e);
			return "";
		}
	}

	/**
	 * @return java.util.Date
	 * @Description 增加当前时间的若干个小时
	 * @Param [date, hours]
	 **/
	public static Date addHours(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}

	public static String date2TimeStamp(String date_str) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
			return String.valueOf(sdf.parse(date_str).getTime() / 1000);
		} catch (Exception e) {
			logger.error("时间转换失败", e);
		}
		return "";
	}
public static String date2String(Date date,String pattern)
{
	if(date==null||pattern==null){
		return null;
	}return new SimpleDateFormat(pattern).format(date);
}

}

