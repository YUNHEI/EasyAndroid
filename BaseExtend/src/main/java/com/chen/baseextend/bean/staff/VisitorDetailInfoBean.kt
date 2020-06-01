package com.chen.baseextend.bean.staff

import com.chen.basemodule.basem.BaseBean
import java.io.Serializable

/**
 * @author alan
 * @date 2019-05-17
 */
data class VisitorDetailInfoBean(
    val receiver: String,
    val cardNum: String,
    val cardType: Int,
    val endTime: String,
    val id: String,
    val mobile: String,
    val name: String,
    val num: Int,
    val startTime: String,
    val visitCompany: String,
    val visitDate: String,
    val visitPlace: String,
    val visitReason: String,
    val visitType: Int,
    val visitorsCount: Int
) : BaseBean() , Serializable