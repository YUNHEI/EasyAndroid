package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by 86152 on 2019-12-02
 **/
data class UserShopInfoBean(
        val acceptComplainNums: Int = 0,
        val acceptTaskNums: Int = 0,
        val appealNums: Int = 0,
        val bondAmount: BigDecimal = BigDecimal(0),
        val completeNums: Int = 0,
        val publishComplainNums: Int = 0,
        val publishTaskNums: Int = 0,
        val headImg: String?,
        val userName: String?
) : BaseBean()