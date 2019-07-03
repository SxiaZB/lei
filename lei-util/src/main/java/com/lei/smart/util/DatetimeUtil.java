package com.lei.smart.util;

import com.aliyun.oss.common.utils.DateUtil;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatetimeUtil {

	public final static String DATETIME_PATTERN = "yyyyMMddHHmmss";

	public final static String TIME_STAMP_PATTERN = "yyyyMMddHHmmssSSS";

	public final static String DATE_PATTERN = "yyyyMMdd";

	public final static String TIME_PATTERN = "HHmmss";

	public final static String STANDARD_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public final static String STANDARD_DATETIME_PATTERN_HM = "yyyy-MM-dd HH:mm";

	public final static String STANDARD_DATE_PATTERN = "yyyy-MM-dd";

	public final static String STANDARD_TIME_PATTERN = "HH:mm:ss";

	public final static String STANDARD_DATETIME_PATTERN_SOLIDUS = "yyyy/MM/dd HH:mm:ss";

	public final static String STANDARD_DATETIME_PATTERN_SOLIDUS_HM = "yyyy/MM/dd HH:mm";

	public final static String STANDARD_DATE_PATTERN_SOLIDUS = "yyyy/MM/dd";

	private DatetimeUtil() {
		super();
	}

	public static Timestamp currentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String currentDatetime() {
		return formatDate(new Date());
	}
	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	 *
	 * @param nowTime 当前时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @author jqlin
	 */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime()
				|| nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}
	public static Timestamp parseDate(String dateStr, String pattern)  {
		Timestamp timestamp=null;
		try {
			Date d = DatetimeUtil.parse(dateStr, pattern);
			 timestamp = new Timestamp(d.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  timestamp;
	}

	public static Timestamp parseDate(String dateStr) throws ParseException {
		return parseDate(dateStr, STANDARD_DATE_PATTERN);
	}

	public static java.sql.Date parseSQLDate(String dateStr, String pattern) throws ParseException {
		Date d = parse(dateStr, pattern);
		return new java.sql.Date(d.getTime());
	}

	public static java.sql.Date parseSQLDate(String dateStr) throws ParseException {
		Date d = parse(dateStr, STANDARD_DATE_PATTERN);
		return new java.sql.Date(d.getTime());
	}

	public static Timestamp getFutureTime(int month) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, month);
		return new Timestamp(c.getTimeInMillis());
	}

	/**
	 * 显示今天时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String today() {
		return formatDateTime(new Date());
	}

	public static String formatDate(Timestamp t) {
		return formatDate(new Date(t.getTime()));
	}

	public static String formatDate(Timestamp t, String pattern) {
		return formatDate(new Date(t.getTime()), STANDARD_DATE_PATTERN);
	}

	public static String formatDateTime(Timestamp t, String pattern) {
		return formatDate(new Date(t.getTime()), STANDARD_DATETIME_PATTERN);
	}

	/**
	 * 格式化日期
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String formatDate(Date date) {
		return formatDate(date, STANDARD_DATE_PATTERN);
	}

	/**
	 * 格式化日期
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, STANDARD_DATETIME_PATTERN);
	}

	/**
	 * 格式化日期
	 * @param date 日期
	 * @param pattern 格式
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null)
			return null;
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	public static Date getNowYYYY(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowdayTime = dateFormat.format(new Date());
		Date nowDate = null;
		try {
			nowDate = dateFormat.parse(nowdayTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nowDate;
	}

	public static void main(String[] args) {

	}
	/**
	 * 解析日期
	 * @param dateStr yyyy-MM-dd
	 * @return
	 */
	public static Date parse(String dateStr) {
		return parse(dateStr, STANDARD_DATE_PATTERN);
	}

	/**
	 * 解析日期
	 * @param dateStr yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date parseTime(String dateStr){
		return parse(dateStr, STANDARD_DATETIME_PATTERN);
	}

	public static Date parse(String dateStr, String pattern) {

		try {
			DateFormat format = new SimpleDateFormat(pattern);
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static  boolean judge4Open(Date  date){
		Date date1 = new Date();
		return  date.after(date1);
	}


	public static String getTime4Super(Long time){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置格式
		String timeText=format.format(time);
		return timeText;
	}

	/**
	 * 当月的第一天
	 *
	 * @return
	 */
	public static String firstDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return DatetimeUtil.formatDate(calendar.getTime()) + " 00:00:00";
	}

	/**
	 * 当月的最后一天
	 *
	 * @return
	 */
	public static String lastDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return DatetimeUtil.formatDate(calendar.getTime()) + " 23:59:59";
	}


}
