package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.activity.InviteDetailBean
import com.chen.baseextend.bean.activity.InviteRankMineBean
import com.chen.baseextend.bean.activity.InviteRankUserBean
import com.chen.baseextend.bean.activity.RankListBean
import com.chen.basemodule.network.base.BaseRequest
import com.chen.basemodule.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created by chen on 2019/6/12
 **/
/**
 * 活动相关接口
 */
object ActivityRepos : BaseSimpleRepos<ActivityRepos.ActivityService>() {

    interface ActivityService {

        /*********************排行榜***********************/
        /*邀请详情 */
        @POST("")
        suspend fun invitationDetail(@Body request: BaseRequest = BaseRequest()): BaseResponse<InviteDetailBean>

        /*获取我的收益排行 */
        @POST("")
        suspend fun myEarningRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<InviteRankMineBean>

        /*获取我的任务排行 */
        @POST("")
        suspend fun myProjectRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<InviteRankMineBean>

        /*获取我的邀请排行 */
        @POST("")
        suspend fun myInviteRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<InviteRankMineBean>

        /*邀请排行榜 */
        @POST("")
        suspend fun listInviteRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<MutableList<InviteRankUserBean>>

        /*昨日分红排行榜 */
        @POST("")
        suspend fun listYesBonusRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<MutableList<InviteRankUserBean>>

        /*昨日接单排行榜 */
        @POST("")
        suspend fun listYesProjectRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<RankListBean>

        /*昨日邀请排行榜 */
        @POST("")
        suspend fun listYesInviteRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<RankListBean>

    }
}