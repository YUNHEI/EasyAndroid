package com.chen.baseextend.bean.project

import com.chen.baseextend.bean.VerifyNumBean
import com.chen.basemodule.basem.BaseBean

/**
 *  Created by chen on 2019/9/4
 **/
data class VerifyDetailBean(
        val taskStatus: VerifyNumBean? = null,
        val verifyInfo: VerifyInfoBean? = null
) : BaseBean()