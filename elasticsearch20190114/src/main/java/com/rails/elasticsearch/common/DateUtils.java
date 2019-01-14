/**
 * Copyright 2017 电子计算技术研究所
 * Author：WenLi
 * 创建日期：2017年6月1日
 */
package com.rails.elasticsearch.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	public static String MMddSplit = "MM-dd";

	public static String yyyyMMdd = "yyyyMMdd";

	public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static String yyyyMMdd_HHmm = "yyyyMMdd HHmm";

	public static String yyyyMMddHHmm12 = "yyyyMMddHHmm";

	public static String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

	public static String yyyyMMddHHmmssForLoseTime = "yyyy-MM-dd HH:mm:ss";

	public static String yyyyMMddHHmmSS = "yyyy/MM/dd HH:mm:ss";

	public final static String yyyyMMdd_HHmmss = "yyyyMMdd HH:mm:ss";

	public static String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";

	public static String yyyyMMddHHmmssSplit = "yyyy-MM-dd-HH-mm-ss";

	public static String yyyyNMMYddHHHmm = "yyyy年MM月dd号 HH时mm分";// 格式2011年4月20号4时23分

	public static String yyyyNMMYdd = "yyyy年MM月dd日";// 格式2011年4月20日

	public static String yyyyMMddSplit = "yyyy-MM-dd";

	public static String yyyyMMddSplitP = "yyyy/MM/dd";

	public static String HHmm = "HHmm";

	public static String kkmm = "kkmm";

	public static String HHmmSplit = "HH:mm";

	public static String HHmmss = "HHmmss";

	public static String HHmmssSplit = "HH:mm:ss";

	public static String MMYddR = "MM月dd日";
	public static String MdRSplit = "M月d日";

	public static final String PHONE_TRANS_FORMAT = "MMM dd yyyy h:mma";

	private static final String dateRegx = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";

	public static String Time2GMT(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(date);
	}

	/**
	 * 1、验证是否是日期格式，日期字符串适用格式如下：<br>
	 * 2009-01-01 12:13:12<br>
	 * 2009-1-1 12:13:12<br>
	 * 2009/01/01 12:13:12<br>
	 * 2009/1/1 12:13:12<br>
	 * 2009-01-01<br>
	 * 2009/01/01<br>
	 * 20091004/01<br>
	 * ......<br>
	 * <br>
	 * 
	 * 2、日期字符串错误格式如下：<br>
	 * 2009/13/01<br>
	 * 2009/01/01 24:59:59<br>
	 * 20091234<br>
	 * ......<br>
	 * <br>
	 * 
	 * 3、例代码：<br>
	 * DateUtils.isDate("20091004") &nbsp;&nbsp;输出true<br>
	 * DateUtils.isDate("20091304") &nbsp;&nbsp;输出false<br>
	 * 
	 * @param dateStr
	 *            日期字符串<br>
	 * @return true：是日期格式<br>
	 *         false：不是日期格式<br>
	 */
	public static boolean isDate(String dateStr) {
		Pattern pattern = Pattern.compile(dateRegx);
		Matcher matcher = pattern.matcher(dateStr);
		return matcher.matches();
	}

	/**
	 * TODO 将Long型数字格式化成日期格式<br>
	 * 
	 * @param dateLong
	 * @return
	 */
	public static String formatLong(Long dateLong, String fmtStr) {
		String d = "";
		if (dateLong == null)
			return "";
		try {
			Calendar cl = Calendar.getInstance();
			cl.setTimeInMillis(dateLong);
			SimpleDateFormat formatter = new SimpleDateFormat(fmtStr);
			return formatter.format(cl.getTime());

			// d = DateFormatUtils.format(cl.getTime(), fmtStr);
		} catch (Exception e) {
			// LogUtil.getCommonLogger().error("error format date:" + dateLong, e);
		}
		return d;
	}

	public static long toLong3(String dateStr, String sf) {
		try {
			SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat(sf);
			Date dt = YYYY_MM_DD.parse(dateStr);
			return dt.getTime();
		} catch (ParseException e) {
			// LogUtil.getCommonLogger().debug("DateFormatUtil.toLong", e);
		}
		return 0;
	}

	/**
	 * TODO 获取预售期内的时间集合
	 * 
	 * @author liwen
	 * @return
	 */
	public static List<String> getDateRange() {
		// 获取页面传递的参数
		String current_date = DateUtils.dateToString(DateUtils.getSysTime(), DateUtils.MMddSplit);
		Calendar calendar = Calendar.getInstance();
		// TODO 取预售期,目前写死,默认就是展示当天之后20天的效果
		int control = 19;
		calendar.add(Calendar.DAY_OF_YEAR, +control);
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.MMddSplit);
		String control_day = sdf.format(date);
		List<String> result = new ArrayList<String>();
		try {
			current_date = StringUtils.trim(current_date);
			control_day = StringUtils.trim(control_day);
			if (StringUtils.isEmpty(current_date) || StringUtils.isEmpty(control_day)) {
				return result;
			}
			int i = diffDate(sdf.parse(control_day), sdf.parse(current_date));
			if (i < 0) {
				String temp = current_date;
				current_date = control_day;
				control_day = temp;
				i = -1 * i;
			}
			// current_date = this.getWeekByDate(current_date);
			result.add(current_date);
			String string_date = new String(current_date.getBytes("UTF-8"), "UTF-8");
			while (i > 0) {
				string_date = turnDate(string_date, DateUtils.MMddSplit, 1);
				// 将长日期拼起来用于回显
				// string_date = this.getWeekByDate(string_date);
				result.add(string_date);
				i--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	private static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	private static String turnDate(String showDate, String format, int interDay) {
		// 日期加指定天数
		Calendar cal = Calendar.getInstance();
		Date date = DateUtils.stringToDate(showDate, format);
		if (date == null) {
			return null;
		}
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, interDay);
		String next = DateUtils.dateToString(cal.getTime(), format);
		return next;
	}

	public static Long getTimeM(String mm) {
		long m = 0l;
		try {
			m = Long.parseLong(mm);
		} catch (Exception e) {
			logger.error("配置文件获取closeTime无法转化为long型");
		}
		if (m <= 1) {
			logger.error("配置文件获取closeTime,时间未大于1分钟");
			m = 0l;
		}
		return m;
	}

	/**
	 * 1、验证是否指定格式的日期，日期字符串适用格式如下：<br>
	 * 2009-01-01 12:13:12<br>
	 * 2009-1-1 12:13:12<br>
	 * 2009/01/01 12:13:12<br>
	 * 2009/1/1 12:13:12<br>
	 * 2009-01-01<br>
	 * 2009/01/01<br>
	 * 20091004/01<br>
	 * ......<br>
	 * <br>
	 * 
	 * 2、日期字符串错误格式如下：<br>
	 * 2009/13/01<br>
	 * 2009/01/01 24:59:59<br>
	 * 20091234<br>
	 * ......<br>
	 * <br>
	 * 
	 * 3、示例代码：<br>
	 * DateUtils.isDate("20091004",DateUtils.yyyyMMdd) &nbsp;&nbsp;输出true<br>
	 * DateUtils.isDate("20091004",DateUtils.yyyyMMddHHmmss) &nbsp;&nbsp;输出false
	 * <br>
	 * DateUtils.isDate("2009-1-04","yyyy-MM-dd") &nbsp;&nbsp;输出true<br>
	 * 
	 * @param dateStr
	 *            日期字符串<br>
	 * @param dateFormat
	 *            指定格式，如：DateUtils.yyyyMMdd、DateUtils.yyyyMMddHHmmss ...
	 * @return true：是指定格式的日期<br>
	 *         false：不是指定格式的日期<br>
	 */
	public static boolean isDate(String dateStr, String dateFormat) {
		boolean isDate = true;
		Pattern pattern = Pattern.compile(dateRegx);
		Matcher matcher = pattern.matcher(dateStr);
		if (matcher.matches()) {
			try {
				stringToDate(dateStr, dateFormat);
			} catch (Exception e) {
				isDate = false;
			}
		} else {
			isDate = false;
		}

		return isDate;
	}

	/**
	 * 日期格式转换：从一种字符串格式转成成另外一种字符串格式
	 * 
	 * 
	 * @param dateStr
	 * @param srcFormat
	 * @param descFormat
	 * @return
	 * 
	 * 		Sample: <br>
	 *         String dateStr =
	 *         covertDateStrFormat("20100428","yyyyMMdd","yyyy-MM-dd"); <br>
	 *         String timeStr = covertDateStrFormat("1012","HHmm","HH:mm"); String
	 *         timeStr = covertDateStrFormat("101222","HHmmss","HH:mm:ss");
	 */
	public static String covertDateStrFormat(String dateStr, String srcFormat, String descFormat) {
		SimpleDateFormat format_src = new SimpleDateFormat(srcFormat);
		SimpleDateFormat format_desc = new SimpleDateFormat(descFormat);
		try {
			Date date = format_src.parse(dateStr);
			dateStr = format_desc.format(date);
		} catch (ParseException e) {
			logger.error("could not convert the dateStr [" + dateStr + "] from formatter [" + srcFormat
					+ "] to the given formatter[" + descFormat + "]", e);
		}
		return dateStr;
	}

	/**
	 * 日期格式转换：从一种日期格式转成成另外一种日期格式
	 * 
	 * 
	 * @param date
	 * @param srcFormat
	 * @param descFormat
	 * @return
	 */
	public static Date covertDateFormat(Date date, String srcFormat, String descFormat) {
		SimpleDateFormat format_src = new SimpleDateFormat(srcFormat);
		SimpleDateFormat format_desc = new SimpleDateFormat(descFormat);
		try {
			String str = format_src.format(date);
			date = format_desc.parse(str);
		} catch (ParseException e) {
			logger.error("could not convert the date [" + date + "] from formatter [" + srcFormat
					+ "] to the given formatter[" + descFormat + "]", e);
		}
		return date;
	}

	/**
	 * Long型时间转换为String类型。
	 * 
	 * @param dateLong
	 *            long型时间
	 * @param timeFormat
	 *            时间类型
	 * @return String类型时间
	 */
	public static String longToString(long dateLong, String timeFormat) {
		if (dateLong <= 0) {
			return "error";
		}
		Date date = new Date(dateLong);
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		return sdf.format(date);
	}

	/**
	 * 根据给定的格式把字符串格式化为日期：格式yyyy-MM-dd
	 * 
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date stringToDate(String dateStr) {
		SimpleDateFormat formater = new SimpleDateFormat(yyyyMMddSplit);
		Date date = null;
		try {
			date = formater.parse(dateStr);
		} catch (ParseException e) {
			logger.error("format String to Date fail:", e);
		}
		return date;
	}

	/**
	 * 将时间类型修改为string
	 * 
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String dateToString(Date date) {
		String dateStr = "";
		try {
			SimpleDateFormat formater = new SimpleDateFormat(yyyyMMddSplit);
			dateStr = formater.format(date);

		} catch (Exception e) {
			logger.error("format Date to String fail:" + e);
		}
		return dateStr;
	}

	/**
	 * 将时间类型修改为string
	 * 
	 * @param date
	 * @return yyyy/MM/dd
	 */
	public static String dateToStringP(Date date) {
		String dateStr = "";
		try {
			SimpleDateFormat formater = new SimpleDateFormat(yyyyMMddSplitP);
			dateStr = formater.format(date);

		} catch (Exception e) {
			logger.error("format Date to String fail:" + e);
		}
		return dateStr;
	}

	/**
	 * 根据给定的格式把字符串格式化为日期：格式yyyyMMdd
	 * 
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date strToDate(String dateStr) {
		SimpleDateFormat formater = new SimpleDateFormat(yyyyMMdd);
		Date date = null;
		try {
			date = formater.parse(dateStr);
		} catch (ParseException e) {
			logger.error("format String to Date fail:", e);
		}
		return date;
	}

	/**
	 * 根据给定的格式把字符串格式化为日期：格式MM/dd/yyyy hh:mm:ss
	 * 
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date sToDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.error("format String to Date fail:", e);
		}
		return date;
	}

	/**
	 * 获取系统当前日期时间（字符串类型）：格式yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getSysTimeStr() {
		// 按指定格式格式化日期
		SimpleDateFormat formater = new SimpleDateFormat(yyyyMMddHHmmss);
		// 返回格式化后的结果
		return formater.format(getSysTime());
	}

	/**
	 * 获取系统当前日期时间（毫秒,字符串类型）：格式yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getSysTimeStr_yyyyMMddHHmmssSSS() {
		// 按指定格式格式化日期
		SimpleDateFormat formater = new SimpleDateFormat(yyyyMMddHHmmssSSS);
		// 返回格式化后的结果
		return formater.format(getSysTime());
	}

	/**
	 * 获取系统当前日期时间（毫秒,字符串类型）：格式yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getSysTimeStr_yyyyMMddHHmm() {
		// 按指定格式格式化日期
		SimpleDateFormat formater = new SimpleDateFormat(yyyyMMddHHmm12);
		// 返回格式化后的结果
		return formater.format(getSysTime());
	}

	/**
	 * 获取系统当前日期时间（Date类型）：格式yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static Date getSysTime() {
		// 获得日历类实例
		Calendar c = Calendar.getInstance();
		// 返回格式化后的结果
		return c.getTime();
	}

	/**
	 * 根据给定的格式把字符串格式化为日期
	 * 
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String dateStr, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = formater.parse(dateStr);
		} catch (ParseException e) {
			logger.error("format String to Date fail:", e);
		}
		return date;
	}

	/**
	 * 根据当期时间往前推一个月
	 * 
	 * @param date
	 * @return
	 */
	public static Date preDateMM(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 根据当期时间往前推一年
	 * 
	 * @param date
	 * @return
	 */
	public static Date preDateYYYY(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -1);
		return calendar.getTime();
	}

	/**
	 * 根据日期获取周几
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekByDate(Date date) {
		String weekofDay = "";
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			int w = now.get(Calendar.DAY_OF_WEEK);

			switch (w) {
			case 1:
				weekofDay = "周日";
				break;
			case 2:
				weekofDay = "周一";
				break;
			case 3:
				weekofDay = "周二";
				break;
			case 4:
				weekofDay = "周三";
				break;
			case 5:
				weekofDay = "周四";
				break;
			case 6:
				weekofDay = "周五";
				break;
			case 7:
				weekofDay = "周六";
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return weekofDay;
	}

	/**
	 * 根据给定的格式把日期格式化为字符串
	 * 
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 根据给定的格式,转化时间格式
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date dateToDateFormat(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			date = formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("");
			e.printStackTrace();
		}
		return date;
	}

	//
	public static String getSysTimeStr_yymmdd() {
		// 按指定格式格式化日期
		SimpleDateFormat formater = new SimpleDateFormat(yyyyMMdd);
		// 返回格式化后的结果
		return formater.format(getSysTime());
	}

	public static int todayMinute() {
		Calendar c = Calendar.getInstance();
		int hh = c.get(Calendar.HOUR_OF_DAY);
		int mm = c.get(Calendar.MINUTE);
		return hh * 60 + mm;
	}

	// 根据传入时间转换为 2013年11月22日 晚上12点前 的形式
	@SuppressWarnings("deprecation")
	public static String dateStrToShowZZW(String date) {
		Date datetime = DateUtils.stringToDate(date, yyyyMMddHHmmssForLoseTime);
		int h = datetime.getHours();
		StringBuffer sb = new StringBuffer(dateToString(datetime, yyyyNMMYdd)).append(" ");
		if (h > 0 && h <= 12) {
			sb.append("上午12");
		} else {
			sb.append("晚上24");
		}
		return sb.append("点前").toString();
	}

	// 根据传入时间转换为 2013年11月22日 晚上12点前 的形式
	public static String dateToShowZZW(Calendar c) {
		int h = c.get(Calendar.HOUR_OF_DAY);
		StringBuffer sb = new StringBuffer();
		if (h > 0 && h <= 12) {
			sb.append(dateToString(c.getTime(), yyyyNMMYdd)).append(" 上午12");
		} else {
			c.add(Calendar.DATE, -1);
			sb.append(dateToString(c.getTime(), yyyyNMMYdd)).append(" 晚上24");
		}
		return sb.append("点").toString();
	}

	// 根据字符才HHmm计算当天已过的分钟数
	public static int HHmmToMintue(String strHHmm) {
		String strHour = strHHmm.substring(0, 2);
		String strMinute = strHHmm.substring(2);
		int iMinute = Integer.parseInt(strHour) * 60 + Integer.parseInt(strMinute);
		return iMinute;
	}

	/**
	 * 
	 * inDateStr Jun 5 2011 1:47:19:073PM outStr 20110605014719073
	 * 
	 * @author Ylp
	 * @param inDateStr
	 * @return
	 */
	public static String ConverToString(String inDateStr) {
		// "Jun 5 2011 1:47:19:073PM" ——
		Locale locale = new Locale("en", "US");
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy HH:mm:ss:SSS", locale);

		String retStr = null;
		try {
			Date d = sdf.parse(inDateStr);
			retStr = dateToString(d, yyyyMMddHHmmssSSS);
		} catch (ParseException e) {
			logger.error("Convert " + inDateStr + " to string failed! format:  MMM dd yyyy HH:mm:ss:SSS,locale", e);
			e.printStackTrace();
		}

		return retStr;
	}

	/**
	 * 获得系统当前年月日
	 * 
	 * @author yichao
	 * @return
	 */
	public static String getCurrentTime() {
		Date currentTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(currentTime);
	}

	/**
	 * 计算两个日期之间的相差多长时间
	 * 
	 * @param sDate1
	 * @param sDate2
	 * @return
	 */
	public static int dayDiff(String sDate1, String sDate2) {
		// inDate format: YYYYMMDD
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		java.util.Date dBegin, dEnd;
		Calendar calendar = Calendar.getInstance();
		long timeBegin, timeEnd;
		int dayDiff = 0;
		try {
			if ("today".equalsIgnoreCase(sDate1)) {
				dBegin = sdf1.parse(sdf1.format(calendar.getTime()));
			} else {
				dBegin = sdf1.parse(sDate1);
			}
			timeBegin = dBegin.getTime();
			if ("today".equalsIgnoreCase(sDate2)) {
				dEnd = sdf1.parse(sdf1.format(calendar.getTime()));
			} else {
				dEnd = sdf1.parse(sDate2);
			}
			timeEnd = dEnd.getTime();

			// Calendar calendar = Calendar.getInstance();

			dayDiff = (int) (timeEnd / (1000 * 60 * 60 * 24) - timeBegin / (1000 * 60 * 60 * 24));
			// System.out.println(dayDiff);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayDiff;
	}

	/**
	 * 日期相加
	 * 
	 * @param sDatePart
	 * @param iPartValue
	 * @param sDate
	 * @return
	 */
	public static String dateAdd(String sDatePart, int iPartValue, String sDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		java.util.Date dBegin, dEnd;
		long timeBegin, timeEnd, timeAdd = 0;

		String sDatePartTmp = "", sOutDate = "";

		try {
			dBegin = sdf.parse(sDate);// 20090514

			timeBegin = dBegin.getTime();// 把今天转为毫秒

			sDatePartTmp = sDatePart.toLowerCase();// dd
			if (sDatePartTmp == "dd") {
				timeAdd = iPartValue * 1000 * 60 * 60 * 24;// 0
			}
			/*
			 * else if(sDatePartTmp == "mm"){ timeAdd = iPartValue * 1000 * 60 * 60 * 24 *
			 * (28|29|30|31); }else if(sDatePartTmp == "yy"){ timeAdd = iPartValue * 1000 *
			 * 60 * 60 * (365|366); }
			 */
			else if (sDatePartTmp == "hh") {
				timeAdd = iPartValue * 1000 * 60 * 60;
			} else if (sDatePartTmp == "mi") {
				timeAdd = iPartValue * 1000 * 60;
			} else if (sDatePartTmp == "ss") {
				timeAdd = iPartValue * 1000;
			} else {
				timeAdd = 0;
			}

			timeEnd = timeBegin + timeAdd;
			dEnd = new Date(timeEnd);// timeEnd今天毫秒+查询毫秒

			// System.out.println(sdf.format(dEnd));
			sOutDate = sdf.format(dEnd);// 把总时间转为yyyyMMdd

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutDate;
	}

	/**
	 * 添加时间，如果是减少时间则设置数字为-1 ，-2返回增减后的Date类型日期
	 * 
	 * 
	 * @author Administrator
	 * @param changeDate
	 *            要改变的日期
	 * @param addDayNum
	 *            增加或减少的天数
	 * @return
	 */
	public static Date addDayForDate(Date changeDate, int DayNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(changeDate);
		cal.add(Calendar.DAY_OF_MONTH, DayNum);
		return cal.getTime();
	}

	/**
	 * 获取预售期内的时间集合MM-DD格式
	 * 
	 * @param current_date
	 * @param control_date
	 * @return
	 */
	public static List<String> getDateRange(String current_date, String control_date) {
		List<String> result = new ArrayList<String>();
		try {
			current_date = StringUtils.trim(current_date);
			control_date = StringUtils.trim(control_date);
			if (StringUtils.isEmpty(current_date) || StringUtils.isEmpty(control_date)) {
				return result;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.yyyyMMddSplit);
			int i = diffDate(sdf.parse(control_date), sdf.parse(current_date));
			// String _current_date=current_date.substring(5);
			// String _control_date=control_date.substring(5);
			if (i < 0) {
				String temp = current_date;
				current_date = control_date;
				control_date = temp;
				i = -1 * i;
			}
			// current_date = this.getWeekByDate(current_date);
			result.add(current_date.substring(5));
			String string_date = new String(current_date.getBytes("UTF-8"), "UTF-8");
			while (i > 0) {
				string_date = turnDate(string_date, DateUtils.yyyyMMddSplit, 1);
				// 将长日期拼起来用于回显
				// string_date = this.getWeekByDate(string_date);
				result.add(string_date.substring(5));
				i--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取预售期内的时间集合YY-MM-DD格式
	 * 
	 * @param current_date
	 * @param control_date
	 * @return
	 */
	public static List<String> getFullDateRange(String current_date, String control_date) {
		List<String> result = new ArrayList<String>();
		try {
			current_date = StringUtils.trim(current_date);
			control_date = StringUtils.trim(control_date);
			if (StringUtils.isEmpty(current_date) || StringUtils.isEmpty(control_date)) {
				return result;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.yyyyMMddSplit);
			int i = diffDate(sdf.parse(control_date), sdf.parse(current_date));
			// String _current_date=current_date.substring(5);
			// String _control_date=control_date.substring(5);
			if (i < 0) {
				String temp = current_date;
				current_date = control_date;
				control_date = temp;
				i = -1 * i;
			}
			// current_date = this.getWeekByDate(current_date);
			result.add(current_date);
			String string_date = new String(current_date.getBytes("UTF-8"), "UTF-8");
			while (i > 0) {
				string_date = turnDate(string_date, DateUtils.yyyyMMddSplit, 1);
				// 将长日期拼起来用于回显
				// string_date = this.getWeekByDate(string_date);
				result.add(string_date);
				i--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 计算两个日期之间相差的天数/小时/分钟
	 * 
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @param str
	 * @return
	 */
	public static Long dateDiff(Date startTime, Date endTime, String str) {
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		// 获得两个时间的毫秒时间差异
		try {
			diff = endTime.getTime() - startTime.getTime();
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh + day * 24;// 计算差多少小时
			min = diff % nd % nh / nm + hour * 60 + day * 24 * 60;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒
			// 输出结果
			System.out.println(
					"时间相差：" + day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟" + sec + "秒。");
			System.out.println("hour=" + hour + ",min=" + min);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (str.equalsIgnoreCase("d")) {
			return day;
		} else if (str.equalsIgnoreCase("h")) {
			return hour;
		} else if (str.equalsIgnoreCase("m")) {
			return min;
		} else {
			return sec;
		}
	}

	/**
	 * 获取系统当前日期（字符串类型）: 默认格式yyyy年MM月dd日
	 * 
	 * @param format
	 *            可以制定返回日期串格式，如果不指定返回默认格式：yyyy年MM月dd日
	 * @return
	 */
	public static String getCurrentDateStr(String format) {
		if (format == null || "".equals(format)) {
			format = yyyyNMMYdd;
		}
		// 按指定格式格式化日期
		SimpleDateFormat formater = new SimpleDateFormat(format);
		// 返回格式化后的结果
		return formater.format(getSysTime());
	}

	/**
	 * 获取系统当前日期（字符串类型）: 格式yyyy年MM月dd日
	 * 
	 * @return
	 */
	public static String getCurrentDateStr_yyyyNMMYdd() {
		return getCurrentDateStr(null);
	}
}
