package com.chen.baseextend.bean.staff

import java.io.Serializable

/**
 * @author alan
 * @date 2019-06-21
 */
data class VisitorLastInfo(
        val receiver: String ?= null,
        val cardNum: String?= null,
    val cardType: Int,
    val eid: String?= null,
    val endTime: String?= null,
    val id: String?= null,
    val mobile: String?= null,
    val name: String?= null,
    val num: Int,
    val startTime: String?= null,
    val visitCompany: String?= null,
    val visitDate: String?= null,
    val visitPlace: String?= null,
    val visitReason: String?= null,
    val visitTime: String?= null,
    val visitType: Int,
    val visitorsCount: Int,
    /**
     * 0-当前记录  1-以前的记录
     */
    val usable:Int ?= 1
):Serializable