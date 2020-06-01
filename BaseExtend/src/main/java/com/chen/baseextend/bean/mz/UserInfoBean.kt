package com.chen.baseextend.bean.mz

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/7/12
 **/
data class UserInfoBean(
        val appId: Int?,
        val avatar: String?,
        val channelId: Int?,
        val deviceId: String?,
        val deviceName: String?,
        val deviceType: Int?,
        val gender: Int?,
        val id: String?,
        val inviteCode: String?,
        val nickname: String?,
        val createTime: String? = null,
        val updateTime: String? = null,
        val username: String?
) : BaseBean()