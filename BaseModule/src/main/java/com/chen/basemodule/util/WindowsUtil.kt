package com.chen.basemodule.util

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes


import com.chen.basemodule.extend.color


object WindowsUtil {

    fun setDarkTheme(activity: Activity, dark: Boolean) {

        activity.window.decorView.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or if (dark) View.SYSTEM_UI_FLAG_LAYOUT_STABLE else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    fun setStatusBar(activity: Activity, @ColorRes color: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = activity.color(color)
        }
    }
}
