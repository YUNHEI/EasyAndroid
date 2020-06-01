package com.chen.baseextend.bean.admin

import com.chen.basemodule.network.base.BasePageRequest

/**
 *  Created by chen on 2019/8/9
 **/
data class ProjectPageRequest(
        val searchInfo: String? = null,//搜索类型
        val status: Int? = null//状态 null 全部 1.待审核 2.审核中 3.审核失败 4.拒绝审核 5.上架 6.暂停 7.下架 8.下架验证 9.冻结
) : BasePageRequest()