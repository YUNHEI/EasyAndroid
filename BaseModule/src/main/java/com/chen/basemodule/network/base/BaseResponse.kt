package com.chen.basemodule.network.base

import com.chen.basemodule.allroot.RootResponse
import com.google.gson.annotations.SerializedName

open class BaseResponse<K>(
    //根据自己的接口映射到对应字段
    @SerializedName("result")
    val data: K? = null,
    @SerializedName("code")
    var status: Int = 300,
    var message: String? = null,
    val fromCache: Boolean = false
) : RootResponse() {

    fun suc(): Boolean {
        return status in 200..289
    }

    fun localSuc(): Boolean {
        return status in 290..299
    }

    fun fail(): Boolean {
        return status in 300..399
    }

}
