package com.chen.baseextend.bean.project

import com.chen.basemodule.network.base.BaseRequest

/**
 *  Created by chen on 2019/8/23
 **/
data class AppealRequest(
        val acceptTaskId: String? = null,//接单ID
        val type: Int? = null,//类型 1.申述单 2.投诉单
        val msg: String? = null,//驳回理由
        val image: MutableList<AppealImageBean>? = mutableListOf()//申述图片
) : BaseRequest()