package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.ADRequest
import com.chen.baseextend.bean.AdvertBean
import com.chen.basemodule.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created by chen on 2019/6/12
 **/
object ADRepos : BaseSimpleRepos<ADRepos.ADService>() {

    interface ADService {

        /*根据位置列出广告列表 */
        @POST("musicRankings")
        suspend fun listAdvertise(@Body request: ADRequest): BaseResponse<MutableList<AdvertBean>>

    }
}