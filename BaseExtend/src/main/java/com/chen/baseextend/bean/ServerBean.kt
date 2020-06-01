package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/12/10
 **/
data class ServerBean(
    val customerUserId: String? = null,
    val customerUserName: String? = null,
    val id: String? = null
):BaseBean()