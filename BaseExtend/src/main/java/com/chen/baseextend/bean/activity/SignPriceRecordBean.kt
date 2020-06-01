package com.chen.baseextend.bean.activity

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

data class SignPriceRecordBean(
    val activityId: String? = null,
    val amount: BigDecimal = BigDecimal(0),
    val avatar: String? = null,
    val createTime: String? = null,
    val id: String? = null,
    val isReceive: Int? = null,
    val nickname: String? = null,
    val prizeNum: Int? = null,
    val prizeType: Int? = null,
    val rankNum: Int? = null,
    val remark: String? = null,
    val userId: String? = null
):BaseBean()