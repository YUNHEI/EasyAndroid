package com.chen.baseextend.bean.wallet

import java.math.BigDecimal

/**
 *  Created by chen on 2019/11/12
 **/
data class WithdrawInfo(
        val outNum: Int? = 0,//每日提现次数
        val realAmount: BigDecimal = BigDecimal(0),//到账金额 ,
        val remainOutNum: Int? = 0,//剩余提现次数
        val totalAmount: BigDecimal = BigDecimal(0)//提现金额
)