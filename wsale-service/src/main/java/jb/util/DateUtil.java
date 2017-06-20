package jb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jb.absx.F;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

public final class DateUtil {
	
	private static final Logger log = Logger.getLogger(DateUtil.class);
	
	/**
	 * 将日期字符串转化成日期类型
	 * 
	 * @param date
	 * @param fmt
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String date, String fmt) {
		try {
			if(F.empty(date)) return null;
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			Date newdate = sdf.parse(date);
			return newdate;
		} catch (Exception e) {
			log.error("Util>>DateUtil.parse>>" + date + ">>" + fmt+"-->>" +e.getMessage());
			return null;
		}
	}	

	public static String format(Date date, String fmt) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}

	/**
	 * 给Date加s秒
	 * @param date
	 * @param s
	 * @return
	 */
	public static Date addSecondToDate(Date date, int s) {  
	    Calendar calendar = Calendar.getInstance();  
	    calendar.setTime(date);  
	    calendar.add(Calendar.SECOND, s);  
	    return calendar.getTime();  
	}
	
	/**
	 * 给Date加m分钟
	 * @param date
	 * @param m
	 * @return
	 */
	public static Date addMinuteToDate(Date date, int m) {  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);  
		calendar.add(Calendar.MINUTE, m);  
		return calendar.getTime();  
	}
	
	/**'
	 * 给Date加h小时
	 * @param date
	 * @param m
	 * @return
	 */
	public static Date addHourToDate(Date date, int h) {  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);  
		calendar.add(Calendar.HOUR, h);  
		return calendar.getTime();  
	}
	
	/**
	 * 给Date加d天
	 * @param date
	 * @param d
	 * @return
	 */
	public static Date addDayToDate(Date date, int d) {  
		return DateUtils.addDays(date, d);  
	}
	
	/**
	 * 给Date加m月
	 * @param date
	 * @param m
	 * @return
	 */
	public static Date addMonthToDate(Date date, int m) {  
		return DateUtils.addMonths(date, m);  
	}
	
	/**
	 * 给Date加y年
	 * @param date
	 * @param y
	 * @return
	 */
	public static Date addYearToDate(Date date, int y) {  
		return DateUtils.addYears(date, y);  
	}
	
	/**
	 * 给Date加w周
	 * @param date
	 * @param w
	 * @return
	 */
	public static Date addWeekToDate(Date date, int w) {  
		return DateUtils.addWeeks(date, w);  
	}	
	
	/**
	 * 根据用户生日计算年龄
	 */
	public static int getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}
	
	/**
	 * 根据用户年龄计算生日（月份默认为1月份，日期默认为1号）
	 */
	public static Date getBirthdayByAge(int age) {
		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);
		int yearBirth = yearNow - age;
		cal.set(yearBirth, 0, 1, 0, 0, 0);
		return cal.getTime();
	}
	
}