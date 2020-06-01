package com.chen.baseextend.bean.comm

import android.os.Parcelable
import com.chen.basemodule.basem.BaseBean
import kotlinx.android.parcel.Parcelize

/**
 * @author alan
 * @date 2018/12/18
 */
@Parcelize
data class CommInformationBean(
        var id: String,
        var name: String? = null,
        var introduce: String? = null,
        var icon: String? = null,
        var picture: String? = null,
        var email: String? = null,
        var type: Int = 0,
        var number: Int = 0,
        var articleCount: Int = 0,
        var myArticleCount: Int = 0,
        var myTalkCount: Int = 0,

        /**
         * 1-默认社区
         */
        var isDefault: Int = 0,
        /**
         * 1-需要认证
         */
        var isVerify: Int = 0,
        /**
         * 1-是否加入了社区
         */
        var isJoined: Int = 0,

        /**
         * 1- 管理员 0-普通
         */
        var role: Int = 0
) : BaseBean(), Parcelable
