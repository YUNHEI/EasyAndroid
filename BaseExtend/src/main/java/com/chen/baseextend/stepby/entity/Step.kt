package com.chen.baseextend.stepby.entity

import com.chen.basemodule.network.base.BaseResponse

/**
 *  Created by 86152 on 2019-10-13
 **/
open class Step<R, B>(
        val process: ((param: Pair<R?, B?>) -> StepInfo<R, B>)? = null,
        var callback: ((response: BaseResponse<StepInfo<R, B>>) -> Unit)? = null,
        var stepName: String? = null,//步骤名称默认为添加顺序
        val handler: ((stepInfo: StepInfo<R, B>?) -> Unit)? = null
)