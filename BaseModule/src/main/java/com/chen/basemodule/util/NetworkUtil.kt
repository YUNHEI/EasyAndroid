package com.chen.basemodule.util

import android.content.Context
import android.net.ConnectivityManager
import com.chen.basemodule.BaseModuleLoad.app

/**
 * Created by chen on 2016/10/22.
 */
object NetworkUtil {
    val isConnected: Boolean
        get() {
            val connectivityManager = app!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

    /**
     * 判断wifi是否连接
     * @param context
     * @return
     */
    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return networkInfo.isConnected
    }

    /**
     * 判断移动网络是否连接
     * @param context
     * @return
     */
    fun isMobileConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return networkInfo.isConnected
    }
}