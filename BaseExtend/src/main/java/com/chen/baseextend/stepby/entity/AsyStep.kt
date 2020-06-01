package com.chen.baseextend.stepby.entity

import com.chen.basemodule.network.base.BaseResponse

/**
 *  Created by 86152 on 2019-10-13
 **/
class AsyStep<R, B>(
        val asyProcess: ((req: R?, next: (info: StepInfo<R, B>) -> Unit) -> Unit),
        callback: ((response: BaseResponse<StepInfo<R, B>>) -> Unit)? = null,
        stepName: String? = null,//步骤名称默认为添加顺序
        handler: ((stepInfo: StepInfo<R, B>?) -> Unit)? = null
) : Step<R, B>(null, callback, stepName, handler)