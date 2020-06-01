package com.chen.baseextend.bean

import com.chen.baseextend.BuildConfig
import com.chen.basemodule.network.base.BaseRequest

class ReviewVersionRequest(
        val version: Int = BuildConfig.VERSION_CODE
) : BaseRequest()
