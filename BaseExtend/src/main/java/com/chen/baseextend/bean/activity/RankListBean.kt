package com.chen.baseextend.bean.activity

import com.chen.basemodule.basem.BaseBean

data class RankListBean(
        val date: String? = null,
        val list:MutableList<ProfitRankBean> = mutableListOf()
) : BaseBean()