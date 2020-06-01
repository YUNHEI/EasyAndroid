package com.chen.baseextend.bean

import android.os.Build

import com.chen.basemodule.network.base.BaseRequest

class RegisterRequest(
        var mobile: String,
        var password: String,
        var smsCode: String,
        var confirmPWD: String,
        var regChannel: String = "2",
        var unitType: String = Build.MODEL,//手机型号
        var loginChannel:Int = 2//登录途径
        ) : BaseRequest()
