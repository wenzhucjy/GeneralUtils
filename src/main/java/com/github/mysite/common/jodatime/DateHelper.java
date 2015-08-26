package com.github.mysite.common.jodatime;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 名称: DateHelper.java<br>
 * 描述: joda-time时间工具类<br>
 * 类型: JAVA<br>
 * @since  2015年7月14日
 * @author jy.chen
 */
public class DateHelper {

	/**
	 * 当前日期与给定日期的间隔
	 * @param startDate
	 * @return
	 */
	public static Period periodBetween(Date startDate) {
		return periodBetween(startDate, new Date());
	}
	
	/**
	 * 两个日期范围间隔
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Period periodBetween(Date startDate, Date endDate) {
		DateTime start = new DateTime(startDate.getTime());
		DateTime end = new DateTime(endDate.getTime());
		return new Period(start, end, PeriodType.dayTime());
	}
	

	/**
	 * 判断当前时间是否在指定的时间范围内
	 * 
	 * @return true 是 , false 否
	 */
	public static boolean judgeTimeInterval() {
			
		Date nowDate = new Date();
			
		DateTime nDateTime = new DateTime(nowDate);
		// Joda-time
		DateTime startDateTime = new DateTime(2015, 3, 13,12, 36, 00, 0);
		DateTime endDateTime = new DateTime(2015, 3, 18, 23, 00, 00, 000);

		return nDateTime.isAfter(startDateTime) && nDateTime.isBefore(endDateTime);
	}
	

	/**
	 * 获取当前系统毫秒数
	 * 
	 * @return 当前系统毫秒数
	 * 
	 */
	public static long getCurrentTimeMillis() {
		return DateTimeUtils.currentTimeMillis();
	}

	/**
	 * 获取当前系统时间
	 * <p>
	 * ISO8601标准格式,eg: <code> Mon Dec 17 22:31:17 CST 2014</code>
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		DateTime dateTime = new DateTime();
		return dateTime.toDate();
	}

	/**
	 * 获取年份
	 * 
	 * @param date
	 *            日期
	 * 
	 * @return 年份数
	 */
	public static int getYear(Date date) {
		return new DateTime(date).getYear();
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 *            日期
	 * 
	 * @return 月份数
	 */
	public static int getMonth(Date date) {
		return new DateTime(date).getMonthOfYear();
	}

	/**
	 * 获取月份中的某天 <br>
	 * eg: 2014-10-08 -> 08
	 * 
	 * @param date
	 *            日期
	 * 
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		return new DateTime(date).getDayOfMonth();
	}



	/**
	 * 格式化时间
	 * 
	 * @param date
	 *            需要格式化的时间
	 * 
	 * @param pattern
	 *            时间格式
	 * 
	 * @return 格式化后的字符串
	 * 
	 */
	public static String formateDate(Date date, String pattern) {
		return new DateTime(date).toString(pattern);
	}

	/**
	 * 解析时间字符串
	 * 
	 * @param dateStr
	 *            时间字符串
	 * @param pattern
	 *            相应的格式
	 * 
	 * @return 解析后的Date
	 */
	public static Date parseDate(String dateStr, String pattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
		return DateTime.parse(dateStr, dateTimeFormatter).toDate();
	}
	/**
	 * 增加天数
	 * 
	 * @param date
	 *            日期
	 * @param days
	 *            天数
	 * 
	 * @return 计算之后的日期
	 */
	public static Date plusDays(Date date, int days) {
		return new DateTime(date).plusDays(days).toDate();
	}

	/**
	 * 增加星期数
	 * 
	 * @param date
	 *            日期
	 * @param weeks
	 *            星期数
	 * 
	 * @return 计算之后的日期
	 */
	public static Date plusWeeks(Date date, int weeks) {
		return new DateTime(date).plusWeeks(weeks).toDate();
	}

	/**
	 * 增加月数
	 * 
	 * @param date
	 *            日期
	 * @param months
	 *            月数
	 * 
	 * @return 计算之后的日期
	 */
	public static Date plusMonths(Date date, int months) {
		return new DateTime(date).plusMonths(months).toDate();
	}

	/**
	 * 减少天数
	 * 
	 * @param date
	 *            日期
	 * @param days
	 *            天数
	 * 
	 * @return 计算之后的日期
	 */
	public static Date minusDays(Date date, int days) {
		return new DateTime(date).minusDays(days).toDate();
	}

	/**
	 * 减少月数
	 * 
	 * @param date
	 *            日期
	 * @param months
	 *            月数
	 * 
	 * @return 计算之后的日期
	 */
	public static Date minusMonths(Date date, int months) {
		return new DateTime(date).minusMonths(months).toDate();
	}

	/**
	 * 减少年数
	 * 
	 * @param date
	 *            日期
	 * @param years
	 *            年数
	 * 
	 * @return 计算之后的日期
	 */
	public static Date minusYears(Date date, int years) {
		return new DateTime(date).minusYears(years).toDate();
	}

	/**
	 * 减少小时
	 * 
	 * @param date
	 *            日期
	 * @param hours
	 *            小时
	 * 
	 * 
	 * @return 计算之后的日期
	 */
	public static Date minusHours(Date date, int hours) {
		return new DateTime(date).minusHours(hours).toDate();
	}

	/**
	 * 减少分钟
	 * 
	 * @param date
	 *            日期
	 * @param years
	 *            分钟
	 * 
	 * @return 计算之后的日期
	 */
	public static Date minusMinutes(Date date, int minutes) {
		return new DateTime(date).minusMinutes(minutes).toDate();
	}

	/**
	 * 减少秒数
	 * 
	 * @param date
	 *            日期
	 * @param seconds
	 *            年数
	 * 
	 * @return 计算之后的日期
	 */
	public static Date minusSeconds(Date date, int seconds) {
		return new DateTime(date).minusSeconds(seconds).toDate();
	}

	/**
	 * 减少毫秒数
	 * 
	 * @param date
	 *            日期
	 * @param millis
	 *            毫秒
	 * 
	 * @return 计算之后的日期
	 */
	public static Date minusMilliseconds(Date date, int millis) {
		return new DateTime(date).minusMillis(millis).toDate();
	}

	/**
	 * 比较两个日期大小
	 * 
	 * @param date1
	 * @param date2
	 * 
	 * @return date1小于date2，返回负数<br>
	 *         date1等于date2，返回0<br>
	 *         date1大于date2，返回正数<br>
	 */
	public static int compare(Date date1, Date date2) {
		DateTime dateTime1 = new DateTime(date1);
		DateTime dateTime2 = new DateTime(date2);
		return dateTime1.compareTo(dateTime2);
	}

}
