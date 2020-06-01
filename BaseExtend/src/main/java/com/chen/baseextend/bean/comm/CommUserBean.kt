package com.chen.baseextend.bean.comm

import com.chen.basemodule.basem.BaseBean

/**
 * @author alan
 * @date 2018/12/19
 */
class CommUserBean(
        var id: String,
        var nickname: String,
        var name: String? = null,
        var avatar: String? = null,
        var shareCount: Int = 0
) : BaseBean()
