package com.chen.basemodule.extend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import com.chen.basemodule.basem.FragmentBindingDelegate
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
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


fun Fragment.mark(start: Long, key: String) =
    println("${this::class.jvmName} key:${key} time:${System.currentTimeMillis() - start}")

