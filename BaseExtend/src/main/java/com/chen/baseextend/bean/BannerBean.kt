package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/9/5
 **/
data class BannerBean(
        var id: Long = 0,
        var time: String?= "",
        var img: String? = ""
) : BaseBean()