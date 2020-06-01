package com.chen.baseextend.bean.comm

import com.chen.basemodule.network.base.BasePageRequest

/**
 *  Created by chen on 2019/6/26
 **/
data class CommUserShareRequest(

        var crtUserId: String,

        var communityId: String,

        var typeQuery: String
) : BasePageRequest()