package com.chen.basemodule.mlist.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class AutoGridLayoutManager(context: Context, spanCount: Int) : GridLayoutManager(context, spanCount) {

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }
}