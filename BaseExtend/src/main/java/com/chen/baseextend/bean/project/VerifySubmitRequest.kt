package com.chen.baseextend.bean.project

import com.chen.basemodule.network.base.BaseRequest

/**
 *  Created by chen on 2019/8/23
 **/
data class VerifySubmitRequest(
        val taskId: String? = null,//任务id
        val list: List<VerifyStepBean>? = mutableListOf()//审核信息列表
):BaseRequest()