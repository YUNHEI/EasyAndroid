package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.*
import com.chen.baseextend.bean.notice.NoticeBean
import com.chen.baseextend.bean.notice.NoticePageRequest
import com.chen.baseextend.bean.project.ItemTypeBean
import com.chen.baseextend.bean.project.ItemTypeRequest
import com.chen.baseextend.bean.wallet.RechargeRequest
import com.chen.baseextend.bean.wallet.WithdrawConfigBean
import com.chen.baseextend.bean.wallet.WithdrawInfoBean
import com.chen.baseextend.bean.wallet.WithdrawRequest
import com.chen.basemodule.network.base.BasePageRequest
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
        @POST("manager/banner/show")
        suspend fun listBanner(@Body request: BannerRequest): BaseResponse<MutableList<AdvertBean>>

        /**获取更新日志*/
        @POST("manager/version/check")
        suspend fun getLastedVersion(@Body request: BaseRequest): BaseResponse<VersionBean>

        /**获取更新日志*/
        @POST("manager/version/checkIsReview")
        suspend fun getReviewVersion(@Body request: ReviewVersionRequest = ReviewVersionRequest()): BaseResponse<Int>

        /**更新日志列表*/
        @POST("manager/version/list")
        suspend fun listVersionInfo(@Body request: BasePageRequest): BaseResponse<MutableList<HistoryVersionBean>>

        /**提现配置 */
        @POST("manager/cashout/getCashOutConfig")
        suspend fun withdrawConfig(@Body request: RechargeRequest): BaseResponse<WithdrawConfigBean>

        /**提现配置 */
        @POST("manager/cashout/getCashOutInfo")
        suspend fun withdrawInfo(@Body request: WithdrawRequest): BaseResponse<WithdrawInfoBean>


        /**通知列表 */
        @POST("user/notice/list")
        suspend fun listNotice(@Body request: NoticePageRequest): BaseResponse<MutableList<NoticeBean>>

        /*获取任务类型 */
        @POST("manager/item/getByItemType")
        suspend fun listItems(@Body request: ItemTypeRequest): BaseResponse<MutableList<ItemTypeBean>>
    }
}