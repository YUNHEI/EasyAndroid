package com.chen.basemodule.extend

import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.chen.basemodule.BaseModuleLoad
import com.chen.basemodule.BuildConfig
import com.chen.basemodule.R
import com.chen.basemodule.network.base.BaseResponse

/**
 *  Created by chen on 2019/9/2
 **/
fun String.toastSuc() {
    toastCus(R.mipmap.ic_load_suc)
}

fun String.toastError() {
    toastCus(R.mipmap.ic_load_fail)
}

fun String.toastWarn() {
    toastCus(R.mipmap.ic_load_warn)
}

fun String.toastCus(@DrawableRes resId: Int = 0, duration: Int = 0) {

    val toast = Toast(BaseModuleLoad.context).apply {
        view = LayoutInflater.from(BaseModuleLoad.context).inflate(R.layout.layout_toast, null).also {
            it.findViewById<ImageView>(R.id._icon).run {
                visibility = View.VISIBLE
                setImageResource(resId)
            }
            it.findViewById<TextView>(R.id._msg).run {
                text = this@toastCus
            }
        }

        if (duration in setOf(0, 1)) {
            this.duration = duration
        }

        setGravity(Gravity.CENTER, 0, 0)
        show()
    }

    if (duration > 1) {
        Handler().postDelayed({ toast.cancel() }, duration.toLong())
    }
}

fun String.toastDebug() {
    if (BuildConfig.DEBUG) {
        toastSuc()
    }
}

fun BaseResponse<*>.toast() {
    when {
        suc() -> message?.toastSuc()
        fail() -> message?.toastWarn()
        status == 401 -> "登陆失效,请重新登陆！".toastError()
        status == 400 -> "后台繁忙，请稍后再试".toastError()
        else -> message?.toastError()
    }
}