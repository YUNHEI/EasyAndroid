package com.chen.baseextend.bean.project

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/8/14
 **/
data class ProjectChargeBean(
        val newCount: BigDecimal = BigDecimal(0),
        val oldCount: BigDecimal = BigDecimal(0)
) : BaseBean()