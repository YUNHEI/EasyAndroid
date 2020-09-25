package com.chen.app.k_line.entity

import java.util.*

/**
 * 分时图实体接口
 * Created by tifezh on 2017/7/19.
 */
interface IMinuteLine {
    /**
     * @return 获取均价
     */
    val avgPrice: Float

    /**
     * @return 获取成交价
     */
    val price: Float

    /**
     * 该指标对应的时间
     */
    val date: Date?

    /**
     * 成交量
     */
    val volume: Float
}