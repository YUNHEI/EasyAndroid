package com.chen.basemodule.event_bus

import com.chen.basemodule.allroot.RootEventBusEvent

open class BaseEventBusEvent(val from: Class<*>) : RootEventBusEvent
