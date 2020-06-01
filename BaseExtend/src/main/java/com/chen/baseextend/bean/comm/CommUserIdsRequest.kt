package com.chen.baseextend.bean.comm

import com.chen.basemodule.network.base.BaseRequest

/**
 * @author alan
 * @date 2018/12/18
 */
data class CommUserIdsRequest(
        var communityId: String,
        var userIds: String
) : BaseRequest()
