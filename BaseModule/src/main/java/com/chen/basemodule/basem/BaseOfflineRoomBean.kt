package com.chen.basemodule.basem

/**
 *  Created by chen on 2019/6/5
 **/
open class BaseOfflineRoomBean(
        var requestJson: String? = null,
        var retryTimes: Int = 0,
        var stepName: String? = null,
        var errorInfo: String? = null
) : BaseRoomBean()