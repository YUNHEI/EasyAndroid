package com.chen.basemodule.network.base

import android.net.ParseException
import android.util.Log

import com.google.gson.JsonParseException

import org.json.JSONException

import java.net.SocketTimeoutException
import java.net.UnknownHostException

import retrofit2.HttpException

class BaseErrorResponse<K>(t: Throwable) : BaseResponse<K>() {

    init {

        t.printStackTrace()

        if (t is JsonParseException
                || t is ParseException
                || t is JSONException) {

            status = 403//数据解析异常
            message = "数据解析异常"
            Log.e("数据解析异常", t.message)

        } else if (t is HttpException) {
            status = t.code()
            when (t.code()) {
                404//找不到网页
                -> message = "服务器响应错误"
                408//请求超时
                -> message = "请求超时，请检查网络是否正常"
                500, 502//连接超时
                -> message = "系统繁忙，请稍后再试"
                else -> {
                    message = t.message ?: "http请求异常"
                    Log.e("http请求异常", t.message)
                }
            }
        } else if (t is UnknownHostException) {
            status = 402
            message = "手机网络已断开，请连接网络后再试"
        } else if (t is SocketTimeoutException) {
            status = 405
            message = "请求超时，请检查网络是否通畅"
        } else if (t is BaseNetException) {
            status = t.code
            message = t.message!!
        } else {
            status = 406
            message = "网络异常，请检查后重试"
        }
    }
}
