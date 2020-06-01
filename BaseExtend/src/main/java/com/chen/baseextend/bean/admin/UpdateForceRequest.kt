package com.chen.baseextend.bean.admin

import com.chen.basemodule.basem.BaseBean

data class UpdateForceRequest(
        val id: String? = null,
        val isForceUpdate: Int? = null,
        val platformType: Int? = 1
) : BaseBean()