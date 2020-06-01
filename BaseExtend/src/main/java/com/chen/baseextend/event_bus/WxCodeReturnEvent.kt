package com.chen.baseextend.event_bus

import com.chen.basemodule.event_bus.BaseEventBusEvent
import com.tencent.mm.opensdk.modelbase.BaseResp

/**
 *  Created by chen on 2019/7/11
 **/
class WxCodeReturnEvent(clazz: Class<*>, val resp: BaseResp) : BaseEventBusEvent(clazz)