package com.chen.baseextend.bean.comment

import com.chen.basemodule.network.base.BaseResponse

/**
 *  Created by chen on 2019/6/19
 **/
data class CommentListResponse(
        var total: Int = 0,

        var count: Int = 0
) : BaseResponse<List<CommentBean>>()