package com.chen.baseextend.bean.activity

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/12/12
 **/
data class InviteDetailBean(
    val todayInvitedUserNum: Int = 0,
    val todayProfit: BigDecimal = BigDecimal(0),
    val totalInvitedUserNum: Int = 0,
    val totalProfit: BigDecimal = BigDecimal(0)
):BaseBean()