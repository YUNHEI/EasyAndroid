package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

/**
 *  Created by chen on 2019/9/12
 **/
data class UserInfoUpdateRequest(
        val avatar :String?= null,
        val nickname :String?= null
):BaseRequest()