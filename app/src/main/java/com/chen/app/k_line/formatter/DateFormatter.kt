package com.chen.app.k_line.formatter

import com.chen.app.k_line.DateUtil
import com.chen.app.k_line.base.IDateTimeFormatter
import java.util.*

/**
 * 时间格式化器
 * Created by tifezh on 2016/6/21.
 */
class DateFormatter : IDateTimeFormatter {
    override fun format(date: Date?): String? {
        return if (date != null) {
            DateUtil.DateFormat.format(date)
        } else {
            ""
        }
    }
}