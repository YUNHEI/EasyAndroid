package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.AdvertBean
import com.chen.baseextend.bean.BannerRequest
import com.chen.baseextend.bean.VersionBean
import com.chen.basemodule.network.base.BaseRequest
import com.chen.basemodule.network.base.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created by chen on 2019/6/12
 **/
object SystemRepos : BaseSimpleRepos<SystemRepos.SystemService>() {

    interface SystemService {


        /**(获取七牛token new) */
        @POST("basic/qiniu/getUploadToken?bucket=usertask")
        fun getQiNiuToken(): Observable<BaseResponse<String>>

        /**(获取七牛token new) */
        @POST("*************")
        suspend fun listBanner(@Body request: BannerRequest): BaseResponse<MutableList<AdvertBean>>

        /**获取更新日志*/
        @POST("*************")
        suspend fun getLastedVersion(@Body request: BaseRequest): BaseResponse<VersionBean>
    }
}