package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 * @author alan
 * @date 2019/1/30
 */
data class HistoryVersionBean(
        val id: String?,
        val appId: String?,
        val createTime: String?,
        val downloadLink: String?,
        var isForceUpdate: Int?,
        var isReview: Int?,
        val platformType: String?,
        val updateLog: String?,
        val version: Int?,
        val forceUpdateVersion: Int?,
        val versionDesc: String,
        val versionType: Int?
) : BaseBean()

