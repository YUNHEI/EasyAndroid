package com.chen.basemodule.mlist

import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import com.chen.basemodule.basem.BaseBean
import com.google.gson.Gson
import java.io.Serializable
import java.math.BigDecimal
import kotlin.reflect.KClass

/**
 * Fragment 容器
 */
class BaseItemFragmentBean(
    val fragmentClass: KClass<*>,
    vararg args: Pair<String, Any?>,
    val bd: Bundle = Bundle(),
    val key: String? = fragmentClass.simpleName
) : BaseBean() {

    init {
        args.forEach {
            bd.run {
                it.second?.run {
                    when (this) {
                        is Byte -> putByte(it.first, this)
                        is Char -> putChar(it.first, this)
                        is Size -> putSize(it.first, this)
                        is Int -> putInt(it.first, this)
                        is Short -> putShort(it.first, this)
                        is Long -> putLong(it.first, this)
                        is Float -> putFloat(it.first, this)
                        is Double -> putDouble(it.first, this)
                        is CharSequence -> putCharSequence(it.first, this)
                        is Parcelable -> putParcelable(it.first, this)
                        is Serializable -> putSerializable(it.first, this)
                        is String -> putString(it.first, this)
                        else -> {
                            putString(it.first, Gson().toJson(this))
                        }
                    }
                }
            }
        }
    }
}