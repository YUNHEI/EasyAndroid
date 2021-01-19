package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.BannerBean
import com.chen.baseextend.bean.JokeBean
import com.chen.baseextend.bean.PoetryBean
import com.chen.baseextend.bean.news.NewsBean
import com.chen.baseextend.repos.response.ResponseExt1
import com.chen.basemodule.basem.BaseBean
import com.chen.basemodule.network.base.BaseRequest
import com.chen.basemodule.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *  Created by chen on 2019/6/12
 **/
object NewsRepos : BaseSimpleRepos<NewsRepos.NewsService>() {


    //获取文字轮播，用新闻代替
    suspend fun listTextBanner(): BaseResponse<MutableList<String>> {

        val resp = service.listNews()
        return BaseResponse(
            resp.data
                ?.map { it.title.orEmpty() }
                ?.filter { it.isNotEmpty() }
                .orEmpty()
                .toMutableList(),
            200,
            "成功"
        )
    }

    interface NewsService {

        //获取开源新闻
        @POST("http://ic.snssdk.com/2/article/v25/stream/")
        suspend fun listNews(@Body request: BaseRequest = BaseRequest()): BaseResponse<MutableList<NewsBean>>

        //获取图集
        @POST("getImages")
        suspend fun listBanner(@Body request: BaseRequest = BaseRequest()): ResponseExt1<MutableList<BannerBean>>

        //获取唐诗
        @POST("getTangPoetry")
        suspend fun listPoetry(@Body request: BaseRequest = BaseRequest()): ResponseExt1<MutableList<PoetryBean>>

        //段子 (all/video/image/gif/text)
        @POST("getJoke")
        suspend fun getJoke(
            @Query("page") page: Int = 1,
            @Query("count") count: Int = 5,
            @Query("type") type: String = "text"
        ): ResponseExt1<MutableList<JokeBean>>

    }
}