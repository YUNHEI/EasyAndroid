package com.chen.baseextend.bean

import com.chen.basemodule.basem.BaseBean

/**
 * @author alan
 * @date 2019/1/22
 */
data class RecommendInformationBean(val apkQrCode: String,
                                    val apkUrl: String,
                                    val shareWay: Int
) : BaseBean()