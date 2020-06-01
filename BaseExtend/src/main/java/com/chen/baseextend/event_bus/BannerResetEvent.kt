package com.chen.baseextend.event_bus

import com.chen.basemodule.event_bus.BaseEventBusEvent

/**
 * @author alan
 * @date 2019-07-02
 * 重置banner位置
 */
class BannerResetEvent(from: Class<*>) : BaseEventBusEvent(from)
