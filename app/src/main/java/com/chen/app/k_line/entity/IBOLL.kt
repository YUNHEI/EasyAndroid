package com.chen.app.k_line.entity

/**
 * 布林线指标接口
 * @see [](https://baike.baidu.com/item/%E5%B8%83%E6%9E%97%E7%BA%BF%E6%8C%87%E6%A0%87/3325894)相关说明
 * Created by tifezh on 2016/6/10.
 */
interface IBOLL {
    /**
     * 上轨线
     */
    val up: Float

    /**
     * 中轨线
     */
    val mb: Float

    /**
     * 下轨线
     */
    val dn: Float
}