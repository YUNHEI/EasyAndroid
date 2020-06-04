package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.ADRequest
import com.chen.baseextend.bean.AdvertBean
import com.chen.basemodule.network.base.BaseResponse
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created by chen on 2019/6/12
 **/
object ADRepos : BaseSimpleRepos<ADRepos.ADService>() {

    interface ADService {

        /*根据位置列出广告列表 */
        @POST("musicRankings")
        suspend fun listAdvertise(@Body request: ADRequest): CustomResponse<MutableList<AdvertBean>>

    }
}

data class CustomResponse<K>(
    @SerializedName("result")
    override val data: K? = null,

    @SerializedName("code")
    override var status: Int = 300
) : BaseResponse<K>()