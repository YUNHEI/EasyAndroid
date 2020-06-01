package com.chen.baseextend.bean.mz

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/7/12
 **/
data class WalletInfoBean(
        val accountBalance:  BigDecimal = BigDecimal(0),
        val bondBalance:  BigDecimal = BigDecimal(0),
        val bondStatus: Int? = 0,
        val id: String? = null,
        val platformBalance: BigDecimal = BigDecimal(0),
        val shareBonus: BigDecimal = BigDecimal(0),
        val taskProfit: BigDecimal = BigDecimal(0),
        val createTime: String? = null,
        val updateTime: String? = null,
        val userId: String? = null
) : BaseBean()