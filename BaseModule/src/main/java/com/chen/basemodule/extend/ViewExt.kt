package com.chen.basemodule.extend

import android.view.View

fun View.visible(visible: Boolean? = null) {
    visible?.run {
        visibility = if (this) View.VISIBLE else View.GONE
    }
}
