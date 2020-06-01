package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.ActivityIdRequest
import com.chen.baseextend.bean.IdRequest
import com.chen.baseextend.bean.activity.*
import com.chen.basemodule.network.base.BaseRequest
import com.chen.basemodule.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST
import java.math.BigDecimal

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
        @POST("activity/invitation/detail")
        suspend fun invitationDetail(@Body request: BaseRequest = BaseRequest()): BaseResponse<InviteDetailBean>

        /*获取我的收益排行 */
        @POST("activity/ranklist/bonus/my")
        suspend fun myEarningRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<InviteRankMineBean>

        /*获取我的任务排行 */
        @POST("activity/ranklist/complete/my")
        suspend fun myProjectRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<InviteRankMineBean>

        /*获取我的邀请排行 */
        @POST("activity/ranklist/invitation/my")
        suspend fun myInviteRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<InviteRankMineBean>

        /*邀请排行榜 */
        @POST("activity/invitation/profitRank")
        suspend fun listInviteRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<MutableList<InviteRankUserBean>>

        /*昨日分红排行榜 */
        @POST("activity/ranklist/yday/bonus/list")
        suspend fun listYesBonusRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<MutableList<InviteRankUserBean>>

        /*昨日接单排行榜 */
        @POST("activity/ranklist/yday/complete/list")
        suspend fun listYesProjectRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<RankListBean>

        /*昨日邀请排行榜 */
        @POST("activity/ranklist/yday/invitation/list")
        suspend fun listYesInviteRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<RankListBean>

        /*昨日邀请排行榜 */
        @POST("activity/ranklist/yday/activity/profit")
        suspend fun listYesActivityProfit(@Body request: IdRequest): BaseResponse<RankListBean>

        /*今日昨日分红排行榜 */
        @POST("activity/ranklist/bonus/list")
        suspend fun listTodayBonusRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<RankListBean>

        /*今日昨日接单排行榜 */
        @POST("activity/ranklist/complete/list")
        suspend fun listTodayProjectRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<MutableList<InviteRankUserBean>>

        /*今日邀请排行榜 */
        @POST("activity/ranklist/invitation/list")
        suspend fun listTodayInviteRank(@Body request: BaseRequest = BaseRequest()): BaseResponse<MutableList<InviteRankUserBean>>


        /*********************签到***********************/

        /*查询用户签到活动配置 */
        @POST("activity/clock/config")
        suspend fun getSignConfig(@Body request: IdRequest = IdRequest("4")): BaseResponse<Any>

        /*签到获取红包接口 */
        @POST("activity/clock/getRedPacket")
        suspend fun getRedPacket(@Body request: BaseRequest = BaseRequest()): BaseResponse<Any>

        /*签到 */
        @POST("activity/clock/sign")
        suspend fun signIn(@Body request: IdRequest = IdRequest("4")): BaseResponse<SignInBean>

        /*根据时间查询用户签到状态 */
        @POST("activity/clock/userInfo")
        suspend fun userSignInInfo(@Body request: IdRequest = IdRequest("4")): BaseResponse<SignInStatusListBean>

        /*查询奖励列表 */
        @POST("activity/prize/list")
        suspend fun prizeList(@Body request: IdRequest = IdRequest("4")): BaseResponse<Any>

        /*查询历史列表 */
        @POST("activity/record/historicalbonus")
        suspend fun historicalBonus(@Body request: IdRequest = IdRequest("1")): BaseResponse<BigDecimal>

        /*查询奖金历史列表 */
        @POST("activity/record/win")
        suspend fun listPriceRecordList(@Body request: ActivityIdRequest = ActivityIdRequest("4")): BaseResponse<MutableList<SignPriceRecordBean>>

        /*查询我的累计奖金 */
        @POST("activity/record/accumulative")
        suspend fun accumulative(@Body request: IdRequest = IdRequest("4")): BaseResponse<BigDecimal>

    }
}