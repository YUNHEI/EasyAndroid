package com.chen.baseextend.bean.comm

import com.chen.basemodule.network.base.BasePageRequest

/**
 * @author alan
 * @date 2018/12/18
 */
data class CommUserRequest(
        var communityId: String,
        var verifyStatus: String,
        var role: Int? = null
) : BasePageRequest()
