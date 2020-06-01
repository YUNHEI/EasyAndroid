package com.chen.baseextend.repos.admin

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.admin.LoginRequest
import com.chen.basemodule.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created by chen on 2019/6/12
 **/
object AdUserRepos : BaseSimpleRepos<AdUserRepos.AdUserService>() {

    interface AdUserService {

        /**
         * 登录
         */
        @POST("manager/backuser/login")
        suspend fun login(@Body request: LoginRequest): BaseResponse<String>

    }
}