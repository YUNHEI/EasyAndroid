package com.chen.baseextend.extend

import android.content.Context
import com.chen.baseextend.util.ReLoginUtil
import com.chen.basemodule.network.base.BaseResponse


/**
 *  Created by chen on 2019/6/10
 **/
val commonFailInterrupt: ((context: Context, response: BaseResponse<*>) -> Boolean) = { c, it ->

    if (it.status == 401 || it.status == 40101) {
        ReLoginUtil.reLogin(c)
    }
    false

}