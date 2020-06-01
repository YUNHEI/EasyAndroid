package com.chen.baseextend.bean.mz

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/8/13
 **/
data class BillBean(
        val id: String? = null,
        val title: String? = null,
        val status: String? = null,
        val billType: Int = 0,//1 账户余额账单， 2发单笔账单， 3保证金账单
        val money: BigDecimal = BigDecimal(0),
        val remainAmount: BigDecimal = BigDecimal(0),
        val operationType: Int = 0,
        val operationTypeDesc: String? = null,
        val billStatus: Int = 0,
        val billStatusDesc: String? = null,
        val bizType: String? = null,
        val createTime: String? = null
):BaseBean()