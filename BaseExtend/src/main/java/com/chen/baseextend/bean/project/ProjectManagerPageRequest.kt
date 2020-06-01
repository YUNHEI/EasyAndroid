package com.chen.baseextend.bean.project

import com.chen.basemodule.network.base.BasePageRequest

/**
 *  Created by chen on 2019/8/9
 **/
data class ProjectManagerPageRequest(
        val itemId: String?//任务类型
) : BasePageRequest()