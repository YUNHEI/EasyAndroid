package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 * 唐诗
 *  Created by chen on 2019/9/5
 **/
data class PoetryBean(
        var title: String?= "",
        var content: String?= "",
        var authors: String? = ""
) : BaseBean()