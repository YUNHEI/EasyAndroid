package com.chen.baseextend.bean

import com.chen.basemodule.network.base.BaseRequest

/**
 *  Created by chen on 2019/6/13
 **/
data class MobileRequest(
        val mobile: String//手机号
) : BaseRequest()