package com.chen.baseextend.event_bus

import com.chen.basemodule.event_bus.BaseEventBusEvent

/**
 * @author alan
 * @date 2019-07-02
 * 登录状态改变
 */
class RefreshUserInfoEvent(from: Class<*>) : BaseEventBusEvent(from)
