/*
 * Copyright (c) 2016, HuiZhe Corporation, All Rights Reserved
 */
package com.ssj.hulijie.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    @SuppressLint("SimpleDateFormat")
    public static String longToString(Long enterTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (enterTime != null) {
            Date curDate = new Date(enterTime);// 获取当前时间
            return formatter.format(curDate);
        }
        return null;
    }

    public static String longToString(Long enterTime,String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        if (enterTime != null) {
            Date curDate = new Date(enterTime);// 获取当前时间
            return formatter.format(curDate);
        }
        return null;
    }


    public static String stoppedTimeCalc(Long driveinTime, Long leftTime) {
        if (driveinTime == leftTime) {
            return "0" + "天" + "0" + "小时" + "0" + "分";
        }
        long l = leftTime - driveinTime;
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        // long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return "" + day + "天" + hour + "小时" + min + "分";

    }

    public static String getIntervalUpdateTime(long intervalTime) {
        StringBuilder result = new StringBuilder();
        long interval = intervalTime / 1000;
        final long day = 24 * 60 * 60;
        final long hour = 60 * 60;
        final long minute = 60;
        int detailDay = 0;
        int detailHour = 0;
        int detailMinute = 0;
        int detailSecond = 0;
        if (interval >= day) {
            detailDay = (int) (interval / day);
            interval = interval - detailDay * day;
        }
        if (interval >= hour) {
            detailHour = (int) (interval / hour);
            interval = interval - hour * detailHour;
        }
        if (interval >= minute) {
            detailMinute = (int) (interval / minute);
            interval = interval - detailMinute * minute;
        }
        if (interval > 0) {
            detailSecond = (int) interval;
        }
        result.setLength(0);
        if (detailDay > 0) {
            result.append(detailDay);
            result.append("天");
        }
        if (detailHour > 0) {
            result.append(detailHour);
            result.append("小时");
        }
        if (detailMinute > 0) {
            result.append(detailMinute);
            result.append("分");
        }
        if (detailSecond > 0) {
            result.append(detailSecond);
            result.append("秒");
        }
        return result.toString();
    }


    public static Date getDate(long unixDate) {
        //时间戳转化为Sting或Date

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:WheelViewInit");

        Long time = new Long(unixDate);


        String d = format.format(time);

        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }




//    Calendar cal = Calendar.getInstance();
//    int day = cal.get(Calendar.DATE);
//    int month = cal.get(Calendar.MONTH) + 1;
//    int year = cal.get(Calendar.YEAR);
//    int dow = cal.get(Calendar.DAY_OF_WEEK);
//    int dom = cal.get(Calendar.DAY_OF_MONTH);
//    int doy = cal.get(Calendar.DAY_OF_YEAR);
//
//    System.out.println("Current Date: " + cal.getTime());
//    System.out.println("Day: " + day);
//    System.out.println("Month: " + month);
//    System.out.println("Year: " + year);
//    System.out.println("Day of Week: " + dow);
//    System.out.println("Day of Month: " + dom);
//    System.out.println("Day of Year: " + doy);

    public static int getCurrentDateYear(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static int getCurrentDateMonth(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getCurrentDateDay(){
        Calendar cal = Calendar.getInstance();
        return  cal.get(Calendar.DATE);
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }


    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

}
