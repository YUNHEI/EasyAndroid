package com.chen.baseextend.bean.notice

import com.chen.basemodule.basem.BaseBean

/**
 * Created by chen on 2018/10/12
 */
class NoticeBean(
        val content: String? = null,
        val createTime: String? = null,
        val id: String? = null,
        val isRead: Int? = null,
        val title: String? = null,
        val typeDesc: String? = null,
        val redirectUrl: String? = null,
        val typeId: Int? = null,
        val userId: String? = null
) : BaseBean()