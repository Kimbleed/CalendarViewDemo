package com.example.a49479.calendarviewdemo;

/**
 * Created by 49479 on 2017/8/30.
 */

public class CalendarDate {
    String year;
    String month;
    String day;
    String way;
    long time;

    public CalendarDate(String year, String month, String day, String way, long time) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.way = way;
        this.time = time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getDateFormat(String format){
        return DateUtils.getDateFromNum(time,format);
    }
}
