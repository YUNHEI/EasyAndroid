package com.chen.baseextend.bean.notice

import com.chen.basemodule.network.base.BasePageRequest

/**
 *  Created by chen on 2019/5/30
 **/
class NoticePageRequest(
        val typeId: Int//1.任务通知 2.系统通知
) : BasePageRequest()