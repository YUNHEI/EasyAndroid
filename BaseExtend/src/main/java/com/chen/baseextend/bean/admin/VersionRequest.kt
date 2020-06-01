package com.chen.baseextend.bean.admin

import com.chen.basemodule.basem.BaseBean

data class VersionRequest(
        val downloadLink: String? = null,
        val updateLog: String? = null,
        val versionDesc: String? = null,
        val isForceUpdate: Int? = null,
        val version: Int? = null,
        val versionType: Int? = null,
        val appID: Int? = 1,
        val channelId: Int? = 1,
        val platformType: Int? = 1
) : BaseBean()