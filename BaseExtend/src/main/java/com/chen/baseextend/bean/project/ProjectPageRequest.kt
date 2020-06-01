package com.chen.baseextend.bean.project

import com.chen.basemodule.network.base.BasePageRequest

/**
 *  Created by chen on 2019/8/9
 **/
data class ProjectPageRequest(
        val itemTypeId: String? = null,//任务类型id
        val orderRule: Int = 1,//排序规则不能为空 1.综合 2.价格 3.最近 4.人气
        val searchInfo: String? = null//搜索关键字
) : BasePageRequest()