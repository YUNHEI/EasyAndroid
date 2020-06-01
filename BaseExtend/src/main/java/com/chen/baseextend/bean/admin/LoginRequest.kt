package com.chen.baseextend.bean.admin

import com.chen.basemodule.network.base.BaseRequest

data class LoginRequest(
        val username:String,
        val password:String
):BaseRequest()