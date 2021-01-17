package com.chen.baseextend.base

import com.chen.basemodule.basem.BaseBean

data class MultiSourceGroupBean(

    val title:String = "",

    val items: MutableList<BaseBean> = mutableListOf(),

    val des:String = ""
) : BaseBean()