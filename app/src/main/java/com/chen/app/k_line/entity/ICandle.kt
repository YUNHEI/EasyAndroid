package com.chen.app.k_line.entity

/**
 * 蜡烛图实体接口
 * Created by tifezh on 2016/6/9.
 */
interface ICandle {
    /**
     * 开盘价
     */
    val openPrice: Float

    /**
     * 最高价
     */
    val highPrice: Float

    /**
     * 最低价
     */
    val lowPrice: Float

    /**
     * 收盘价
     */
    val closePrice: Float

    /**
     * 五(月，日，时，分，5分等)均价
     */
    val mA5Price: Float

    /**
     * 十(月，日，时，分，5分等)均价
     */
    val mA10Price: Float

    /**
     * 二十(月，日，时，分，5分等)均价
     */
    val mA20Price: Float
}