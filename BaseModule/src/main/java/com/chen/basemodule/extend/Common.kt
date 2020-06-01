package com.chen.basemodule.extend

import android.view.View
import androidx.annotation.IdRes
import com.google.gson.Gson

/**
 *  Created by chen on 2019/6/17
 **/

fun listenClick(vararg views: View?, onClick: ((view: View) -> Unit)) {
    views.forEach { it?.setOnClickListener(onClick) }
}

fun View.findViews(@IdRes vararg ids: Int): Array<View?> {
    val views = Array<View?>(ids.size) { null }
    ids.forEachIndexed { index, i -> views[index] = findViewById(i) }
    return views
}

inline fun <reified T> fromJson(json: String?): T? = Gson().fromJson(if (json.isNullOrEmpty()) null else json, T::class.java)