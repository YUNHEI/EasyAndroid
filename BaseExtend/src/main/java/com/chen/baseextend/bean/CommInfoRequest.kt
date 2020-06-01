package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

/**
 * @author alan
 * @date 2018/12/13
 */
data class CommInfoRequest(
        val communityId: String,
        /*社区图标*/
        val icon: String? = null,
        /*社区简介*/
        val remark: String? = null
) : BaseRequest()
