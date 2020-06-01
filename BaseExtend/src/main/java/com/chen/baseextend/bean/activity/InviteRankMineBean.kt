package com.chen.baseextend.bean.activity

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/12/12
 **/
data class InviteRankMineBean(
        val userId: String? = null,
        val top: Int = 0,
        val avatar: String? = null,
        val nickname: String? = null
) : BaseBean()