package com.chen.app.k_line.entity

/**
 * RSI指标接口
 * @see [](https://baike.baidu.com/item/RSI%E6%8C%87%E6%A0%87)相关说明
 * Created by tifezh on 2016/6/10.
 */
interface IRSI {
    /**
     * RSI1值
     */
    val rsi1: Float

    /**
     * RSI2值
     */
    val rsi2: Float

    /**
     * RSI3值
     */
    val rsi3: Float
}