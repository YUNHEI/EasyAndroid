package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

data class SendVerifySMSRequest(var mobile: String, val msgType: String = "MSG_FIND_PASSWORD") : BaseRequest()
