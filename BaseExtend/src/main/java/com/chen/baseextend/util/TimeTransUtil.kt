package com.chen.baseextend.util

/**
 *  Created by chen on 2019/9/24
 **/
object TimeTransUtil {

    fun timeToTip(minute: Int? = 0): Pair<String, String> {
        return when (minute) {
            30 -> Pair("30","分钟")
            60 -> Pair("1","小时")
            120 -> Pair("2","小时")
            180 -> Pair("3","小时")
            240 -> Pair("4","小时")
            300 -> Pair("5","小时")
            360 -> Pair("6","小时")
            720 -> Pair("12","小时")
            1440 -> Pair("1","天")
            2880 -> Pair("2","天")
            4320 -> Pair("3","天")
            5760 -> Pair("4","天")
            7200 -> Pair("5","天")
            8640 -> Pair("6","天")
            10080 -> Pair("7","天")
            else -> Pair("0","分钟")
        }
    }

    fun tipToTime(tip: String): Int {
        return when (tip) {
            "30分钟" -> 30
            "1小时" -> 60
            "2小时" -> 120
            "3小时" -> 180
            "4小时" -> 240
            "5小时" -> 300
            "6小时" -> 360
            "12小时" -> 720
            "1天" -> 1440
            "2天" -> 2880
            "3天" -> 4320
            "4天" -> 5760
            "5天" -> 7200
            "6天" -> 8640
            "7天" -> 10080
            else -> 0
        }
    }
}