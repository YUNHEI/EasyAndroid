package com.chen.baseextend.bean.comment

import com.chen.basemodule.network.base.BasePageRequest

class CommentListRequest(

        var informationId: String? = null,
        var id: String? = null

) : BasePageRequest()
