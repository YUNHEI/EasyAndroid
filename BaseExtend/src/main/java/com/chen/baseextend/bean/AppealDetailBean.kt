package com.chen.baseextend.bean

import com.chen.baseextend.bean.project.AppealImageBean
import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/12/6
 **/
data class AppealDetailBean(
        val acceptTaskId: String? = null,
        val accusedUserId: String? = null,
        val appealImage: List<AppealImageBean>? = null,
        val appealUserId: String? = null,
        val createTime: String? = null,
        val id: String? = null,
        val isDelete: Int? = null,
        val msg: String? = null,
        val status: Int? = null,//1.未处理 2.处理中 3.申述成功 4.申述失败
        val taskId: String? = null,
        val title: String? = null,
        val userNickName: String? = null,
        val healImg: String? = null,
        val type: Int? = null,
        val unitprice: Int? = null
) : BaseBean()