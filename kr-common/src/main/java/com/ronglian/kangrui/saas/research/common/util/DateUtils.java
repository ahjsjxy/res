package com.ronglian.kangrui.saas.research.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
	public final static String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	public final static String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";

	public final static String FORMAT_YYYYMMDD = "yyyyMMdd";

	public final static String FORMAT_YYYYMM = "yyyyMM";

	public final static String FORMAT_YYYY = "yyyy";

	public final static String FORMAT_YYYYMMDDHHMISS = "yyyyMMdd HH:mm:ss";

	public final static String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public final static String FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";

	public final static String FORMAT_YYYY_MM_DD_HH_MI_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

	public final static String FORMAT_YYYY_MM = "yyyy-MM";

	public final static String FORMAT_DD_HH_MM_SS = "dd HH:mm:ss";

	public final static String FORMAT_HH_MM_SS = "HH:mm:ss";

	public final static String FORMAT_DD = "dd" ;

	public final static String FORMAT_MM = "MM" ;

	public static final String FORMAT_STR_DATE10_2 = "yyyy/MM/dd";
	public static final String FORMAT_STR_DATE11_C = "yyyy年MM月dd日";
	public static final String FORMAT_STR_DATETIME13 = "yyyy-MM-dd HH";
	public static final String FORMAT_STR_DATETIME16 = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_STR_TIME9 = "HHmmssSSS";

	public static final int USE_YEAR = 1;

	public static final int USE_MONTH = 2;

	public static final int USE_DAY = 3;

	public static final SimpleDateFormat DATEFORMATE_DATE8 = new SimpleDateFormat(FORMAT_YYYYMMDD);
	public static final SimpleDateFormat DATEFORMATE_DATE10 = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
	public static final SimpleDateFormat DATEFORMATE_DATE10_2 = new SimpleDateFormat(FORMAT_STR_DATE10_2);
	public static final SimpleDateFormat DATEFORMATE_DATE11_C = new SimpleDateFormat(FORMAT_STR_DATE11_C);
	public static final SimpleDateFormat DATEFORMATE_DATETIME13 = new SimpleDateFormat(FORMAT_STR_DATETIME13);
	public static final SimpleDateFormat DATEFORMATE_DATETIME14 = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
	public static final SimpleDateFormat DATEFORMATE_DATETIME16 = new SimpleDateFormat(FORMAT_STR_DATETIME16);
	public static final SimpleDateFormat DATEFORMATE_DATETIME19 = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MI_SS);
	public static final SimpleDateFormat DATEFORMATE_TIME9 = new SimpleDateFormat(FORMAT_STR_TIME9);

	/**
	 *
	 * @description 根据指定的日期格式，获得相匹配的SimpleDateFormat对象
	 * @author yichen
	 * @date 2016年5月3日 下午5:31:33
	 * @param formatStr 日期格式
	 * @return
	 */
	private static SimpleDateFormat getFormaterByPattern(String formatStr){
		if(StringUtils.isBlank(formatStr)) {
			return null;
		}

		if(FORMAT_YYYYMMDD.equals(formatStr)){
			return DATEFORMATE_DATE8;
		} else if (FORMAT_YYYY_MM_DD.equals(formatStr)){
			return DATEFORMATE_DATE10;
		} else if (FORMAT_STR_DATE10_2.equals(formatStr)){
			return DATEFORMATE_DATE10_2;
		} else if (FORMAT_STR_DATE11_C.equals(formatStr)){
			return DATEFORMATE_DATE11_C;
		} else if (FORMAT_STR_DATETIME13.equals(formatStr)){
			return DATEFORMATE_DATETIME13;
		} else if(FORMAT_YYYYMMDDHHMMSS.equals(formatStr)){
			return DATEFORMATE_DATETIME14;
		} else if (FORMAT_STR_DATETIME16.equals(formatStr)){
			return DATEFORMATE_DATETIME16;
		} else if (FORMAT_YYYY_MM_DD_HH_MI_SS.equals(formatStr)){
			return DATEFORMATE_DATETIME19;
		} else if (FORMAT_STR_TIME9.equals(formatStr)){
			return DATEFORMATE_TIME9;
		} else {
			//logger.info("没有与格式["+ formatStr +"]相匹配的SimpleDateFormat对象！");
		}

		return null;
	}

	/**
	 *
	 * @description
	 * @author yichen
	 * @date 2016年5月3日 下午5:30:13
	 * @param date
	 * @param formater
	 * @return
	 */
	private static String syncFormat(Date date, SimpleDateFormat formater){
		if(date == null) {return "";}
		try {
			synchronized (formater) {
				return formater.format(date);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 *
	 * @description 将指定日期字符串转化为日期对象
	 * @author yichen
	 * @date 2016年11月16日 下午6:34:17
	 * @param dateStr
	 * @param formater
	 * @return Date
	 */
	private static Date syncParse(String dateStr, SimpleDateFormat formater){
		if(dateStr==null || "".equals(dateStr))
		{
			return null;
		}

		try {
			synchronized (formater) {
				return formater.parse(dateStr);
			}
		} catch (ParseException e) {
			//logger.error("将指定日期字符串["+ dateStr +"]转化为日期对象异常：", e);
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 日期转成string
	 * @author zhliu
	 * @date 2015年7月8日
	 * @param date
	 * @param fomt
	 * @return
	 */
	public static String date2String(Date date,String fomt){
		SimpleDateFormat sdf = new SimpleDateFormat(fomt);
		return sdf.format(date);
	}


	/**
	 * 字符串转date
	 * @param dateString
	 * @param fomt
	 * @return
	 */
	public static Date string2Date(String dateString,String fomt){
		SimpleDateFormat sdf = new SimpleDateFormat(fomt);
		return syncParse(dateString, sdf);
	}



	public static DateFormat getDateFormat(final String pattern) {
		return new SimpleDateFormat(pattern);
	}

	public static Date getPreviousOrNextDaysOfNow(int days) {
		return getPreviousOrNextDaysOfDate(new Date(), days);
	}

	/**
	 * Current date with format(yyyy-MM-dd)
	 * @return
	 */
	public static String getCurrentDate() {
		Date now = new Date();
		return getDateFormat(FORMAT_YYYY_MM_DD).format(now);
	}

	/**
	 * Current date with format(yyyy-MM-dd HH:MM:SS)
	 * @return
	 */
	public static String getCurrentDateTime() {
		Date now = new Date();
		return getDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS).format(now);
	}


	/**
	 * Current date with format(yyyy-MM-dd HH:MM:SS)
	 * @return
	 */
	public static String getCurrentDateFormat(String format) {
		Date now = new Date();
		return getDateFormat(format).format(now);
	}

	/**
	 * Current date with format(yyyy-MM-dd HH:MM:SS:SSS)
	 * @return
	 */
	public static String getCurrentDateTimeMilliSecond(Date date) {
		return getDateFormat(FORMAT_YYYY_MM_DD_HH_MI_SS_SSS).format(date);
	}


	/**
	 * Current date with format
	 * @return
	 */
	public static String getStringByTimeFormat(Date date, String format) {
		return getDateFormat(format).format(date);
	}



	/**
	 * Current date with format(yyyy-MM-dd HH:MM:SS:SSS)
	 * @return
	 */
	public static Date getDateByTimeMilliSecond(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MI_SS_SSS);
		return sdf.parse(date);
	}


	/**
	 * 转换日期类
	 * @return
	 */
	public static Date convertStringDate(String date, String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}



	public static Date getFirstDayOfThisMonth() {
		Calendar nowday = Calendar.getInstance();
		nowday.set(Calendar.DAY_OF_MONTH, 1);
		nowday.set(Calendar.HOUR_OF_DAY, 0);
		nowday.set(Calendar.MINUTE, 0);
		nowday.set(Calendar.SECOND, 0);
		nowday.set(Calendar.MILLISECOND, 0);
		return nowday.getTime();
	}

	public static Date getLastMonth() {
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(new Date());
		nowday.set(Calendar.DAY_OF_MONTH, 1);
		nowday.add(Calendar.MONTH, -1);
		return nowday.getTime();
	}


	public static Date getLastDayByCalendar(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}



	/**
	 * Is in <code>days</code> of the specific date
	 * @param old
	 * @param days
	 * @return
	 */
	public static boolean isDaysInterval(Date old, int days) {
		Calendar now = Calendar.getInstance();
		return (now.getTimeInMillis() - old.getTime()) <= days * 24 * 3600 * 1000;
	}

	public static Date getFirstDayOfTheMonth(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Next count by a positive number or previous by a negtive number
	 * @param days Day
	 * @return
	 */
	public static Date getPreviousOrNextDaysOfDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	public static Date getPreviousOrNextMonthsOfDate(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	public static Date getPreviousOrNextYearsOfDate(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}


	public static Date getBeginOfCurrentDay() {
		return getBeginOfTheDate(new Date());
	}

	public static Date getEndOfCurrentDay() {
		return getEndOfTheDate(new Date());
	}

	public static long getDifferenceDays(Calendar beginCalender, Calendar endCalendar) {
		long days = (endCalendar.getTimeInMillis() - beginCalender.getTimeInMillis()) / (24 * 60 * 60 * 1000);
		days = days + 1;
		return days;
	}

	public static long getDifferenceDays(long begin, long end) {
		long days = (end - begin) / (24 * 60 * 60 * 1000);
		days = days + 1;
		return days;
	}

	public static long getDifferenceYears(long begin, long end) {
		long years = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(end);
		if (begin > end) {
			throw new IllegalArgumentException(
					"The begin date is before end date.It's unbelievable!");
		}
		int yearEnd = cal.get(Calendar.YEAR);
		int monthEnd = cal.get(Calendar.MONTH)+1;
		int dayOfMonthEnd = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTimeInMillis(begin);
		int yearBegin = cal.get(Calendar.YEAR);
		int monthBegin = cal.get(Calendar.MONTH)+1;
		int dayOfMonthBegin = cal.get(Calendar.DAY_OF_MONTH);

		years = yearEnd - yearBegin;

		if (monthEnd <= monthBegin) {
			if (monthEnd == monthBegin) {
				if (dayOfMonthEnd < dayOfMonthBegin) {
					years--;
				}
			} else {
				years--;
			}
		}

		return years;
	}

	public static Date getBeginOfCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getPreviousOrNextMinutesOfDate(Date date, int minute) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MINUTE, minute);
		return now.getTime();
	}

	/**
	 * getPreviousOrNextMinutesOfDate
	 *
	 * @Description TODO (获取当前时间前后秒，正数为当前时间后多少秒)
	 * @param @param date
	 * @param @param minute
	 * @param @return    设定文件
	 * @return Date    返回类型
	 * @throws
	 */
	public static Date getPreviousOrNextSecondsOfDate(Date date, int seconds) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.SECOND, seconds);
		return now.getTime();
	}


	public static Date getLastDateOfTheMonth(Date date) {
		Calendar newday = Calendar.getInstance();
		newday.setTime(date);
		newday.set(Calendar.DAY_OF_MONTH, newday.getActualMaximum(Calendar.DAY_OF_MONTH));
		newday.set(Calendar.HOUR_OF_DAY, 23);
		newday.set(Calendar.MINUTE, 59);
		newday.set(Calendar.SECOND, 59);
		newday.set(Calendar.MILLISECOND, 999);
		return newday.getTime();
	}


	public static Date getBeginOfTheDate(Date date) {
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(date);
		nowday.set(Calendar.HOUR_OF_DAY, 0);
		nowday.set(Calendar.MINUTE, 0);
		nowday.set(Calendar.SECOND, 0);
		nowday.set(Calendar.MILLISECOND, 0);
		return nowday.getTime();
	}


	public static Date getEndOfTheDate(Date date) {
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(date);
		nowday.set(Calendar.HOUR_OF_DAY, 23);
		nowday.set(Calendar.MINUTE, 59);
		nowday.set(Calendar.SECOND, 59);
		nowday.set(Calendar.MILLISECOND, 998);//Sql Server BUG
		return nowday.getTime();
	}

	public static List<String> getAreaTime(String beginDate, String endDate) throws Exception {
		List<String> timeList = new ArrayList<String>();
		if (endDate == null || "".equals(endDate)) {
			timeList.add(beginDate);
			return timeList;
		}
		// day
		if (beginDate.length() <= 7) {
			return getAreaMonthTime(beginDate, endDate);
			// month
		} else {
			return getAreaDayTime(beginDate, endDate);
		}
	}

	private static List<String> getAreaMonthTime(String beginMonth, String endMonth) throws Exception {
		Date parsedBeginMonth = getDateFormat(FORMAT_YYYY_MM).parse(beginMonth);
		String tempStr = "";
		List<String> timeList = new ArrayList<String>();
		int months = 0;
		while (!endMonth.equals(tempStr)) {
			tempStr = getDateFormat(FORMAT_YYYY_MM).format(getPreviousOrNextMonthsOfDate(parsedBeginMonth, months));
			timeList.add(tempStr);
			months++;
		}
		return timeList;
	}

	public static List<String> getAreaDayTime(String beginDate, String endDate) throws Exception {
		Date parsedBeginMonth = getDateFormat(FORMAT_YYYY_MM_DD).parse(beginDate);
		String tempStr = "";
		List<String> timeList = new ArrayList<String>();
		int months = 0;
		while (!endDate.equals(tempStr)) {
			tempStr = getDateFormat(FORMAT_YYYY_MM_DD).format(getPreviousOrNextDaysOfDate(parsedBeginMonth, months));
			timeList.add(tempStr);
			months++;
		}
		return timeList;
	}

	public static Long getPreviousDay() {
		Date date = getPreviousOrNextDaysOfDate(new Date(), -1);
		return Long.valueOf((getDateFormat(FORMAT_YYYYMMDD).format(date)));
	}

	public static String getPreviousOrNextMonthsOfTheMonth(String date, int n) throws Exception {
		Calendar calendar = new GregorianCalendar();
		DateFormat dateFormat = getDateFormat(FORMAT_YYYYMM);
		calendar.setTime(dateFormat.parse(date));
		calendar.add(Calendar.MONTH, n);
		return dateFormat.format(calendar.getTime());
	}

	public static String getPreviousOrNextDaysOfTheDay(String date, int days) throws Exception {
		Calendar calendar = new GregorianCalendar();
		DateFormat dateFormat = getDateFormat(FORMAT_YYYYMMDD);
		calendar.setTime(dateFormat.parse(date));
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return dateFormat.format(calendar.getTime());
	}

	public static Long getFirstDayOfCurrentMonth() {
		Date date = getFirstDayOfThisMonth();
		String dateStr = getDateFormat(FORMAT_YYYYMMDD).format(date);
		return Long.valueOf(dateStr);
	}

	public static Long getFirstMonthOfNow() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 0);
		return Long.valueOf(getDateFormat(FORMAT_YYYYMM).format(calendar.getTime()));
	}

	public static Integer getCurrentDay() {
		Date date = new Date();
		return Integer.parseInt(getDateFormat(FORMAT_YYYYMMDD).format(date));
	}

	/**
	 *
	 * @description 获得当前系统日期号数
	 * @author yichen
	 * @date 2017年3月10日 下午4:55:43
	 * @return int
	 */
	public static int getCurrentDay2(){
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(new Date());
		return nowday.get(Calendar.DATE);
	}

	public static Integer getCurrentMonth() {
		Date date = new Date();
		return Integer.parseInt(getDateFormat(FORMAT_YYYYMM).format(date));
	}

	/**
	 *
	 * @description 获得当前系统日期月份
	 * @author yichen
	 * @date 2017年3月10日 下午4:51:19
	 * @return int
	 */
	public static int getCurrentMonth2(){
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(new Date());
		return nowday.get(Calendar.MONTH)+1;
	}

	/**
	 *
	 * @description 获得当前系统日期年份
	 * @author yichen
	 * @date 2017年3月10日 下午4:54:07
	 * @return Integer
	 */
	public static Integer getCurrentYear() {
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(new Date());
		return nowday.get(Calendar.YEAR);
	}

	/**
	 *
	 * @description 获得指定日期的年份
	 * @author yichen
	 * @date 2017年3月10日 下午3:33:17
	 * @param date
	 * @return int
	 */
	public static int getYearForDate(Date date){
		if(date==null){
			return 0;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static Integer getKpiDbAnyOffsetDayOfNow(int day) {
		Calendar nowday = Calendar.getInstance();
		nowday.set(Calendar.DAY_OF_YEAR, nowday.get(Calendar.DAY_OF_YEAR) + day);
		return Integer.parseInt(getDateFormat(FORMAT_YYYYMMDD).format(nowday.getTime()));
	}

	public static Long getPreviousMonthOfNextYear() {
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(new Date());
		nowday.set(Calendar.DAY_OF_MONTH, 1);
		nowday.add(Calendar.YEAR, +1);
		nowday.add(Calendar.MONTH, -1);
		Long time = Long.valueOf(getDateFormat(FORMAT_YYYYMM).format(nowday.getTime()));
		return time;
	}

	public static Long getTheSameDayOfTheNextMonth(Date date) {
		Date next = getPreviousOrNextMonthsOfDate(date, 1);
		Long time = Long.valueOf(getDateFormat(FORMAT_YYYYMMDD).format(next));
		return time;
	}

	public static Long getTheSameDayOfNextMonth() {
		return getTheSameDayOfTheNextMonth(new Date());
	}

	public static Long getPreviousYear() {
		Calendar nowday = Calendar.getInstance();
		nowday.setTime(new Date());
		nowday.add(Calendar.YEAR, -1);
		return Long.valueOf(getDateFormat(FORMAT_YYYY).format(nowday.getTime()));
	}

	public static List<String> getTimeListByParameter(Date paramDate, int paramter, int type) {
		if (type == USE_YEAR) {
			Date newDate = getPreviousOfNextYearOfDate(paramDate, -paramter);
			return getDaysBetweenDate(paramDate, newDate);
		} else if (type == USE_MONTH) {
			Date newDate = getPreviousOrNextMonthsOfDate(paramDate, paramter);
			return getDaysBetweenDate(paramDate, newDate);
		} else {
			return getDaysByBeginDate(paramDate, paramter);
		}
	}

	private static Date getPreviousOfNextYearOfDate(Date date, int year) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.YEAR,year);
		return now.getTime();
	}

	private static List<String> getDaysBetweenDate(Date paramDate, Date newDate) {
		DateFormat dateFormat = getDateFormat(FORMAT_YYYYMMDD);
		String formatNewDate = dateFormat.format(newDate);
		String tempStr = "";
		List<String> timeList = new ArrayList<String>();
		int days = 0;
		while (!formatNewDate.equals(tempStr)) {
			tempStr = dateFormat.format(getPreviousOrNextDaysOfDate(paramDate, -days));
			timeList.add(tempStr);
			days++;
		}
		return timeList;
	}

	private static List<String> getDaysByBeginDate(Date paramDate, int paramter) {
		List<String> timeList = new ArrayList<String>();
		for (int i = 1; i <= paramter; i++) {
			timeList.add(getDateFormat(FORMAT_YYYYMMDD).format(getPreviousOrNextDaysOfDate(paramDate, -i)));
		}
		return timeList;
	}


	public static Long getFirstMonthOfCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH,0);
		return Long.valueOf(getDateFormat(FORMAT_YYYYMM).format(calendar.getTime()));
	}

	public static Date getPreviousOrNextMonthsOfDateTime(Date date, int months){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	public static String  getPreviousOrNextDaysOfNow(String ymd,int days) throws ParseException {
		Date date = getDateFormat(FORMAT_YYYY_MM_DD).parse(ymd);
		Date result = getPreviousOrNextDaysOfDate(date, days);
		return getDateFormat(FORMAT_YYYY_MM_DD).format(result);
	}

	public static Date getBeginOfThePreviousOrNextMonths(int month){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getPreviousOrNextWorkDaysOfDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int step = 0;
		if(days>0){
			step = 1;
		}else{
			step = -1;
		}
		for(int i = 0; i < Math.abs(days) ; ){
			calendar.add(Calendar.DAY_OF_YEAR, step);
			if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
				continue;
			}
			i++;
		}
		return calendar.getTime();
	}

	public static Date getBeginDateOfTheMonth(Date date) {
		Calendar newday = Calendar.getInstance();
		newday.setTime(date);
		newday.set(Calendar.DAY_OF_MONTH, 1);
		newday.set(Calendar.HOUR_OF_DAY, 0);
		newday.set(Calendar.MINUTE, 0);
		newday.set(Calendar.SECOND, 0);
		newday.set(Calendar.MILLISECOND, 0);
		return newday.getTime();
	}

	public static Date getEndDateOfTheMonth(Date date) {
		Calendar newday = Calendar.getInstance();
		newday.setTime(date);
		newday.set(Calendar.DAY_OF_MONTH, newday.getActualMaximum(Calendar.DAY_OF_MONTH));
		newday.set(Calendar.HOUR_OF_DAY, 23);
		newday.set(Calendar.MINUTE, 59);
		newday.set(Calendar.SECOND, 59);
		newday.set(Calendar.MILLISECOND, 998);
		return newday.getTime();
	}


	/**
	 * 得到开始日期和结束日期之间相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDiffDay(String startDate, String endDate){
		int diffDay = 0 ;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
			Date d1=sdf.parse(startDate);
			Date d2=sdf.parse(endDate);
			diffDay = (int) ((d2.getTime()-d1.getTime())/(3600*24*1000));
		} catch (Exception e) {
			e.printStackTrace() ;
		}
		return diffDay ;
	}

	public static Date getBeginDateOfTheWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		return getBeginOfTheDate(calendar.getTime());
	}

	public static Date getEndDateOfTheWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		return getEndOfTheDate(calendar.getTime());

	}

	/**
	 *
	 * @description 将日期格式化为字符串，格式为：yyyyMMdd
	 * @author yichen
	 * @date 2016年5月3日 下午5:52:08
	 * @param date
	 * @return
	 */
	public static String formatDate8(Date date){
		return syncFormat(date, DATEFORMATE_DATE8);
	}

	/**
	 *
	 * @description 将系统当前日期格式化为字符串，格式为：yyyyMMdd
	 * @author yichen
	 * @date 2016年5月3日 下午5:52:17
	 * @return
	 */
	public static String formatDate8(){
		return syncFormat(new Date(), DATEFORMATE_DATE8);
	}

	/**
	 *
	 * @description 将日期格式化为字符串，格式为：yyyy-MM-dd
	 * @author yichen
	 * @date 2016年6月6日 下午5:55:15
	 * @param date
	 * @return
	 */
	public static String formatDate10(Date date){
		return syncFormat(date, DATEFORMATE_DATE10);
	}

	/**
	 *
	 * @description 将系统当前日期格式化为字符串，格式为：yyyy-MM-dd
	 * @author yichen
	 * @date 2016年6月6日 下午5:55:54
	 * @return
	 */
	public static String formatDate10(){
		return syncFormat(new Date(), DATEFORMATE_DATE10);
	}

	/**
	 *
	 * @description 将日期格式化为字符串，格式为：yyyy/MM/dd
	 * @author yichen
	 * @date 2016年9月18日 下午4:36:52
	 * @param date
	 * @return String
	 */
	public static String formatDate10_2(Date date){
		return syncFormat(date, DATEFORMATE_DATE10_2);
	}

	/**
	 *
	 * @description 将系统当前日期格式化为字符串，格式为：yyyy/MM/dd
	 * @author yichen
	 * @date 2016年9月18日 下午4:37:34
	 * @return String
	 */
	public static String formatDate10_2(){
		return syncFormat(new Date(), DATEFORMATE_DATE10_2);
	}

	/**
	 *
	 * @description 将指定日期格式化为字符串，格式为：yyyy年MM月dd日
	 * @author yichen
	 * @date 2016年9月18日 下午4:36:52
	 * @param date
	 * @return String
	 */
	public static String formatDate11_C(Date date){
		return syncFormat(date, DATEFORMATE_DATE11_C);
	}

	/**
	 *
	 * @description 将系统当前日期格式化为字符串，格式为：yyyy年MM月dd日
	 * @author yichen
	 * @date 2016年11月17日 下午3:24:30
	 * @return String
	 */
	public static String formatDate11_C(){
		return syncFormat(new Date(), DATEFORMATE_DATE11_C);
	}

	/**
	 *
	 * @description 将日期格式化为字符串，格式为：yyyy-MM-dd HH
	 * @author yichen
	 * @date 2016年5月3日 下午5:53:49
	 * @param date
	 * @return
	 */
	public static String formatDatetime13(Date date){
		return syncFormat(date, DATEFORMATE_DATETIME13);
	}

	/**
	 *
	 * @description 将系统当前日期格式化为字符串，格式为：yyyy-MM-dd HH
	 * @author yichen
	 * @date 2016年5月3日 下午5:53:52
	 * @return
	 */
	public static String formatDatetime13(){
		return syncFormat(new Date(), DATEFORMATE_DATETIME13);
	}

	/**
	 *
	 * @description 将日期格式化为字符串，格式为：yyyyMMddHHmmss
	 * @author yichen
	 * @date 2016年9月14日 下午2:31:52
	 * @param date
	 * @return String
	 */
	public static String formatDatetime14(Date date){
		return syncFormat(date, DATEFORMATE_DATETIME14);
	}

	/**
	 *
	 * @description 将系统当前日期格式化为字符串，格式为：yyyyMMddHHmmss
	 * @author yichen
	 * @date 2016年9月14日 下午2:33:05
	 * @return String
	 */
	public static String formatDatetime14(){
		return syncFormat(new Date(), DATEFORMATE_DATETIME14);
	}

	/**
	 *
	 * @description 将日期格式化为字符串，格式为：yyyy-MM-dd HH:mm:ss
	 * @author yichen
	 * @date 2016年5月3日 下午5:37:12
	 * @param date
	 * @return
	 */
	public static String formatDatetime19(Date date){
		return syncFormat(date, DATEFORMATE_DATETIME19);
	}

	/**
	 *
	 * @description 将系统当前日期格式化为字符串，格式为：yyyy-MM-dd HH:mm:ss
	 * @author yichen
	 * @date 2016年5月3日 下午5:37:38
	 * @return
	 */
	public static String formatDatetime19(){
		return syncFormat(new Date(), DATEFORMATE_DATETIME19);
	}

	/**
	 *
	 * @description 将格式为yyyyMMdd的字符串，转为日期对象
	 * @author yichen
	 * @date 2016年11月16日 下午6:39:40
	 * @param dateStr
	 * @return Date
	 */
	public static Date parseDate8(String dateStr){
		return syncParse(dateStr, DATEFORMATE_DATE8);
	}

	/**
	 *
	 * @description 将格式为yyyy-MM-dd的字符串，转为日期对象
	 * @author yichen
	 * @date 2016年11月16日 下午6:39:40
	 * @param dateStr
	 * @return Date
	 */
	public static Date parseDate10(String dateStr){
		return syncParse(dateStr, DATEFORMATE_DATE10);
	}

	/**
	 *
	 * @description 将格式为yyyyMMddHHmmss的字符串，转为日期对象
	 * @author yichen
	 * @date 2016年11月16日 下午6:43:35
	 * @param dateStr
	 * @return Date
	 */
	public static Date parseDatetime14(String dateStr){
		return syncParse(dateStr, DATEFORMATE_DATETIME14);
	}

	/**
	 *
	 * @description 将格式为yyyy-MM-dd HH:mm:ss的字符串，转为日期对象
	 * @author yichen
	 * @date 2016年11月16日 下午6:43:35
	 * @param dateStr
	 * @return Date
	 */
	public static Date parseDatetime19(String dateStr){
		return syncParse(dateStr, DATEFORMATE_DATETIME19);
	}

	/**
	 *
	 * @description 获得今年第一天日期
	 * @author yichen
	 * @date 2016年6月6日 下午8:01:16
	 * @return
	 */
	public static Date getFirstDayOfThisYear(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
	}

	/**
	 * 时间+几个月的月份后的当前时间
	 * @param date
	 * @param num
	 * @return
	 */
	public Date getDateAddMonth(Date date,int num){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, num);
		return calendar.getTime();
	}

	/**
	 *
	 * @description 给指定日期+num年
	 * @author yichen
	 * @date 2016年12月5日 下午3:10:46
	 * @param date 指定日期
	 * @param num 增加年数
	 * @return Date 结果
	 */
	public static Date getDateAddYear(Date date, int num){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, num);

		return calendar.getTime();
	}

	/**
	 *
	 * @description 创建日期对象
	 * @author yichen
	 * @date 2017年3月10日 上午11:58:51
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return Date
	 */
	public static Date getMakeDate(int year, int month, int day){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 *
	 * @description 给指定日期添加23时59分59秒
	 * @author yichen
	 * @date 2017年3月3日 上午11:03:03
	 * @param date
	 * @return Date
	 */
	public static Date addDatetime235959(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.HOUR, 23);
		calendar.add(Calendar.MINUTE, 59);
		calendar.add(Calendar.SECOND, 59);

		return calendar.getTime();
	}

	/**
	 *
	 * @description 指定年份是否为闰年
	 * @author yichen
	 * @date 2017年3月9日 上午11:35:43
	 * @param year 年份
	 * @return boolean
	 */
	public static boolean isLeapYear(int year){
		if(year % 100==0){
			if(year % 400==0){
				return true;
			}
		} else {
			if(year % 4==0){
				return true;
			}
		}

		return false;
	}

	/**
	 *
	 * @description 系统当前年份是否为闰年
	 * @author yichen
	 * @date 2017年3月9日 上午11:43:30
	 * @return boolean
	 */
	public static boolean thisYearIsLeapYear(){
		return isLeapYear(getCurrentYear());
	}

	/** 计算年龄 */
	public static int getAge(Date birthDay) throws Exception {
		int currentAge = 0;
		if(birthDay != null){
			Calendar cal = Calendar.getInstance();
			if (cal.before(birthDay)) {
				throw new IllegalArgumentException(
						"The birthDay is before Now.It's unbelievable!");
			}
			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH)+1;
			int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

			cal.setTime(birthDay);
			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH)+1;
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

			currentAge = yearNow - yearBirth;

			if (monthNow <= monthBirth) {
				if (monthNow == monthBirth) {
					//monthNow==monthBirth
					if (dayOfMonthNow < dayOfMonthBirth) {
						currentAge--;
					}
				} else {
					//monthNow>monthBirth
					currentAge--;
				}
			}
		}

		return currentAge;
	}
	/**
	 * 当start大于end时返回true，小于等于时返回false
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean after(String start,String end){
		Date d1=parseDatetime19(start);
		Date d2=parseDatetime19(end);
		if(d1.after(d2)){
			return true;
		}
		return false;
	}

	/**
	 * 昨天的日期
	 * @param format
	 * @return
	 */
	public static String yesterday(String format){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		Date time=cal.getTime();
		return new SimpleDateFormat(format).format(time);
	}

	public static String  tomorrow(String format){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,1);
		Date time=cal.getTime();
		return new SimpleDateFormat(format).format(time);
	}

	public static void main(String[] args) throws Exception {
//		FORMAT_YYYY_MM_DD : String
//		DateUtils.get
		SimpleDateFormat sdf = DateUtils.getFormaterByPattern(DateUtils.FORMAT_YYYY_MM_DD);
		Date startDay = sdf.parse("1984-08-12");
		Date endDay = sdf.parse("2016-08-13");
		Date ofDate = DateUtils.getPreviousOrNextYearsOfDate(endDay, -32);
		System.out.println(DateUtils.date2String(ofDate, DateUtils.FORMAT_YYYY_MM_DD));
		long age = DateUtils.getDifferenceYears(startDay.getTime(), endDay.getTime());
		System.out.println("age---"+age);
		System.out.println(DateUtils.parseDate8("20160507"));
		System.out.println(DateUtils.formatDate11_C());

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDay);

		cal.add(Calendar.HOUR, 23);
		cal.add(Calendar.MINUTE, 59);
		cal.add(Calendar.SECOND, 59);
		System.out.println(DateUtils.formatDatetime19(cal.getTime()));
		System.out.println(DateUtils.getCurrentYear());
		System.out.println(DateUtils.thisYearIsLeapYear());
		System.out.println(DateUtils.formatDate10(DateUtils.getMakeDate(2017, 3, 10)));

		String now=DateUtils.date2String(new Date(),DateUtils.FORMAT_YYYY_MM_DD);
		System.out.println("now=="+now);


		String tomorrow=DateUtils.tomorrow(DateUtils.FORMAT_YYYY_MM_DD);
		System.out.println("tomorrow=="+tomorrow);
	}

}