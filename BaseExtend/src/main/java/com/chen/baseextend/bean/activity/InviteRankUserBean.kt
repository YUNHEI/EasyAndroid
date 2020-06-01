package com.chen.baseextend.bean.activity

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/12/12
 **/
data class InviteRankUserBean(
        val userId: String? = null,
        val amount: BigDecimal = BigDecimal(0),
        val inviteNum: Int = 0,
        val completeNum: Int = 0,
        val avatar: String? = null,
        val nickname: String? = null
) : BaseBean()