package com.chen.baseextend.bean.mz

import com.chen.basemodule.network.base.BaseRequest

/**
 * @author chen
 * @date 2019-06-13
 */
class BindingRequest(
        val phoneNum: String,
        val code: String,
        val userId: String,
        val inviteCode: String? = null
) : BaseRequest()