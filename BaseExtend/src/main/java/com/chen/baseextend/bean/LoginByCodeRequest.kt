package com.chen.baseextend.bean

import android.os.Build

import com.chen.basemodule.network.base.BaseRequest

data class LoginByCodeRequest(
        var mobile: String,
        var smsCode: String,
        var unitType: String? = Build.MODEL,//手机型号
        var loginChannel: Int? = 2//登录途径
) : BaseRequest()
