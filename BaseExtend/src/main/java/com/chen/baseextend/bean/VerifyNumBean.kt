package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

data class VerifyNumBean(
        val successNum: Int = 0,
        val waitVerifyNum: Int = 0
) : BaseBean()