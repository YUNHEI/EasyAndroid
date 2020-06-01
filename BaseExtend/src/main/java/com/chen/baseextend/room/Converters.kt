package com.chen.baseextend.room

import androidx.room.TypeConverter
import java.math.BigDecimal

/**
 *  Created by chen on 2019/10/10
 **/
class Converters {

    @TypeConverter
    fun convertPrice(price: BigDecimal?): String? {
        return price?.toString()
    }

    @TypeConverter
    fun revertPrice(price: String?): BigDecimal {
        return if (price.isNullOrEmpty()) {
            BigDecimal(0)
        } else BigDecimal(price)
    }

    @TypeConverter
    fun convertMutableList(list: MutableList<String>?): String? {
        return list?.joinToString(",") { it }
    }

    @TypeConverter
    fun revertMutableList(lists: String?): MutableList<String>? {
        return if (lists.isNullOrEmpty()) {
            mutableListOf()
        } else lists.split(",").toMutableList()
    }

    @TypeConverter
    fun convertBoolean(value: Boolean?): Int? {
        return if (value == true) 1 else 0
    }

    @TypeConverter
    fun revertBoolean(value: Int?): Boolean {
        return value?.run { this > 0 } ?: false
    }
}