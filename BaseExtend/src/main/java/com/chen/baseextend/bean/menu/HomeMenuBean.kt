package com.chen.baseextend.bean.menu

import com.chen.basemodule.basem.BaseBean

data class HomeMenuBean(
    var cmd: String? = null,//跳转指令
    val name: String? = null,//标题
    val image: String? = null,//图标
    val icon: Int? = null,//本地图标
    val params: MutableMap<String, Any> = mutableMapOf()//参数
) : BaseBean()