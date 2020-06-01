package com.chen.baseextend.bean.project

import com.chen.basemodule.network.base.BasePageRequest

/**
 *  Created by chen on 2019/8/9
 **/
data class ProjectAcceptPageRequest(
        val status: String? = "0"// ALL(0, "全部") DOING(1, "进行中"), WAIT_VERIFY(2,"待审核"), SUCCESS(3,"审核通过"), FAIL(4,"审核失败"), OUTTIME(5,"超时"), REJECT(6,"驳回"), FINISH (7, "已结束（放弃）");
) : BasePageRequest()