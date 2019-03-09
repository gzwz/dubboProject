/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.qumiandan.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil extends DateUtils {
	

    // ==格式到年==   
    /** 
     * 日期格式，年份，例如：2004，2008 
     */  
    public static final String DATE_FORMAT_YYYY = "yyyy";  
      
    // ==格式到年月 ==   
    /** 
     * 日期格式，年份和月份，例如：200707，200808 
     */  
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";  
  
    /** 
     * 日期格式，年份和月份，例如：200707，2008-08 
     */  
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";  
      
    // ==格式到年月日==   
    /** 
     * 日期格式，年月日，例如：050630，080808 
     */  
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";  
  
    /** 
     * 日期格式，年月日，用横杠分开，例如：06-12-25，08-08-08 
     */  
    public static final String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";  
  
    /** 
     * 日期格式，年月日，例如：20050630，20080808 
     */  
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";  
      
    /** 
     * 日期格式，年月日，用横杠分开，例如：2006-12-25，2008-08-08 
     */  
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";  
      
    /** 
     * 日期格式，年月日，例如：2016.10.05 
     */  
    public static final String DATE_FORMAT_POINTYYYYMMDD = "yyyy.MM.dd";  
      
    /** 
     * 日期格式，年月日，例如：2016年10月05日 
     */  
    public static final String DATE_TIME_FORMAT_YYYY年MM月DD日 = "yyyy年MM月dd日";  
      
    // ==格式到年月日 时分 ==   
    /** 
     * 日期格式，年月日时分，例如：200506301210，200808081210 
     */  
    public static final String DATE_FORMAT_YYYYMMDDHHmm = "yyyyMMddHHmm";  
  
    /** 
     * 日期格式，年月日时分，例如：20001230 12:00，20080808 20:08 
     */  
    public static final String DATE_TIME_FORMAT_YYYYMMDD_HH_MI = "yyyyMMdd HH:mm";  
      
    /** 
     * 日期格式，年月日时分，例如：2000-12-30 12:00，2008-08-08 20:08 
     */  
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI = "yyyy-MM-dd HH:mm";  
      
    // ==格式到年月日 时分秒==   
    /** 
     * 日期格式，年月日时分秒，例如：20001230120000，20080808200808 
     */  
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";  
      
    /** 
     * 日期格式，年月日时分秒，年月日用横杠分开，时分秒用冒号分开 
     * 例如：2005-05-10 23：20：00，2008-08-08 20:08:08 
     */  
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";  
  
    // ==格式到年月日 时分秒 毫秒==   
    /** 
     * 日期格式，年月日时分秒毫秒，例如：20001230120000123，20080808200808456 
     */  
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS = "yyyyMMddHHmmssSSS";  
      
      
    // ==特殊格式==  
    /** 
     * 日期格式，月日时分，例如：10-05 12:00 
     */  
    public static final String DATE_FORMAT_MMDDHHMI = "MM-dd HH:mm";  
    
	public static final String TIME_WITH_MINUTE_PATTERN = "HH:mm";

	public static final long DAY_MILLI = 24 * 60 * 60 * 1000; // 一天的MilliSecond

	public final static int LEFT_OPEN_RIGHT_OPEN = 1;
	public final static int LEFT_CLOSE_RIGHT_OPEN = 2;
	public final static int LEFT_OPEN_RIGHT_CLOSE = 3;
	public final static int LEFT_CLOSE_RIGHT_CLOSE = 4;
	/**
	 * 比较日期的模式 --只比较日期，不比较时间
	 */
	public final static int COMP_MODEL_DATE = 1;
	/**
	 * 比较日期的模式 --只比较时间，不比较日期
	 */
	public final static int COMP_MODEL_TIME = 2;
	/**
	 * 比较日期的模式 --比较日期，也比较时间
	 */
	public final static int COMP_MODEL_DATETIME = 3;


	/**
	 * 要用到的DATE Format的定义
	 */
	
	
	public static SimpleDateFormat sdfDateTime = new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
	// Global SimpleDateFormat object
	public static SimpleDateFormat sdfDateOnly = new SimpleDateFormat(DateUtil.DATE_FORMAT_YYYY_MM_DD);
	public static final SimpleDateFormat SHORTDATEFORMAT = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD);
	public static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
	public static final SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat HMS_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat formatTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	
	 
	    /* ************工具方法***************   */  
     
	    /**  
	     * 获取某日期的年份 
	     * @param date  
	     * @return  
	     */  
	    public static Integer getYear(Date date) {  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(date);  
	        return cal.get(Calendar.YEAR);  
	    }    
	      
	    /** 
	     * 获取某日期的月份 
	     * @param date 
	     * @return 
	     */  
	    public static Integer getMonth(Date date) {  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(date);  
	        return cal.get(Calendar.MONTH) + 1;  
	    }  
	      
	    /** 
	     * 获取某日期的日数 
	     * @param date 
	     * @return 
	     */  
	    public static Integer getDay(Date date){  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(date);  
	         int day=cal.get(Calendar.DATE);//获取日  
	         return day;  
	    }  
	      
	    /** 
	     * 格式化Date时间 
	     * @param time Date类型时间 
	     * @param timeFromat String类型格式 
	     * @return 格式化后的字符串 
	     */  
	    public static String parseDateToStr(Date time, String timeFromat){  
	        DateFormat dateFormat=new SimpleDateFormat(timeFromat);  
	        return dateFormat.format(time);  
	    }  
	      
	    /** 
	     * 格式化Timestamp时间 
	     * @param timestamp Timestamp类型时间 
	     * @param timeFromat 
	     * @return 格式化后的字符串 
	     */  
	    public static String parseTimestampToStr(Timestamp timestamp,String timeFromat){  
	        SimpleDateFormat df = new SimpleDateFormat(timeFromat);  
	        return df.format(timestamp);  
	    }  
	      
	    /** 
	     * 格式化Date时间 
	     * @param time Date类型时间 
	     * @param timeFromat String类型格式 
	     * @param defaultValue 默认值为当前时间Date 
	     * @return 格式化后的字符串 
	     */  
	    public static String parseDateToStr(Date time, String timeFromat, final Date defaultValue){  
	        try{  
	            DateFormat dateFormat=new SimpleDateFormat(timeFromat);  
	            return dateFormat.format(time);  
	        }catch (Exception e){  
	            if(defaultValue!=null)  
	                return parseDateToStr(defaultValue, timeFromat);  
	            else  
	                return parseDateToStr(new Date(), timeFromat);  
	        }  
	    }  
	      
	    /** 
	     * 格式化Date时间 
	     * @param time Date类型时间 
	     * @param timeFromat String类型格式 
	     * @param defaultValue 默认时间值String类型 
	     * @return 格式化后的字符串 
	     */  
	    public static String parseDateToStr(Date time, String timeFromat, final String defaultValue){  
	        try{  
	            DateFormat dateFormat=new SimpleDateFormat(timeFromat);  
	            return dateFormat.format(time);  
	        }catch (Exception e){  
	            return defaultValue;  
	        }  
	    }  
	      
	    /** 
	     * 格式化String时间 
	     * @param time String类型时间 
	     * @param timeFromat String类型格式 
	     * @return 格式化后的Date日期 
	     */  
	    public static Date parseStrToDate(String time, String timeFromat) {  
	        if (time == null || time.equals("")) {  
	            return null;  
	        }  
	          
	        Date date=null;  
	        try{  
	            DateFormat dateFormat=new SimpleDateFormat(timeFromat);  
	            date=dateFormat.parse(time);  
	        }catch(Exception e){  
	              
	        }  
	        return date;  
	    }  
	      
	    /** 
	     * 格式化String时间 
	     * @param strTime String类型时间 
	     * @param timeFromat String类型格式 
	     * @param defaultValue 异常时返回的默认值 
	     * @return 
	     */  
	    public static Date parseStrToDate(String strTime, String timeFromat,  
	            Date defaultValue) {  
	        try {  
	            DateFormat dateFormat = new SimpleDateFormat(timeFromat);  
	            return dateFormat.parse(strTime);  
	        } catch (Exception e) {  
	            return defaultValue;  
	        }  
	    }  
	      
	    /** 
	     * 当strTime为2008-9时返回为2008-9-1 00:00格式日期时间，无法转换返回null. 
	     * @param strTime 
	     * @return 
	     */  
	    public static Date strToDate(String strTime) {  
	        if(strTime==null || strTime.trim().length()<=0)  
	            return null;  
	          
	        Date date = null;  
	        List<String> list = new ArrayList<String>(0);  
	          
	        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);  
	        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS);  
	        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI);  
	        list.add(DATE_TIME_FORMAT_YYYYMMDD_HH_MI);  
	        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISS);  
	        list.add(DATE_FORMAT_YYYY_MM_DD);  
	        //list.add(DATE_FORMAT_YY_MM_DD);  
	        list.add(DATE_FORMAT_YYYYMMDD);  
	        list.add(DATE_FORMAT_YYYY_MM);  
	        list.add(DATE_FORMAT_YYYYMM);  
	        list.add(DATE_FORMAT_YYYY);  
	          
	          
	        for (Iterator<String> iter = list.iterator(); iter.hasNext();) {  
	            String format = (String) iter.next();  
	            if(strTime.indexOf("-")>0 && format.indexOf("-")<0)  
	                continue;  
	            if(strTime.indexOf("-")<0 && format.indexOf("-")>0)  
	                continue;  
	            if(strTime.length()>format.length())  
	                continue;  
	            date = parseStrToDate(strTime, format);  
	            if (date != null)  
	                break;  
	        }  
	  
	        return date;  
	    }  
	      
	    /** 
	     * 解析两个日期之间的所有月份 
	     * @param beginDateStr 开始日期，至少精确到yyyy-MM 
	     * @param endDateStr 结束日期，至少精确到yyyy-MM 
	     * @return yyyy-MM日期集合 
	     */    
	    public static List<String> getMonthListOfDate(String beginDateStr, String endDateStr) {    
	        // 指定要解析的时间格式    
	        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");    
	        // 返回的月份列表    
	        String sRet = "";    
	    
	        // 定义一些变量    
	        Date beginDate = null;    
	        Date endDate = null;    
	    
	        GregorianCalendar beginGC = null;    
	        GregorianCalendar endGC = null;    
	        List<String> list = new ArrayList<String>();    
	    
	        try {    
	            // 将字符串parse成日期    
	            beginDate = f.parse(beginDateStr);    
	            endDate = f.parse(endDateStr);    
	    
	            // 设置日历    
	            beginGC = new GregorianCalendar();    
	            beginGC.setTime(beginDate);    
	    
	            endGC = new GregorianCalendar();    
	            endGC.setTime(endDate);    
	    
	            // 直到两个时间相同    
	            while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {    
	                sRet = beginGC.get(Calendar.YEAR) + "-"    
	                        + (beginGC.get(Calendar.MONTH) + 1);    
	                list.add(sRet);    
	                // 以月为单位，增加时间    
	                beginGC.add(Calendar.MONTH, 1);    
	            }    
	            return list;    
	        } catch (Exception e) {    
	            e.printStackTrace();    
	            return null;    
	        }    
	    }    
	    
	    /**  
	     * 解析两个日期段之间的所有日期 
	     * @param beginDateStr 开始日期  ，至少精确到yyyy-MM-dd 
	     * @param endDateStr 结束日期  ，至少精确到yyyy-MM-dd 
	     * @return yyyy-MM-dd日期集合 
	     */    
	    public static List<String> getDayListOfDate(String beginDateStr, String endDateStr) {    
	        // 指定要解析的时间格式    
	        SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);    
	    
	        // 定义一些变量    
	        Date beginDate = null;    
	        Date endDate = null;    
	    
	        Calendar beginGC = null;    
	        Calendar endGC = null;    
	        List<String> list = new ArrayList<String>();    
	    
	        try {    
	            // 将字符串parse成日期    
	            beginDate = f.parse(beginDateStr);    
	            endDate = f.parse(endDateStr);    
	    
	            // 设置日历    
	            beginGC = Calendar.getInstance();    
	            beginGC.setTime(beginDate);    
	    
	            endGC = Calendar.getInstance();    
	            endGC.setTime(endDate);    
	            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);    
	    
	            // 直到两个时间相同    
	            while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {    
	    
	                list.add(sdf.format(beginGC.getTime()));    
	                // 以日为单位，增加时间    
	                beginGC.add(Calendar.DAY_OF_MONTH, 1);    
	            }    
	            return list;    
	        } catch (Exception e) {    
	            e.printStackTrace();    
	            return null;    
	        }    
	    }    
	    
	    /** 
	     * 获取当下年份指定前后数量的年份集合 
	     * @param before 当下年份前年数 
	     * @param behind 当下年份后年数 
	     * @return 集合 
	     */  
	    public static List<Integer> getYearListOfYears(int before,int behind) {  
	        if (before<0 || behind<0) {  
	            return null;  
	        }  
	        List<Integer> list = new ArrayList<Integer>();    
	        Calendar c = null;    
	        c = Calendar.getInstance();    
	        c.setTime(new Date());    
	        int currYear = Calendar.getInstance().get(Calendar.YEAR);    
	    
	        int startYear = currYear - before;    
	        int endYear = currYear + behind;    
	        for (int i = startYear; i < endYear; i++) {    
	            list.add(Integer.valueOf(i));    
	        }    
	        return list;    
	    }  
	      
	    /**  
	     * 获取当前日期是一年中第几周  
	     * @param date  
	     * @return  
	     */    
	    public static Integer getWeekthOfYear(Date date) {    
	        Calendar c = new GregorianCalendar();    
	        c.setFirstDayOfWeek(Calendar.MONDAY);    
	        c.setMinimalDaysInFirstWeek(7);    
	        c.setTime(date);    
	    
	        return c.get(Calendar.WEEK_OF_YEAR);    
	    }   
	  
	    /** 
	     * 获取某一年各星期的始终时间 
	     * 实例：getWeekList(2016)，第52周(从2016-12-26至2017-01-01) 
	     * @param 年份 
	     * @return 
	     */    
	    public static HashMap<Integer,String> getWeekTimeOfYear(int year) {    
	        HashMap<Integer,String> map = new LinkedHashMap<Integer,String>();    
	        Calendar c = new GregorianCalendar();    
	        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);    
	        int count = getWeekthOfYear(c.getTime());    
	    
	        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);    
	        String dayOfWeekStart = "";    
	        String dayOfWeekEnd = "";    
	        for (int i = 1; i <= count; i++) {    
	            dayOfWeekStart = sdf.format(getFirstDayOfWeek(year, i));    
	            dayOfWeekEnd = sdf.format(getLastDayOfWeek(year, i));    
	            map.put(Integer.valueOf(i), "第"+i+"周(从"+dayOfWeekStart + "至" + dayOfWeekEnd+")");    
	        }    
	        return map;    
	    
	    }    
	        
	    /**  
	     * 获取某一年的总周数  
	     * @param year  
	     * @return  
	     */    
	    public static Integer getWeekCountOfYear(int year){    
	        Calendar c = new GregorianCalendar();    
	        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);    
	        int count = getWeekthOfYear(c.getTime());    
	        return count;    
	    }    
	      
	    /**  
	     * 获取指定日期所在周的第一天  
	     * @param date  
	     * @return  
	     */    
	    public static Date getFirstDayOfWeek(Date date) {    
	        Calendar c = new GregorianCalendar();    
	        c.setFirstDayOfWeek(Calendar.MONDAY);    
	        c.setTime(date);    
	        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday    
	        return c.getTime();    
	    }    
	    
	    /**  
	     * 获取指定日期所在周的最后一天  
	     * @param date  
	     * @return  
	     */    
	    public static Date getLastDayOfWeek(Date date) {  
	        Calendar c = new GregorianCalendar();  
	        c.setFirstDayOfWeek(Calendar.MONDAY);  
	        c.setTime(date);  
	        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday  
	        return c.getTime();  
	    }  
	    
	    /**  
	     * 获取某年某周的第一天  
	     * @param year 目标年份 
	     * @param week 目标周数 
	     * @return  
	     */    
	    public static Date getFirstDayOfWeek(int year, int week) {    
	        Calendar c = new GregorianCalendar();    
	        c.set(Calendar.YEAR, year);    
	        c.set(Calendar.MONTH, Calendar.JANUARY);    
	        c.set(Calendar.DATE, 1);    
	    
	        Calendar cal = (GregorianCalendar) c.clone();  
	        cal.add(Calendar.DATE, week * 7);    
	    
	        return getFirstDayOfWeek(cal.getTime());    
	    }    
	    
	    /**  
	     * 获取某年某周的最后一天  
	     * @param year 目标年份 
	     * @param week 目标周数 
	     * @return  
	     */    
	    public static Date getLastDayOfWeek(int year, int week) {    
	        Calendar c = new GregorianCalendar();    
	        c.set(Calendar.YEAR, year);    
	        c.set(Calendar.MONTH, Calendar.JANUARY);    
	        c.set(Calendar.DATE, 1);    
	    
	        Calendar cal = (GregorianCalendar) c.clone();    
	        cal.add(Calendar.DATE, week * 7);    
	    
	        return getLastDayOfWeek(cal.getTime());    
	    }    
	        
	    /**  
	     * 获取某年某月的第一天  
	     * @param year 目标年份 
	     * @param month 目标月份 
	     * @return  
	     */    
	    public static Date getFirstDayOfMonth(int year,int month){    
	        month = month-1;    
	        Calendar   c   =   Calendar.getInstance();       
	        c.set(Calendar.YEAR, year);    
	        c.set(Calendar.MONTH, month);    
	            
	        int day = c.getActualMinimum(Calendar.DAY_OF_MONTH);    
	    
	        c.set(Calendar.DAY_OF_MONTH, day);  
	        c.set(Calendar.HOUR_OF_DAY, 0);  
	        c.set(Calendar.MINUTE, 0);  
	        c.set(Calendar.SECOND, 0);  
	        c.set(Calendar.MILLISECOND, 0);  
	        return c.getTime();  
	    }    
	        
	    /**  
	     * 获取某年某月的最后一天  
	     * @param year 目标年份 
	     * @param month 目标月份 
	     * @return  
	     */    
	    public static Date getLastDayOfMonth(int year,int month){    
	        month = month-1;    
	        Calendar   c   =   Calendar.getInstance();       
	        c.set(Calendar.YEAR, year);    
	        c.set(Calendar.MONTH, month);    
	        int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);    
	        c.set(Calendar.DAY_OF_MONTH, day);  
	        c.set(Calendar.HOUR_OF_DAY, 23);  
	        c.set(Calendar.MINUTE, 59);  
	        c.set(Calendar.SECOND, 59);  
	        c.set(Calendar.MILLISECOND, 999);  
	        return c.getTime();    
	    }    
	    
	    /**  
	     * 获取某个日期为星期几  
	     * @param date  
	     * @return String "星期*" 
	     */    
	    public static String getDayWeekOfDate1(Date date) {    
	         String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};  
	         Calendar cal = Calendar.getInstance();  
	         cal.setTime(date);  
	  
	         int w = cal.get(Calendar.DAY_OF_WEEK) - 1;  
	         if (w < 0)  
	             w = 0;  
	           
	         return weekDays[w];  
	    }    
	      
	    /** 
	     * 获得指定日期的星期几数 
	     * @param date 
	     * @return int  
	     */   
	    public static Integer getDayWeekOfDate2(Date date){    
	        Calendar aCalendar = Calendar.getInstance();    
	        aCalendar.setTime(date);       
	        int weekDay = aCalendar.get(Calendar.DAY_OF_WEEK);       
	        return weekDay;  
	    }  
	      
	    /** 
	     * 验证字符串是否为日期 
	     * 验证格式:YYYYMMDD、YYYY_MM_DD、YYYYMMDDHHMISS、YYYYMMDD_HH_MI、YYYY_MM_DD_HH_MI、YYYYMMDDHHMISSSSS、YYYY_MM_DD_HH_MI_SS 
	     * @param strTime 
	     * @return null时返回false;true为日期，false不为日期 
	     */  
	    public static boolean validateIsDate(String strTime) {  
	        if (strTime == null || strTime.trim().length() <= 0)  
	            return false;  
	          
	        Date date = null;  
	        List<String> list = new ArrayList<String>(0);  
	          
	        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);  
	        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS);  
	        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI);  
	        list.add(DATE_TIME_FORMAT_YYYYMMDD_HH_MI);  
	        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISS);  
	        list.add(DATE_FORMAT_YYYY_MM_DD);  
	        //list.add(DATE_FORMAT_YY_MM_DD);  
	        list.add(DATE_FORMAT_YYYYMMDD);  
	        //list.add(DATE_FORMAT_YYYY_MM);  
	        //list.add(DATE_FORMAT_YYYYMM);  
	        //list.add(DATE_FORMAT_YYYY);  
	          
	        for (Iterator<String> iter = list.iterator(); iter.hasNext();) {  
	            String format = (String) iter.next();  
	            if(strTime.indexOf("-")>0 && format.indexOf("-")<0)  
	                continue;  
	            if(strTime.indexOf("-")<0 && format.indexOf("-")>0)  
	                continue;  
	            if(strTime.length()>format.length())  
	                continue;  
	            date = parseStrToDate(strTime.trim(), format);  
	            if (date != null)  
	                break;  
	        }  
	          
	        if (date != null) {  
	            System.out.println("生成的日期:"+DateUtil.parseDateToStr(date, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS, "--null--"));  
	            return true;  
	        }  
	        return false;  
	    }  
	      
	    /** 
	     * 将指定日期的时分秒格式为零 
	     * @param date 
	     * @return 
	     */  
	    public static Date formatHhMmSsOfDate(Date date) {  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(date);  
	        cal.set(Calendar.HOUR_OF_DAY, 0);  
	        cal.set(Calendar.MINUTE, 0);  
	        cal.set(Calendar.SECOND, 0);  
	        cal.set(Calendar.MILLISECOND, 0);  
	        return cal.getTime();  
	    }  
	      
	    /** 
	     * 获得指定时间加减参数后的日期(不计算则输入0)  
	     * @param date 指定日期 
	     * @param year 年数，可正可负 
	     * @param month 月数，可正可负 
	     * @param day 天数，可正可负 
	     * @param hour 小时数，可正可负 
	     * @param minute 分钟数，可正可负 
	     * @param second 秒数，可正可负 
	     * @param millisecond 毫秒数，可正可负 
	     * @return 计算后的日期 
	     */  
	    public static Date addDate(Date date,int year,int month,int day,int hour,int minute,int second,int millisecond){  
	        Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        c.add(Calendar.YEAR, year);//加减年数  
	        c.add(Calendar.MONTH, month);//加减月数  
	        c.add(Calendar.DATE, day);//加减天数  
	        c.add(Calendar.HOUR,hour);//加减小时数  
	        c.add(Calendar.MINUTE, minute);//加减分钟数  
	        c.add(Calendar.SECOND, second);//加减秒  
	        c.add(Calendar.MILLISECOND, millisecond);//加减毫秒数  
	          
	        return c.getTime();  
	    }  
	      
	    /** 
	     * 获得两个日期的时间戳之差 
	     * @param startDate 
	     * @param endDate 
	     * @return 
	     */  
	    public static Long getDistanceTimestamp(Date startDate,Date endDate){  
	        long daysBetween=(endDate.getTime()-startDate.getTime()+1000000)/(3600*24*1000);  
	        return daysBetween;  
	    }  
	      
	    /** 
	     * 判断二个时间是否为同年同月 
	     * @param date1 
	     * @param date2 
	     * @return 
	     */  
	    public static Boolean compareIsSameMonth(Date date1,Date date2){  
	        boolean flag = false;  
	        int year1  = getYear(date1);  
	        int year2 = getYear(date2);  
	        if(year1 == year2){  
	            int month1 = getMonth(date1);  
	            int month2 = getMonth(date2);  
	            if(month1 == month2)flag = true;  
	        }  
	        return flag;  
	    }  
	      
	     /**  
	     * 获得两个时间相差距离多少天多少小时多少分多少秒  
	     * @param str1 时间参数 1 格式：1990-01-01 12:00:00  
	     * @param str2 时间参数 2 格式：2009-01-01 12:00:00  
	     * @return long[] 返回值为：{天, 时, 分, 秒}  
	     */   
	    public static long[] getDistanceTime(Date one, Date two) {   
	        long day = 0;   
	        long hour = 0;   
	        long min = 0;   
	        long sec = 0;   
	        try {   
	             
	            long time1 = one.getTime();   
	            long time2 = two.getTime();   
	            long diff ;   
	            if(time1<time2) {   
	                diff = time2 - time1;   
	            } else {   
	                diff = time1 - time2;   
	            }   
	            day = diff / (24 * 60 * 60 * 1000);   
	            hour = (diff / (60 * 60 * 1000) - day * 24);   
	            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);   
	            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }  
	        long[] times = {day, hour, min, sec};   
	        return times;   
	    }   
	      
	    /**  
	     * 两个时间相差距离多少天多少小时多少分多少秒  
	     * @param str1 时间参数 1 格式：1990-01-01 12:00:00  
	     * @param str2 时间参数 2 格式：2009-01-01 12:00:00  
	     * @return String 返回值为：{天, 时, 分, 秒} 
	     */   
	    public static long[] getDistanceTime(String str1, String str2) {   
	        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);   
	        Date one;   
	        Date two;   
	        long day = 0;   
	        long hour = 0;   
	        long min = 0;   
	        long sec = 0;   
	        try {   
	            one = df.parse(str1);   
	            two = df.parse(str2);   
	            long time1 = one.getTime();   
	            long time2 = two.getTime();   
	            long diff ;   
	            if(time1<time2) {   
	                diff = time2 - time1;   
	            } else {   
	                diff = time1 - time2;   
	            }   
	            day = diff / (24 * 60 * 60 * 1000);   
	            hour = (diff / (60 * 60 * 1000) - day * 24);   
	            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);   
	            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);   
	        } catch (ParseException e) {   
	            e.printStackTrace();   
	        }   
	        long[] times = {day, hour, min, sec};   
	        return times;   
	    }   
	      
	    /**  
	     * 两个时间之间相差距离多少天  
	     * @param one 时间参数 1：  
	     * @param two 时间参数 2：  
	     * @return 相差天数  
	     */   
	    public static Long getDistanceDays(String str1, String str2) throws Exception{   
	        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);   
	        Date one;   
	        Date two;   
	        long days=0;   
	        try {   
	            one = df.parse(str1);   
	            two = df.parse(str2);   
	            long time1 = one.getTime();   
	            long time2 = two.getTime();   
	            long diff ;   
	            if(time1<time2) {   
	                diff = time2 - time1;   
	            } else {   
	                diff = time1 - time2;   
	            }   
	            days = diff / (1000 * 60 * 60 * 24);   
	        } catch (ParseException e) {   
	            e.printStackTrace();   
	        }   
	        return days;   
	    }   
	      
	    /** 
	     * 获取指定时间的那天 00:00:00.000 的时间 
	     * @param date 
	     * @return 
	     */  
	    public static Date getDayBeginTime(final Date date) {  
	            Calendar c = Calendar.getInstance();  
	            c.setTime(date);  
	            c.set(Calendar.HOUR_OF_DAY, 0);  
	            c.set(Calendar.MINUTE, 0);  
	            c.set(Calendar.SECOND, 0);  
	            c.set(Calendar.MILLISECOND, 0);  
	            return c.getTime();  
	    }  
	      
	    /** 
	     * 获取指定时间的那天 23:59:59.999 的时间 
	     * @param date 
	     * @return 
	     */  
	    public static Date getDayEndTime(final Date date) {  
	            Calendar c = Calendar.getInstance();  
	            c.setTime(date);  
	            c.set(Calendar.HOUR_OF_DAY, 23);  
	            c.set(Calendar.MINUTE, 59);  
	            c.set(Calendar.SECOND, 59);  
	            c.set(Calendar.MILLISECOND, 999);  
	            return c.getTime();  
	    }  
	     

		/**
		 * 根据日期格式字符串解析日期字符串
		 * 
		 * @param str
		 *            日期字符串
		 * @param parsePatterns
		 *            日期格式字符串
		 * @return 解析后日期
		 * @throws ParseException
		 */
		public static Date parseDate(String str, String parsePatterns) {
			try {
				return parseDate(str, new String[] { parsePatterns });
			} catch (ParseException e) {
				log.error(e.toString());
				return null;
			}
		}

		/**
		 * 根据单位字段比较两个日期
		 * 
		 * @param date
		 *            日期1
		 * @param otherDate
		 *            日期2
		 * @param withUnit
		 *            单位字段，从Calendar field取值
		 * @return 等于返回0值, 大于返回大于0的值 小于返回小于0的值
		 */
		public static int compareDate(Date date, Date otherDate, int withUnit) {
			Calendar dateCal = Calendar.getInstance();
			dateCal.setTime(date);
			Calendar otherDateCal = Calendar.getInstance();
			otherDateCal.setTime(otherDate);

			switch (withUnit) {
			case Calendar.YEAR:
				dateCal.clear(Calendar.MONTH);
				otherDateCal.clear(Calendar.MONTH);
			case Calendar.MONTH:
				dateCal.set(Calendar.DATE, 1);
				otherDateCal.set(Calendar.DATE, 1);
			case Calendar.DATE:
				dateCal.set(Calendar.HOUR_OF_DAY, 0);
				otherDateCal.set(Calendar.HOUR_OF_DAY, 0);
			case Calendar.HOUR:
				dateCal.clear(Calendar.MINUTE);
				otherDateCal.clear(Calendar.MINUTE);
			case Calendar.MINUTE:
				dateCal.clear(Calendar.SECOND);
				otherDateCal.clear(Calendar.SECOND);
			case Calendar.SECOND:
				dateCal.clear(Calendar.MILLISECOND);
				otherDateCal.clear(Calendar.MILLISECOND);
			case Calendar.MILLISECOND:
				break;
			default:
				throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
			}
			return dateCal.compareTo(otherDateCal);
		}

		/**
		 * 根据单位字段比较两个时间
		 * 
		 * @param date
		 *            时间1
		 * @param otherDate
		 *            时间2
		 * @param withUnit
		 *            单位字段，从Calendar field取值
		 * @return 等于返回0值, 大于返回大于0的值 小于返回小于0的值
		 */
		public static int compareTime(Date date, Date otherDate, int withUnit) {
			Calendar dateCal = Calendar.getInstance();
			dateCal.setTime(date);
			Calendar otherDateCal = Calendar.getInstance();
			otherDateCal.setTime(otherDate);

			dateCal.clear(Calendar.YEAR);
			dateCal.clear(Calendar.MONTH);
			dateCal.set(Calendar.DATE, 1);
			otherDateCal.clear(Calendar.YEAR);
			otherDateCal.clear(Calendar.MONTH);
			otherDateCal.set(Calendar.DATE, 1);
			switch (withUnit) {
			case Calendar.HOUR:
				dateCal.clear(Calendar.MINUTE);
				otherDateCal.clear(Calendar.MINUTE);
			case Calendar.MINUTE:
				dateCal.clear(Calendar.SECOND);
				otherDateCal.clear(Calendar.SECOND);
			case Calendar.SECOND:
				dateCal.clear(Calendar.MILLISECOND);
				otherDateCal.clear(Calendar.MILLISECOND);
			case Calendar.MILLISECOND:
				break;
			default:
				throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
			}
			return dateCal.compareTo(otherDateCal);
		}

		/**
		 * 获得当前的日期毫秒
		 * 
		 * @return
		 */
		public static long nowTimeMillis() {
			return System.currentTimeMillis();
		}

		/**
		 * 获得当前的时间戳
		 * 
		 * @return
		 */
		public static Timestamp nowTimeStamp() {
			return new Timestamp(nowTimeMillis());
		}

		/**
		 * yyyyMMdd 当前日期
		 * 
		 */
		public static String getReqDate() {
			return SHORTDATEFORMAT.format(new Date());
		}
		/**
		 * 得到短日期格式字串 yyyyMMdd
		 * @param date
		 * @return
		 */
		public static String getShortDate() {
			return SHORTDATEFORMAT.format(new Date());
		}
		
		/**
		 * yyyy-MM-dd 传入日期
		 * 
		 * @param date
		 * @return
		 */
		public static String getReqDate(Date date) {
			return SHORT_DATE_FORMAT.format(date);
		}

		/**
		 * yyyyMMdd 传入日期
		 * 
		 * @param date
		 * @return
		 */
		public static String getReqDateyyyyMMdd(Date date) {
			return SHORTDATEFORMAT.format(date);
		}

		/**
		 * yyyy-MM-dd 传入的时间戳
		 * 
		 * @param tmp
		 * @return
		 */
		public static String TimestampToDateStr(Timestamp tmp) {
			return SHORT_DATE_FORMAT.format(tmp);
		}

		/**
		 * HH:mm:ss 当前时间
		 * 
		 * @return
		 */
		public static String getReqTime() {
			return HMS_FORMAT.format(new Date());
		}

		/**
		 * 得到时间戳格式字串
		 * 
		 * @param date
		 * @return
		 */
		public static String getTimeStampStr(Date date) {
			return LONG_DATE_FORMAT.format(date);
		}

		/**
		 * 得到长日期格式字串
		 * 
		 * @return
		 */
		public static String getLongDateStr() {
			return LONG_DATE_FORMAT.format(new Date());
		}

		public static String getLongDateStr(Timestamp time) {
			return LONG_DATE_FORMAT.format(time);
		}

		/**
		 * 得到短日期格式字串
		 * 
		 * @param date
		 * @return
		 */
		public static String getShortDateStr(Date date) {
			return SHORT_DATE_FORMAT.format(date);
		}
		/**
		 * 得到短日期格式字串 yyyy-MM-dd
		 * @param date
		 * @return
		 */
		public static String getShortDateStr() {
			return SHORT_DATE_FORMAT.format(new Date());
		}
		
		/**
		 * 计算 second 秒后的时间
		 * 
		 * @param date
		 * @param second
		 * @return
		 */
		public static Date addSecond(Date date, int second) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.SECOND, second);
			return calendar.getTime();
		}

		/**
		 * 计算 minute 分钟后的时间
		 * 
		 * @param date
		 * @param minute
		 * @return
		 */
		public static Date addMinute(Date date, int minute) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, minute);
			return calendar.getTime();
		}

		/**
		 * 计算 hour 小时后的时间
		 * 
		 * @param date
		 * @param hour
		 * @return
		 */
		public static Date addHour(Date date, int hour) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR, hour);
			return calendar.getTime();
		}

		/**
		 * 得到day的起始时间点。
		 * 
		 * @param date
		 * @return
		 */
		public static Date getDayStart(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}

		/**
		 * 得到day的终止时间点.
		 * 
		 * @param date
		 * @return
		 */
		public static Date getDayEnd(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MILLISECOND, -1);
			return calendar.getTime();
		}

		/**
		 * 计算 day 天后的时间
		 * 
		 * @param date
		 * @param day
		 * @return
		 */
		public static Date addDay(Date date, int day) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, day);
			return calendar.getTime();
		}
		
		/**
		 * 计算 day(如：1.5) 天后的时间
		 * 只精确到小时位，分钟位舍去了
		 * @param date
		 * @param day
		 * @return
		 */
		public static Date addDoubleDay(Date date, double day) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			double houres = getNumberDecimalDigits(day) * 24;
			calendar.add(Calendar.HOUR_OF_DAY, Integer.valueOf(getNumberInteger(houres)));
			calendar.add(Calendar.DAY_OF_MONTH, getNumberInteger(day));
			return calendar.getTime();
		}
		// 获取小数位点后面的位数
		public static double getNumberDecimalDigits(double number) {
		    String moneyStr = String.valueOf(number);
		    String[] num = moneyStr.split("\\.");
		    if (num.length == 2) {
		    	String str = "0."+num[1];
		        return Double.valueOf(str);
		    }else {
		        return 0;
		    }
		}
		// 获取小数的整位数
		public static int getNumberInteger(double number) {
		    String moneyStr = String.valueOf(number);
		    String[] num = moneyStr.split("\\.");
		    if (num.length == 2) {
		        return Integer.valueOf(num[0]);
		    }else {
		        return 0;
		    }
		}
		/**
		 * 得到month的终止时间点.
		 * 
		 * @param date
		 * @return
		 */
		public static Date getMonthEnd(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.MILLISECOND, -1);
			return calendar.getTime();
		}

		public static Date addYear(Date date, int year) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, 365 * year);
			return calendar.getTime();
		}

		public static Timestamp strToTimestamp(String dateStr) {
			return Timestamp.valueOf(dateStr);
		}

		public static Timestamp strToTimestamp(Date date) {
			return Timestamp.valueOf(formatTimestamp.format(date));
		}

		public static Timestamp getCurTimestamp() {
			return Timestamp.valueOf(formatTimestamp.format(new Date()));
		}

		/**
		 * 取得两个日期之间的日数
		 * 
		 * @return t1到t2间的日数，如果t2 在 t1之后，返回正数，否则返回负数
		 */
		public static long daysBetween(java.sql.Timestamp t1, java.sql.Timestamp t2) {
			return (t2.getTime() - t1.getTime()) / DAY_MILLI;
		}

		/**
		 * 返回java.sql.Timestamp型的SYSDATE
		 * 
		 * @return java.sql.Timestamp型的SYSDATE
		 * @since 1.0
		 * @history
		 */
		public static java.sql.Timestamp getSysDateTimestamp() {
			return new java.sql.Timestamp(System.currentTimeMillis());
		}

		/**
		 * 利用缺省的Date格式(YYYY/MM/DD)转换String到java.sql.Timestamp
		 * 
		 * @param sDate
		 *            Date string
		 * @return
		 * @since 1.0
		 * @history
		 */
		public static java.sql.Timestamp toSqlTimestamp(String sDate) {
			if (sDate == null) {
				return null;
			}
			if (sDate.length() != DateUtil.DATE_FORMAT_YYYY_MM_DD.length()
					&&sDate.length() != DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS.length()) {
				return null;
			}
			return toSqlTimestamp(sDate, 
					sDate.length() == DateUtil.DATE_FORMAT_YYYY_MM_DD.length()
					?DateUtil.DATE_FORMAT_YYYY_MM_DD
					:DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);

		}

		/**
		 * 利用缺省的Date格式(YYYY/MM/DD hh:mm:ss)转化String到java.sql.Timestamp
		 * 
		 * @param sDate
		 *            Date string
		 * @param sFmt
		 *            Date format DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME
		 * @return
		 * @since 1.0
		 * @history
		 */
		public static java.sql.Timestamp toSqlTimestamp(String sDate, String sFmt) {
			String temp = null;
			if (sDate == null || sFmt == null) {
				return null;
			}
			if (sDate.length() != sFmt.length()) {
				return null;
			}
			if (sFmt.equals(DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS)) {
				temp = sDate.replace('/', '-');
				temp = temp + ".000000000";
			} else if (sFmt.equals(DateUtil.DATE_FORMAT_YYYY_MM_DD)) {
				temp = sDate.replace('/', '-');
				temp = temp + " 00:00:00.000000000";
				// }else if( sFmt.equals (DateUtils.DATE_FORMAT_SESSION )){
				// //Format: 200009301230
				// temp =
				// sDate.substring(0,4)+"-"+sDate.substring(4,6)+"-"+sDate.substring(6,8);
				// temp += " " + sDate.substring(8,10) + ":" +
				// sDate.substring(10,12) + ":00.000000000";
			} else {
				return null;
			}
			// java.sql.Timestamp.value() 要求的格式必须为yyyy-mm-dd hh:mm:ss.fffffffff
			return java.sql.Timestamp.valueOf(temp);
		}

		/**
		 * 以YYYY/MM/DD HH24:MI:SS格式返回系统日期时间
		 * 
		 * @return 系统日期时间
		 * @since 1.0
		 * @history
		 */
		public static String getSysDateTimeString() {
			return toString(new java.util.Date(System.currentTimeMillis()), DateUtil.sdfDateTime);
		}

		/**
		 * 根据指定的Format转化java.util.Date到String
		 * 
		 * @param dt
		 *            java.util.Date instance
		 * @param sFmt
		 *            Date format , DATE_FORMAT_DATEONLY or DATE_FORMAT_DATETIME
		 * @return
		 * @since 1.0
		 * @history
		 */
		public static String toString(java.util.Date dt, String sFmt) {
			if (dt == null || sFmt == null || "".equals(sFmt)) {
				return "";
			}
			return toString(dt, new SimpleDateFormat(sFmt));
		}

		/**
		 * 利用指定SimpleDateFormat instance转换java.util.Date到String
		 * 
		 * @param dt
		 *            java.util.Date instance
		 * @param formatter
		 *            SimpleDateFormat Instance
		 * @return
		 * @since 1.0
		 * @history
		 */
		private static String toString(java.util.Date dt, SimpleDateFormat formatter) {
			String sRet = null;

			try {
				sRet = formatter.format(dt).toString();
			} catch (Exception e) {
				log.error(e.toString());
				sRet = null;
			}

			return sRet;
		}

		/**
		 * 转换java.sql.Timestamp到String，格式为YYYY/MM/DD HH24:MI
		 * 
		 * @param dt
		 *            java.sql.Timestamp instance
		 * @return
		 * @since 1.0
		 * @history
		 */
		public static String toSqlTimestampString2(java.sql.Timestamp dt) {
			if (dt == null) {
				return null;
			}
			String temp = toSqlTimestampString(dt, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
			return temp.substring(0, 16);
		}

		public static String toString(java.sql.Timestamp dt) {
			return dt == null ? "" : toSqlTimestampString2(dt);
		}

		/**
		 * 根据指定的格式转换java.sql.Timestamp到String
		 * 
		 * @param dt
		 *            java.sql.Timestamp instance
		 * @param sFmt
		 *            Date 格式，DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME/
		 *            DATE_FORMAT_SESSION
		 * @return
		 * @since 1.0
		 * @history
		 */
		public static String toSqlTimestampString(java.sql.Timestamp dt, String sFmt) {
			String temp = null;
			String out = null;
			if (dt == null || sFmt == null) {
				return null;
			}
			temp = dt.toString();
			if (sFmt.equals(DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS) || // "YYYY/MM/DD
					// HH24:MI:SS"
					sFmt.equals(DateUtil.DATE_FORMAT_YYYY_MM_DD)) { // YYYY/MM/DD
				temp = temp.substring(0, sFmt.length());
				out = temp.replace('/', '-');
				// }else if( sFmt.equals (DateUtils.DATE_FORMAT_SESSION ) ){
				// //Session
				// out =
				// temp.substring(0,4)+temp.substring(5,7)+temp.substring(8,10);
				// out += temp.substring(12,14) + temp.substring(15,17);
			}
			return out;
		}

		// 得到当前日期的星期
		public static int getWeek() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int w = cal.get(Calendar.DAY_OF_WEEK);
			return w;
		}

		/**
		 * Timestamp 格式转换成yyyy-MM-dd timestampToSql(Timestamp 格式转换成yyyy-MM-dd)
		 * 
		 * @param timestamp
		 *            时间
		 * @return createTimeStr yyyy-MM-dd 时间
		 * @Exception 异常对象
		 * @since V1.0
		 */
		public static String timestampToStringYMD(java.sql.Timestamp timestamp) {
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_YYYY_MM_DD);
			String createTimeStr = sdf.format(timestamp);
			return createTimeStr;
		}

		/**
		 * 判断一个时间是否在某个时间区间内
		 * 
		 * @param now
		 *            目标时间
		 * @param start
		 *            时间区间开始
		 * @param end
		 *            时间区间结束
		 * @param model
		 *            区间模式
		 * @return 是否在区间内
		 */
		public static boolean isBetween(Date now, Date start, Date end, int model) {
			return isBetween(now, start, end, model, LEFT_OPEN_RIGHT_OPEN);
		}

		/**
		 * 判断时间是否在制定的时间段之类
		 * 
		 * @param date
		 *            需要判断的时间
		 * @param start
		 *            时间段的起始时间
		 * @param end
		 *            时间段的截止时间
		 * @param interModel
		 *            区间的模式
		 * 
		 *            <pre>
		 * 		取值：
		 * 			LEFT_OPEN_RIGHT_OPEN
		 * 			LEFT_CLOSE_RIGHT_OPEN
		 * 			LEFT_OPEN_RIGHT_CLOSE
		 * 			LEFT_CLOSE_RIGHT_CLOSE
		 * </pre>
		 * @param compModel
		 *            比较的模式
		 * 
		 *            <pre>
		 * 		取值：
		 * 			COMP_MODEL_DATE		只比较日期，不比较时间
		 * 			COMP_MODEL_TIME		只比较时间，不比较日期
		 * 			COMP_MODEL_DATETIME 比较日期，也比较时间
		 * </pre>
		 * @return
		 */
		public static boolean isBetween(Date date, Date start, Date end, int interModel, int compModel) {
			if (date == null || start == null || end == null) {
				throw new IllegalArgumentException("日期不能为空");
			}
			SimpleDateFormat format = null;
			switch (compModel) {
			case COMP_MODEL_DATE: {
				format = new SimpleDateFormat("yyyyMMdd");
				break;
			}
			case COMP_MODEL_TIME: {
				format = new SimpleDateFormat("HHmmss");
				break;
			}
			case COMP_MODEL_DATETIME: {
				format = new SimpleDateFormat("yyyyMMddHHmmss");
				break;
			}
			default: {
				throw new IllegalArgumentException(String.format("日期的比较模式[%d]有误", compModel));
			}
			}
			long dateNumber = Long.parseLong(format.format(date));
			long startNumber = Long.parseLong(format.format(start));
			long endNumber = Long.parseLong(format.format(end));
			switch (interModel) {
			case LEFT_OPEN_RIGHT_OPEN: {
				if (dateNumber <= startNumber || dateNumber >= endNumber) {
					return false;
				} else {
					return true;
				}
			}
			case LEFT_CLOSE_RIGHT_OPEN: {
				if (dateNumber < startNumber || dateNumber >= endNumber) {
					return false;
				} else {
					return true;
				}
			}
			case LEFT_OPEN_RIGHT_CLOSE: {
				if (dateNumber <= startNumber || dateNumber > endNumber) {
					return false;
				} else {
					return true;
				}
			}
			case LEFT_CLOSE_RIGHT_CLOSE: {
				if (dateNumber < startNumber || dateNumber > endNumber) {
					return false;
				} else {
					return true;
				}
			}
			default: {
				throw new IllegalArgumentException(String.format("日期的区间模式[%d]有误", interModel));
			}
			}
		}

		/**
		 * 得到当前周起始时间
		 * 
		 * @param date
		 * @return
		 */
		public static Date getWeekStart(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.get(Calendar.WEEK_OF_YEAR);
			int firstDay = calendar.getFirstDayOfWeek();
			calendar.set(Calendar.DAY_OF_WEEK, firstDay);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}

		/**
		 * 得到当前周截止时间
		 * 
		 * @param date
		 * @return
		 */
		public static Date getWeekEnd(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.get(Calendar.WEEK_OF_YEAR);
			int firstDay = calendar.getFirstDayOfWeek();
			calendar.set(Calendar.DAY_OF_WEEK, 8 - firstDay);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}

		/**
		 * 得到当月起始时间
		 * 
		 * @param date
		 * @return
		 */
		public static Date getMonthStart(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}

		/**
		 * 得到当前年起始时间
		 * 
		 * @param date
		 * @return
		 */
		public static Date getYearStart(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}

		/**
		 * 得到当前年最后一天
		 * 
		 * @param date
		 * @return
		 */
		public static Date getYearEnd(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
			calendar.set(Calendar.MONTH, 11);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}

		/**
		 * 取得月天数
		 * 
		 * @param date
		 * @return
		 */
		public static int getDayOfMonth(Date date) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.getActualMaximum(Calendar.DAY_OF_MONTH);
		}

		/**
		 * 取得月第一天
		 * 
		 * @param date
		 * @return
		 */
		public static Date getFirstDateOfMonth(Date date) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			return c.getTime();
		}

		/**
		 * 取得月最后一天
		 * 
		 * @param date
		 * @return
		 */
		public static Date getLastDateOfMonth(Date date) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			return c.getTime();
		}

		/**
		 * 取得季度第一天
		 * 
		 * @param date
		 * @return
		 */
		public static Date getSeasonStart(Date date) {
			return getDayStart(getFirstDateOfMonth(getSeasonDate(date)[0]));
		}

		/**
		 * 取得季度最后一天
		 * 
		 * @param date
		 * @return
		 */
		public static Date getSeasonEnd(Date date) {
			return getDayEnd(getLastDateOfMonth(getSeasonDate(date)[2]));
		}

		/**
		 * 取得季度月
		 * 
		 * @param date
		 * @return
		 */
		public static Date[] getSeasonDate(Date date) {
			Date[] season = new Date[3];

			Calendar c = Calendar.getInstance();
			c.setTime(date);

			int nSeason = getSeason(date);
			if (nSeason == 1) {// 第一季度
				c.set(Calendar.MONTH, Calendar.JANUARY);
				season[0] = c.getTime();
				c.set(Calendar.MONTH, Calendar.FEBRUARY);
				season[1] = c.getTime();
				c.set(Calendar.MONTH, Calendar.MARCH);
				season[2] = c.getTime();
			} else if (nSeason == 2) {// 第二季度
				c.set(Calendar.MONTH, Calendar.APRIL);
				season[0] = c.getTime();
				c.set(Calendar.MONTH, Calendar.MAY);
				season[1] = c.getTime();
				c.set(Calendar.MONTH, Calendar.JUNE);
				season[2] = c.getTime();
			} else if (nSeason == 3) {// 第三季度
				c.set(Calendar.MONTH, Calendar.JULY);
				season[0] = c.getTime();
				c.set(Calendar.MONTH, Calendar.AUGUST);
				season[1] = c.getTime();
				c.set(Calendar.MONTH, Calendar.SEPTEMBER);
				season[2] = c.getTime();
			} else if (nSeason == 4) {// 第四季度
				c.set(Calendar.MONTH, Calendar.OCTOBER);
				season[0] = c.getTime();
				c.set(Calendar.MONTH, Calendar.NOVEMBER);
				season[1] = c.getTime();
				c.set(Calendar.MONTH, Calendar.DECEMBER);
				season[2] = c.getTime();
			}
			return season;
		}

		/**
		 * 
		 * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
		 * 
		 * @param date
		 * @return
		 */
		public static int getSeason(Date date) {

			int season = 0;

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int month = c.get(Calendar.MONTH);
			switch (month) {
			case Calendar.JANUARY:
			case Calendar.FEBRUARY:
			case Calendar.MARCH:
				season = 1;
				break;
			case Calendar.APRIL:
			case Calendar.MAY:
			case Calendar.JUNE:
				season = 2;
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.SEPTEMBER:
				season = 3;
				break;
			case Calendar.OCTOBER:
			case Calendar.NOVEMBER:
			case Calendar.DECEMBER:
				season = 4;
				break;
			default:
				break;
			}
			return season;
		}

		/**
		 * 字符串转date
		 * 
		 * @param dateString yyyy-MM-dd
		 * @return Date
		 */
		public static Date StringToDate(String dateString) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
			Date date = null;
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				log.error(e.toString());
			}
			return date;
		}
		
		/**
		 * 判断输入日期是一个星期中的第几天(星期天为一个星期第一天)
		 * 
		 * @param date
		 * @return
		 */
		public static int getWeekIndex(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar.get(Calendar.DAY_OF_WEEK);
		}

		/**
		 * 当前时间的前几天，并且以例如2013/12/09 00:00:00 形式输出
		 */
		public static Date subDays(int days) {
			Date date = addDay(new Date(), -days);
			String dateStr = getReqDate(date);
			Date date1 = null;
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
			try {
				date1 = sdf.parse(dateStr);
			} catch (ParseException e) {
				log.error(e.toString());
			}
			return date1;
		}

		/**
		 * 判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制 如：开始时间和结束时间，不能超出距离当前时间90天
		 * 
		 * @param startDate
		 *            开始时间
		 * @param endDate
		 *            结束时间按
		 * @param interval
		 *            间隔数
		 * @param dateUnit
		 *            单位(如：月，日),参照Calendar的时间单位
		 * @return
		 */
		public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval, int dateUnit) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(dateUnit, interval * (-1));
			Date curDate = getDayStart(cal.getTime());
			if (getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0) {
				return true;
			}
			return false;
		}

		/**
		 * 判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制, 时间单位默认为天数 如：开始时间和结束时间，不能超出距离当前时间90天
		 * 
		 * @param startDate
		 *            开始时间
		 * @param endDate
		 *            结束时间按
		 * @param interval
		 *            间隔数
		 * @return
		 */
		public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, interval * (-1));
			Date curDate = getDayStart(cal.getTime());
			if (getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0) {
				return true;
			}
			return false;
		}

		/**
		 * 判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制, 时间单位默认为天数 如：开始时间和结束时间，不能超出距离当前时间90天
		 * 
		 * @param startDateStr
		 *            开始时间
		 * @param endDateStr
		 *            结束时间按
		 * @param interval
		 *            间隔数
		 * @return
		 */
		public static boolean isOverIntervalLimit(String startDateStr, String endDateStr, int interval) {
			Date startDate = null;
			Date endDate = null;
			startDate = DateUtil.parseDate(startDateStr, DateUtil.DATE_FORMAT_YYYY_MM_DD);
			endDate = DateUtil.parseDate(endDateStr, DateUtil.DATE_FORMAT_YYYY_MM_DD);
			if (startDate == null || endDate == null){
				return true;
			}

			return isOverIntervalLimit(startDate, endDate, interval);
		}

		/**
		 * 传入时间字符串及时间格式，返回对应的Date对象
		 * 
		 * @param src
		 *            时间字符串
		 * @param pattern
		 *            时间格式
		 * @return Date
		 */
		public static java.util.Date getDateFromString(String src, String pattern) {
			SimpleDateFormat f = new SimpleDateFormat(pattern);
			try {
				return f.parse(src);
			} catch (ParseException e) {
				return null;
			}
		}

		/**
		 * 取季度
		 * 
		 * @param date
		 * @return
		 */
		@SuppressWarnings("deprecation")
		public static int getQuarter(Date date) {
			if (date.getMonth() == 0 || date.getMonth() == 1 || date.getMonth() == 2) {
				return 1;
			} else if (date.getMonth() == 3 || date.getMonth() == 4 || date.getMonth() == 5) {
				return 2;
			} else if (date.getMonth() == 6 || date.getMonth() == 7 || date.getMonth() == 8) {
				return 3;
			} else if (date.getMonth() == 9 || date.getMonth() == 10 || date.getMonth() == 11) {
				return 4;
			} else {
				return 0;

			}
		}

		/**
		 * 取得通用日期时间格式字符串
		 * 
		 * @param date
		 * @return String
		 */
		public static String formatDate(Date date) {
			if (date == null) {
				return "";
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return dateFormat.format(date);
		}

		/**
		 * 获取当日的日期格式串
		 * 
		 * @param
		 * @return String
		 */
		public static String today() {
			return formatDate(new Date(), DATE_FORMAT_YYYY_MM_DD);
		}

		/**
		 * 获取当前时间格式串
		 * 
		 * @param
		 * @return String
		 */
		public static String currentTime() {
			return formatDate(new Date(), "yyyyMMddhhmmssSSS");
		}

		/**
		 * 取得指定日期格式的字符串
		 * 
		 * @param date
		 * @return String
		 */
		public static String formatDate(Date date, String format) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		}

		/**
		 * 获取昨日的日期格式串
		 * 
		 * @return Date
		 */
		public static String getYesterday() {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			return formatDate(calendar.getTime(), DATE_FORMAT_YYYY_MM_DD);
		}

		/**
		 * 判断当前时间是否在一定的时间范围内
		 * 
		 * @param startTime
		 * @return boolean
		 */
		public static boolean isInBetweenTimes(String startTime, String endTime) {
			Date nowTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String time = sdf.format(nowTime);
			if (time.compareTo(startTime) >= 0 && time.compareTo(endTime) <= 0) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * 字符转日期
		 * 
		 * @param dateStr
		 * @return
		 */
		public static Date getDateByStr(String dateStr) {
			SimpleDateFormat formatter = null;
			if (dateStr == null) {
				return null;
			} else if (dateStr.length() == 10) {
				formatter = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
			} else if (dateStr.length() == 16) {
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			} else if (dateStr.length() == 19) {
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else if (dateStr.length() > 19) {
				dateStr = dateStr.substring(0, 19);
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else {
				return null;
			}
			try {
				return formatter.parse(dateStr);
			} catch (ParseException e) {
				log.error(e.toString());
				return null;
			}
		}

		/**
		 * 根据传入的数字，输出相比现在days天的数据
		 * 
		 * @param days
		 * @return Date
		 */
		public static Date getDate(int days) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, days);
			return calendar.getTime();
		}

		/**
		 * 日期最大时间
		 * 
		 * @param dt
		 * @return
		 */
		public static Date getMaxTime(Date dt) {

			Date dt1 = null;
			Calendar ca = Calendar.getInstance();
			ca.setTime(dt);
			ca.add(Calendar.DAY_OF_MONTH, 1);
			dt1 = ca.getTime();
			dt1 = DateUtil.getMinTime(dt1);
			ca.setTime(dt1);
			ca.add(Calendar.SECOND, -1);
			dt1 = ca.getTime();
			return dt1;
		}

		/**
		 * 日期最小时间
		 * 
		 * @param dt
		 * @return
		 */
		public static Date getMinTime(Date dt) {
			Date dt1 = null;
			dt1 = DateUtil.getDateByStr(DateUtil.formatDate(dt, DATE_FORMAT_YYYY_MM_DD));
			return dt1;
		}

		/**
		 * 月的最后一天
		 * 
		 * @param date
		 * @return
		 */
		@SuppressWarnings("deprecation")
		public static Date getLastDayOfMonth(Date date) {
			Calendar cDay1 = Calendar.getInstance();
			cDay1.setTime(date);
			int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
			Date lastDate = cDay1.getTime();
			lastDate.setDate(lastDay);
			return lastDate;
		}

		/**
		 * 月的第一天
		 * 
		 * @param date
		 * @return
		 */
		public static Date getFirstDayOfMonth(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
			return calendar.getTime();
		}

		/**
		 * 上月第一天
		 * 
		 * @return
		 */
		public static Date getPreviousMonthFirstDay() {
			Calendar lastDate = Calendar.getInstance();
			lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
			lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
			return getMinTime(lastDate.getTime());
		}

		/**
		 * 上月最后一天
		 * 
		 * @return
		 */
		public static Date getPreviousMonthLastDay() {
			Calendar lastDate = Calendar.getInstance();
			lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
			lastDate.add(Calendar.DATE, -1);
			return getMinTime(lastDate.getTime());
		}

		/**
		 * 两个日期相关天数
		 * 
		 * @param startDate
		 * @param endDate
		 * @return
		 */
		public static long getDateDiff(String startDate, String endDate) {
			long diff = 0;
			try {
				Date date1 = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD).parse(startDate);
				Date date2 = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD).parse(endDate);

				diff = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2.getTime())
						/ (24 * 60 * 60 * 1000)
						: (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
			} catch (ParseException e) {
			}
			return diff;
		}

		/**
		 * 返回天数
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static long getDateDiff(Date date1, Date date2) {
			if (date1 == null || date2 == null) {
				return 0L;
			}
			long diff = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2
					.getTime()) / (24 * 60 * 60 * 1000) : (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
			return diff;
		}

		/**
		 * 判断两个时间的相差年数
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static int getYearDiff(Date date1, Date date2){
			if (date1 == null || date2 == null) {
				return 0;
			}

			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(date1);
			int year1 = calendar1.get(Calendar.YEAR);

			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(date2);
			int year2 = calendar2.get(Calendar.YEAR);

			return Math.abs( year1 - year2);
		}

		/**
		 * 获取两个时间的毫秒数
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static long getTimeDiff(Date date1, Date date2){
			if (date1 == null || date1 == null) {
				return 0L;
			}
			long diff = (date1.getTime() - date2.getTime()) > 0 ? (date1.getTime() - date2
					.getTime())  : (date2.getTime() - date1.getTime()) ;
			return diff;
		}

		/*
		 * 判断两个时间是不是在一个周中
		 */
		public static boolean isSameWeekWithToday(Date date) {

			if (date == null) {
				return false;
			}

			// 0.先把Date类型的对象转换Calendar类型的对象
			Calendar todayCal = Calendar.getInstance();
			Calendar dateCal = Calendar.getInstance();

			todayCal.setTime(new Date());
			dateCal.setTime(date);
			int subYear = todayCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
			// subYear==0,说明是同一年
			if (subYear == 0) {
				if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
					return true;
			} else if (subYear == 1 && dateCal.get(Calendar.MONTH) == 11 && todayCal.get(Calendar.MONTH) == 0) {
				if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
					return true;
			} else if (subYear == -1 && todayCal.get(Calendar.MONTH) == 11 && dateCal.get(Calendar.MONTH) == 0) {
				if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
					return true;
			}
			return false;
		}

		/**
		 * getStrFormTime: <br/>
		 * 
		 * @param form
		 *            格式时间
		 * @param date
		 *            时间
		 * @return
		 */
		public static String getStrFormTime(String form, Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat(form);
			return sdf.format(date);
		}

		/**
		 * 获取几天内日期 return 2014-5-4、2014-5-3
		 */
		public static List<String> getLastDays(int countDay) {
			List<String> listDate = new ArrayList<String>();
			for (int i = 0; i < countDay; i++) {
				listDate.add(DateUtil.getReqDateyyyyMMdd(DateUtil.getDate(-i)));
			}
			return listDate;
		}

		/**
		 * 对时间进行格式化
		 * 
		 * @param date
		 * @return
		 */
		public static Date dateFormat(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date value = new Date();

			try {
				value = sdf.parse(sdf.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return value;

		}
		
		public static boolean isSameDayWithToday(Date date) {

			if (date == null) {
				return false;
			}

			Calendar todayCal = Calendar.getInstance();
			Calendar dateCal = Calendar.getInstance();

			todayCal.setTime(new Date());
			dateCal.setTime(date);
			int subYear = todayCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
			int subMouth = todayCal.get(Calendar.MONTH) - dateCal.get(Calendar.MONTH);
			int subDay = todayCal.get(Calendar.DAY_OF_MONTH) - dateCal.get(Calendar.DAY_OF_MONTH);
			if (subYear == 0 && subMouth == 0 && subDay == 0) {
				return true;
			}
			return false;
		}

}
