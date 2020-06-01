package com.chen.baseextend.bean.wallet

import com.chen.basemodule.network.base.BaseRequest

data class WithdrawRequest(
        val payType: Int = 101,//操作类型 1.充值 101.微信提现
        val cashoutType: Int,//业务类型 1.充值任务币 2.充值保证金 3.提现任务币 4.提现余额 5.提现保证金,
        val userType: Int = 1//操作类型 1.非会员 2.会员
) :BaseRequest()