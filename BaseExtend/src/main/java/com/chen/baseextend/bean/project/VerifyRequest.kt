package com.chen.baseextend.bean.project

import com.chen.basemodule.network.base.BaseRequest

/**
 *  Created by chen on 2019/8/23
 **/
data class VerifyRequest(
        val acceptTaskId: String? = null,//接单ID
        val status: String? = null,//审核状态 3通过 4终止 6驳回
        val failMsg: String? = null,//驳回理由
        val failImage: List<String>? = null//审核失败图
):BaseRequest()