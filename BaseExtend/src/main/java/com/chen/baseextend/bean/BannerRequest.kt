package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

data class BannerRequest(
        val bannerType: String
) : BaseRequest()