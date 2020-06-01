package com.chen.baseextend.bean.project

import com.chen.basemodule.network.base.BaseRequest
import java.math.BigDecimal

/**
 *  Created by chen on 2019/8/23
 **/
data class ProjectManageRequest(
        var operationType: Int? = null,//1.暂停 2.刷新 3.置顶 4.追加 5.上调 6.下架 7.推荐  8.审核
        var taskId: String? = null,//任务id
        var price: BigDecimal = BigDecimal(0),//调整价格
        var num: Int? = null,//任务数量
        var addHour: Int? = null//任务时长
) : BaseRequest()