package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.AppealDetailBean
import com.chen.baseextend.bean.IdRequest
import com.chen.baseextend.bean.UserIdRequest
import com.chen.baseextend.bean.project.*
import com.chen.baseextend.bean.tim.GroupBean
import com.chen.basemodule.network.base.BasePageRequest
import com.chen.basemodule.network.base.BaseRequest
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
        @POST("order/taskInfo/publish")
        fun publishProject(@Body request: ProjectAddRequest): Observable<BaseResponse<Any>>

        /*发布任务和修改任务*/
        @POST("order/taskInfo/updateWithReverify")
        suspend fun updateProject(@Body request: ProjectAddRequest): BaseResponse<Any>

        /*获取任务详情 */
        @POST("order/taskInfo/detail")
        suspend fun getProjectDetail(@Body request: IdRequest): BaseResponse<ProjectBean>

        /*发单管理 */
        @POST("order/taskInfo/manager/list")
        suspend fun listPublishProject(@Body request: ProjectManagerPageRequest): BaseResponse<MutableList<ProjectBean>>

        /*根据用户id获取发单列表 */
        @POST("order/manager/getPublishTaskList")
        suspend fun listPublishProjectByUser(@Body request: UserIdRequest): BaseResponse<MutableList<ProjectBean>>

        /*任务列表 */
        @POST("order/taskInfo/list")
        suspend fun listProject(@Body request: ProjectPageRequest): BaseResponse<MutableList<ProjectBean>>

        /*任务首页列表 */
        @POST("order/taskInfo/recommendList")
        suspend fun listRecProject(@Body request: ProjectPageRequest): BaseResponse<MutableList<ProjectBean>>

        /*获取发单任务类型 */
        @POST("order/taskInfo/getServiceChargeList")
        suspend fun listTypes(@Body request: ItemTypeRequest): BaseResponse<MutableList<ItemTypeBean>>

        /*获取任务相关费用 */
        @POST("order/taskInfo/checkServiceCharge")
        suspend fun getCharge(@Body request: ItemTypeRequest): BaseResponse<ChargeBean>

        /*发单管理接口 */
        @POST("order/taskStatus/control")
        suspend fun taskStatusControl(@Body request: ProjectManageRequest): BaseResponse<ProjectBean>

        /*获取待审核详情 */
        @POST("order/acceptTask/waitVerifyTask/complex")
        suspend fun getVerifyTask(@Body request: IdRequest): BaseResponse<VerifyDetailBean>

        /*审核接口 */
        @POST("order/acceptTask/verify")
        suspend fun verifyTask(@Body request: VerifyRequest): BaseResponse<Any>

        /*服务费计算 */
        @POST("order/taskInfo/serviceChargeCount")
        suspend fun serviceChargeCount(@Body request: ProjectChargeRequest): BaseResponse<ProjectChargeBean>


        //        ###############################任务接口###########################

        /*获取多级任务类型 */
        @POST("manager/item/getTreeByItemClass")
        suspend fun listMultiItems(@Body request: ItemTypeRequest): BaseResponse<MutableList<ItemTypeBean>>

        /*最新完成任务列表 */
        @POST("order/acceptTask/recently/complete")
        suspend fun newestCompletedProject(@Body request: BaseRequest): BaseResponse<MutableList<ProjectBean>>


        //        ###############################接单人接口##########################

        /*接单列表 */
        @POST("order/acceptTask/getAcceptTaskList")
        suspend fun listAcceptProject(@Body request: ProjectAcceptPageRequest): BaseResponse<MutableList<AcceptProjectBean>>

        /*用户接单 */
        @POST("order/acceptTask/userAcceptTask")
        suspend fun acceptProject(@Body request: IdRequest): BaseResponse<Any>

        /*提交接单的任务的验证信息 */
        @POST("order/acceptTask/submitVerify")
        suspend fun submitVerify(@Body request: VerifySubmitRequest): BaseResponse<Any>

        /*提交新手接单的任务的验证信息 */
        @POST("order/acceptTask/submitNoviceVerify")
        suspend fun submitNoviceVerify(@Body request: VerifySubmitRequest): BaseResponse<Any>

        /*获取失败审核详情 */
        @POST("order/acceptTask/getDetailForVerify")
        suspend fun getFailTask(@Body request: IdRequest): BaseResponse<VerifyInfoBean>

        /*放弃任务 */
        @POST("order/acceptTask/giveupTask")
        suspend fun giveUpTask(@Body request: IdRequest): BaseResponse<Any>

        /*回复审核失败的接口 */
        @POST("order/acceptTask/replyPublisher")
        suspend fun replyFailTask(@Body request: ReplyRequest): BaseResponse<Any>

        /*添加收藏 */
        @POST("order/collection/add")
        suspend fun addCollection(@Body request: IdRequest): BaseResponse<Any>

        /*取消收藏 */
        @POST("order/collection/remove")
        suspend fun removeCollection(@Body request: IdRequest): BaseResponse<Any>

        /*收藏列表 */
        @POST("order/collection/list")
        suspend fun listCollection(@Body request: BasePageRequest): BaseResponse<MutableList<ProjectBean>>

        /*随机获取下一个任务 */
        @POST("order/taskInfo/getRandomTask")
        suspend fun getRandomTask(@Body request: BaseRequest = BaseRequest()): BaseResponse<MutableList<String>>

        //        ###############################申诉##########################

        /*提交申诉 */
        @POST("order/appeal/createComplaints")
        suspend fun appeal(@Body request: AppealRequest): BaseResponse<Any>

        /*提交申诉 */
        @POST("order/appeal/getTaskAppealDetail")
        suspend fun getTaskAppealDetail(@Body request: IdRequest): BaseResponse<AppealDetailBean>

        /*获取群id */
        @POST("order/counsel/addGroupWithAdmin")
        suspend fun getGroup(@Body request: IdRequest): BaseResponse<GroupBean>

        /*获取待完成任务数 */
        @POST("order/acceptTask/getWaitCompeletNum")
        suspend fun getWaitCompeletNum(@Body request: BaseRequest = BaseRequest()): BaseResponse<Int>

    }
}