package com.chen.baseextend.bean.project

import com.chen.basemodule.network.base.BaseRequest

/**
 *  Created by chen on 2019/8/23
 **/
data class ReplyRequest(
        val acceptTaskId: String? = null,//接单ID
        val message: String? = null,//回复理由
        val replyType:String = "1"
):BaseRequest()