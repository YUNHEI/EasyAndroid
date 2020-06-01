package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BasePageRequest


/**
 * Created by chen on 2018/12/12
 */
data class UserIdListRequest(
        var crtUserId: String?= null,//用户ID

        var bizUserId: String? = null,//业务用户ID

        var communityId: String? = null//社区ID
) : BasePageRequest()
