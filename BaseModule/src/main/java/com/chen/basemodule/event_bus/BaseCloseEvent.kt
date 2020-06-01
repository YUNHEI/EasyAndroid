package com.chen.basemodule.event_bus

/**
 *  Created by chen on 2019/9/10
 **/
class BaseCloseEvent(fromClassName: String, vararg target: String) : BaseLiveBusEvent(fromClassName, target)