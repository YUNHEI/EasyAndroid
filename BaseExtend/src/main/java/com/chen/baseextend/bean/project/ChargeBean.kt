package com.chen.baseextend.bean.project

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/8/14
 **/
data class ChargeBean(
        val id: Int,
        val itemId: Int,
        val minNum: Int,
        val minUnitprice: BigDecimal = BigDecimal(0),
        val userType: Int,
        val serviceChargeProportion: Float = 0f,
        val itemTypeDesc: String? = null,
        val itemDesc: String? = null,
        val itemTypeId: Int? = null
) : BaseBean()