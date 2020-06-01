package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

/**
 * @author alan
 * @date 2018/11/13
 */
data class CollectLinkRequest(
        //待收藏链接
        var originalUrl: String? = null,
        //收藏原链接
        var sourceUrl: String? = null
) : BaseRequest()