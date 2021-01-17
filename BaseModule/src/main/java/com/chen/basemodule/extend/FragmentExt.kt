package com.chen.basemodule.extend

import androidx.fragment.app.Fragment
import kotlin.reflect.jvm.jvmName

const val FRAGMENT_SWIPE_TYPE = "fragmentSwipeType"

fun Fragment.argString(key: String): String? {
    return arguments?.getString(key)
}

fun Fragment.argInt(key: String, default: Int = 0): Int {
    return arguments?.getInt(key, default) ?: default
}

fun Fragment.argLong(key: String, default: Long = 0): Long {
    return arguments?.getLong(key, default) ?: default
}

fun Fragment.argBoolean(key: String, default: Boolean = false): Boolean {
    return arguments?.getBoolean(key, default) ?: default
}



fun Fragment.mark(start: Long, key: String) = println("${this::class.jvmName} key:${key} time:${System.currentTimeMillis() - start}")