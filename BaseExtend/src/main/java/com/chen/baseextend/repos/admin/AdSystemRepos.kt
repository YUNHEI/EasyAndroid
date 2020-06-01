package com.chen.baseextend.repos.admin

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.IdRequest
import com.chen.baseextend.bean.admin.UpdateForceRequest
import com.chen.baseextend.bean.admin.VersionRequest
import com.chen.basemodule.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created by chen on 2019/6/12
 **/
object AdSystemRepos : BaseSimpleRepos<AdSystemRepos.AdSystemService>() {


    interface AdSystemService {

        /**
         * 添加日志
         */
        @POST("manager/version/save")
        suspend fun addVersion(@Body request: VersionRequest): BaseResponse<Any>

        /**
         * 删除日志
         */
        @POST("manager/version/del")
        suspend fun deleteVersion(@Body request: IdRequest): BaseResponse<Any>

        /**
         * 是否强制
         */
        @POST("manager/version/changeIsForceUpdate")
        suspend fun updateForce(@Body request: UpdateForceRequest): BaseResponse<Any>
    }
}