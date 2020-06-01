package com.chen.baseextend.stepby.entity

/**
 *  Created by 86152 on 2019-10-13
 **/
data class StepInfo<R, B>(
        var reqBean: R? = null,
        var dataBean: B? = null,
        val interrupt: InterruptStatus? = null,//主动中断流程
        var nextStepName: String? = null,//空字符表示从第一步开始  const val END = "#END#" 代表流程结束
        val handlerName: String? = null/*不影响流程*/) {

    constructor(params: Pair<R?, B?>, interrupt: InterruptStatus? = null, nextStepName: String? = null, handlerName: String? = null)
            : this(params.first, params.second, interrupt, nextStepName, handlerName)
}