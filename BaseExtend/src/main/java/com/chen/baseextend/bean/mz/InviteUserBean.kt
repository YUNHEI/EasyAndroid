package com.chen.baseextend.bean.mz

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by 86152 on 2019-12-28
 **/
data class InviteUserBean (
    val userId: String?,
    val avatar: String?,
    val nickname: String?,
    val amount: BigDecimal = BigDecimal(0)
):BaseBean()