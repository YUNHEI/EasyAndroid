package com.chen.baseextend.bean.project

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/8/23
 **/
data class ReplyBean(
        val index: Int? = null,//排序
        val userType: Int? = null,//步骤类型 1.网址 2.二维码 3.数据复制 4.图文说明 5.验证图 6.收集信息 ,
        val replyType: Int? = null,//审核类型 1.图片 2.文字 ,
        val content: String? = null//审核信息，文本或图片url
):BaseBean()