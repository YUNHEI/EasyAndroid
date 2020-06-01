package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.AppealBean
import com.chen.baseextend.bean.AppealTypePageRequest
import com.chen.basemodule.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created by chen on 2019/6/12
 **/
object AppealRepos : BaseSimpleRepos<AppealRepos.AppealService>() {

    interface AppealService {


        @POST("")
        suspend fun listAppealsByType(@Body request: AppealTypePageRequest): BaseResponse<MutableList<AppealBean>>

    }
}