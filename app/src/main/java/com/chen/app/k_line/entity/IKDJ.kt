package com.chen.app.k_line.entity

/**
 * KDJ指标(随机指标)接口
 * @see [](https://baike.baidu.com/item/KDJ%E6%8C%87%E6%A0%87/6328421?fr=aladdin&fromid=3423560&fromtitle=kdj)相关说明
 * Created by tifezh on 2016/6/10.
 */
interface IKDJ {
    /**
     * K值
     */
    val k: Float

    /**
     * D值
     */
    val d: Float

    /**
     * J值
     */
    val j: Float
}