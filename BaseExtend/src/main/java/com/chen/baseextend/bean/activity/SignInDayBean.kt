package com.chen.baseextend.bean.activity

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

data class SignInDayBean(
        val amount :BigDecimal = BigDecimal(0),
        val prizeNum :Int = 0,
        val prizeType :Int = 0,
        val clockDay :Int?= null
):BaseBean()