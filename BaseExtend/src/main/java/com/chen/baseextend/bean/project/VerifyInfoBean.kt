package com.chen.baseextend.bean.project

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/9/4
 **/
data class VerifyInfoBean(
        var acceptTaskId: String? = null,
        var avatar: String? = null,
        var nickname: String? = null,
        var taskId: String? = null,
        var title: String? = null,
        var reply: MutableList<ReplyBean>? = null,
        var steps: MutableList<VerifyStepBean>? = null,
        val unitprice: BigDecimal = BigDecimal(0), //单价
        var userId: String? = null,
        var submitTime: String? = null,
        var verifyCount: Int? = null
) : BaseBean()