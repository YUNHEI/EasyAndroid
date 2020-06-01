package com.chen.baseextend.repos.admin

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.IdRequest
import com.chen.baseextend.bean.admin.ProjectPageRequest
import com.chen.baseextend.bean.admin.VerifyRequest
import com.chen.baseextend.bean.project.ProjectBean
import com.chen.basemodule.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created by chen on 2019/6/12
 **/
object AdProjectRepos : BaseSimpleRepos<AdProjectRepos.AdProjectService>() {

    interface AdProjectService {

        /**
         * 列出任务列表
         */
        @POST("manager/order/list")
        suspend fun listProject(@Body request: ProjectPageRequest): BaseResponse<MutableList<ProjectBean>>


        /*获取任务详情 */
        @POST("manager/order/detail")
        suspend fun getProjectDetail(@Body request: IdRequest): BaseResponse<ProjectBean>


        /*审核接口 */
        @POST("manager/order/updateStatus")
        suspend fun verifyTask(@Body request: VerifyRequest): BaseResponse<Any>
    }
}