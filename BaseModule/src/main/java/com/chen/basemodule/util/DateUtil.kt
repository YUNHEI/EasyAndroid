package com.chen.basemodule.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    const val DAY = 0
    const val HOUR = 1
    const val MINUTE = 2
    const val SECOND = 3
    const val THIS_DAY = 4
    const val YEAR = 5
    const val MILLI = 6
    const val WEEK = 7
    var ONE_DAY = 1000 * 60 * 60 * 24.toLong()
    var ONE_HOUR = 1000 * 60 * 60.toLong()
    var ONE_MINUTE = 1000 * 60.toLong()
    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val dateFormatNoYear: DateFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
    private val dateFormatHm: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dateFormatMD: DateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
    val dateFormatFull: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val dateFormatNoSecond: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    private val dateFormatNoSecond2: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
    private val dateFormatNoSecond3: DateFormat = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
    private val dateFormatTimer: DateFormat = SimpleDateFormat("mm分ss秒", Locale.getDefault())
    fun timestampToString(timestamp: Long): String {
        return dataToString(Date(timestamp))
    }

    fun secToTime(second: Int): String {
        if (second <= 0) return "00:00:00"
        val hour = second / 3600
        val min = (second - hour * 3600) / 60
        val sec = second - hour * 3600 - min * 60
        val secS = if (sec < 10) "0$sec" else sec.toString() + ""
        val minS = if (min < 10) "0$min" else min.toString() + ""
        val hourS = if (hour <= 0) "00" else if (hour < 10) "0$hour" else hour.toString() + ""
        return if (hourS.length > 0) String.format("%s:%s:%s", hourS, minS, secS) else String.format("%s:%s", minS, secS)
    }

    fun millToTime(millis: Long): String {
        val sec = (millis / 1000).toInt()
        return if (sec < 60) sec.toString() + "秒" else {
            val second = sec % 60
            val min = sec / 60
            if (min < 60) min.toString() + "分" + second + "秒" else {
                val minute = min % 60
                val h = min / 60
                if (h < 24) h.toString() + "时" + minute + "分" + second + "秒" else {
                    val hour = h % 24
                    val day = h / 24
                    day.toString() + "天" + hour + "时" + minute + "分" + second + "秒"
                }
            }
        }
    }

    fun isToday(timestamp: Long): Boolean {
        return dateFormat.format(Date(timestamp)).equals(dateFormat.format(Date(System.currentTimeMillis())), ignoreCase = true)
    }

    fun isYesterday(timestamp: Long): Boolean {
        val cur = dateFormat.format(Date(System.currentTimeMillis()))
        val date = dateFormat.format(Date(timestamp))
        return (cur.substring(0, 7).equals(date.substring(0, 7), ignoreCase = true)
                && cur.substring(8, 10).toInt() - date.substring(8, 10).toInt() == 1)
    }

    fun dayLeft(timestamp: Long): Int {
        val timeLeft = timestamp - System.currentTimeMillis()
        if (timeLeft <= 0) return 0
        return if (timeLeft <= ONE_DAY) 1 else (timeLeft / ONE_DAY).toInt() + if (timeLeft % ONE_DAY > 0) 1 else 0
    }

    fun formatStringDate(date: String): String {
        if (date.length > 0) {
            val temp = date.split("-").toTypedArray()
            return if (temp.size < 3) "" else temp[1] + "月" + temp[2] + "日"
        }
        return ""
    }

    fun dataToString(date: Date?): String {
        return dateFormat.format(date)
    }

    fun stringToData(dates: String?, format: DateFormat = dateFormat): Date {
        return try {
            format.parse(dates) ?: Date()
        } catch (e: ParseException) {
            Date()
        }
    }

    fun getChatDate(timestamp: Long): String {
        return if (isToday(timestamp)) {
            dateFormatHm.format(timestamp)
        } else if (isYesterday(timestamp)) {
            "昨天 " + dateFormatHm.format(timestamp)
        } else {
            dateFormatNoSecond.format(timestamp)
        }
    }

    fun getDateWithHours(timestamp: Long): String {
        return getDateWithHours(Date(timestamp), true, true, true)
    }

    fun getDateWithHours(date: String?): String {
        return getDateWithHours(date, true, false, false)
    }

    fun getDateWithHours(date: String?, dataWithHour: Boolean, showWeek: Boolean, showYesterday: Boolean): String {
        try {
            val d = dateFormatFull.parse(date)
            return getDateWithHours(d, dataWithHour, showWeek, showYesterday)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    @JvmStatic
    fun getDateWithHours(date: Date?, dataWithHour: Boolean, showWeek: Boolean, showYesterday: Boolean): String {
        val nowDate = Date()
        val result = ""
        try {
            val diffM = timeOffset(date, nowDate, MINUTE)
            if (diffM == 0L) {
                return "刚刚"
            }
            val diffHour = timeOffset(date, nowDate, HOUR)
            if (diffHour == 0L) {
                return if (diffM > 0) {
                    diffM.toString() + "分钟前"
                } else {
                    (0 - diffM).toString() + "分钟后"
                }
            }
            val diffDay = timeOffset(date, nowDate, DAY)
            return if (diffDay == 0L) {
                if (diffHour > 0) {
                    diffHour.toString() + "小时前"
                } else {
                    (0 - diffHour).toString() + "小时后"
                }
            } else if (showYesterday && diffDay == 1L) {
                "昨天"
            } else if (showWeek && diffDay < 7) {
                dayForWeek(date)
            } else if (timeOffset(date, nowDate, YEAR) < 1) {
                if (dataWithHour) dateFormatNoYear.format(date) else dateFormatMD.format(date)
            } else {
                dateFormat.format(date)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun dayForWeek(pTime: Date?): String {
        val c = Calendar.getInstance()
        c.time = pTime
        val week: String
        week = when (c[Calendar.DAY_OF_WEEK]) {
            Calendar.SUNDAY -> "星期日"
            Calendar.MONDAY -> "星期一"
            Calendar.TUESDAY -> "星期二"
            Calendar.WEDNESDAY -> "星期三"
            Calendar.THURSDAY -> "星期四"
            Calendar.FRIDAY -> "星期五"
            Calendar.SATURDAY -> "星期六"
            else -> ""
        }
        return week
    }

    /**
     * 计算两个日期的差值
     *
     * @param dateEarly 较早的时间
     * @param dateLater 较晚的时间
     * @return 差值
     * @throws ParseException
     */
    fun timeOffset(dateEarly: Date?, dateLater: Date?, diffBase: Int): Long {
        if (dateEarly == null || dateLater == null) {
            return 0
        }
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = dateEarly
        val timeEarly = cal1.timeInMillis
        cal2.time = dateLater
        val timeLater = cal2.timeInMillis
        var diff: Long = 0
        when (diffBase) {
            DAY -> {
                diff = (timeLater - timeEarly) / ONE_DAY
                if (diff < 365) {
                    val earlyDay = cal1[Calendar.DAY_OF_YEAR]
                    val laterDay = cal2[Calendar.DAY_OF_YEAR]
                    diff = laterDay - earlyDay.toLong()
                }
            }
            WEEK -> {
                val earlyWeek = cal1[Calendar.WEEK_OF_YEAR]
                val laterWeek = cal2[Calendar.WEEK_OF_YEAR]
                diff = laterWeek - earlyWeek.toLong()
            }
            HOUR -> diff = (timeLater - timeEarly) / ONE_HOUR
            MINUTE -> diff = (timeLater - timeEarly) / ONE_MINUTE
            SECOND -> diff = (timeLater - timeEarly) / 1000
            MILLI -> diff = timeLater - timeEarly
            YEAR -> {
                val earlyYear = cal1[Calendar.YEAR]
                val laterYaer = cal2[Calendar.YEAR]
                diff = laterYaer - earlyYear.toLong()
            }
            else -> {
            }
        }
        return diff
    }

    fun getDateWithHoursFormated(timestamp: Long): String {
        return dateFormatNoSecond2.format(timestamp)
    }

    fun getDateWithHoursFormated3(timestamp: Long): String {
        return dateFormat.format(timestamp)
    }

    fun getMinSecondTimerFormated(timestamp: Long): String {
        return dateFormatTimer.format(timestamp)
    }

    fun utc2LocalDate(date: String?): String {
        val utcFormater = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        utcFormater.timeZone = TimeZone.getTimeZone("UTC")
        var utcDate: Date? = null
        try {
            utcDate = utcFormater.parse(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dateFormatFull.format(utcDate)
    }

    fun timeToLong(time: String?): Long {
        try {
            val Gmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            return Gmt.parse(time).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }
}