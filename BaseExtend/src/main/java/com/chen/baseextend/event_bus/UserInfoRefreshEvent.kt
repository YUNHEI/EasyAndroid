package com.chen.baseextend.event_bus

import com.chen.basemodule.event_bus.BaseEventBusEvent

/**
 * @author alan
 * @date 2019-07-02
 * 用户信息改变
 */
class UserInfoRefreshEvent(from: Class<*>) : BaseEventBusEvent(from)
