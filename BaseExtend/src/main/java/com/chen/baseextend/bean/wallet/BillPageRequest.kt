package com.chen.baseextend.bean.wallet

import com.chen.basemodule.network.base.BasePageRequest

data class BillPageRequest(
        val billType: String?= null,//订单类型
        val billOperaType: String?= null,//操作类型
        val beginTime: Long?= null,//开始时间
        val endTime: Long?= null,//结束时间
        val orderName: String?= null,//排序名称
        val orderRule: String?= null//排序名称
) :BasePageRequest()