package com.chen.basemodule.event_bus

import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

/**
 *  Created by chen on 2019/9/10
 **/
class BaseRefreshEvent(fromClassName: KClass<*>, vararg target: String, val obj: Any? = null) : BaseLiveBusEvent(fromClassName.jvmName, target) {
}