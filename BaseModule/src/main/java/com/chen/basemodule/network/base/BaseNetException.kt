package com.chen.basemodule.network.base

import com.chen.basemodule.network.constant.ResponseCode

class BaseNetException(var code: Int = ResponseCode.FAIL, message: String) : Exception(message)
