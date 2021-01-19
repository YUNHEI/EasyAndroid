package com.chen.baseextend.repos.response

import com.chen.basemodule.network.base.BaseResponse
import com.google.gson.annotations.SerializedName

class ResponseExt1<K>(
    @SerializedName("code")
    override var status: Int = 300,
    @SerializedName("result")
    override var data: K? = null
) : BaseResponse<K>()