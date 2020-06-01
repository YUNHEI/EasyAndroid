package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/8/13
 **/
data class MessageBean(
        val id: String? = null,
        val avatar: String? = null,
        val nickname: String? = null,
        val message: String? = null,
        val createTime: String? = null
):BaseBean()