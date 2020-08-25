package com.chen.baseextend.repos

import com.chen.baseextend.base.BaseSimpleRepos
import com.chen.baseextend.bean.IdRequest
import com.chen.baseextend.bean.UserInfoUpdateRequest
import com.chen.baseextend.bean.UserShopInfoBean
import com.chen.baseextend.bean.admin.LoginRequest
import com.chen.baseextend.bean.mz.*
import com.chen.baseextend.bean.system.WxPayInfo
import com.chen.baseextend.bean.wallet.BillPageRequest
import com.chen.baseextend.bean.wallet.RechargeRequest
import com.chen.baseextend.bean.wallet.WithdrawInfo
import com.chen.baseextend.util.ExtendPreHelper
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.network.base.BaseNetException
import com.chen.basemodule.network.base.BasePageRequest
import com.chen.basemodule.network.base.BaseRequest
import com.chen.basemodule.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Created by chen on 2019/6/12
 **/
object UserRepos : BaseSimpleRepos<UserRepos.UserService>() {

    suspend fun updateUserInfo(request: UserInfoUpdateRequest): BaseResponse<UserInfoAndAccountBean> {
        return service.updateUserInfo(request).run {
            if (suc()) initUserInfo()
            else throw BaseNetException(status, message.orEmpty())
        }
    }

    /**
     * 获取用户token后刷新用户信息
     */
    suspend fun initUserInfo(token: String? = null): BaseResponse<UserInfoAndAccountBean> {

        token?.run {
            BasePreference.USER_TOKEN = token
            BasePreference.LOGIN_STATE = true
        }

        return service.getUserInfo(BaseRequest()).apply {
            if (suc()) {
                data?.run {
                    ExtendPreHelper.setUserInfo(basicInfo)
                    BasePreference._INVITE_CODE = basicInfo.inviteCode.orEmpty()
                    BasePreference.USER_ID = basicInfo.id.orEmpty()
                    ExtendPreHelper.setWalletInfo(walletInfo)
                }
            }
        }

    }


    interface UserService {

        /**
         * 微信登录
         */
        @POST("*************")
        suspend fun wxLogin(@Body request: WxLoginRequest): BaseResponse<String>

        /**
         * 账号密码登录
         */
        @POST("*************")
        suspend fun accountLogin(@Body request: LoginRequest): BaseResponse<String>

        /**
         * 发送手机绑定验证码
         */
        @POST("*************")
        suspend fun sendBindingSms(@Body request: SendSmsRequest): BaseResponse<String>

        /**
         * 绑定手机号，邀请码
         */
        @POST("*************")
        suspend fun bindInfo(@Body request: BindingRequest): BaseResponse<String>

        /**
         * 获取IM 签名
         */
        @POST("*************")
        suspend fun getIMSign(@Body request: BaseRequest): BaseResponse<String>

        /**
         * 获取用户信息
         */
        @POST("*************")
        suspend fun getUserInfo(@Body request: BaseRequest): BaseResponse<UserInfoAndAccountBean>

        /**
         * 获取用户其他信息
         */
        @POST("*************")
        suspend fun getShopInfoByUserId(@Body request: IdRequest): BaseResponse<UserShopInfoBean>

        /**
         * 更新用户信息
         */
        @POST("*************")
        suspend fun updateUserInfo(@Body request: UserInfoUpdateRequest): BaseResponse<Any>


        /**创建微信支付订单 */
        @POST("*************")
        suspend fun createWxOrder(@Body request: RechargeRequest): BaseResponse<WxPayInfo>

        /**提现 */
        @POST("*************")
        suspend fun withdraw(@Body request: RechargeRequest): BaseResponse<String>

        /**提现审核 */
        @POST("*************")
        suspend fun withdrawApply(@Body request: RechargeRequest): BaseResponse<String>

        /**获取提现信息 */
        @POST("*************")
        suspend fun preWithdraw(@Body request: RechargeRequest): BaseResponse<WithdrawInfo>

        /**用户订单*/
        @POST("*************")
        suspend fun listBill(@Body request: BillPageRequest): BaseResponse<MutableList<BillBean>>

        /**用户邀请列表*/
        @POST("*************")
        suspend fun listInviteFriends(@Body request: BasePageRequest): BaseResponse<MutableList<InviteUserBean>>
    }
}