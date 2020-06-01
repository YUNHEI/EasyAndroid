package com.chen.baseextend.bean.admin

import com.chen.basemodule.network.base.BaseRequest

/**
 *  Created by chen on 2019/8/23
 **/
data class VerifyRequest(
        val taskId: String? = null,//任务id
        var status: String? = null,// 1.待审核 2.审核中 3.审核失败 4.拒绝审核 5.上架 6.暂停 7.下架 8.下架验证 9.冻结
        var failMsg: String? = null//驳回理由
):BaseRequest()