package com.sds.core.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author tantyou
 *
 */
public class DateUtils {

    public DateUtils() {

    }

    public final static Calendar myc = Calendar.getInstance();

    /**
     * 获得当天时间
     * 
     * @param parrten
     *            输出的时间格式
     * @return 返回时间
     */
    public static String getTime(String parrten) {

        String timestr;
        if (parrten == null || parrten.equals("")) {
            parrten = "yyyyMMddHHmmss";
        }
        java.text.SimpleDateFormat sdf = new SimpleDateFormat(parrten);
        java.util.Date cday = new Date();
        timestr = sdf.format(cday);
        return timestr;
    }

    public static Date parseDate(String date) {
	     return parseDate(date,new String[] { "yyyy-MM-dd", "yyyy/MM/dd","yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm" });
    }
    public static Date parseDate(String date,String ... partten) {
        Date dt = null;
        try {
            dt = org.apache.commons.lang.time.DateUtils.parseDate(date,partten);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }
    
    public static Date parseDate(String date,String partten){
    	return parseDate(date,new String[]{partten});
    }
    /**
     * 获得当天日期
     * 
     * @param parrten
     * @return
     */
    public static String getDate(String parrten) {

        if (parrten == null || parrten.equals("")) {
            parrten = "yyyy-MM-dd";
        }
        return DateUtils.getTime(parrten);
    }

    /**
     * 时间格式转换
     * 
     * @param cday
     * @param parrten
     * @return
     */
    public static String getTime(Date cday, String parrten) {

        String timestr;
        if (parrten == null || parrten.equals("")) {
            parrten = "yyyyMMddHHmmss";
        }
        java.text.SimpleDateFormat sdf = new SimpleDateFormat(parrten);
        timestr = sdf.format(cday);
        return timestr;
    }

    /**
     * 日期格式转换
     * 
     * @param parrten
     * @return
     */
    public static String getDate(Date date, String parrten) {

        if (parrten == null || parrten.equals("")) {
            parrten = "yyyy-MM-dd";
        }
        return DateUtils.getTime(date, parrten);
    }

    /**
     * 得到昨天的时间
     * 
     * @return
     */
    public static String getYestday() {

        String timestr;
        java.util.Calendar cc = myc;
        cc.roll(Calendar.DAY_OF_YEAR, -1);
        java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if ((cc.get(Calendar.MONTH) + 1) == 1 && cc.get(Calendar.DAY_OF_MONTH) == 1) {
            cc.roll(Calendar.YEAR, 1);
        }

        timestr = sdf.format(cc.getTime());
        return timestr;
    }

    /**
     * 将字串转换为指定格式的日期
     * 
     * @param time
     *            时间
     * @param parrten
     *            为空时，将使用yyyy-MM-dd格式
     * @return
     */
    public static Date StrToDate(String time, String parrten) {

        if (parrten == null || parrten.equals("")) {
            parrten = "yyyy-MM-dd";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(parrten);
        ParsePosition pos = new ParsePosition(0);
        Date dt1 = formatter.parse(time, pos);
        return dt1;
    }



    /**
     * 将时间转换为xxxx年xx月xx日格式
     * 
     * @param t1
     *            原时间
     * @param parrten
     *            原时间格式
     * @return
     */
    public static String getTime(String t1, String parrten) {

        SimpleDateFormat formatter = new SimpleDateFormat(parrten);
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy年MM月dd日");
        ParsePosition pos = new ParsePosition(0);
        Date dt1 = formatter.parse(t1, pos);
        return formatter2.format(dt1);
    }

    /**
     * 将时间转换为parrten2格式
     * 
     * @param t1
     *            时间字符串
     * @param parrten
     *            原时间格式
     * @param parrten2
     *            要转化的格式
     * @return
     */
    public static String getTime(String t1, String parrten, String parrten2) {

        SimpleDateFormat formatter = new SimpleDateFormat(parrten);
        SimpleDateFormat formatter2 = new SimpleDateFormat(parrten2);
        ParsePosition pos = new ParsePosition(0);
        Date dt1 = formatter.parse(t1, pos);
        return formatter2.format(dt1);
    }


    /**
     * 比较两个日期相差的天数
     * 
     * @param time1
     * @param time2
     * @return
     */
    public static int compareTime2(String time1, String time2) {

        return compareStringTimes(time1, time2, "yyyy-MM-dd");
    }

    public static String addTime(String datetime, String parrten, long days) {

        SimpleDateFormat formatter = new SimpleDateFormat(parrten);
        ParsePosition pos = new ParsePosition(0);
        Date dt1 = formatter.parse(datetime, pos);
        long l = dt1.getTime() / 1000 + days * 24 * 60 * 60;
        dt1.setTime(l * 1000);
        String mydate = formatter.format(dt1);

        return mydate;
    }

    public static String jianTime(String datetime, String parrten, long days) {

        SimpleDateFormat formatter = new SimpleDateFormat(parrten);
        ParsePosition pos = new ParsePosition(0);
        Date dt1 = formatter.parse(datetime, pos);
        long l = dt1.getTime() / 1000 - days * 24 * 60 * 60;
        dt1.setTime(l * 1000);
        String mydate = formatter.format(dt1);

        return mydate;
    }

    /**
     * 取得昨天的日期,以今天为基准
     * 
     * @return
     */
    public static String getYestdayBaseToday() {

        String timestr;

        Calendar calendar = Calendar.getInstance();

        calendar.roll(Calendar.DAY_OF_YEAR, -1);

        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(calendar.getTime());
        if (tempCalendar.get(Calendar.DAY_OF_YEAR) == 1) {
            calendar.roll(Calendar.YEAR, -1);
        }

        java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        timestr = sdf.format(calendar.getTime());
        return timestr;

    }

    /**
     * 取得指定日期的前一天
     * 
     * @param date
     * @return
     */
    public static Date getPreDate(Date date) {

        Calendar calendar = Calendar.getInstance();

        Date tempDate = date;

        calendar.setTime(date);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);

        Calendar cal = Calendar.getInstance();

        cal.setTime(tempDate);

        if (cal.get(Calendar.DAY_OF_YEAR) == 1) {
            calendar.roll(Calendar.YEAR, -1);
        }

        Date preDate = calendar.getTime(); // 得到前一天的日期
        return preDate;

    }

    /**
     * @author 童贝
     * @version 2007-5-17
     * @return 当前时间的后一天
     * @param date
     * @return
     */
    @SuppressWarnings("static-access")
	public static String endDate(Date date) {

        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DAY_OF_MONTH, 1);
        String e = ca.get(ca.YEAR) + "-" + (ca.get(ca.MONTH) + 1) + "-" + ca.get(ca.DATE);
        return e;
    }
    
    /**
     * 格式化日期  指定格式
     * @param date
     * @param formatStyle
     * @return
     */
    public static String formatDate(Date date,String formatStyle){
    	String defaultStyle="yyyy-MM-dd HH:mm:ss";
    	if(formatStyle!=null && !formatStyle.equals(""))
    		defaultStyle=formatStyle;
    	DateFormat df=new SimpleDateFormat(defaultStyle);
    	String dt=df.format(date);
    	return dt;
    }
    /**
     * 格式化日期 默认格式:yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDate(Date date){
    	return formatDate(date,null);
    }
    
    /**
	 * 
	 *描述：得到指定月份后的时间(比如:2009-1-10,3,"yyyy-MM-dd",那么得到的是2009-4-10)
	 *时间：2010-1-13
	 *作者：童贝
	 *参数：curDate(日期),monthCount(往后退的月数),parrten(格式)
	 *返回值:后推得时间
	 *抛出异常：
	 */
	public static String getNextDate(Date curDate,Integer monthCount,String parrten){
		if (parrten == null || parrten.equals("")) {
			parrten = "yyyy-MM-dd";
		}
		java.text.SimpleDateFormat sdf = new SimpleDateFormat(parrten);

		Calendar calendar = Calendar.getInstance();
		try {
			String strDate=DateUtils.getDate(curDate, parrten);
			calendar.setTime(sdf.parse(strDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, monthCount);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 
	 *描述：比较两个时间相差的天数
	 *时间：2010-1-14
	 *作者：童贝
	 *参数：
	 *返回值:0表示相等，>0表示t1大,<0表示t2大
	 *抛出异常：
	 */
	public static int compareStringTimes(String t1, String t2, String parrten) {

        SimpleDateFormat formatter = new SimpleDateFormat(parrten);
        ParsePosition pos = new ParsePosition(0);
        ParsePosition pos1 = new ParsePosition(0);
        Date dt1 = formatter.parse(t1, pos);
        Date dt2 = formatter.parse(t2, pos1);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt1);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(dt2);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        return (day1 - day2);

    }
    
	/**
	 * 获取某年上一个月
	 *@author  MrBao 
	 *@date 	  2010-2-10
	 *@param currDate  当前时间
	 *@param parrten    
	 *@return
	 *@return String
	 *@remark
	 */
	public static String  getPrevMonth(String currDate ,String parrten){
		if (parrten == null || parrten.equals("")) {
			parrten = "yyyy-MM";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(parrten);  
        Calendar   calendar = Calendar.getInstance(); 
        Date mydate = new Date();
        try {
			mydate = sdf.parse(currDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        calendar.setTime(mydate);   
        calendar.add(Calendar.MONTH, -1);
        String result =sdf.format(calendar.getTime()).toString();
		return  result;
	}
	
	/**
	 * 获取某年下一个月
	 *@author  MrBao 
	 *@date 	  2010-2-10
	 *@param currDate
	 *@param parrten
	 *@return
	 *@return String
	 *@remark
	 */
	public static String  getNextMonth(String currDate ,String parrten){
		if (parrten == null || parrten.equals("")) {
			parrten = "yyyy-MM";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(parrten);  
        Calendar   calendar = Calendar.getInstance(); 
        Date mydate = new Date();
        try {
			mydate = sdf.parse(currDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        calendar.setTime(mydate);   
        calendar.add(Calendar.MONTH, 1);
        String result =sdf.format(calendar.getTime()).toString();
		return  result;
	}
	
	/**
	 * 获取上一年
	 *@author  MrBao 
	 *@date 	  2010-2-10
	 *@param currYear
	 *@return
	 *@return String
	 *@remark
	 */
	public static String  getPrevYear(String currYear){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");  
        Calendar   calendar = Calendar.getInstance(); 
        Date mydate = new Date();
        try {
			mydate = sdf.parse(currYear);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        calendar.setTime(mydate);   
        calendar.add(Calendar.YEAR, -1);
        String result =sdf.format(calendar.getTime()).toString();
		return  result;
	}
	
	/**
	 * 获取下一年
	 *@author  MrBao 
	 *@date 	  2010-2-10
	 *@param currYear
	 *@return
	 *@return String
	 *@remark
	 */
	public static String  getNextYear(String currYear){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");  
        Calendar   calendar = Calendar.getInstance(); 
        Date mydate = new Date();
        try {
			mydate = sdf.parse(currYear);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        calendar.setTime(mydate);   
        calendar.add(Calendar.YEAR, 1);
        String result =sdf.format(calendar.getTime()).toString();
		return  result;
	}
	
	
	

	/**
     * 功能说明:得到指定月份的最后一天
     * @author 童贝
     * @version Feb 16, 2009
     * @return
     */
    public static String getLastDate(Date date){
    	//Date date=new Date(month);	
    	Calendar cal = Calendar.getInstance(); 
    	cal.setTime(date);
    	//当前月＋1，即下个月
    	cal.add(Calendar.MONTH,1); 
    	//将下个月1号作为日期初始zhii
    	cal.set(Calendar.DATE,1); 
        //下个月1号减去一天，即得到当前月最后一天
    	cal.add(Calendar.DATE,-1);      
    	SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");       
    	String day_end=df.format(cal.getTime());   
    	//System.out.println("day_end>>>"+day_end);  
    	return day_end;
    }
    /**
     * 功能说明:得到指定月份的最后一天
     * @author 童贝
     * @version Feb 16, 2009
     * @return
     */
    public static String getLastDate(String date){
    	Date newDate=DateUtils.StrToDate(date, "");
    	//Date date=new Date(month);	
    	Calendar cal = Calendar.getInstance(); 
    	cal.setTime(newDate);
    	//当前月＋1，即下个月
    	cal.add(Calendar.MONTH,1); 
    	//将下个月1号作为日期初始zhii
    	cal.set(Calendar.DATE,1); 
        //下个月1号减去一天，即得到当前月最后一天
    	cal.add(Calendar.DATE,-1);      
    	SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");       
    	String day_end=df.format(cal.getTime());   
    	//System.out.println("day_end>>>"+day_end);  
    	return day_end;
    }
    
    /**
     * 功能说明:得到一年的最后一天
     * @author 童贝
     * @version Feb 16, 2009
     * @return
     */
    public static String getLastYearByDate(String year){
    	Calendar cal = Calendar.getInstance(); 
    	String newYear=year+"-01-01";//一年的第一天
    	Date date=DateUtils.StrToDate(newYear, "yyyy-mm-dd");
    	cal.setTime(date);
    	cal.add(Calendar.MONTH,12); 
    	cal.add(Calendar.DATE,-1); 
    	SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");       
    	String day_end=df.format(cal.getTime());   
    	//System.out.println("day_end>>>"+day_end);  
    	return day_end;
    }

    
    
    public static void main(String args[]) {
//    	Date dt=new Date();
//    	String xhDate=DateUtils.getNextDate(dt, 3, "");
//    	System.out.println(xhDate);
//		String curDate=DateUtils.getDate("");
//		int xcts= DateUtils.compareStringTimes(curDate,xhDate,  "yyyy-MM-dd");
//		System.out.println(xcts);
    	System.out.println(compareStringTimes("2010-1" ,"2010-01","yyyy-MM"));
    }

}
