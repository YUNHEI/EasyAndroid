package com.chen.baseextend.repos.admin

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.admin.ApkInfoBean
import com.chen.basemodule.network.base.BaseResponse
import com.chen.basemodule.network.constant.NetConfig
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

/**
 *  Created by chen on 2019/6/12
 **/
object AdFileRepos : BaseSimpleRepos<AdFileRepos.AdFileService>() {

    override fun createHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
                .retryOnConnectionFailure(NetConfig.RETRY_TO_CONNECT)
                .connectTimeout(NetConfig.CONNECT_TIMEOUT_LONG, TimeUnit.SECONDS)
                .readTimeout(NetConfig.CONNECT_TIMEOUT_LONG, TimeUnit.SECONDS)
                .writeTimeout(NetConfig.CONNECT_TIMEOUT_LONG, TimeUnit.SECONDS)
                .addInterceptor(createAuthInterceptor())
                .addNetworkInterceptor(createLogInterceptor())
                .build()
    }

    interface AdFileService {

        /**
         * 上传安装包
         */
        @Multipart
        @POST("zuul/manager/version/uploadApk")
        suspend fun uploadApk(@Part request: MultipartBody.Part): BaseResponse<ApkInfoBean>
    }
}