
package com.foresys.core.util;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	
	private static TimeZone timezone = TimeZone.getTimeZone("Asia/Seoul");

    public static String getYear(){
    	return getDateFormatString("yyyy");
    }

    public static String getMonth(){
    	return getDateFormatString("MM");
    }

    public static String getDay(){
    	return getDateFormatString("dd");
    }

    /**
     *
     * @return
     */
    public static String getHour(){
    	return getDateFormatString("HH");
    }
    /**
     *
     * @return
     */
    public static String getMinute(){
    	return getDateFormatString("mm");
    }

    /**
     *
     * @return
     */
    public static String getHourMinute(){
    	return getDateFormatString("HHmm");
    }

    /**
     * ��
     */
    public static String getSecond(){
    	return getDateFormatString("ss");
    }

   /**
    *
    * @return
    */
    public static String getMilli(){
    	return getDateFormatString("SSS");
    }

    /**
     *
     * @param delimeter
     * @return
     */
    public static String getYearMonth(String delimeter){
    	String formatStr = "yyyy" + delimeter + "MM";
    	return getDateFormatString(formatStr);
    }
    /**
     *
     * @return
     */
    public static String getYearMonth(){
    	return getDateFormatString("yyyyMM");
    }

    /**
     *
     * @param delimeter
     * @return
     */
    public static String getYearMonthDay(String delimeter){
    	String formatStr = "yyyy" + delimeter + "MM" + delimeter + "dd";
    	return getDateFormatString(formatStr);
    }

    /**
     *
     * @param i
     * @param delimeter
     * @return
     */
    public static String getYearMonthDay(int i , String delimeter){
    	Calendar g = GregorianCalendar.getInstance(timezone, Locale.KOREAN);
    	g.add(Calendar.DATE, i);
    	Date d = g.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy"+delimeter+"MM"+delimeter+"dd", Locale.KOREAN);
    	return sdf.format(d);
    }


    public static String getDateTimeUms(int i){
    	Calendar g = GregorianCalendar.getInstance(timezone, Locale.KOREAN);
    	g.add(Calendar.DATE, -i);
    	Date d = g.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
    	return sdf.format(d);
    }

    public static int compareDate(String sDate, String eDate){

        return Integer.parseInt(sDate) -  Integer.parseInt(eDate);

    }

	/**
     * useing : getCalsDate(Calendar.MONTH, 10, "yyyyMMdd");
     *
     * @param field  --> Calendar.YEAR, Calendar.MONTH, Calendar.DATE
     * @param amount -->
     * @param dateFormatStr -->  ex) yyyy
     * @return
     * @throws Exception
     */
    public static String getCalsDate(int field, int amount, String dateFormatStr) {
    	Calendar g = GregorianCalendar.getInstance(timezone, Locale.KOREAN);
    	g.add(field, amount);
    	Date d = g.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr, Locale.KOREAN);
    	return sdf.format(d);
    }

    /**
     * useing : getCalsDate(Calendar.MONTH, 10, "yyyyMMdd");
     *
     * @param field  --> Calendar.YEAR, Calendar.MONTH, Calendar.DATE
     * @param amount -->
     * @param dateFormatStr -->  ex) yyyy MM dd
     * @return
     * @throws Exception
     */
    public static String getCalsDate(int field, int amount, String dateFormatStr, String beforeDate){
    	Calendar cal = GregorianCalendar.getInstance(timezone, Locale.KOREAN);
    	if(beforeDate != null){
    		if(!"".equals(beforeDate)){
    			cal.set(Integer.parseInt(beforeDate.substring(0,4)),Integer.parseInt(beforeDate.substring(4,6))-1,Integer.parseInt(beforeDate.substring(6)), 0, 0, 0);	 // ������ ����
    		}
    	}

    	cal.add(field, amount);
    	Date d = cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr, Locale.KOREAN);
    	return sdf.format(d);
    }

    /**
     * useing : getCalsHour(10, "HHmmss");
     *
     * @param field  --> Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR
     * @param amount -->
     * @param formatStr -->  ex) yyyy
     * @return
     * @throws Exception
     */
	public static String getCalsHour(int amount, String formatStr, String beforeDate){
		Calendar cal = GregorianCalendar.getInstance(timezone, Locale.KOREAN);
    	if(beforeDate != null){
    		if(!"".equals(beforeDate)){
    			cal.set(0,0,0,Integer.parseInt(beforeDate.substring(0,2)),Integer.parseInt(beforeDate.substring(2,4))-1,Integer.parseInt(beforeDate.substring(4,6)));	 // ������ ����
    		}
    	}

    	cal.add(Calendar.HOUR, amount);
    	Date d = cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat(formatStr, Locale.KOREAN);
    	return sdf.format(d);
	}
	
	public static String getCurrentYyyyMMddHHmmss()  {
    	return getDateFormatString("yyyyMMddHHmmss");
    }

    /**
     *
     * @return
     */
    public static String getCurrentYyyyMMddHHmmssSSS()  {
    	return getDateFormatString("yyyyMMddHHmmssSSS");
    }
    /**
     * "yyyyMMddHHmmssSSS"
     * @param format
     * @return
     */
    public static String getDateFormatString(String formatStr){

    	Calendar g = GregorianCalendar.getInstance(timezone, Locale.KOREAN);
    	Date d = g.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat(formatStr, Locale.KOREAN);
    	return sdf.format(d);
    }

}
