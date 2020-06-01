package com.chen.baseextend.extend

import java.math.BigDecimal
import java.math.RoundingMode

/**
 *  Created by chen on 2019/8/14
 **/
fun BigDecimal?.string(length: Int = 2): String {
    if (this == null) return "0"
    val value = setScale(length, RoundingMode.HALF_UP).toString()
    return if (".*\\.[0-9]0$".toRegex().matches(value)) value.substring(0, value.length.dec()) else value
}

fun BigDecimal?.fenToYuan(unit: String? = "元"): String {
    if (this == null) return "0${unit.orEmpty()}"
    return "${divide(BigDecimal(100)).string()}${unit.orEmpty()}"
}

fun String?.withWaterM(): String? {
    return when {
        isNullOrEmpty() -> {
            this
        }
        this!!.contains("?") -> {
            replace("?", "-watermark?")
        }
        else -> {
            "$this-watermark"
        }
    }
}