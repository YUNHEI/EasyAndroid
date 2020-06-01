package com.chen.baseextend.bean.project

import com.chen.basemodule.network.base.BaseRequest
import java.math.BigDecimal

/**
 *  Created by chen on 2019/8/23
 **/
data class ProjectChargeRequest(
        val itemId: String? = null,//任务id
        val num: String? = null,//任务数量
        val unitprice: BigDecimal? = null//任务单价
):BaseRequest()