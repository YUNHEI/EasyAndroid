package com.chen.baseextend.bean.wallet

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by chen on 2019/11/12
 **/
data class WithdrawInfoBean(
        val id: Int? = null,
        val cashoutType: Int? = null,//业务类型，3.提现任务币 4.提现余额 5.提现保证金
        val verifyDays: Int = 0,//审核所需工作日
        val dayAmount: Int? = null,//每人每日提现总限额，单位分 ,
        val everyAmount: BigDecimal = BigDecimal(0),//每次提现限额，单位分 ,
        val lowestAmount: BigDecimal = BigDecimal(0),// 每次最低提现金额，单位分 ,
        val outNum: Int? = null,
        val payType: Int? = null,
        val totalAmount: BigDecimal = BigDecimal(0),//提现总限额，单位分
        val serviceCharge: Float?
) : BaseBean()