package com.chen.basemodule.network.base

import retrofit2.Call
import retrofit2.CallAdapter

import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type
import java.lang.reflect.WildcardType


class DirectCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation?>?, retrofit: Retrofit?): CallAdapter<*, *> {
        val responseType: Type = getResponseType(returnType)
        return object : CallAdapter<Any?, Any?> {
            override fun responseType(): Type {
                return responseType
            }

            override fun adapt(call: Call<Any?>): Any? {
                // to  do 可以在这里判断接口数据格式
                try {
                    return call.execute().body()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                return null
            }
        }
    }

    private fun getResponseType(type: Type): Type {
        return if (type is WildcardType) {
            type.upperBounds[0]
        } else type
    }
}