package com.chen.basemodule.extend

import android.view.View
import androidx.annotation.IdRes
import com.google.gson.Gson

/**
 *  Created by chen on 2019/6/17
 **/


fun listenClick(vararg views: View?, interval:Long = 200, onClick: ((view: View) -> Unit)) {
    views.forEach {
        if (interval > 0) {
            var lastClick = 0L
            it?.setOnClickListener {
                if (System.currentTimeMillis() - lastClick >= interval){
                    onClick.invoke(it)
                    lastClick = System.currentTimeMillis()
                }
            }
        }else {
            it?.setOnClickListener(onClick)
        }
    }
}

fun View.findViews(@IdRes vararg ids: Int): Array<View?> {
    val views = Array<View?>(ids.size) { null }
    ids.forEachIndexed { index, i -> views[index] = findViewById(i) }
    return views
}

inline fun <reified T> fromJson(json: String?): T? = Gson().fromJson(if (json.isNullOrEmpty()) null else json, T::class.java)


/**
 * 获取第index个数据在数组中的位置
 */
inline fun <T> Iterable<T>.indexToPosition(index:Int, predicate: (T) -> Boolean): Pair<Int,T?> {
    var i = 0
    for ((p, element) in withIndex()) {
        if (predicate(element)) {
            if (i == index) return p to element
            else i++
        }
    }
    return -1 to null
}