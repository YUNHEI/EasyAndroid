package com.chen.baseextend.util

import com.google.gson.Gson
import com.chen.baseextend.bean.mz.UserInfoBean
import com.chen.baseextend.bean.mz.WalletInfoBean
import com.chen.basemodule.constant.BasePreference
import com.chen.basemodule.util.PrefUtils

object ExtendPreHelper {

    private var userInfo: UserInfoBean? = null
    private var walletInfo: WalletInfoBean? = null

    fun setUserInfo(bean: UserInfoBean?) {
        userInfo = bean
        PrefUtils.putString(BasePreference.KEY_USER_INFO, Gson().toJson(bean))
    }

    fun setWalletInfo(bean: WalletInfoBean?) {
        walletInfo = bean
        PrefUtils.putString(BasePreference.KEY_WALLET_INFO, Gson().toJson(bean))
    }

    fun getUserInfo(): UserInfoBean? {
        if (userInfo != null) return userInfo
        val userInfoJson = PrefUtils.getString(BasePreference.KEY_USER_INFO)
        return if (userInfoJson != null) {
            Gson().fromJson(userInfoJson, UserInfoBean::class.java)
        } else null
    }

    fun getWalletInfo(): WalletInfoBean? {
        if (walletInfo != null) return walletInfo
        val walletInfoJson = PrefUtils.getString(BasePreference.KEY_WALLET_INFO)
        return if (walletInfoJson != null) {
            Gson().fromJson(walletInfoJson, WalletInfoBean::class.java)
        } else null
    }

    fun logout() {
        PrefUtils.removeAll()
        userInfo = null
        walletInfo = null
    }
}