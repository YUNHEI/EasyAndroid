package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.IdRequest
import com.chen.baseextend.bean.UserIdRequest
import com.chen.baseextend.bean.project.*
import com.chen.basemodule.network.base.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created by chen on 2019/6/12
 **/
object ProjectRepos : BaseSimpleRepos<ProjectRepos.ProjectService>() {

    interface ProjectService {

//        ###############################发单人接口###########################

        /*发布任务和修改任务*/
        @POST("")
        fun publishProject(@Body request: ProjectAddRequest): Observable<BaseResponse<Any>>

        /*发布任务和修改任务*/
        @POST("")
        suspend fun updateProject(@Body request: ProjectAddRequest): BaseResponse<Any>

        /*获取任务详情 */
        @POST("")
        suspend fun getProjectDetail(@Body request: IdRequest): BaseResponse<ProjectBean>

        /*发单管理 */
        @POST("")
        suspend fun listPublishProject(@Body request: ProjectManagerPageRequest): BaseResponse<MutableList<ProjectBean>>

        /*根据用户id获取发单列表 */
        @POST("")
        suspend fun listPublishProjectByUser(@Body request: UserIdRequest): BaseResponse<MutableList<ProjectBean>>

        /*任务列表 */
        @POST("")
        suspend fun listProject(@Body request: ProjectPageRequest): BaseResponse<MutableList<ProjectBean>>


        @POST("musicRankings")
        suspend fun listMultiItems(@Body request: ItemTypeRequest): BaseResponse<MutableList<ItemTypeBean>>

    }
}