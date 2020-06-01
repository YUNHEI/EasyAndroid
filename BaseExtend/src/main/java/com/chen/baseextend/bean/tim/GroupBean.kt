package com.chen.baseextend.bean.tim

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/9/12
 **/
data class GroupBean(
        val acceptTaskId: String?= null,
        val acceptUserId: String?= null,
        val id: String?= null,
        val imGroupId: String?= null,
        val publishUserId: String?= null,
        val taskId: String?= null
) : BaseBean()
