package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

class VersionBean(
        val id: String?,
        val appId: String?,
        val createTime: String?,
        val downloadLink: String?,
        val isForceUpdate: Int?,
        val platformType: String?,
        val updateLog: String?,
        val version: Int = 0,
        val isReview: Int = 0,
        val forceUpdateVersion: Int = 0,
        val versionDesc: String,
        val versionType: Int?
) : BaseBean()
