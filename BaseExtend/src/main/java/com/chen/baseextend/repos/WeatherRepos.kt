package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.ADRequest
import com.chen.baseextend.bean.AdvertBean
import com.chen.baseextend.bean.WeatherBean
import com.chen.basemodule.basem.BaseBean
import com.chen.basemodule.network.base.BaseRequest
import com.chen.basemodule.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created by chen on 2019/6/12
 **/
object WeatherRepos : BaseSimpleRepos<WeatherRepos.ADService>() {

    interface ADService {

        /*天气 */
        @POST("http://www.tianqiapi.com/api?version=v3&appid=94888922&appsecret=nLaJzD5j")
        suspend fun getWeather(@Body request: BaseRequest): BaseResponse<MutableList<WeatherBean>>

    }
}