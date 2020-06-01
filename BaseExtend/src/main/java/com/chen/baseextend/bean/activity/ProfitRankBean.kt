package com.chen.baseextend.bean.activity

import com.chen.basemodule.basem.BaseBean
import java.math.BigDecimal

/**
 *  Created by 86152 on 2020-03-18
 **/
data class ProfitRankBean(
        val activityId: String?,
        val amount: BigDecimal = BigDecimal(0),
        val avatar: String?,
        val createTime: String?,
        val id: String?,
        val isReceive: Int?,
        val nickname: String?,
        val prizeNum: Int?,
        val prizeType: Int?,
        val rankNum: Int?,
        val remark: String?,
        val userId: String?,
        val winTime: String?
) : BaseBean()