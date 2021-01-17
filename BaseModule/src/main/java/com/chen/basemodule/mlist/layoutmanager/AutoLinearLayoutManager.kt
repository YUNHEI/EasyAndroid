package com.chen.basemodule.mlist.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

class AutoLinearLayoutManager(context: Context?, orientation: Int, reverseLayout: Boolean = false) : LinearLayoutManager(context, orientation, reverseLayout) {

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }
}