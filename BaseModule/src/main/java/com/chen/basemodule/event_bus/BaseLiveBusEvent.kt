package com.chen.basemodule.event_bus

import com.chen.basemodule.allroot.RootEventBusEvent

/**
 *  Created by chen on 2019/6/3
 **/
open class BaseLiveBusEvent(val fromClassName: String?, val target: Array<out String>) : RootEventBusEvent