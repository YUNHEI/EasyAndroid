package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

class VerifyCodeRequest(
        var mobile: String,
        var msgType: String,
        var smsCode: String
) : BaseRequest()
