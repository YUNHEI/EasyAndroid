package com.chen.baseextend.base

import android.annotation.SuppressLint
import com.chen.baseextend.BuildConfig
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.network.base.BaseRepos
import com.chen.basemodule.network.constant.NetConfig
import com.chen.basemodule.util.log.okHttpLog.HttpLoggingInterceptor
import com.chen.basemodule.util.log.okHttpLog.LogInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.SoftReference
import java.util.concurrent.TimeUnit

/**
 * 网络框架的简单实现， 提供主模块的网络配置。需要个性化配置继承[BaseSimpleRepos]
 * Created by chen on 2019/4/29
 */
open class BaseSimpleRepos<T> : BaseRepos<T>() {

    companion object {
        val retrofits: MutableMap<String, SoftReference<Retrofit>> = mutableMapOf()
    }

    open val baseHost = BuildConfig.HOST

    override fun createRetrofit(): Retrofit {
        return retrofits[baseHost]?.get() ?: run {
            Retrofit.Builder()
                .baseUrl(baseHost)
                .client(createHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().apply {
                    retrofits[baseHost] = SoftReference(this)
                }
        }
    }

    protected open fun createHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(NetConfig.RETRY_TO_CONNECT)
            .connectTimeout(NetConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(createAuthInterceptor())
            .addNetworkInterceptor(createLogInterceptor())
            .build()
    }

    protected open fun createLogInterceptor(): Interceptor {


        val interceptor = HttpLoggingInterceptor(LogInterceptor())

        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return interceptor
    }

    //请求头添加Authorization
    @SuppressLint("MissingPermission")
    protected open fun createAuthInterceptor(): Interceptor {

        return Interceptor { chain ->
            val token = BasePreference.USER_TOKEN_

//            LogUtil.i("user token", token)

            val builder = chain.request()
                .newBuilder()
                .header("Accept", "application/json")
                .header("appId", "1")
                .header("channelId", "1")
                .header("Content-Type", "application/json")

            if (token.isNullOrEmpty()) {
                builder.header(
                    "Authorization",
                    "Basic Y2E5OWVhZGJiYjgzMTUzNDh0NTRkOmVhZGJiYjgzMTUzNDg4ZjcxYzIzZjQ0MDQzNjBjYTk5"
                )
            } else {
                builder.header("Authorization", token)
            }

            chain.proceed(builder.build())
        }
    }

}
