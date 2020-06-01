package com.chen.baseextend.bean.system

import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/8/16
 **/
data class WxPayInfo(
        val appid: String,
        val noncestr: String,
        val orderId: String,
        val packageStr: String,
        val partnerid: String,
        val prepayid: String,
        val sign: String,
        val status: Int,
        val timestamp: String
) : BaseBean()