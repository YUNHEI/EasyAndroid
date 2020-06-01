package com.chen.basemodule.util

import android.R
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.FragmentActivity
import java.io.File
import java.util.*

/**
 * 公共类
 * Created by shuyu on 2016/11/11.
 */
object CommonUtil {
    fun stringForTime(timeMs: Int): String {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00"
        }
        val totalSeconds = timeMs / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        val stringBuilder = StringBuilder()
        val mFormatter = Formatter(stringBuilder, Locale.getDefault())
        return if (hours > 0) {
            mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        } else {
            mFormatter.format("%02d:%02d", minutes, seconds).toString()
        }
    }

    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return wifiNetworkInfo.isConnected
    }

    /**
     * Get activity from context object
     *
     * @param context something
     * @return object of Activity or null if it is not Activity
     */
    fun scanForActivity(context: Context?): Activity? {
        if (context == null) return null
        if (context is Activity) {
            return context
        } else if (context is ContextWrapper) {
            return scanForActivity(context.baseContext)
        }
        return null
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources
                .getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 获取ActionBar高度
     *
     * @param activity activity
     * @return ActionBar高度
     */
    fun getActionBarHeight(activity: Activity): Int {
        val tv = TypedValue()
        return if (activity.theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
            TypedValue.complexToDimensionPixelSize(tv.data, activity.resources.displayMetrics)
        } else 0
    }

    fun hideSupportActionBar(context: Context?, actionBar: Boolean, statusBar: Boolean) {
        if (actionBar) {
            val appCompatActivity = getAppCompActivity(context)
            if (appCompatActivity != null) {
                val ab = appCompatActivity.supportActionBar
                if (ab != null) {
                    ab.setShowHideAnimationEnabled(false)
                    ab.hide()
                }
            }
        }
        if (statusBar) {
            if (context is FragmentActivity) {
                context.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                getAppCompActivity(context)!!.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }
    }

    fun showSupportActionBar(context: Context?, actionBar: Boolean, statusBar: Boolean) {
        if (actionBar) {
            val appCompatActivity = getAppCompActivity(context)
            if (appCompatActivity != null) {
                val ab = appCompatActivity.supportActionBar
                if (ab != null) {
                    ab.setShowHideAnimationEnabled(false)
                    ab.show()
                }
            }
        }
        if (statusBar) {
            if (context is FragmentActivity) {
                context.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                getAppCompActivity(context)!!.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }
    }

    fun hideNavKey(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //       设置屏幕始终在前面，不然点击鼠标，重新出现虚拟按键
            (context as Activity).window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
// bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
        } else {
            (context as Activity).window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                    )
        }
    }

    fun showNavKey(context: Context, systemUiVisibility: Int) {
        (context as Activity).window.decorView.systemUiVisibility = systemUiVisibility
    }

    /**
     * Get AppCompatActivity from context
     *
     * @param context
     * @return AppCompatActivity if it's not null
     */
    fun getAppCompActivity(context: Context?): AppCompatActivity? {
        if (context == null) return null
        if (context is AppCompatActivity) {
            return context
        } else if (context is ContextThemeWrapper) {
            return getAppCompActivity(context.baseContext)
        }
        return null
    }

    /**
     * dip转为PX
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val fontScale = context.resources.displayMetrics.density
        return (dipValue * fontScale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 获取屏幕的宽度px
     *
     * @param context 上下文
     * @return 屏幕宽px
     */
    fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics() // 创建了一张白纸
        windowManager.defaultDisplay.getMetrics(outMetrics) // 给白纸设置宽高
        return outMetrics.widthPixels
    }

    /**
     * 获取屏幕的高度px
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    fun getScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics() // 创建了一张白纸
        windowManager.defaultDisplay.getMetrics(outMetrics) // 给白纸设置宽高
        return outMetrics.heightPixels
    }

    /**
     * 下载速度文本
     */
    fun getTextSpeed(speed: Long): String {
        var text = ""
        if (speed >= 0 && speed < 1024) {
            text = "$speed KB/s"
        } else if (speed >= 1024 && speed < 1024 * 1024) {
            text = java.lang.Long.toString(speed / 1024) + " KB/s"
        } else if (speed >= 1024 * 1024 && speed < 1024 * 1024 * 1024) {
            text = java.lang.Long.toString(speed / (1024 * 1024)) + " MB/s"
        }
        return text
    }

    fun deleteFile(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            if (file.isFile) {
                file.delete()
            } else {
                val filePaths = file.list()
                for (path in filePaths) {
                    deleteFile(filePath + File.separator + path)
                }
                file.delete()
            }
        }
    }
}