package com.chen.baseextend.bean.project

import com.chen.basemodule.network.base.BaseRequest
import java.math.BigDecimal

/**
 *  Created by chen on 2019/8/9
 **/
data class ProjectAddRequest(
        val id: String? = null,//修改项目时传id
        val offlineId: String? = null,//离线id
        val itemDesc: String? = null,//项目名称
        val title: String? = null,//标题
        val typeId: String? = null,//任务类型id
        val typeDesc: String? = null,//任务类型描述
        val supportDevice: Int = 1,//支持设备类型 1.全部 2.安卓 3.苹果
        val description: String? = null,//任务说明
        val finishTime: Int? = null,//接单完成时间，单位h
        val verifyTime: Int? = null,//审核时间,单位h
        val acceptCount: Int? = null,//做单次数 1.每人一次 2.每人三次 3.每日一次
        val unitprice: BigDecimal = BigDecimal(0),//单价
        val taskNum: Int = 0,//任务数量
        val step: MutableList<ProjectStepBean>? = null//任务步骤
) : BaseRequest()