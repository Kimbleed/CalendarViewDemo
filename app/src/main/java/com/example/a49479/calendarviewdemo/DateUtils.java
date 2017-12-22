package com.example.a49479.calendarviewdemo;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/2/23.
 */


public class DateUtils {
    /**
     * 判断给定字符串时间是否为今日(效率不是很高，不过也是一种方法)
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate){
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if(time != null){
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if(nowDate.equals(timeDate)){
                b = true;
            }
        }
        return b;
    }

    /**
     * 将字符串转位日期类型
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM/dd/yyyy");
        }
    };
    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();
    /**
     * 判断是否为今天(效率比较高)
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsYesterday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

    public static SimpleDateFormat getDateFormat()
    {
        if (null == DateLocal.get())
        {
            DateLocal.set(new SimpleDateFormat("MM/dd/yyyy", Locale.CHINA));
        }

        return DateLocal.get();
    }


    /**
     * 判定date1  比 date2 早
     * @param date1
     * @param date2
     * @return  true 早    false 晚
     */
    public static boolean earlyThan(String date1, String date2){

        return false;
    }

    /**
     * 判定date1  比 date2 晚
     * @param date1
     * @param date2
     * @return  true 晚    false 早
     */
    public static boolean lateThan(String date1, String date2){
        return false;
    }

    /**
     * 通过long 数字  获取  日期 年月日
     * @param time
     * @return
     */
    public static String getDateFromNum(long time){
        SimpleDateFormat mSdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = new Date(time);
        String str = mSdf.format(date);
        return str;
    }
    public static String getDateFromNum(long time, String format){
        SimpleDateFormat mSdf = new SimpleDateFormat(format, Locale.CHINA);
        Date date = new Date(time);
        String str = mSdf.format(date);
        return str;
    }

    public static String getYearFromNumm(long time){
        SimpleDateFormat mSdf = new SimpleDateFormat("yyyy", Locale.CHINA);
        Date date = new Date(time);
        String str = mSdf.format(date);
        return str;
    }
    public static String getMonthFromNumm(long time){
        SimpleDateFormat mSdf = new SimpleDateFormat("MM", Locale.CHINA);
        Date date = new Date(time);
        String str = mSdf.format(date);
        return str;
    }
    public static String getDateFromNumm(long time){
        SimpleDateFormat mSdf = new SimpleDateFormat("dd", Locale.CHINA);
        Date date = new Date(time);
        String str = mSdf.format(date);
        return str;
    }



    /**
     * 通过int 数字  获取  时分秒
     * @param time
     * @return
     */
    public static String getTimeFromNum(int time){

        int h,m,s;
        StringBuffer timeLong = new StringBuffer();
        h = time/60/60;
        m = time/60 - h*60;
        s = time -m*60-h*60*60;
        if(h>9)
            timeLong.append(h+":");
        else
            timeLong.append("0"+h+":");

        if(m>9)
            timeLong.append(m+":");
        else
            timeLong.append("0"+m+":");

        if(s>9)
            timeLong.append(s+"");
        else
            timeLong.append("0"+s+"");

        return timeLong.toString();
    }

    public static String getNumberDecimalFormat(long millis){
        DecimalFormat df = new DecimalFormat("###.00");
        return df.format(millis);
    }

    /**
     * 字符串时间 转换为 long
     * @param timeStr
     */
    public static long getTime(String timeStr, String format){
        SimpleDateFormat mSdf = new SimpleDateFormat(format, Locale.CHINA);
        Date date =null;
        try {
            date = mSdf.parse(timeStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public static String convertHHMMSS(int time)
    {
        String strRet = "";
        int h, m, s;
        int left = 0;

        h = time/3600;

        left = time%3600;

        m = left/60;

        s = left%60;

        if ( h < 10 )
        {
            strRet = strRet + "0" + h;
        }
        else
        {
            strRet = strRet + h;
        }

        if ( m < 10 )
        {
            strRet = strRet + ":" + "0" + m;
        }
        else
        {
            strRet = strRet + ":" + m;
        }

        if ( s < 10 )
        {
            strRet = strRet + ":" + "0" + s;
        }
        else
        {
            strRet = strRet + ":"+ s;
        }

        return strRet;
    }

    public static String getDay(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        return day;
    }

    public static String getMonth(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String month = String.valueOf(c.get(Calendar.MONTH));
        return month;
    }

    public static CalendarDate getCalendarDate(long time){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String month = String.valueOf(c.get(Calendar.MONTH));// 获取当前月份
        String day= String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String way= String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        return new CalendarDate(year,month,day,way,time);
    }

    /*
     * 获取多少时间之前
     */
    public static String getCurrentBeforeTime(String videotime){
        String beforetime = "1分钟前";
        long time = (System.currentTimeMillis() - Long.parseLong(videotime))/1000;

        if(time >= 60){
            time = time / 60; //分
            beforetime = time + "分钟之前";
            if(time >= 60){
                time = time / 60; //时
                beforetime = time + "小时之前";
                if(time >= 24){
                    time = time / 24; //天
                    beforetime = time + "天之前";
                }
            }
        }else {
            beforetime = time + "秒之前";
        }

        return beforetime;
    }

}


