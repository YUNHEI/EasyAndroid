package com.chen.basemodule.network.base

import com.chen.basemodule.allroot.RootRepos
import com.chen.basemodule.util.GenericsUtils

import retrofit2.Retrofit


/*基于retrofit 网络框架的基础类*/
abstract class BaseRepos<T> : RootRepos() {

    val service: T by lazy {
        val serviceClass = GenericsUtils.findGenericsClass<T>(javaClass, 0)

        createRetrofit().create(serviceClass!!)
    }

    /*网络框架初始化*/
    protected abstract fun createRetrofit(): Retrofit
}
