package com.chen.basemodule.event_bus

import com.chen.basemodule.allroot.RootBean
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

/**
 *  Created by chen on 2019/9/10
 **/
class BaseProgressEvent(fromClassName: KClass<*>, vararg target: String, val obj: ProgressBean) : BaseLiveBusEvent(fromClassName.jvmName, target)

data class ProgressBean(
        val id: String,
        val percent: String,
        val totalSize: Long,
        val currentSize: Long
) : RootBean {
    override var isHideBaseListItem = false
}