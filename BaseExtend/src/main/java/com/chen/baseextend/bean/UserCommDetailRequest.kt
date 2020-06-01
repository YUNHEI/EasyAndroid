package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

/**
 * @author alan
 * @date 2018/12/13
 */
data class UserCommDetailRequest(
        val id: String? = null,
        val communityId: String? = null
) : BaseRequest()
