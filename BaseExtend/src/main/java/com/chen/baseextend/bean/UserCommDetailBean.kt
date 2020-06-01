package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 * @author alan
 * @date 2018/12/13
 */

data class UserCommDetailBean(
        var communityId: String? = null,
        var communityName: String? = null,
        var crtTime: String? = null,
        var crtUserId: String? = null,
        var crtUserName: String? = null,
        var email: String? = null,
        var icon: String? = null,
        var id: String? = null,
        var info: String? = null,
        var name: String? = null,
        /**,
         * 分享权限 0 - 未申请  1- 已开通  2- 申请中,
         */
        var shareAuth: Int = 0,
        /**,
         * 言论权限 0 - 未申请  1- 已开通  2- 申请中,
         */
        var talkShareAuth: Int = 0,
        var status: Int = 0,
        var updTime: String? = null,
        var verifyStatus: Int = 0,
        /**
         * 1- 管理员 0-普通
         */
        var rule: Int = 0

) : BaseBean()