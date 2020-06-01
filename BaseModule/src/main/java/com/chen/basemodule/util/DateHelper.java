package com.chen.basemodule.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * 日期时间的工具类
 *
 * @author qiujunqi
 * @version 1.0
 */

public class DateHelper {
    public static Calendar CALENDAR = Calendar.getInstance();

    private DateHelper() {
    }


    /**
     * 获得年份
     *
     * @return yyyy
     */
    public static int getYear() {
        return CALENDAR.get(Calendar.YEAR);
    }

    /**
     * 获得月份
     *
     * @return yyyy
     */
    public static int getMonth() {
        return CALENDAR.get(Calendar.MONTH);
    }

    /**
     * 获得日期for月份
     *
     * @return yyyy
     */
    public static int getDay() {
        return CALENDAR.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得日期
     *
     * @return yyyy年MM月dd日
     */
    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(CALENDAR.getTime());
    }

    /**
     * 获得 年月
     *
     * @return yyyy-MM
     */
    public static String getDateYYMM() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(CALENDAR.getTime());
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();

        a.set(Calendar.DATE, 1);

        a.roll(Calendar.DATE, -1);

        int maxDate = a.get(Calendar.DATE);

        return maxDate;

    }



    /**
     * 获得星期几
     *
     * @return 星期几
     */
    public static String getWeek() {
        String str = "";
        switch (CALENDAR.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                str = "星期日 ";
                break;
            case 2:
                str = "星期一 ";
                break;
            case 3:
                str = "星期二 ";
                break;
            case 4:
                str = "星期三 ";
                break;
            case 5:
                str = "星期四 ";
                break;
            case 6:
                str = "星期五 ";
                break;
            default:
                str = "星期六 ";
                break;
        }
        return str;
    }

    /**
     * 根据给定时间获得星期几
     *
     * @param dateTime
     * @return
     * @author OLH
     * @date 2013-9-10 下午3:45:50
     */
    public static String getWeek(long dateTime) {
        String str = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                str = "星期日 ";
                break;
            case 2:
                str = "星期一 ";
                break;
            case 3:
                str = "星期二 ";
                break;
            case 4:
                str = "星期三 ";
                break;
            case 5:
                str = "星期四 ";
                break;
            case 6:
                str = "星期五 ";
                break;
            default:
                str = "星期六 ";
                break;
        }
        return str;
    }

    /**
     * 获取当前小时数
     *
     * @return int
     */
    public static int getHour() {
        return CALENDAR.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟数
     *
     * @return int
     */
    public static int getMin() {
        return CALENDAR.get(Calendar.MINUTE);
    }

    /**
     * 获取当前时间
     *
     * @return 02：01
     */
    public static String getTime() {
        int hour = CALENDAR.get(Calendar.HOUR_OF_DAY);
        int min = CALENDAR.get(Calendar.MINUTE);
        StringBuilder stringBuilder = new StringBuilder();
        // String ss = "AM";
        // if (hour > 12) {
        // hour -= 12;
        // ss = "PM";
        // }
        if (hour < 10)
            stringBuilder.append("0");
        stringBuilder.append(hour);
        stringBuilder.append(":");
        if (min < 10)
            stringBuilder.append("0");
        stringBuilder.append(min);
        stringBuilder.append(" ");
        // stringBuilder.append(ss);
        return stringBuilder.toString();
    }

    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return yyyy-MM-dd
     */
    public static String formatDataToYMD(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * 格式化时间
     *
     * @param data
     * @return yyyy-MM-dd
     */
    public static String formatDataToYMD(Date data) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(data);
    }

    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return yyyy-MM-dd HH:mm
     */
    public static String formatDataToYMDMS(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return dd HH
     */
    public static String formatDataToddHH(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("dd日HH点");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return mm:ss
     */
    public static String formatDataToMS(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return MM月dd日
     */
    public static String formatDateToHMS(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }


    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return MM月dd日
     */
    public static String formatDateToMD(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("MM月dd日");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return MM月dd日
     */
    public static String formatDateToHM(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return yyyy年MM月dd日 HH:mm
     */
    public static String formatDataToYMDHM(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return yyyy年MM月dd日
     */
    public static String formatDataToYMDCN(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return 日
     */
    public static String formatDataToD(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("dd");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }
    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return 年
     */
    public static String formatDataToYY(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }
    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return 月
     */
    public static String formatDataToMM(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("MM");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return yyyy年MM月
     */
    public static String formatDateToYM(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * @param timeMillis
     * @return yyyy/MM/dd HH:mm
     */
    public static String formatRealTimeData(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * @param timeMillis
     * @return yyyy/MM/dd HH:mm
     */
    public static String formatCNYMDHM(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * @param timeMillis
     * @return yyyy/MM/dd HH:mm:ss
     */
    public static String formatDetailTimeData(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * 转换字符串为Date类型
     *
     * @param str ：yyyy-MM-dd HH:mm，yyyy/MM/dd HH:mm
     * @return date
     */
    public static Date formatStrToDate(String str) {
        try {
            if (str.contains("-"))
                return formatStrToDate2(str);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = sdf.parse(str);
            return date;
        } catch (ParseException e1) {
            e1.printStackTrace();
            return new Date();
        }
    }

    private static Date formatStrToDate2(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(str);
            return date;
        } catch (ParseException e1) {
            e1.printStackTrace();
            return new Date();
        }
    }

    /**
     * 将Long值的时间转换成标准日期格式
     *
     * @param millis
     * @return "yyyy-MM-dd HH:mm:ss.SSS"
     */
    public static String covertMillisToDateStr(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if (millis > 0) {
            Date dt = new Date(millis);
            return sdf.format(dt);
        } else {
            return "1900-01-01 00:00:00";
        }
    }

    //

    /**
     * 将时间类型转换成毫秒
     *
     * @param date yyyy-MM-dd HH:mm:ss.SSS
     * @return long
     */
    public static long getdaytime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date dt2 = null;
        try {
            dt2 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt2 == null) {
            return 0;
        }
        return dt2.getTime();
    }


    public static int getDay(long timeMs) {
        long totalSeconds = timeMs / 1000;// 获取文件有多少秒
        StringBuilder mFormatBuilder = new StringBuilder();

        Formatter formatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int seconds = (int) totalSeconds % 60;
        int minutes = (int) (totalSeconds / 60) % 60;
        int hours = (int) totalSeconds / 3600;
        int day = (int) hours / 24;
        return day;
    }

    public static int getHour(long timeMs) {
        long totalSeconds = timeMs / 1000;// 获取文件有多少小时
        int hours = (int) totalSeconds / 3600;
        return hours;
    }

    public static int getMinute(long timeMs) {
        long totalSeconds = timeMs / 1000;// 获取文件有多少分钟
        int minutes = (int) (totalSeconds / 60) % 60;
        return minutes;
    }

    public static int getSecond(long timeMs) {
        long totalSeconds = timeMs / 1000;// 获取文件有多少秒
        int seconds = (int) totalSeconds % 60;
        return seconds;
    }

    /**
     * 将结束时间戳距离开始时间戳的毫秒值转化成小时、分钟、秒数
     *
     * @param timeMs 结束时间戳距离开始时间戳的毫秒值
     * @return 00小时:00分:00秒
     */
    @SuppressWarnings("resource")
    public static String covertDiffMillisToTimeStr(long timeMs) {
        long totalSeconds = timeMs / 1000;// 获取文件有多少秒
        StringBuilder mFormatBuilder = new StringBuilder();

        Formatter formatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int seconds = (int) totalSeconds % 60;
        int minutes = (int) (totalSeconds / 60) % 60;
        int hours = (int) totalSeconds / 3600;
        int day = (int) hours / 24;
        mFormatBuilder.setLength(0);
        if (day > 0) {
            return formatter.format("%02d天%02d小时%02d分%02d秒", day, hours % 24, minutes, seconds).toString();// 格式化字符串
        } else if (hours > 0) {
            return formatter.format("%02d小时%02d分%02d秒", hours, minutes, seconds).toString();// 格式化字符串
        } else if (minutes > 0) {
            return formatter.format("%02d分%02d秒", minutes, seconds).toString();
        } else {
            return formatter.format("%02d分%02d秒", 0, seconds).toString();
        }
    }

    /**
     * 获得服务端传回来的时间，
     * 可以解析【"/Date(1376530771673)/"】和【"2013-08-15T09:39:31.673"】这两种格式
     *
     * @param
     * @return long型时间，当参数为空，返回Long.MIN_VALUE
     */
    public static long convertToLongByStr(String str) {
        try {
            if (str.contains("Date")) {//
                return Long.valueOf(str.substring(6, 19));
            } else if (str.contains("T")) {
                return getDayTimeToLongToSecondByStr(str.replace('T', ' ').toString());
            } else if (!TextUtils.isEmpty(str)) {
                return getDayTimeToLongToSecondByStr(str);
            }
            return Long.MIN_VALUE;
        } catch (Exception e1) {
            e1.printStackTrace();
            return Long.MIN_VALUE;
        }
    }

    /**
     * 将字符串dateStr(2013-01-31 08:59:49.42)转换成long型
     *
     * @param dateStr
     * @return long型，精确到毫秒
     */
    public static long getDayTimeToLongToSecondByStr(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt2 = null;
        try {
            dt2 = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt2.getTime();
    }

    /**
     * JASON 时间字符串转化为时间戳
     *
     * @param jasonStr
     * @return
     */
    public static long getDateByJasonStr(String jasonStr) {
        int startIndex = jasonStr.indexOf("(");
        int endIndex = jasonStr.indexOf(")");
        if (startIndex > 0 && endIndex > 0) {
            long l = new Long(jasonStr.substring(startIndex + 1, endIndex));
            return l;
        }
        return Long.MIN_VALUE;
    }

    /**
     * 更换时间天数
     *
     * @param date
     * @param n
     * @return
     */
    public static long changeDay(long date, int n) {
        long endDate = 0;
        try {
            Calendar cd = Calendar.getInstance();
            cd.setTimeInMillis(date);
            cd.add(Calendar.DATE, n);// 增加一天
            return cd.getTimeInMillis();
        } catch (Exception e) {
            return endDate;
        }
    }

    /**
     * 获取剩余时间（天）
     *
     * @return
     */
    public static int getDayNum(long current, long end) {
        try {
            if (current >= end) {
                return 0;
            } else {
                int day = (int) ((end - current) / (1000 * 24 * 60 * 60));
                if (day == 0){
                    return 1;
                }
                return day;
            }
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 时间差
     */
    public static long timeDifference(String nowtime, String endtime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = 0;
        try {
            //系统时间转化为Date形式
            Date dstart = format.parse(nowtime);
            //活动结束时间转化为Date形式
            Date dend = format.parse(endtime);
            //算出时间差，用ms表示
            diff = dend.getTime() - dstart.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //返回时间差
        return diff;
    }

    /**
     * 根据时间获取天数
     * @return
     */
    public static int getDayNum(long time){
        return (int) (time/(1000*60*60*24));
    }

}
