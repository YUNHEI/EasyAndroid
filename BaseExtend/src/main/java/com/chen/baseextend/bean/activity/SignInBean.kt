package com.chen.baseextend.bean.activity

import java.math.BigDecimal

data class SignInBean(
    val activityId: String? = null,
    val clockDayCount: Int? = null,
    val createTime: String? = null,
    val id: String? = null,
    val redPacket: BigDecimal = BigDecimal(0),
    val userId: String? = null
)