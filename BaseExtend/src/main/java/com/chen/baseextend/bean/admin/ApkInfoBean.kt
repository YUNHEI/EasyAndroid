package com.chen.baseextend.bean.admin

import com.chen.basemodule.basem.BaseBean

data class ApkInfoBean(
        val name: String? = null,
        val versionName: String? = null,
        val downloadLink: String? = null,
        var localPath: String? = null,
        var fileName: String? = null,
        var log: String? = null,
        var isForceUpdate: Int? = 0,
        var versionType: Int? = null,
        val versionCode: Int = 0
) : BaseBean()