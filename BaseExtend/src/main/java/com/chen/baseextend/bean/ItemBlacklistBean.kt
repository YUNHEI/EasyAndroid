package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean


/**
 *  Created by chen on 2019/6/3
 **/
data class ItemBlacklistBean(
        val avatar: String,
        val id: String,
        val nickname: String,
        val remark: String?,
        val userBlacklistId: String,
        var isRelieve: Boolean = false
) : BaseBean()